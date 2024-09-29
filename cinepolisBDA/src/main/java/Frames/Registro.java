package Frames;

import javax.swing.*;
import java.awt.*;
import com.github.lgooddatepicker.components.DatePicker;
import java.sql.*;

public class Registro extends JFrame {
    private JTextField txtCiudad, txtNombre, txtApellidoPaterno, txtApellidoMaterno, txtCorreo;
    private JPasswordField txtContraseña;
    private DatePicker datePickerNacimiento;

    public Registro() {
        setTitle("Registro de Usuario");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(135, 206, 235)); // Azul cielo

        // Componentes
        JLabel lblCiudad = new JLabel("Ciudad:");
        txtCiudad = new JTextField(20);
        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField(20);
        JLabel lblApellidoPaterno = new JLabel("Apellido Paterno:");
        txtApellidoPaterno = new JTextField(20);
        JLabel lblApellidoMaterno = new JLabel("Apellido Materno:");
        txtApellidoMaterno = new JTextField(20);
        JLabel lblCorreo = new JLabel("Correo:");
        txtCorreo = new JTextField(20);
        JLabel lblContraseña = new JLabel("Contraseña:");
        txtContraseña = new JPasswordField(20);
        JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento:");
        datePickerNacimiento = new DatePicker();

        JButton btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.setBackground(Color.BLACK);
        btnRegistrarse.setForeground(Color.WHITE);
        btnRegistrarse.setFont(new Font("Tahoma", Font.BOLD, 12));

        // Layout
        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.setBackground(new Color(135, 206, 235)); // Mismo fondo azul
        panel.add(lblCiudad);
        panel.add(txtCiudad);
        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(lblApellidoPaterno);
        panel.add(txtApellidoPaterno);
        panel.add(lblApellidoMaterno);
        panel.add(txtApellidoMaterno);
        panel.add(lblCorreo);
        panel.add(txtCorreo);
        panel.add(lblContraseña);
        panel.add(txtContraseña);
        panel.add(lblFechaNacimiento);
        panel.add(datePickerNacimiento);

        add(panel, BorderLayout.CENTER);
        add(btnRegistrarse, BorderLayout.SOUTH);

        // Acción del botón de registro
        btnRegistrarse.addActionListener(e -> registrarUsuario());
    }

    private void registrarUsuario() {
        String ciudad = txtCiudad.getText();
        String nombre = txtNombre.getText();
        String apellidoPaterno = txtApellidoPaterno.getText();
        String apellidoMaterno = txtApellidoMaterno.getText();
        String correo = txtCorreo.getText();
        String contraseña = new String(txtContraseña.getPassword());
        String fechaNacimiento = datePickerNacimiento.getText();

        // Conexión a la base de datos y registro
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdacinepolis", "usuario", "contraseña")) {
            String query = "INSERT INTO Clientes (nombres, apellidoPaterno, apellidoMaterno, correoElectrónico, fechaNacimiento, idCiudad, contraseña) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, nombre);
            stmt.setString(2, apellidoPaterno);
            stmt.setString(3, apellidoMaterno);
            stmt.setString(4, correo);
            stmt.setString(5, fechaNacimiento);
            stmt.setInt(6, 1); // Cambia este valor según el idCiudad real
            stmt.setString(7, contraseña);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registro exitoso.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
