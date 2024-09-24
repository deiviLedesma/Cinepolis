/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import entidad.CompraFuncionEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author filor
 */
public class CompraFuncionDAO {
    private IConexionBD conexionBD;

    public CompraFuncionDAO() {
        this.conexionBD = new ConexionBD();
    }

    public CompraFuncionDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    
    public void insertarCompraFuncion(CompraFuncionEntidad compra) throws PersistenciaException {
        try {
            Connection conexion = this.conexionBD.obtenerConexion();

            String insertCliente = """
                                    INSERT INTO compras_funciones (precio,
                                   horaFuncion,
                                   idCompra,
                                   idFuncion)
                                                 VALUES (?,?,?,?);
                                    """;

            PreparedStatement preparedStatement = conexion.prepareStatement(insertCliente, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, compra.getPrecio());
            preparedStatement.setTime(2, compra.getHoraFuncion());
            preparedStatement.setInt(3, compra.getIdCompra());
            preparedStatement.setInt(4, compra.getIdFuncion());
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
}
