/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import entidad.CompraEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author filor
 */
public class CompraDAO {

    private IConexionBD conexionBD;

    public CompraDAO() {
        this.conexionBD = new ConexionBD();
    }

    public CompraDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    public void insertarCompra(CompraEntidad compra) throws PersistenciaException {
        try {
            Connection conexion = this.conexionBD.obtenerConexion();

            String insertCliente = """
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

            PreparedStatement preparedStatement = conexion.prepareStatement(insertCliente, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, compra.getCodigoCompra());
            preparedStatement.setDate(2, compra.getFechaHoraCompra());
            preparedStatement.setString(3, compra.getNombreCliente());
            preparedStatement.setString(4, compra.getCorreoCliente());
            preparedStatement.setInt(5, compra.getCantidadAsientos());
            preparedStatement.setString(6, compra.getMetodoDePago());
            preparedStatement.setDouble(7, compra.getCostoTotal());
            preparedStatement.setInt(8, compra.getIdCliente());

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

    /**
     * public void EliminarCompra(int idCompra) throws PersistenciaException,
     * SQLException {
     *
     * String eliminar = "DELETE FROM Compras WHERE idCompra = ?";
     *
     * try (Connection connection = conexionBD.obtenerConexion();
     * PreparedStatement stmt = connection.prepareStatement(eliminar)) {
     * stmt.setInt(1, idCompra); stmt.executeUpdate(); } }
    *
     */
}
