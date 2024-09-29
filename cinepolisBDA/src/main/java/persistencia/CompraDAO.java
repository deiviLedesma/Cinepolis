/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dtos.CompraDTO;
import dtos.SucursalDTO;
import entidad.CompraEntidad;
import entidad.SucursalEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author filor
 */
public class CompraDAO implements ICompraDAO{

    private IConexionBD conexionBD;
    private Connection conexionGeneral;

    public CompraDAO() {
        this.conexionBD = new ConexionBD();
    }

    public CompraDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public int guardarCompra(CompraDTO compra) throws SQLException {
        
        int idCompra = 0;
        String insertCompra = """
                                    INSERT INTO compras (codigoCompra,
                                   fechaHoraCompra,
                                   nombreCliente,
                                   correoCliente,
                                   cantidadAsientos,
                                   metodoDePago,
                                   costoTotal,
                                   idCliente)
                                                 VALUES (?,?,?,?,?,?,?,?);
                                    """;
        try (PreparedStatement preparedStatement = conexionGeneral.prepareStatement(insertCompra, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, compra.getCodigoCompra());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(compra.getFechaHoraCompra()));
            preparedStatement.setString(3, compra.getNombreCliente());
            preparedStatement.setString(4, compra.getCorreoCliente());
            preparedStatement.setInt(5, compra.getCantidadAsientos());
            preparedStatement.setString(6, compra.getMetodoDePago());
            preparedStatement.setDouble(7, compra.getCostoTotal());
            preparedStatement.setInt(8, compra.getIdCliente());
            preparedStatement.executeUpdate();

            try (ResultSet resultado = preparedStatement.getGeneratedKeys()) {
                if (resultado.next()) {
                    idCompra = resultado.getInt(1);
                }
            }
        }
        System.out.println(idCompra);
        return idCompra;
    }

    @Override
    public CompraEntidad guardarConTransacion(CompraDTO compra) throws PersistenciaException {
        try {
            this.conexionGeneral = this.conexionBD.obtenerConexion();
            this.conexionGeneral.setAutoCommit(false);
            int id = this.guardarCompra(compra); // Confirmar la transacción
            conexionGeneral.commit();
            return this.obtenerCompraPorId(id);
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

    @Override
    public CompraEntidad obtenerCompraPorId(int id) throws SQLException {

        CompraEntidad compra = null;
        String sql = """ 
                     SELECT idCompra,
                     codigoCompra,
                     fechaHoraCompra,
                     nombreCliente,
                     correoCliente,
                     cantidadAsientos,
                     metodoDePago,
                     costoTotal,
                     idCliente
                     FROM compras WHERE idCompra = ? """;
        try (Connection connection = conexionBD.obtenerConexion(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                compra = this.compraEntidad(rs);
            }

            rs.close();
            stmt.close();
            connection.close();

            return compra;
        }
    }

    @Override
    public CompraEntidad compraEntidad(ResultSet rs) throws SQLException {
        int id = rs.getInt("idCompra");
        String codigo = rs.getString("codigoCompra");
        LocalDateTime hora = rs.getTimestamp("fechaHoraCompra").toLocalDateTime();
        String cliente = rs.getString("nombreCliente");
        String correo = rs.getString("correoCliente");
        int asientos = rs.getInt("cantidadAsientos");
        String pago = rs.getString("metodoDePago");
        double total = rs.getDouble("costoTotal");
        int idCliente = rs.getInt("idCliente");
        return new CompraEntidad(id, codigo, hora, cliente, correo, asientos, pago, total, idCliente);
    }
}
