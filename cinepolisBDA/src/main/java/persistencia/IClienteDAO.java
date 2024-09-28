/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia;

import dtos.ClienteDTO;
import dtos.FiltroTablaDTO;
import dtos.ClienteTablaDTO;
import entidad.ClienteEntidad;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author filor
 */
public interface IClienteDAO {
    
    public List<ClienteTablaDTO> buscarClientesTabla(FiltroTablaDTO filtro) throws PersistenciaException;
    
    public ClienteEntidad guardar(ClienteDTO cliente) throws PersistenciaException;
            
    int GuardarCliente(ClienteDTO cliente) throws SQLException;

    ClienteEntidad modificar(ClienteDTO cliente) throws PersistenciaException;

    ClienteEntidad eliminar(int idCliente) throws PersistenciaException;

    ClienteEntidad buscarPorId(int id) throws PersistenciaException;
    
    
}
