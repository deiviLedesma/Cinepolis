/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dtos.FuncionDTO;
import dtos.SucursalDTO;
import entidad.ClienteEntidad;
import entidad.FuncionEntidad;
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
public class FuncionDAO {
    private IConexionBD conexionBD;
    private Connection conexionGeneral;

    public FuncionDAO() {
        this.conexionBD = new ConexionBD();
    }

    public FuncionDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    
    
    
    private int guardarFuncion(FuncionDTO funcion) throws SQLException {
        int idSucursal = 0;
        String insertCliente = """
                                    INSERT INTO funciones (horaIniciaFuncion,
                                                         horaAcabaFuncion,
                                                         horaAcabaPelicula,
                                                         dia,
                                                         precio,
                                                         idSala,
                                                         idPelicula)
                                                 VALUES (?,?,?,?,?,?,?);
                                    """;
        try (PreparedStatement preparedStatement = conexionGeneral.prepareStatement(insertCliente, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setTime(1, funcion.getHoraIniciaFuncion());
            preparedStatement.setTime(2, funcion.getHoraAcabaFuncion());
            preparedStatement.setTime(3, funcion.getHoraAcabaPelicula());
            preparedStatement.setInt(4, funcion.getDia());
            preparedStatement.setDouble(5, funcion.getPrecio());
            preparedStatement.setInt(6,funcion.getIdSala());
            preparedStatement.setInt(7,funcion.getIdPelicula());

            preparedStatement.executeUpdate();

            try (ResultSet resultado = preparedStatement.getGeneratedKeys()) {
                if (resultado.next()) {
                    idSucursal = resultado.getInt(1);
                }
            }
        }
        return idSucursal;
    }
    
    public FuncionEntidad guardarConTransacion(FuncionDTO funcion) throws PersistenciaException {
        try {
            this.conexionGeneral = this.conexionBD.obtenerConexion();
            this.conexionGeneral.setAutoCommit(false);
            int idFuncion = this.guardarFuncion(funcion); // Confirmar la transacción
            conexionGeneral.commit();
            return this.obtenerSucursalPorId(idFuncion);
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
            throw new PersistenciaException("Ocurrió un error al registrar la funcion, inténtelo de nuevo y si el error persiste comuníquese con el encargado del sistema.");
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
    
    public FuncionEntidad obtenerSucursalPorId(int id) throws SQLException {
        FuncionEntidad funcion = null;
        String sql = """ 
                     SELECT idFuncion,
                     horaIniciaFuncion,
                     horaAcabaFuncion,
                     horaAcabaPelicula,
                     dia,
                     precio,
                     idSala,
                     idPelicula
                     FROM funciones WHERE idFuncion = ? """;
        try (Connection connection = conexionBD.obtenerConexion(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                funcion = this.funcionEntidad(rs);
            }

            rs.close();
            stmt.close();
            connection.close();

            return funcion;
        }
    }
    
    private FuncionEntidad funcionEntidad(ResultSet rs) throws SQLException {
        int id = rs.getInt("idFuncion");
        Time iniciaFuncion = rs.getTime("horaIniciaFuncion");
        Time acabaFuncion = rs.getTime("horaAcabaFuncion");
        Time acabaPelicula = rs.getTime("horaAcabaPelicula");
        int dia = rs.getInt("dia");
        double precio = rs.getDouble("precio");
        int idPelicula = rs.getInt("idPelicula");
        int idSala = rs.getInt("idSala");
        return new FuncionEntidad(id,iniciaFuncion,acabaFuncion,acabaPelicula,dia,precio,idSala,idPelicula);
    }
}
