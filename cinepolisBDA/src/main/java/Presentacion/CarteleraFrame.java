/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import dtos.PeliculaDTO;
import dtos.SucursalDTO;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import persistencia.ConexionBD;
import persistencia.IConexionBD;
import persistencia.PeliculaDAO;

/**
 *
 * @author SDavidLedesma
 */
public class CarteleraFrame extends JFrame {

    private JComboBox<SucursalDTO> sucursalComboBox;
    private JPanel carteleraPanel;
    private PeliculaDAO peliculaDAO;

    public CarteleraFrame(PeliculaDAO peliculaDAO, List<SucursalDTO> sucursales) {
        this.peliculaDAO = peliculaDAO;
        setTitle("Cartelera de Películas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear ComboBox para sucursales
        sucursalComboBox = new JComboBox<>();
        for (SucursalDTO sucursal : sucursales) {
            sucursalComboBox.addItem(sucursal);
        }
        sucursalComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarPeliculasPorSucursal();
            }
        });

        add(sucursalComboBox, BorderLayout.NORTH);

        // Panel para mostrar cartelera
        carteleraPanel = new JPanel();
        carteleraPanel.setLayout(new GridLayout(2, 3)); // 2 filas, 3 columnas
        add(new JScrollPane(carteleraPanel), BorderLayout.CENTER);

        cargarPeliculasPorSucursal(); // Cargar las películas al iniciar
    }

    private void cargarPeliculasPorSucursal() {
        SucursalDTO sucursalSeleccionada = (SucursalDTO) sucursalComboBox.getSelectedItem();
        if (sucursalSeleccionada != null) {
            try {
                List<PeliculaDTO> peliculas = peliculaDAO.obtenerPeliculasPorSucursal(sucursalSeleccionada.getIdSucursal());
                mostrarPeliculas(peliculas);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void mostrarPeliculas(List<PeliculaDTO> peliculas) {
        carteleraPanel.removeAll(); // Limpiar panel

        for (PeliculaDTO pelicula : peliculas) {
            JPanel panelPelicula = new JPanel();
            panelPelicula.setLayout(new BoxLayout(panelPelicula, BoxLayout.Y_AXIS));

            // Cargar imagen desde la base de datos
            ImageIcon imagenPelicula = new ImageIcon(pelicula.getImagen()); // Ruta de la imagen
            JLabel lblImagen = new JLabel(new ImageIcon(imagenPelicula.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH)));
            JLabel lblTitulo = new JLabel(pelicula.getTitulo());
            JLabel lblSinopsis = new JLabel("<html>" + pelicula.getSinopsis() + "</html>");

            panelPelicula.add(lblImagen);
            panelPelicula.add(lblTitulo);
            panelPelicula.add(lblSinopsis);

            carteleraPanel.add(panelPelicula);
        }

        carteleraPanel.revalidate();
        carteleraPanel.repaint();
    }

}
