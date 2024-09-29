package Frames;

import entidad.ClienteEntidad;
import entidad.PeliculaEntidad;
import entidad.SucursalEntidad;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CarteleraFrame extends JFrame {
    private JComboBox<SucursalEntidad> comboSucursales;
    private JLabel labelDistancia;
    private JPanel panelPeliculas;

    public CarteleraFrame(List<SucursalEntidad> sucursales, ClienteEntidad cliente) {
        setTitle("Cartelera - Seleccione una Sucursal");
        setLayout(new BorderLayout());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Panel superior con selección de sucursal
        JPanel panelSucursal = new JPanel();
        comboSucursales = new JComboBox<>();
        for (SucursalEntidad sucursal : sucursales) {
            comboSucursales.addItem(sucursal); // Mostrar nombre de la sucursal en el combobox
        }
        labelDistancia = new JLabel("Distancia a la sucursal: ");
        
        comboSucursales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SucursalEntidad sucursalSeleccionada = (SucursalEntidad) comboSucursales.getSelectedItem();
                double distancia = calcularDistancia(cliente.getX(), cliente.getY(), sucursalSeleccionada.getLongitud(), sucursalSeleccionada.getLatitud());
                labelDistancia.setText("Distancia a la sucursal: " + String.format("%.2f", distancia) + " km");
                cargarPeliculas(sucursalSeleccionada);
            }
        });
        
        panelSucursal.add(comboSucursales);
        panelSucursal.add(labelDistancia);
        
        // Panel donde se mostrarán las películas
        panelPeliculas = new JPanel();
        panelPeliculas.setLayout(new GridLayout(1, 3)); // Mostrar 3 películas por fila
        
        add(panelSucursal, BorderLayout.NORTH);
        add(panelPeliculas, BorderLayout.CENTER);
        
        // Mostrar el frame
        setVisible(true);
    }

    // Método para calcular la distancia entre dos puntos (x1, y1) y (x2, y2)
    private double calcularDistancia(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private void cargarPeliculas(SucursalEntidad sucursal) {
        // Limpiar panel de películas
        panelPeliculas.removeAll();
        
        // Obtener las películas de la sucursal seleccionada
        List<PeliculaEntidad> peliculas = obtenerPeliculasPorSucursal(sucursal.getIdSucursal());
        
        // Mostrar hasta 3 películas
        for (PeliculaEntidad pelicula : peliculas.subList(0, Math.min(3, peliculas.size()))) {
            JPanel panelPelicula = new JPanel();
            panelPelicula.setLayout(new BorderLayout());
            
            // Imagen de la película
            JLabel labelImagen = new JLabel(new ImageIcon(pelicula.getImagen()));
            panelPelicula.add(labelImagen, BorderLayout.NORTH);
            
            // Título y descripción
            JLabel labelTitulo = new JLabel(pelicula.getTitulo());
            JLabel labelDescripcion = new JLabel("<html>" + pelicula.getDescripcion() + "</html>");
            
            panelPelicula.add(labelTitulo, BorderLayout.CENTER);
            panelPelicula.add(labelDescripcion, BorderLayout.SOUTH);
            
            // Botón para seleccionar película
            JButton btnSeleccionar = new JButton("Seleccionar");
            btnSeleccionar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Abrir el frame de pago con la película seleccionada
                    new Pago(pelicula);
                    dispose(); // Cierra este frame
                }
            });
            
            panelPelicula.add(btnSeleccionar, BorderLayout.SOUTH);
            panelPeliculas.add(panelPelicula);
        }
        
        // Refrescar el panel
        panelPeliculas.revalidate();
        panelPeliculas.repaint();
    }

    // Método que simula la obtención de las películas de la sucursal
    private List<PeliculaEntidad> obtenerPeliculasPorSucursal(int idSucursal) {
        // Aquí harías la consulta a la base de datos para obtener las películas
        return List.of(
                new PeliculaEntidad("Pelicula 1", "Descripción 1", "/ruta/imagen1.jpg"),
                new PeliculaEntidad("Pelicula 2", "Descripción 2", "/ruta/imagen2.jpg"),
                new PeliculaEntidad("Pelicula 3", "Descripción 3", "/ruta/imagen3.jpg")
        );
    }
}
