/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import entidad.CiudadEntidad;
import entidad.ClienteEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author filor
 */
public class ClienteDAO {
    private IConexionBD conexionBD;

    public ClienteDAO() {
        this.conexionBD = new ConexionBD();
    }

    public ClienteDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    
    public void insertarCliente(ClienteEntidad cliente)throws PersistenciaException{
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
            preparedStatement.setDouble(6, cliente.getUbicacion().x);
            preparedStatement.setDouble(7, cliente.getUbicacion().y);
            preparedStatement.setInt(8, cliente.getIdCiudad());
            preparedStatement.setString(9, cliente.getContrasenia());
            
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
