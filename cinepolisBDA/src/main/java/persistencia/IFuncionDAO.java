/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia;

import dtos.FiltroTablaDTO;
import dtos.FuncionDTO;
import entidad.FuncionEntidad;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author filor
 */
public interface IFuncionDAO {
    FuncionEntidad guardarConTransacion(FuncionDTO funcion) throws PersistenciaException;
    
    FuncionEntidad obtenerPorId(int id) throws SQLException;
    
    List<FuncionDTO> buscarClientesTabla(FiltroTablaDTO filtro) throws PersistenciaException;
}
