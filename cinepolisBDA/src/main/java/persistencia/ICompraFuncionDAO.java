/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia;

import dtos.CompraFuncionDTO;
import entidad.CompraFuncionEntidad;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author filor
 */
public interface ICompraFuncionDAO {
    int guardarCompraFuncion(CompraFuncionDTO compra) throws SQLException;
    
    CompraFuncionEntidad guardarConTransacion(CompraFuncionDTO compra) throws PersistenciaException;
    
    CompraFuncionEntidad obtenerCompraPorId(int id) throws SQLException;
    
}
