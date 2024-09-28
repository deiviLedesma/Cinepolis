/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dtos.CompraFuncionDTO;
import dtos.SucursalDTO;
import entidad.CompraFuncionEntidad;
import entidad.SucursalEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

/**
 *
 * @author filor
 */
public class CompraFuncionDAO {

    private IConexionBD conexionBD;
    private Connection conexionGeneral;

    public CompraFuncionDAO() {
        this.conexionBD = new ConexionBD();
    }

    public CompraFuncionDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    private int guardarCompraFuncion(CompraFuncionDTO compra) throws SQLException {
        int id = 0;
        String insertCliente = """
                                    INSERT INTO compras_funciones (precio,
                                   horaFuncion,
                                   idCompra,
                                   idFuncion)
                                                 VALUES (?,?,?,?);
                                    """;
        try (PreparedStatement preparedStatement = conexionGeneral.prepareStatement(insertCliente, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDouble(1, compra.getPrecio());
            preparedStatement.setTime(2, compra.getHoraFuncion());
            preparedStatement.setInt(3, compra.getIdCompra());
            preparedStatement.setInt(4, compra.getIdFuncion());

            preparedStatement.executeUpdate();

            try (ResultSet resultado = preparedStatement.getGeneratedKeys()) {
                if (resultado.next()) {
                    id = resultado.getInt(1);
                }
            }
        }
        return id;
    }

    public CompraFuncionEntidad guardarConTransacion(CompraFuncionDTO compra) throws PersistenciaException {
        try {
            this.conexionGeneral = this.conexionBD.obtenerConexion();
            this.conexionGeneral.setAutoCommit(false);
            int id = this.guardarCompraFuncion(compra); // Confirmar la transacción
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

    public CompraFuncionEntidad obtenerCompraPorId(int id) throws SQLException {
        CompraFuncionEntidad compra = null;
        String sql = """ 
                     SELECT idCompraFunciones,
                     precio,
                     horaFuncion,
                     idCompra,
                     idFuncion
                     FROM compras_funciones WHERE idCompraFunciones = ? """;
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

    private CompraFuncionEntidad compraEntidad(ResultSet rs) throws SQLException {
        int id = rs.getInt("idCompraFunciones");
        double precio = rs.getDouble("precio");
        Time hora = rs.getTime("horaFuncion");
        int compra = rs.getInt("idCompra");
        int funcion = rs.getInt("idFuncion");
        return new CompraFuncionEntidad(id, precio, hora, compra, funcion);
    }
}
