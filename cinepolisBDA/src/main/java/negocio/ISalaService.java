/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio;

import dtos.FiltroTablaDTO;
import dtos.SalaDTO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author filor
 */
public interface ISalaService {
     List<SalaDTO> buscarSalasTabla(FiltroTablaDTO filtro) throws NegocioException;
     
     SalaDTO buscarPorId(int id) throws NegocioException, SQLException;
     
     SalaDTO guardar(SalaDTO sala) throws NegocioException, SQLException;
     
     SalaDTO modificar(SalaDTO sala) throws NegocioException, SQLException;
     
     SalaDTO eliminar(int id) throws NegocioException;
}
