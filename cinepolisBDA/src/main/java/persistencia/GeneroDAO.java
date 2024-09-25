/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import entidad.GeneroEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author filor
 */
public class GeneroDAO {

    private IConexionBD conexionBD;

    public GeneroDAO() {
        this.conexionBD = new ConexionBD();
    }

    public GeneroDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    public void insertarGenero(GeneroEntidad genero) throws PersistenciaException {
        try {
            Connection conexion = this.conexionBD.obtenerConexion();

            String insertCliente = """
                                    INSERT INTO generos (nombre)
                                                 VALUES (?);
                                    """;

            PreparedStatement preparedStatement = conexion.prepareStatement(insertCliente, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, genero.getNombre());

            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas == 0) {
                throw new PersistenciaException("La inserción del cliente falló, no se pudo insertar el registro.");
            }

            ResultSet resultado = preparedStatement.getGeneratedKeys();

            resultado.close();
            preparedStatement.close();
            conexion.close();

        } catch (SQLException ex) {
            throw new PersistenciaException("Ocurrió un error al leer la base de datos, inténtelo de nuevo y si el error persiste comuníquese con el encargado del sistema.");
        }

    }

    public void eliminarGenero(int idGenero) throws PersistenciaException, SQLException {
        String eliminar = "DELETE FROM Generos WHERE idGenero = ?";
        try (Connection connection = conexionBD.obtenerConexion(); PreparedStatement stmt = connection.prepareStatement(eliminar)) {
            stmt.setInt(1, idGenero);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo eliminar el Cliente porque no se encontró");
        }

    }
}
