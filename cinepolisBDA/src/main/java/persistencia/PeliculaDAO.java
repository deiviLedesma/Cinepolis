/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import entidad.PaisEntidad;
import entidad.PeliculaEntidad;
import entidad.SalaEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SDavidLedesma
 */
public class PeliculaDAO {

    private IConexionBD conexionBD;

    public PeliculaDAO() throws SQLDataException {
        this.conexionBD = new ConexionBD();
    }

    public void insertarPelicula(PeliculaEntidad pelicula, PaisEntidad Idpais) throws SQLException {
        String sql = "INSERT INTO Peliculas (titulo, duracionEnMinutos, sinopsis, trailer, idPais, idGenero, idClasificacion) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = conexionBD.obtenerConexion(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pelicula.getTitulo());
            stmt.setInt(2, pelicula.getDuracionEnMinutos());
            stmt.setString(3, pelicula.getSinopsis());
            stmt.setString(4, pelicula.getTrailer());
            stmt.setInt(5, Idpais.getIdPais());
            stmt.setInt(6, pelicula.getIdGenero());
            stmt.setInt(7, pelicula.getIdClasificacion());
            stmt.executeUpdate();
        }
    }

    public List<PeliculaEntidad> obtenerPeliculasPaginadas(int limite, int offset) throws SQLException {
        List<PeliculaEntidad> peliculas = new ArrayList<>();
        String sql = "SELECT * FROM Peliculas LIMIT ? OFFSET ?";
        try (Connection connection = conexionBD.obtenerConexion(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, limite);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PaisEntidad pais = new PaisEntidad();
                PeliculaEntidad pelicula = new PeliculaEntidad();
                pelicula.setIdPelicula(rs.getInt("idPelicula"));
                pelicula.setTitulo(rs.getString("titulo"));
                pelicula.setDuracionEnMinutos(rs.getInt("duracionEnMinutos"));
                pelicula.setSinopsis(rs.getString("sinopsis"));
                pelicula.setTrailer(rs.getString("trailer"));
                pais.setIdPais(rs.getInt("idPais"));
                pelicula.setIdGenero(rs.getInt("idGenero"));
                pelicula.setIdClasificacion(rs.getInt("idClasificacion"));
                peliculas.add(pelicula);
            }
        }
        return peliculas;
    }

   
    public void eliminarPelicula(int idPelicula) throws SQLException {
        String sql = "DELETE FROM Peliculas WHERE idPelicula = ?";
        try (Connection connection = conexionBD.obtenerConexion(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPelicula);
            stmt.executeUpdate();
        }
    }

   

    public PeliculaEntidad obtenerPeliculaPorId(int idPelicula) throws SQLException {
        PeliculaEntidad pelicula = null;
        String sql = "SELECT * FROM Peliculas WHERE idPelicula = ?";
        try (Connection conn = conexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPelicula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                pelicula = new PeliculaEntidad();
                PaisEntidad pais = new PaisEntidad();
                pelicula.setIdPelicula(rs.getInt("idPelicula"));
                pelicula.setTitulo(rs.getString("titulo"));
                pelicula.setDuracionEnMinutos(rs.getInt("duracionEnMinutos"));
                pelicula.setSinopsis(rs.getString("sinopsis"));
                pelicula.setTrailer(rs.getString("trailer"));
                pais.setIdPais(rs.getInt("idPais"));
                pelicula.setIdGenero(rs.getInt("idGenero"));
                pelicula.setIdClasificacion(rs.getInt("idClasificacion"));
            }
        }
        return pelicula; // Devuelve null si no se encuentra
    }
}
