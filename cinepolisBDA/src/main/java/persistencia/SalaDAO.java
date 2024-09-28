/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dtos.SalaDTO;
import entidad.SalaEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author SDavidLedesma
 */
public class SalaDAO {

    private IConexionBD conexionBD;
    private Connection conexionGeneral;

    public SalaDAO(){
        this.conexionBD = new ConexionBD();
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

    public void eliminarSala(int idSala) throws SQLException {
        String sql = "DELETE FROM Salas WHERE idPelicula = ?";
        try (Connection connection = conexionBD.obtenerConexion(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSala);
            stmt.executeUpdate();
        }
    }

    private int guardarSala(SalaDTO sala) throws SQLException {
        int idSala = 0;
        String insertCliente = """
                                    INSERT INTO salas (nombre,
                                                       capacidadAsientos,
                                                       tiempoDeLimpiezaMinutos,
                                                       precioActual,
                                                       idSucursal)
                                                 VALUES (?,?,?,?,?);
                                    """;
        try (PreparedStatement preparedStatement = conexionGeneral.prepareStatement(insertCliente, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, sala.getNombre());
            preparedStatement.setInt(2, sala.getAsientosDisponibles());
            preparedStatement.setInt(3, sala.getTiempoLimpiezaEnMinutos());
            preparedStatement.setFloat(4,(float)sala.getPrecioActual());
            preparedStatement.setInt(5, sala.getIdSala());

            preparedStatement.executeUpdate();

            try (ResultSet resultado = preparedStatement.getGeneratedKeys()) {
                if (resultado.next()) {
                    idSala = resultado.getInt(1);
                }
            }
        }
        return idSala;
    }
    
    public SalaEntidad guardarConTransacion(SalaDTO sala) throws PersistenciaException {
        try {
            this.conexionGeneral = this.conexionBD.obtenerConexion();
            this.conexionGeneral.setAutoCommit(false);
            int idSala = this.guardarSala(sala); // Confirmar la transacción
            conexionGeneral.commit();
            return this.obtenerSalaPorId(idSala);
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
            throw new PersistenciaException("Ocurrió un error al registrar la sala, inténtelo de nuevo y si el error persiste comuníquese con el encargado del sistema.");
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

    public SalaEntidad obtenerSalaPorId(int idSala) throws SQLException {
        SalaEntidad sala = null;
        String sql = """ 
                     SELECT idSala,
                     nombre,
                     capacidadAsientos,
                     tiempoDeLimpiezaMinutos,
                     precioActual,
                     idSucursal
                     FROM Salas WHERE idSala = ? """;
        try (Connection connection = conexionBD.obtenerConexion(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSala);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                sala = this.salaEntidad(rs);
            }

            rs.close();
            stmt.close();
            conexionGeneral.close();

            return sala;
        }
    }

    private SalaEntidad salaEntidad(ResultSet rs) throws SQLException {
        int id = rs.getInt("idSala");
        String nombre = rs.getString("nombre");
        int asientos = rs.getInt("capacidadAsientos");
        int duracion = rs.getInt("tiempoDeLimpiezaMinutos");
        float precio = rs.getFloat("precioActual");
        int sucursal = rs.getInt("idSucursal");
        return new SalaEntidad(id,nombre,asientos,duracion,precio,sucursal);
    }
}
