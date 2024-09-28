/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dtos.PaisDTO;
import entidad.PaisEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author filor
 */
public class PaisDAO {
    private IConexionBD conexionBD;

    public PaisDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    
    public PaisDAO() {
        this.conexionBD = new ConexionBD();
    }
    
    public void insertarPais(PaisDTO paisEntidad) throws PersistenciaException{
        try {
            Connection conexion = this.conexionBD.obtenerConexion();
        
            String insertCliente = """
                                    INSERT INTO paises (nombrePais,
                                   cantidadHabitantes)
                                                 VALUES (?,?);
                                    """;
            
            PreparedStatement preparedStatement = conexion.prepareStatement(insertCliente, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, paisEntidad.getNombre());
            preparedStatement.setInt(2, paisEntidad.getNumeroHabitantes());
            
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
