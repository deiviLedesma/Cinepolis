/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import entidad.PaisEntidad;
import entidad.PeliculaEntidad;
import java.sql.SQLException;
import java.util.List;
import persistencia.PeliculaDAO;

/**
 *
 * @author SDavidLedesma
 */
public class PeliculaService {

    private PeliculaDAO peliculaDAO;

    public PeliculaService(PeliculaDAO peliculaDAO) {
        this.peliculaDAO = peliculaDAO;
    }

    //Metodo para agregar una nueva pelicula
    public void agregarPelicula(PeliculaEntidad pelicula, PaisEntidad Idpais) throws SQLException {
        if (pelicula.getTitulo() == null || pelicula.getTitulo().isEmpty()) {
            throw new IllegalArgumentException("El título de la película no puede estar vacío.");
        }

        if (pelicula.getDuracionEnMinutos() <= 0) {
            throw new IllegalArgumentException("La duración de la película debe ser mayor a 0 minutos.");
        }

        if (pelicula.getSinopsis() == null || pelicula.getSinopsis().isEmpty()) {
            throw new IllegalArgumentException("La sinopsis de la película no puede estar vacía.");
        }

        if (Idpais.getIdPais() <= 0) {
            throw new IllegalArgumentException("El ID del país debe ser un valor positivo.");
        }

        if (pelicula.getIdGenero() <= 0) {
            throw new IllegalArgumentException("El ID del género debe ser un valor positivo.");
        }

        if (pelicula.getIdClasificacion() <= 0) {
            throw new IllegalArgumentException("El ID de la clasificación debe ser un valor positivo.");
        }

        peliculaDAO.insertarPelicula(pelicula, Idpais);

    }

    // Método para obtener una lista de películas paginadas
    public List<PeliculaEntidad> obtenerPeliculasPaginadas(int limite, int offset) throws SQLException {
        return peliculaDAO.obtenerPeliculasPaginadas(limite, offset);
    }

    // Método para eliminar una película por ID
    public void eliminarPelicula(int idPelicula) throws SQLException {
        // Validación de ID de película
        if (idPelicula <= 0) {
            throw new IllegalArgumentException("No existe el ID de la pelicula");
        }

        peliculaDAO.eliminarPelicula(idPelicula);
    }
}
