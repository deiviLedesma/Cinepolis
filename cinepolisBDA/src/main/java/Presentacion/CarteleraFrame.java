package Presentacion;

import dtos.PeliculaDTO;
import persistencia.PeliculaDAO;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class CarteleraFrame extends JFrame {

    private JComboBox<String> sucursalComboBox;
    private JPanel peliculasPanel;

    public CarteleraFrame() {
        // Configuración del JFrame
        setTitle("Cartelera de Películas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior con JComboBox para seleccionar sucursal
        JPanel sucursalPanel = new JPanel();
        sucursalComboBox = new JComboBox<>(new String[]{"Sucursal 1", "Sucursal 2", "Sucursal 3"});  // Puedes cargar estas sucursales dinámicamente si lo deseas
        sucursalComboBox.addActionListener(e -> cargarPeliculas());  // Al cambiar la sucursal, se cargan las películas
        sucursalPanel.add(new JLabel("Selecciona la sucursal:"));
        sucursalPanel.add(sucursalComboBox);

        // Panel donde se mostrará la cartelera de películas
        peliculasPanel = new JPanel();
        peliculasPanel.setLayout(new GridLayout(2, 3));  // Muestra entre 3 y 6 películas
        JScrollPane scrollPane = new JScrollPane(peliculasPanel);

        // Agregar los paneles al JFrame
        add(sucursalPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Método para cargar las películas según la sucursal seleccionada
    private void cargarPeliculas() {
        peliculasPanel.removeAll();  // Limpiar el panel de películas
        String sucursalSeleccionada = (String) sucursalComboBox.getSelectedItem();
        int idSucursal = obtenerIdSucursal(sucursalSeleccionada);  // Método ficticio que obtiene el ID de la sucursal
        try {
            PeliculaDAO peliculaDAO = new PeliculaDAO();
            List<PeliculaDTO> peliculas = peliculaDAO.obtenerPeliculasPorSucursal(idSucursal);

            for (PeliculaDTO pelicula : peliculas) {
                JPanel peliculaPanel = crearPeliculaPanel(pelicula);
                peliculasPanel.add(peliculaPanel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar las películas: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar las películas: " + e.getMessage());
        }

        peliculasPanel.revalidate();  // Refrescar el panel
        peliculasPanel.repaint();
    }

    // Método para crear un panel con la información de una película
    private JPanel crearPeliculaPanel(PeliculaDTO pelicula) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Título de la película
        JLabel tituloLabel = new JLabel(pelicula.getTitulo());
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Imagen de la película
        JLabel imagenLabel = new JLabel();
        ImageIcon imagen = obtenerImagenDesdeBaseDeDatos(pelicula.getImagen());
        if (imagen != null) {
            imagenLabel.setIcon(imagen);
        } else {
            imagenLabel.setText("No disponible");
        }
        imagenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Sinopsis de la película
        JTextArea sinopsisArea = new JTextArea(pelicula.getSinopsis());
        sinopsisArea.setLineWrap(true);
        sinopsisArea.setWrapStyleWord(true);
        sinopsisArea.setEditable(false);
        sinopsisArea.setMaximumSize(new Dimension(200, 100));

        panel.add(tituloLabel);
        panel.add(imagenLabel);
        panel.add(sinopsisArea);

        return panel;
    }

    // Método ficticio para obtener la imagen desde la base de datos
    private ImageIcon obtenerImagenDesdeBaseDeDatos(String rutaImagen) {
        // Aquí debes cargar la imagen desde MySQL usando la ruta almacenada
        // Por ahora, cargamos la imagen desde el disco como ejemplo:
        return new ImageIcon(getClass().getResource("/imagenes/" + rutaImagen));  // Supone que tienes una carpeta de imágenes
    }

    // Método ficticio para obtener el ID de la sucursal según el nombre
    private int obtenerIdSucursal(String sucursalSeleccionada) {
        // Debes implementar la lógica para obtener el ID de la sucursal con base en el nombre
        // Por ahora, uso valores ficticios
        switch (sucursalSeleccionada) {
            case "Sucursal 1": return 1;
            case "Sucursal 2": return 2;
            case "Sucursal 3": return 3;
            default: return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CarteleraFrame frame = new CarteleraFrame();
            frame.setVisible(true);
        });
    }
}
