/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dtos.ClienteDTO;
import dtos.FiltroTablaDTO;
import dtos.ClienteTablaDTO;
import entidad.ClienteEntidad;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utilerias.Encriptador;

/**
 *
 * @author filor
 */
public class ClienteDAO implements IClienteDAO {

    private IConexionBD conexionBD;
    private Connection conexionGeneral;

    public ClienteDAO() {
        this.conexionBD = new ConexionBD();
    }

    public ClienteDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    
    @Override
    public List<ClienteTablaDTO> buscarClientesTabla(FiltroTablaDTO filtro) throws PersistenciaException {
        try {
            List<ClienteTablaDTO> clienteLista = null;
            Connection conexion = this.conexionBD.obtenerConexion();

            String codigoSQL = """
                               SELECT
                                    idCliente,
                                    nombres,
                                    apellidoPaterno,
                                    apellidoMaterno,
                                    fechaNacimiento,
                                    idCiudad
                                FROM clientes
                                WHERE CONCAT(nombres, ' ', apellidoPaterno, ' ', apellidoMaterno) LIKE ?
                                LIMIT ? 
                                OFFSET ?
                               """;

            PreparedStatement preparedStatement = conexion.prepareStatement(codigoSQL);
            preparedStatement.setString(1, "%" + filtro.getFiltro() + "%");
            preparedStatement.setInt(2, filtro.getLimit());
            preparedStatement.setInt(3, filtro.getOffset());

            ResultSet resultado = preparedStatement.executeQuery();
            while (resultado.next()) {
                if (clienteLista == null) {
                    clienteLista = new ArrayList<>();
                }
                clienteLista.add(this.clienteTablaDTO(resultado));
            }

            resultado.close();
            preparedStatement.close();
            conexion.close();

            return clienteLista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new PersistenciaException("Ocurrió un error al leer la base de datos, inténtelo de nuevo y si el error persiste comuníquese con el encargado del sistema.");
        }
    }

    @Override
    public ClienteEntidad guardar(ClienteDTO cliente) throws PersistenciaException {
        try {
            Connection conexion = this.conexionBD.obtenerConexion();
            String insertCliente = """
                                    INSERT INTO clientes (nombres,
                                   apellidoPaterno,
                                   apellidoMaterno,
                                   correoElectrónico,
                                   fechaNacimiento,
                                   ubicación,
                                   idCiudad,
                                   contraseña)
                                                 VALUES (?,?,?,?,?,POINT(?,?),?,?);
                                    """;

            PreparedStatement preparedStatement = conexion.prepareStatement(insertCliente, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, cliente.getNombres());
            preparedStatement.setString(2, cliente.getApellidoPaterno());
            preparedStatement.setString(3, cliente.getApellidoMaterno());
            preparedStatement.setString(4, cliente.getCorreo());
            preparedStatement.setDate(5, cliente.getFechaNacimiento());
            preparedStatement.setDouble(6, cliente.getX());
            preparedStatement.setDouble(7, cliente.getY());
            preparedStatement.setInt(8, cliente.getIdCiudad());
            preparedStatement.setString(9, cliente.getContrasenia());


            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas == 0) {
                throw new PersistenciaException("La inserción del cliente falló, no se pudo insertar el registro.");
            }

            int idCliente = 0;
            ResultSet resultado = preparedStatement.getGeneratedKeys();
            if (resultado.next()) {
                idCliente = (resultado.getInt(1));
            }

            resultado.close();
            preparedStatement.close();
            conexion.close();

            return this.buscarPorId(idCliente);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new PersistenciaException("Ocurrió un error al leer la base de datos, inténtelo de nuevo y si el error persiste comuníquese con el encargado del sistema.");
        }
    }
    
    @Override
    public ClienteEntidad buscarPorId(int id) throws PersistenciaException {
        try {
            ClienteEntidad cliente = null;
            Connection conexion = this.conexionBD.obtenerConexion();

            String codigoSQL = """
                               SELECT
                                    idCliente,
                                    nombres,
                                    apellidoPaterno,
                                    apellidoMaterno,
                                    correoElectrónico,
                                    fechaNacimiento,
                                    ubicación,
                                    idCiudad,
                                    contraseña
                               FROM clientes
                               WHERE idCliente = ?
                               """;

            PreparedStatement preparedStatement = conexion.prepareStatement(codigoSQL);
            preparedStatement.setInt(1, id);

            ResultSet resultado = preparedStatement.executeQuery();
            while (resultado.next()) {
                cliente = this.clienteEntidad(resultado);
            }

            resultado.close();
            preparedStatement.close();
            conexion.close();

            return cliente;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new PersistenciaException("Ocurrió un error al leer la base de datos, inténtelo de nuevo y si el error persiste comuníquese con el encargado del sistema.");
        }
    }

    @Override
    public int GuardarCliente(ClienteDTO cliente) throws SQLException {
        int idCliente = 0;
        String insertCliente = """
                                    INSERT INTO clientes (nombres,
                                   apellidoPaterno,
                                   apellidoMaterno,
                                   correoElectrónico,
                                   fechaNacimiento,
                                   ubicación,
                                   idCiudad,
                                   contraseña)
                                                 VALUES (?,?,?,?,?,POINT(?,?),?,?);
                                    """;
        try (PreparedStatement preparedStatement = conexionGeneral.prepareStatement(insertCliente, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, cliente.getNombres());
            preparedStatement.setString(2, cliente.getApellidoPaterno());
            preparedStatement.setString(3, cliente.getApellidoMaterno());
            preparedStatement.setString(4, cliente.getCorreo());
            preparedStatement.setDate(5, cliente.getFechaNacimiento());
            preparedStatement.setDouble(6, cliente.getX());
            preparedStatement.setDouble(7, cliente.getY());
            preparedStatement.setInt(8, cliente.getIdCiudad());
            preparedStatement.setString(9, cliente.getContrasenia());

            preparedStatement.executeUpdate();

            try (ResultSet resultado = preparedStatement.getGeneratedKeys()) {
                if (resultado.next()) {
                    idCliente = resultado.getInt(1);
                }
            }
        }
        return idCliente;
    }

    public ClienteEntidad guardarConTransacion(ClienteDTO cliente) throws PersistenciaException {
        try {
            this.conexionGeneral = this.conexionBD.obtenerConexion();
            this.conexionGeneral.setAutoCommit(false);
            int idCliente = this.GuardarCliente(cliente);
            // Confirmar la transacción
            conexionGeneral.commit();
            return this.buscarPorId(idCliente);
        } catch (SQLException | PersistenciaException ex) {
            try {
                // Deshacer cambios en caso de error
                if (this.conexionGeneral != null) {
                    this.conexionGeneral.rollback();
                }
            } catch (SQLException rollbackEx) {
                System.out.println("Error al hacer rollback: " + rollbackEx.getMessage());
            }
            System.out.println("Error al querer hacer la transaccion " + ex.getMessage());
            throw new PersistenciaException("Ocurrió un error al registrar el cliente, inténtelo de nuevo y si el error persiste comuníquese con el encargado del sistema.");
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
    public ClienteEntidad eliminar(int idCliente) throws PersistenciaException {
        try {

            ClienteEntidad clienteEliminado = this.buscarPorId(idCliente);
            if (clienteEliminado == null) {
                throw new PersistenciaException("Ocurrio un error en obtener la información del cliente por su clave");
            }

            Connection conexion = this.conexionBD.obtenerConexion();

            // Definimos la consulta SQL para actualizar un cliente en la tabla 'clientes'
            String updateCliente = """
                                   DELETE FROM
                                   clientes
                                   WHERE idCliente=?
                                """;

            // Creamos un objeto PreparedStatement para ejecutar la consulta de actualización
            PreparedStatement preparedStatement = conexion.prepareStatement(updateCliente);

            // Asignamos valores a los parámetros de la consulta SQL            
            preparedStatement.setInt(1, idCliente);

            // Ejecutamos la consulta de actualización en la base de datos
            int filasAfectadas = preparedStatement.executeUpdate();

            // Imprimimos el número de filas afectadas por la actualización
            System.out.println("Filas afectadas: " + filasAfectadas);

            // Cerramos el PreparedStatement y la conexión a la base de datos
            preparedStatement.close();
            conexion.close();

            return clienteEliminado;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new PersistenciaException("Ocurrió un error al modificar, inténtelo de nuevo y si el error persiste comuníquese con el encargado del sistema.");
        }
    }

    @Override
    public ClienteEntidad modificar(ClienteDTO cliente) throws PersistenciaException {
        try {

            Connection conexion = this.conexionBD.obtenerConexion();

            // Definimos la consulta SQL para actualizar un cliente en la tabla 'clientes'
            String updateCliente = """
                                UPDATE clientes 
                                SET
                                    nombres =?,
                                    apellidoPaterno =?,
                                    apellidoMaterno =?,
                                    correoElectrónico =?,
                                    fechaNacimiento =?,
                                    ubicación = Point(?,?),
                                    idCiudad =?,
                                    contraseña =?
                                WHERE idcliente = ?
                                """;

            // Creamos un objeto PreparedStatement para ejecutar la consulta de actualización
            PreparedStatement preparedStatement = conexion.prepareStatement(updateCliente);

            // Asignamos valores a los parámetros de la consulta SQL
            preparedStatement.setString(1, cliente.getNombres());
            preparedStatement.setString(2, cliente.getApellidoPaterno());
            preparedStatement.setString(3, cliente.getApellidoMaterno());
            preparedStatement.setString(4, cliente.getCorreo());
            preparedStatement.setDate(5, cliente.getFechaNacimiento());
            preparedStatement.setDouble(6, cliente.getX());
            preparedStatement.setDouble(7, cliente.getY());
            preparedStatement.setInt(8, cliente.getIdCiudad());
            preparedStatement.setString(9, cliente.getContrasenia());
            preparedStatement.setInt(10, cliente.getIdCliente());
            // Ejecutamos la consulta de actualización en la base de datos
            int filasAfectadas = preparedStatement.executeUpdate();

            // Imprimimos el número de filas afectadas por la actualización
            System.out.println("Filas afectadas: " + filasAfectadas);

            // Cerramos el PreparedStatement y la conexión a la base de datos
            preparedStatement.close();
            conexion.close();

            return this.buscarPorId(cliente.getIdCliente());

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new PersistenciaException("Ocurrió un error al modificar, inténtelo de nuevo y si el error persiste comuníquese con el encargado del sistema.");
        }
    }

    private ClienteEntidad clienteEntidad(ResultSet resultado) throws SQLException {
        int id = resultado.getInt("idCliente");
        String nombre = resultado.getString("nombres");
        String paterno = resultado.getString("apellidoPaterno");
        String materno = resultado.getString("apellidoMaterno");
        String correo = resultado.getString("correoElectrónico");
        String contraseña = resultado.getString("contraseña");
        Date fechaNacimiento = resultado.getDate("fechaNacimiento");
        int idCiudad = resultado.getInt("idCiudad");

//        boolean estatus = resultado.getBoolean("estaEliminado");
//        LocalDateTime fechaHoraRegistro = resultado.getTimestamp("fechaHoraRegistro").toLocalDateTime();
        return new ClienteEntidad(id, contraseña, correo, nombre, paterno, materno, fechaNacimiento,idCiudad);
    }
    
    private ClienteTablaDTO clienteTablaDTO(ResultSet resultado) throws SQLException {
        int id = resultado.getInt("idCliente");
        String nombre = resultado.getString("nombres");
        String paterno = resultado.getString("apellidoPaterno");
        String materno = resultado.getString("apellidoMaterno");
        Date fechaNacimiento = resultado.getDate("fechaNacimiento");
        int idCiudad = resultado.getInt("idCiudad");
        return new ClienteTablaDTO(id, nombre, paterno, materno, fechaNacimiento,idCiudad);
    }


    public boolean validarCliente(String correo, String password) throws SQLException {
        String sql = "SELECT contraseña FROM Clientes WHERE correoElectrónico = ?";

        try (Connection conn = conexionBD.obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHashedPassword = rs.getString("contraseña");
                    return Encriptador.checkPassword(password, storedHashedPassword);
                } else {
                    return false; // Correo no encontrado
                }
            }
        }
    }
    
    @Override
    public ClienteEntidad buscarPorCorreo(String correo) throws PersistenciaException {
    try {
        ClienteEntidad cliente = null;
        Connection conexion = this.conexionBD.obtenerConexion();

        String codigoSQL = """
                           SELECT
                                idCliente,
                                nombres,
                                apellidoPaterno,
                                apellidoMaterno,
                                correoElectrónico,
                                fechaNacimiento,
                                ubicación,
                                idCiudad,
                                contraseña
                           FROM clientes
                           WHERE correoElectrónico = ?
                           """;

        PreparedStatement preparedStatement = conexion.prepareStatement(codigoSQL);
        preparedStatement.setString(1, correo);

        ResultSet resultado = preparedStatement.executeQuery();
        if (resultado.next()) {
            cliente = this.clienteEntidad(resultado);
        }

        resultado.close();
        preparedStatement.close();
        conexion.close();

        return cliente;
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
        throw new PersistenciaException("Ocurrió un error al leer la base de datos.");
    }
}

}
