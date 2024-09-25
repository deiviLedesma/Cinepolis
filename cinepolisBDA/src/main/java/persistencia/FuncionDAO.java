/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import entidad.ClienteEntidad;
import entidad.FuncionEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author filor
 */
public class FuncionDAO {
    private IConexionBD conexionBD;

    public FuncionDAO() {
        this.conexionBD = new ConexionBD();
    }

    public FuncionDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    
    public void insertarFuncion(FuncionEntidad funcion)throws PersistenciaException{
        try {
            Connection conexion = this.conexionBD.obtenerConexion();
        
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
            
            PreparedStatement preparedStatement = conexion.prepareStatement(insertCliente, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTime(1, funcion.getHoraIniciaFuncion());
            preparedStatement.setTime(2, funcion.getHoraAcabaFuncion());
            preparedStatement.setTime(3, funcion.getHoraAcabaPelicula());
            preparedStatement.setInt(4, funcion.getDia());
            preparedStatement.setDouble(5, funcion.getPrecio());
            preparedStatement.setInt(6, funcion.getIdSala());
            preparedStatement.setInt(7, funcion.getIdPelicula());
            
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
    
    //agregar estatus de eliminado mas no eliminar
}
