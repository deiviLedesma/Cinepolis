package Frames;

import entidad.PeliculaEntidad;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import persistencia.ConexionBD;
import persistencia.IConexionBD;

public class Pago extends JFrame {
    
    private JTextField txtCantidadAsientos;
    private JLabel lblPrecioTotal;
    private int precioPorAsiento = 65;
    private int asientosDisponibles = 40; // Suponemos que es un valor fijo para todas las salas
    private String correoUsuario = "ejemplo@correo.com"; // Asume que tienes los datos del usuario en sesión
    private String nombreUsuario = "Usuario Ejemplo";
    
    public Pago(PeliculaEntidad pelicula) {
        setTitle("Pago de Boletos");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(135, 206, 235)); // Azul cielo

        
        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10)); // GridLayout de 4x2 con separación

        // Label y TextField para cantidad de asientos
        JLabel lblCantidadAsientos = new JLabel("Cantidad de Asientos:");
        txtCantidadAsientos = new JTextField();
        lblPrecioTotal = new JLabel("Precio Total: $0");
        
        // Escuchar cambios en el campo de texto para actualizar el precio
        txtCantidadAsientos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarPrecio();
            }
        });

        // Botones de pago
        JButton btnEfectivo = new JButton("Efectivo");
        JButton btnTarjeta = new JButton("Tarjeta Débito/Crédito");

        // Estilo de los botones
        setButtonStyle(btnEfectivo);
        setButtonStyle(btnTarjeta);

        // Acción del botón "Efectivo"
        btnEfectivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarCompra("Efectivo");
            }
        });

        // Acción del botón "Tarjeta Débito/Crédito"
        btnTarjeta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarCompra("Tarjeta Débito/Crédito");
            }
        });

        // Agregar componentes al panel
        panel.add(lblCantidadAsientos);
        panel.add(txtCantidadAsientos);
        panel.add(lblPrecioTotal);
        panel.add(new JLabel()); // Espacio en blanco
        panel.add(btnEfectivo);
        panel.add(btnTarjeta);

        add(panel);
    }

    private void setButtonStyle(JButton button) {
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Tahoma", Font.BOLD, 12));
    }

    private void actualizarPrecio() {
        try {
            int cantidadAsientos = Integer.parseInt(txtCantidadAsientos.getText());
            if (cantidadAsientos <= asientosDisponibles && cantidadAsientos > 0) {
                int precioTotal = cantidadAsientos * precioPorAsiento;
                lblPrecioTotal.setText("Precio Total: $" + precioTotal);
            } else {
                JOptionPane.showMessageDialog(this, "Cantidad de asientos no válida. Máximo: " + asientosDisponibles);
            }
        } catch (NumberFormatException e) {
            lblPrecioTotal.setText("Precio Total: $0");
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un número válido.");
        }
    }

    private void procesarCompra(String metodoPago) {
        try {
            int cantidadAsientos = Integer.parseInt(txtCantidadAsientos.getText());
            if (cantidadAsientos <= asientosDisponibles && cantidadAsientos > 0) {
                int precioTotal = cantidadAsientos * precioPorAsiento;

                // Generar código único de compra
                String codigoCompra = generarCodigoUnico();

                // Insertar la compra en la base de datos
                guardarCompraEnBaseDatos(cantidadAsientos, precioTotal, metodoPago, codigoCompra);

                // Mostrar resumen de compra
                mostrarResumenCompra(cantidadAsientos, precioTotal, codigoCompra);

            } else {
                JOptionPane.showMessageDialog(this, "Cantidad de asientos no válida. Máximo: " + asientosDisponibles);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingresa un número válido.");
        }
    }

    private void guardarCompraEnBaseDatos(int cantidadAsientos, int precioTotal, String metodoPago, String codigoCompra) {
        IConexionBD conexionBD = new ConexionBD();
        
        String sql = "INSERT INTO compra (correo, nombre, cantidad_asientos, precio_total, metodo_pago, codigo_compra, fecha_compra) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correoUsuario);
            stmt.setString(2, nombreUsuario);
            stmt.setInt(3, cantidadAsientos);
            stmt.setInt(4, precioTotal);
            stmt.setString(5, metodoPago);
            stmt.setString(6, codigoCompra);
            stmt.setString(7, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Compra registrada exitosamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la compra: " + e.getMessage());
        }
    }

    private void mostrarResumenCompra(int cantidadAsientos, int precioTotal, String codigoCompra) {
        String mensaje = String.format("Resumen de Compra:\n\nNombre: %s\nCorreo: %s\nCantidad de Asientos: %d\nPrecio Total: $%d\nCódigo de Compra: %s\nFecha: %s",
                nombreUsuario, correoUsuario, cantidadAsientos, precioTotal, codigoCompra, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        JOptionPane.showMessageDialog(this, mensaje);
    }

    private String generarCodigoUnico() {
        Random random = new Random();
        int codigo = random.nextInt(999999);
        return String.format("%06d", codigo); // Código de 6 dígitos
    }

    
}
