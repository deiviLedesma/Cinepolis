package Frames;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class InicioSesion extends JFrame {
    private JTextField txtCorreo;
    private JPasswordField txtContraseña;

    public InicioSesion() {
        setTitle("Iniciar Sesión");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(135, 206, 235)); // Azul cielo

        // Componentes
        JLabel lblCorreo = new JLabel("Correo:");
        txtCorreo = new JTextField(20);
        JLabel lblContraseña = new JLabel("Contraseña:");
        txtContraseña = new JPasswordField(20);

        JButton btnIniciar = new JButton("Iniciar Sesión");
        btnIniciar.setBackground(Color.BLACK);
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFont(new Font("Tahoma", Font.BOLD, 12));

        // Layout
        setLayout(new GridLayout(3, 2));
        add(lblCorreo);
        add(txtCorreo);
        add(lblContraseña);
        add(txtContraseña);
        add(new JLabel()); // Espacio vacío
        add(btnIniciar);

        // Acción del botón
        btnIniciar.addActionListener(e -> verificarCredenciales());
    }

    private void verificarCredenciales() {
        String correo = txtCorreo.getText();
        String contraseña = new String(txtContraseña.getPassword());

        // Conexión a la base de datos y verificación
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdacinepolis", "usuario", "contraseña")) {
            String query = "SELECT * FROM Clientes WHERE correoElectrónico = ? AND contraseña = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, correo);
            stmt.setString(2, contraseña);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.");
                // Aquí rediriges a la siguiente ventana de la aplicación
            } else {
                JOptionPane.showMessageDialog(this, "Correo o contraseña incorrectos.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
