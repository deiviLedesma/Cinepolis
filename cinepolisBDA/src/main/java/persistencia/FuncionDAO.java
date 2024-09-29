/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dtos.ClienteDTO;
import dtos.ClienteTablaDTO;
import dtos.FiltroTablaDTO;
import dtos.FuncionDTO;
import dtos.SucursalDTO;
import entidad.ClienteEntidad;
import entidad.FuncionEntidad;
import entidad.SucursalEntidad;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author filor
 */
public class FuncionDAO implements IFuncionDAO {
    private IConexionBD conexionBD;
    private Connection conexionGeneral;

    public FuncionDAO() {
        this.conexionBD = new ConexionBD();
    }

    public FuncionDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    
    @Override
    public List<FuncionDTO> buscarClientesTabla(FiltroTablaDTO filtro) throws PersistenciaException {
        try {
            List<FuncionDTO> lista = null;
            Connection conexion = this.conexionBD.obtenerConexion();

            String codigoSQL = """
                               SELECT
                                    idFuncion,
                                    horaIniciaFuncion,
                                    horaAcabaFuncion,
                                    horaAcabaPelicula,
                                    dia,
                                    precio,
                                    idSala,
                                    idPelicula
                                FROM funciones
                                LIMIT ? 
                                OFFSET ?
                               """;

            PreparedStatement preparedStatement = conexion.prepareStatement(codigoSQL);
            preparedStatement.setInt(1, filtro.getLimit());
            preparedStatement.setInt(2, filtro.getOffset());

            ResultSet resultado = preparedStatement.executeQuery();
            while (resultado.next()) {
                if (lista == null) {
                    lista = new ArrayList<>();
                }
                lista.add(this.funcionTablaDTO(resultado));
            }

            resultado.close();
            preparedStatement.close();
            conexion.close();

            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new PersistenciaException("Ocurrió un error al leer la base de datos, inténtelo de nuevo y si el error persiste comuníquese con el encargado del sistema.");
        }
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
    
    @Override
    public FuncionEntidad guardarConTransacion(FuncionDTO funcion) throws PersistenciaException {
        try {
            this.conexionGeneral = this.conexionBD.obtenerConexion();
            this.conexionGeneral.setAutoCommit(false);
            int idFuncion = this.guardarFuncion(funcion); // Confirmar la transacción
            conexionGeneral.commit();
            return this.obtenerPorId(idFuncion);
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
    
    @Override
    public FuncionEntidad obtenerPorId(int id) throws SQLException {
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
    
    public FuncionEntidad modificar(FuncionDTO funcion) throws PersistenciaException {
        try {

            Connection conexion = this.conexionBD.obtenerConexion();

            // Definimos la consulta SQL para actualizar un cliente en la tabla 'clientes'
            String update = """
                                UPDATE funciones 
                                SET
                                    horaIniciaFuncion =?,
                                    horaAcabaFuncion=?,
                                    horaAcabaPelicula=?,
                                    dia=?,
                                    precio=?,
                                    idSala=?,
                                    idPelicula=?
                                WHERE idFuncion = ?
                                """;

            // Creamos un objeto PreparedStatement para ejecutar la consulta de actualización
            PreparedStatement preparedStatement = conexion.prepareStatement(update);

            // Asignamos valores a los parámetros de la consulta SQL
            preparedStatement.setTime(1, funcion.getHoraIniciaFuncion());
            preparedStatement.setTime(2, funcion.getHoraAcabaFuncion());
            preparedStatement.setTime(3, funcion.getHoraAcabaPelicula());
            preparedStatement.setInt(4, funcion.getDia());
            preparedStatement.setDouble(5, funcion.getPrecio());
            preparedStatement.setInt(6,funcion.getIdSala());
            preparedStatement.setInt(7,funcion.getIdPelicula());
            preparedStatement.setInt(8,funcion.getIdFuncion());
            // Ejecutamos la consulta de actualización en la base de datos
            int filasAfectadas = preparedStatement.executeUpdate();

            // Imprimimos el número de filas afectadas por la actualización
            System.out.println("Filas afectadas: " + filasAfectadas);

            // Cerramos el PreparedStatement y la conexión a la base de datos
            preparedStatement.close();
            conexion.close();

            return this.obtenerPorId(funcion.getIdFuncion());

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new PersistenciaException("Ocurrió un error al modificar, inténtelo de nuevo y si el error persiste comuníquese con el encargado del sistema.");
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
    
    private FuncionDTO funcionTablaDTO(ResultSet rs) throws SQLException {
        int id = rs.getInt("idFuncion");
        Time iniciaFuncion = rs.getTime("horaIniciaFuncion");
        Time acabaFuncion = rs.getTime("horaAcabaFuncion");
        Time acabaPelicula = rs.getTime("horaAcabaPelicula");
        int dia = rs.getInt("dia");
        double precio = rs.getDouble("precio");
        int idPelicula = rs.getInt("idPelicula");
        int idSala = rs.getInt("idSala");
        return new FuncionDTO(id,iniciaFuncion,acabaFuncion,acabaPelicula,dia,precio,idSala,idPelicula);
    }
}
