/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio;

import dtos.ClienteDTO;
import dtos.ClienteTablaDTO;
import dtos.FiltroTablaDTO;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author filor
 */
public interface IClienteService {
    List<ClienteTablaDTO> buscarClientesTabla(FiltroTablaDTO filtro) throws NegocioException;
    
    ClienteDTO buscarPorId(int id) throws NegocioException;
    
    ClienteDTO buscarPorCorreo(String correo) throws NegocioException;
    
    ClienteDTO guardar(ClienteDTO cliente) throws NegocioException, SQLException;
    
    public ClienteDTO modificar(ClienteDTO cliente) throws NegocioException;
    
    ClienteDTO eliminar(int id) throws NegocioException;
}
