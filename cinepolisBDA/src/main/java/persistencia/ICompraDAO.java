/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia;

import dtos.CompraDTO;
import entidad.CompraEntidad;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author filor
 */
public interface ICompraDAO {
    int guardarCompra(CompraDTO compra) throws SQLException;
    
    CompraEntidad guardarConTransacion(CompraDTO compra) throws PersistenciaException;
    
    CompraEntidad obtenerCompraPorId(int id) throws SQLException;
    
    CompraEntidad compraEntidad(ResultSet rs) throws SQLException;
}
