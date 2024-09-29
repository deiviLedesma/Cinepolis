/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia;

import dtos.FiltroTablaDTO;
import dtos.SalaDTO;
import entidad.SalaEntidad;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author filor
 */
public interface ISalaDAO {
    SalaEntidad actualizarSala(SalaEntidad sala) throws SQLException;
    
    SalaEntidad eliminarSala(int idSala) throws PersistenciaException;
    
    SalaEntidad guardar(SalaDTO sala) throws PersistenciaException;
    
    SalaEntidad obtenerSalaPorId(int idSala) throws SQLException; 
    
    SalaEntidad guardarConTransacion(SalaDTO sala) throws PersistenciaException;
    
    List<SalaDTO> buscarSalaTabla(FiltroTablaDTO filtro) throws PersistenciaException;
}
