package Emisor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class Emisor extends JFrame {

  

    String operacion;

    JPanel  panelSocket;
    JTextField ip, puerto, usr;
    JLabel letrero;

    boolean nuevaOperacion = true;

    public Emisor() {
        super();
        setSize(600, 400);
        setTitle("Emisor");
        setBackground(Color.black);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);

        JPanel panel = (JPanel) this.getContentPane();
        panel.setLayout(new BorderLayout());
        this.getContentPane().setBackground(Color.red);

        ip = new JTextField("ip:", 20);
        ip.setBorder(new EmptyBorder(4, 4, 4, 4));
        ip.setFont(new Font("Arial", Font.BOLD, 25));
        ip.setHorizontalAlignment(JTextField.LEFT);
        ip.setEditable(true);
        ip.setBackground(Color.WHITE);
        panel.add("North", ip);
        
        letrero = new JLabel("Ejemplo");
        panel.add(letrero);

        puerto = new JTextField("Puerto:", 20);
        puerto.setBorder(new EmptyBorder(4, 4, 4, 4));
        puerto.setFont(new Font("Arial", Font.BOLD, 25));
        puerto.setHorizontalAlignment(JTextField.LEFT);
        puerto.setEditable(true);
        puerto.setBackground(Color.WHITE);
        panel.add("Center", puerto);

        usr = new JTextField("Usuario:", 20);
        usr.setBorder(new EmptyBorder(4, 4, 4, 4));
        usr.setFont(new Font("Arial", Font.BOLD, 25));
        usr.setHorizontalAlignment(JTextField.LEFT);
        usr.setEditable(true);
        usr.setBackground(Color.WHITE);
        panel.add("South", usr);

        panelSocket = new JPanel();
        panelSocket.setLayout(new GridLayout(6, 1));
        panelSocket.setBorder(new EmptyBorder(4, 4, 4, 4));
        nuevoBotonOperacion("conectar");

        panel.add("East", panelSocket);
        validate();
    }

    private void nuevoBotonOperacion(String operacion) {
        JButton boton = new JButton(operacion);
        boton.setForeground(Color.RED);

        boton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent evt) {
                JButton btn = (JButton) evt.getSource();
                operacionPulsado(btn.getText());
            }
        });

        panelSocket.add(boton);
    }

    private void operacionPulsado(String tecla) {
        if (tecla.equals("conectar")) {
            AceptarConexion();
        }

        nuevaOperacion = true;
    }

    private void AceptarConexion() {
        if (operacion.equals("conectar")) {
            try {
                Socket sk = new Socket(ip.getText(), Integer.parseInt(puerto.getText()));
                BufferedReader entrada = new BufferedReader(
                        new InputStreamReader(sk.getInputStream()));
                PrintWriter salida = new PrintWriter(
                        new OutputStreamWriter(sk.getOutputStream()), true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "error");
            }
        }
    }
}
