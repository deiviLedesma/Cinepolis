/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dtos.CiudadDTO;
import dtos.FiltroTablaDTO;
import dtos.SalaDTO;
import entidad.CiudadEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author filor
 */
public class CiudadDAO implements ICiudadDAO {
    private IConexionBD conexionBD;

    public CiudadDAO() {
        this.conexionBD = new ConexionBD();
    }

    public CiudadDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    
    @Override
    public List<CiudadDTO> buscarCiudadTabla() throws PersistenciaException {
        try {
            List<CiudadDTO> lista = null;
            Connection conexion = this.conexionBD.obtenerConexion();

            String codigoSQL = """
                               SELECT
                               idCiudad,
                               nombre,
                               cantidadHabitantes,
                               idPaís
                                FROM Ciudades
                               """;

            PreparedStatement preparedStatement = conexion.prepareStatement(codigoSQL);

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
    
    public void insertarCiudad(CiudadDTO ciudad)throws PersistenciaException{
        try {
            Connection conexion = this.conexionBD.obtenerConexion();
        
            String insertCliente = """
                                    INSERT INTO ciudades (nombre,
                                   cantidadHabitantes,
                                   idPaís)
                                                 VALUES (?,?,?);
                                    """;
            
            PreparedStatement preparedStatement = conexion.prepareStatement(insertCliente, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, ciudad.getNombre());
            preparedStatement.setInt(2, ciudad.getNumeroHabitantes());
            preparedStatement.setInt(3, ciudad.getIdPais());
            
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
    
    private CiudadDTO funcionTablaDTO(ResultSet rs) throws SQLException {
        int id = rs.getInt("idCiudad");
        String nombre = rs.getString("nombre");
        int cant = rs.getInt("cantidadHabitantes");
        int ciudad = rs.getInt("idPaís");
        return new CiudadDTO(id,nombre,cant,ciudad);
    }
}
