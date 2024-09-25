/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import entidad.SalaEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author SDavidLedesma
 */
public class SalaDAO {

    private IConexionBD conexionBD;

    public SalaDAO() throws SQLException {
        this.conexionBD = conexionBD;
    }

    public void actualizarSala(SalaEntidad sala) throws SQLException {
        String sql = "UPDATE Salas SET nombre = ?, capacidadAsientos = ?, idSucursal = ?,  WHERE idSala = ?";
        try (Connection connection = conexionBD.obtenerConexion(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, sala.getNombre());
            stmt.setInt(2, sala.getAsientosDisponibles());
            stmt.setInt(3, sala.getIdSucursal());
            //stmt.setInt(4, sala.getIdTipoDeSala());
            stmt.setInt(4, sala.getIdSala());
            stmt.executeUpdate();
        }

    }

    public void eliminarSala(int idSala) throws SQLException, PersistenciaException {
        String sql = "DELETE FROM Salas WHERE idPelicula = ?";
        try (Connection connection = conexionBD.obtenerConexion(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSala);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo elinminar la sala porque no se encontró");
        }
    }

    public SalaEntidad obtenerSalaPorId(int idSala) throws SQLException {
        SalaEntidad sala = null;
        String sql = "SELECT * FROM Salas WHERE idSala = ?";
        try (Connection connection = conexionBD.obtenerConexion(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSala);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sala = new SalaEntidad();
                sala.setIdSala(rs.getInt("idSala"));
                sala.setNombre(rs.getString("nombre"));
                sala.setAsientosDisponibles(rs.getInt("capacidadAsientos"));
                sala.setIdSucursal(rs.getInt("idSucursal"));
                //sala.setIdTipoDeSala(rs.getInt("idTipoDeSala"));
            }
        }
        return sala;
    }

}
