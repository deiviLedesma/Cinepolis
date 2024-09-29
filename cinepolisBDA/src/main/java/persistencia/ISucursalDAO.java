/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia;

import dtos.CiudadDTO;
import dtos.SucursalDTO;
import entidad.SucursalEntidad;
import java.sql.SQLException;

/**
 *
 * @author filor
 */
public interface ISucursalDAO {
    SucursalEntidad guardarConTransacion(SucursalDTO sucursal) throws PersistenciaException;
    
    SucursalEntidad obtenerSucursalPorId(int idSucursal) throws SQLException;
    
    
}
