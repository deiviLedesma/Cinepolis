/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import entidad.CiudadEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author filor
 */
public class CiudadDAO {

    private IConexionBD conexionBD;

    public CiudadDAO() {
        this.conexionBD = new ConexionBD();
    }

    public CiudadDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    public void insertarCiudad(CiudadEntidad ciudad) throws PersistenciaException {
        try {
            Connection conexion = this.conexionBD.obtenerConexion();

            String insertCliente = """
                                    INSERT INTO ciudades (nombre,
                                   cantidadHabitantes,
                                   idPaís)
                                                 VALUES (?,?,?);
                                    """;

            PreparedStatement preparedStatement = conexion.prepareStatement(insertCliente, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, ciudad.getNombre());
            preparedStatement.setInt(2, ciudad.getNumeroHabitantes());
            preparedStatement.setInt(3, ciudad.getIdPais());

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

    public void EliminarCiudad(int idCiudad) throws PersistenciaException, SQLException {

        String eliminar = "DELETE FROM ciudades  WHERE idCiudades = ?";

        try (Connection connection = conexionBD.obtenerConexion(); PreparedStatement stmt = connection.prepareStatement(eliminar)) {
            stmt.setInt(1, idCiudad);
            stmt.executeUpdate();
        }catch(SQLException e){
            throw new PersistenciaException("No se pudo eliminar la Ciudad porque no se encontró");
        }
        
    }
}
