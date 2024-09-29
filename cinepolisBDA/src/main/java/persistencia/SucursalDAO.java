/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dtos.SalaDTO;
import dtos.SucursalDTO;
import entidad.SalaEntidad;
import entidad.SucursalEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author filor
 */
public class SucursalDAO implements ISucursalDAO{
    private IConexionBD conexionBD;
    private Connection conexionGeneral;

    public SucursalDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    public SucursalDAO() {
        this.conexionBD = new ConexionBD();
    }
    
    private int guardarSucursal(SucursalDTO sucursal) throws SQLException {
        int idSucursal = 0;
        String insertCliente = """
                                    INSERT INTO sucursales (nombre,
                                                       ubicacion,
                                                       idCiudad)
                                                 VALUES (?,POINT(?,?),?);
                                    """;
        try (PreparedStatement preparedStatement = conexionGeneral.prepareStatement(insertCliente, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, sucursal.getNombre());
            preparedStatement.setDouble(3, sucursal.getLatitud());
            preparedStatement.setDouble(2, sucursal.getLongitud());
            preparedStatement.setInt(4,sucursal.getIdCiudad());

            preparedStatement.executeUpdate();

            try (ResultSet resultado = preparedStatement.getGeneratedKeys()) {
                if (resultado.next()) {
                    idSucursal = resultado.getInt(1);
                }
            }
        }
        return idSucursal;
    }
    
    public SucursalEntidad guardarConTransacion(SucursalDTO sucursal) throws PersistenciaException {
        try {
            this.conexionGeneral = this.conexionBD.obtenerConexion();
            this.conexionGeneral.setAutoCommit(false);
            int idSucursal = this.guardarSucursal(sucursal); // Confirmar la transacción
            conexionGeneral.commit();
            return this.obtenerSucursalPorId(idSucursal);
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
            throw new PersistenciaException("Ocurrió un error al registrar la sucursal, inténtelo de nuevo y si el error persiste comuníquese con el encargado del sistema.");
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
    
    public SucursalEntidad obtenerSucursalPorId(int idSucursal) throws SQLException {
        SucursalEntidad sucursal = null;
        String sql = """ 
                     SELECT idSucursal,
                     nombre,
                     ubicacion,
                     idCiudad
                     FROM Sucursales WHERE idSucursal = ? """;
        try (Connection connection = conexionBD.obtenerConexion(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSucursal);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                sucursal = this.sucursalEntidad(rs);
            }

            rs.close();
            stmt.close();
           connection.close();

            return sucursal;
        }
    }
    
    private SucursalEntidad sucursalEntidad(ResultSet rs) throws SQLException {
        int id = rs.getInt("idSucursal");
        String nombre = rs.getString("nombre");
        float latitud = rs.getFloat("latitud");
        float longitud = rs.getFloat("longitud");
        int ciudad = rs.getInt("idCiudad");
        return new SucursalEntidad(id,nombre,latitud,longitud,ciudad);
    }
}
