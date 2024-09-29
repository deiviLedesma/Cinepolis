/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia;

import dtos.ClasificacionDTO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author filor
 */
public interface IClasificacionDAO {
    void insertarClasificacion(ClasificacionDTO clasificacion)throws PersistenciaException;
    
    void EliminarClasificacion(int idClasificacion) throws PersistenciaException, SQLException;
    
    List<ClasificacionDTO> buscarClasificacionTabla() throws PersistenciaException;
}
