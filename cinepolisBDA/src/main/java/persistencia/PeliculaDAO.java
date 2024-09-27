/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import com.mysql.cj.protocol.Resultset;
import dtos.ClienteDTO;
import dtos.PeliculaDTO;
import entidad.ClienteEntidad;
import entidad.PaisEntidad;
import entidad.PeliculaEntidad;
import entidad.SalaEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SDavidLedesma
 */
public class PeliculaDAO {

    private IConexionBD conexionBD;
    private Connection conexionGeneral;

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

    private int GuardarPelicula(PeliculaDTO pelicula) throws SQLException {
        int idCliente = 0;
        String insertCliente = """
                                    INSERT INTO peliculas (titulo,
                                                        duraciónEnMinutos,
                                                        sinopsis,
                                                        imagen,
                                                        trailer,
                                                        idGenero,
                                                        idClasificacion,
                                                        idPais)
                                                 VALUES (?,?,?,?,?,?,?,?);
                                    """;
        try (PreparedStatement preparedStatement = conexionGeneral.prepareStatement(insertCliente, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, pelicula.getTitulo());
            preparedStatement.setInt(2, pelicula.getDuracionEnMinutos());
            preparedStatement.setString(3, pelicula.getSinopsis());
            preparedStatement.setString(4, pelicula.getImagen());
            preparedStatement.setString(5, pelicula.getTrailer());
            preparedStatement.setInt(6, pelicula.getIdGenero());
            preparedStatement.setInt(7, pelicula.getIdClasificacion());
            preparedStatement.setInt(8, pelicula.getIdPais());

            preparedStatement.executeUpdate();

            try (ResultSet resultado = preparedStatement.getGeneratedKeys()) {
                if (resultado.next()) {
                    idCliente = resultado.getInt(1);
                }
            }
        }
        return idCliente;
    }

    public PeliculaEntidad obtenerPeliculaPorId(int idPelicula) throws SQLException {
        PeliculaEntidad pelicula = null;
        String sql = """
                     SELECT 
                     idPelicula,
                     titulo,
                     duraciónEnMinutos,
                     sinopsis,
                     imagen,
                     trailer,
                     idGenero,
                     idClasificacion,
                     idPais
                     FROM peliculas WHERE idPelicula = ?""";
        try (Connection conn = conexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPelicula);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pelicula = this.peliculaEntidad(rs);
            }

            rs.close();
            stmt.close();
            conn.close();

            return pelicula;
        }
    }

    public PeliculaEntidad guardarConTransacion(PeliculaDTO pelicula) throws PersistenciaException {
        try {
            this.conexionGeneral = this.conexionBD.obtenerConexion();
            this.conexionGeneral.setAutoCommit(false);
            int idPelicula = this.GuardarPelicula(pelicula); // Confirmar la transacción
            conexionGeneral.commit();
            return this.obtenerPeliculaPorId(idPelicula);
        } catch (SQLException ex) {
            try {
                // Deshacer cambios en caso de error
                if (this.conexionGeneral != null) {
                    this.conexionGeneral.rollback();
                }
            } catch (SQLException rollbackEx) {
                System.out.println("Error al hacer rollback: " + rollbackEx.getMessage());
            }
            System.out.println("Error al querer hacer la transaccion " + ex.getMessage());
            throw new PersistenciaException("Ocurrió un error al registrar la pelicula, inténtelo de nuevo y si el error persiste comuníquese con el encargado del sistema.");
        } finally {
            try {
                if (this.conexionGeneral != null) {
                    this.conexionGeneral.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar la conexion de la base de datos");
            }
        }
    }
    
    public PeliculaEntidad modificar(PeliculaDTO pelicula) throws PersistenciaException {
        try {

            Connection conexion = this.conexionBD.obtenerConexion();

            // Definimos la consulta SQL para actualizar un cliente en la tabla 'clientes'
            String updateCliente = """
                                UPDATE peliculas
                                SET
                                    idPelicula =?,
                                    titulo =?,
                                    duraciónEnMinutos =?,
                                    sinopsis =?,
                                    imagen =?,
                                    trailer =?,
                                    idGenero =?,
                                    idClasificacion =?,
                                    idPais =?
                                WHERE idcliente = ?
                                """;

            // Creamos un objeto PreparedStatement para ejecutar la consulta de actualización
            PreparedStatement preparedStatement = conexion.prepareStatement(updateCliente);

            // Asignamos valores a los parámetros de la consulta SQL
            preparedStatement.setString(1, pelicula.getTitulo());
            preparedStatement.setInt(2, pelicula.getDuracionEnMinutos());
            preparedStatement.setString(3, pelicula.getSinopsis());
            preparedStatement.setString(4, pelicula.getImagen());
            preparedStatement.setString(5, pelicula.getTrailer());
            preparedStatement.setInt(6, pelicula.getIdGenero());
            preparedStatement.setInt(7, pelicula.getIdClasificacion());
            preparedStatement.setInt(8, pelicula.getIdPais());
            preparedStatement.setInt(9, pelicula.getIdPelicula());
            // Ejecutamos la consulta de actualización en la base de datos
            int filasAfectadas = preparedStatement.executeUpdate();

            // Imprimimos el número de filas afectadas por la actualización
            System.out.println("Filas afectadas: " + filasAfectadas);

            // Cerramos el PreparedStatement y la conexión a la base de datos
            preparedStatement.close();
            conexion.close();

            return this.obtenerPeliculaPorId(pelicula.getIdPelicula());

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new PersistenciaException("Ocurrió un error al modificar, inténtelo de nuevo y si el error persiste comuníquese con el encargado del sistema.");
        }
    }

    private PeliculaEntidad peliculaEntidad(ResultSet rs) throws SQLException {
        int id = rs.getInt("idPelicula");
        String titulo = rs.getString("titulo");
        int duracion = rs.getInt("duraciónEnMinutos");
        String sinopsis = rs.getString("sinopsis");
        String trailer = rs.getString("trailer");
        String imagen = rs.getString("imagen");
        int genero = rs.getInt("idGenero");
        int clasificacion = rs.getInt("idClasificacion");
        int pais = rs.getInt("idPais");
        return new PeliculaEntidad(id, titulo, duracion, sinopsis, imagen, trailer, genero, clasificacion, pais);
    }

}
