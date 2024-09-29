package Frames;

import Frames.Registro;
import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        setTitle("Cinépolis - Menú Principal");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(135, 206, 235)); // Azul cielo

        // Logo
        JLabel logo = new JLabel("CINÉPOLIS", SwingConstants.CENTER);
        logo.setFont(new Font("Tahoma", Font.BOLD, 24));
        logo.setForeground(Color.WHITE);

        // Botones
        JButton btnIniciarSesion = new JButton("Iniciar Sesión");
        JButton btnRegistrarse = new JButton("Registrarme");
        
        btnIniciarSesion.setBackground(Color.BLACK);
        btnIniciarSesion.setForeground(Color.WHITE);
        btnIniciarSesion.setFont(new Font("Tahoma", Font.BOLD, 12));

        btnRegistrarse.setBackground(Color.BLACK);
        btnRegistrarse.setForeground(Color.WHITE);
        btnRegistrarse.setFont(new Font("Tahoma", Font.BOLD, 12));

        // Acciones de botones
        btnIniciarSesion.addActionListener(e -> {
            InicioSesion iniciarSesionFrame = new InicioSesion();
            iniciarSesionFrame.setVisible(true);
            dispose(); // Cierra el menú principal
        });

        btnRegistrarse.addActionListener(e -> {
            Registro registroFrame = new Registro();
            registroFrame.setVisible(true);
            dispose(); // Cierra el menú principal
        });

        // Layout
        setLayout(new BorderLayout());
        add(logo, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnIniciarSesion);
        panelBotones.add(btnRegistrarse);
        panelBotones.setBackground(new Color(135, 206, 235)); // Mismo fondo azul
        
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}
