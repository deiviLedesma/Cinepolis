/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dtos.ClasificacionDTO;
import dtos.PaisDTO;
import entidad.ClasificacionEntidad;
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
public class ClasificacionDAO implements IClasificacionDAO {
    private IConexionBD conexionBD;

    public ClasificacionDAO() {
        this.conexionBD = new ConexionBD();
    }

    public ClasificacionDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    
    @Override
    public List<ClasificacionDTO> buscarClasificacionTabla() throws PersistenciaException {
        try {
            List<ClasificacionDTO> lista = null;
            Connection conexion = this.conexionBD.obtenerConexion();

            String codigoSQL = """
                               SELECT
                               idClasificacion,
                               nombre
                                FROM clasificaciones
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
    
    @Override
    public void insertarClasificacion(ClasificacionDTO clasificacion)throws PersistenciaException{
        try {
            Connection conexion = this.conexionBD.obtenerConexion();
        
            String insertCliente = """
                                    INSERT INTO clasificaciones (nombre)
                                                 VALUES (?);
                                    """;
            
            PreparedStatement preparedStatement = conexion.prepareStatement(insertCliente, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, clasificacion.getNombre());
            
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
    
    @Override
       public void EliminarClasificacion(int idClasificacion) throws PersistenciaException, SQLException {

        String eliminar = "DELETE FROM Clasificaciones  WHERE idClasificacion = ?";

        try (Connection connection = conexionBD.obtenerConexion(); PreparedStatement stmt = connection.prepareStatement(eliminar)) {
            stmt.setInt(1, idClasificacion);
            stmt.executeUpdate();
        }catch(SQLException e){
            throw new PersistenciaException("No se pudo eliminar la clasificación porque no se encontró");
        }
    }
    
       private ClasificacionDTO funcionTablaDTO(ResultSet rs) throws SQLException {
        int id = rs.getInt("idClasificacion");
        String nombre = rs.getString("nombre");
        return new ClasificacionDTO(id,nombre);
    }
}
