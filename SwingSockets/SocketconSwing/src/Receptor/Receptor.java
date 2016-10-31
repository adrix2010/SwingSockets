package Receptor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class Receptor extends JFrame {

    Vector<String> datosRecibidos = new Vector<String>();

    JTextField panel1, panel2, panel3;

    JButton boton1;

    String operacion;

    JPanel pantallaSocket;

    boolean nuevaConexion = true;

    public Receptor() {

        setSize(600, 400);
        setTitle("Swing's Socket");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);

        JPanel panel = (JPanel) this.getContentPane();
        panel.setLayout(new BorderLayout());

        panel1 = new JTextField("Servidor listo", 20);
        panel1.setBorder(new EmptyBorder(4, 4, 4, 4));
        panel.add("North", panel1);
        panel1.setBackground(Color.CYAN);
        panel1.setFont(new Font("Serif", Font.BOLD, 25));
        panel1.setHorizontalAlignment(JTextField.CENTER);
        panel1.setEditable(false);
        

        panel2 = new JTextField("ESPERANDO CONEXION....", 20);
        panel2.setBorder(new EmptyBorder(4, 4, 4, 4));
         panel2.setBackground(Color.CYAN);
        
        panel2.setFont(new Font("Serif", Font.ITALIC, 25));
        panel2.setHorizontalAlignment(JTextField.LEFT);
        panel2.setEditable(false);
        
        panel.add("South", panel2);

        pantallaSocket = new JPanel();
        pantallaSocket.setLayout(new GridLayout(6, 1));
        pantallaSocket.setBorder(new EmptyBorder(4, 4, 4, 4));
        nuevaConexion("AceptarConexion");

        panel.add("East", pantallaSocket);
        validate();
    }

    private void nuevaConexion(String operacion) {
        JButton btn = new JButton(operacion);
        btn.setForeground(Color.RED);

        btn.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent evt) {
                JButton boton = (JButton) evt.getSource();
                conexionIniciada(boton.getText());
            }
        });

        pantallaSocket.add(btn);
    }

    private void conexionIniciada(String tecla) {
        if (tecla.equals("AceptarConexion")) {
            ConectarSocket();
        }

        nuevaConexion = true;
    }

    private void ConectarSocket() {
        if (operacion.equals("AceptarConexion")) {
            try {
                ServerSocket s = new ServerSocket(1234);

                while (true) {
                    Socket cliente = s.accept();
                    BufferedReader entrada = new BufferedReader(
                            new InputStreamReader(cliente.getInputStream()));
                    PrintWriter salida = new PrintWriter(
                            new OutputStreamWriter(cliente.getOutputStream()), true);
                    String datos = entrada.readLine();
                    if (datos.equals("DATOS")) {
                        for (int n = 0; n < datosRecibidos.size(); n++) {
                            salida.println(datosRecibidos.get(n));
                        }
                    } else {
                        datosRecibidos.add(0, datos);
                        panel2.setText("Conexion Aceptada");
                    }
                    cliente.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

}
