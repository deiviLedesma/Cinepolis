/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dtos.ClienteDTO;
import dtos.FiltroTablaDTO;
import dtos.FuncionDTO;
import dtos.SalaDTO;
import entidad.ClienteEntidad;
import entidad.SalaEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SDavidLedesma
 */
public class SalaDAO implements ISalaDAO {

    private IConexionBD conexionBD;
    private Connection conexionGeneral;

    public SalaDAO(){
        this.conexionBD = new ConexionBD();
    }

    @Override
    public List<SalaDTO> buscarSalaTabla(FiltroTablaDTO filtro) throws PersistenciaException {
        try {
            List<SalaDTO> lista = null;
            Connection conexion = this.conexionBD.obtenerConexion();

            String codigoSQL = """
                               SELECT
                                    idSala,
                                    nombre,
                                    capacidadAsientos,
                                    tiempoDeLimpiezaMinutos,
                                    precioActual,
                                    idSucursal
                                FROM salas
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
    
    @Override
    public SalaEntidad actualizarSala(SalaEntidad sala) throws SQLException {
        String sql = "UPDATE Salas SET nombre = ?, capacidadAsientos = ?, idSucursal = ?,  WHERE idSala = ?";
        try (Connection connection = conexionBD.obtenerConexion(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, sala.getNombre());
            stmt.setInt(2, sala.getAsientosDisponibles());
            stmt.setInt(3, sala.getIdSucursal());
            //stmt.setInt(4, sala.getIdTipoDeSala());
            stmt.setInt(4, sala.getIdSala());
            stmt.executeUpdate();
            
            int filasAfectadas = stmt.executeUpdate();

            // Imprimimos el número de filas afectadas por la actualización
            System.out.println("Filas afectadas: " + filasAfectadas);

            // Cerramos el PreparedStatement y la conexión a la base de datos
            stmt.close();
            connection.close();

            return this.obtenerSalaPorId(sala.getIdSala());
        }

    }

    @Override
    public SalaEntidad eliminarSala(int idSala) throws PersistenciaException {
        
        try {

            SalaEntidad salaEliminada = this.obtenerSalaPorId(idSala);
            if (salaEliminada == null) {
                throw new PersistenciaException("Ocurrio un error en obtener la información del cliente por su clave");
            }

            Connection conexion = this.conexionBD.obtenerConexion();

            // Definimos la consulta SQL para actualizar un cliente en la tabla 'clientes'
            String sql = "DELETE FROM Salas WHERE idPelicula = ?";

            // Creamos un objeto PreparedStatement para ejecutar la consulta de actualización
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);

            // Asignamos valores a los parámetros de la consulta SQL            
            preparedStatement.setInt(1, idSala);

            // Ejecutamos la consulta de actualización en la base de datos
            int filasAfectadas = preparedStatement.executeUpdate();

            // Imprimimos el número de filas afectadas por la actualización
            System.out.println("Filas afectadas: " + filasAfectadas);

            // Cerramos el PreparedStatement y la conexión a la base de datos
            preparedStatement.close();
            conexion.close();

            return salaEliminada;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new PersistenciaException("Ocurrió un error al modificar, inténtelo de nuevo y si el error persiste comuníquese con el encargado del sistema.");
        }
    }

    @Override
    public SalaEntidad guardar(SalaDTO sala) throws PersistenciaException {
        try {
            Connection conexion = this.conexionBD.obtenerConexion();
            String insert = """
                                    INSERT INTO salas (nombre,
                                                       capacidadAsientos,
                                                       tiempoDeLimpiezaMinutos,
                                                       precioActual,
                                                       idSucursal)
                                                 VALUES (?,?,?,?,?);
                                    """;

            PreparedStatement preparedStatement = conexion.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, sala.getNombre());
            preparedStatement.setInt(2, sala.getAsientosDisponibles());
            preparedStatement.setInt(3, sala.getTiempoLimpiezaEnMinutos());
            preparedStatement.setFloat(4,(float)sala.getPrecioActual());
            preparedStatement.setInt(5, sala.getIdSala());

            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas == 0) {
                throw new PersistenciaException("La inserción del cliente falló, no se pudo insertar el registro.");
            }

            int id = 0;
            ResultSet resultado = preparedStatement.getGeneratedKeys();
            if (resultado.next()) {
                id = (resultado.getInt(1));
            }

            resultado.close();
            preparedStatement.close();
            conexion.close();

            return this.obtenerSalaPorId(id);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new PersistenciaException("Ocurrió un error al leer la base de datos, inténtelo de nuevo y si el error persiste comuníquese con el encargado del sistema.");
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
    
    @Override
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

    @Override
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
            connection.close();

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
    
    private SalaDTO funcionTablaDTO(ResultSet rs) throws SQLException {
        int id = rs.getInt("idSala");
        String nombre = rs.getString("nombre");
        int asientos = rs.getInt("capacidadAsientos");
        int duracion = rs.getInt("tiempoDeLimpiezaMinutos");
        float precio = rs.getFloat("precioActual");
        int sucursal = rs.getInt("idSucursal");
        return new SalaDTO(id,nombre,asientos,duracion,precio,sucursal);
    }
    
}
