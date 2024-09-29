/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import dtos.CiudadDTO;
import dtos.ClasificacionDTO;
import java.util.List;
import persistencia.ClasificacionDAO;
import persistencia.IClasificacionDAO;
import persistencia.PersistenciaException;

/**
 *
 * @author filor
 */
public class ClasificacionService {
    private IClasificacionDAO clasificacionDAO;

    public ClasificacionService() {
        this.clasificacionDAO = new ClasificacionDAO();
    }

    public ClasificacionService(IClasificacionDAO clasificacionDAO) {
        this.clasificacionDAO = clasificacionDAO;
    }
    
    public List<ClasificacionDTO> buscarClasificacionTabla() throws NegocioException, PersistenciaException {
        List<ClasificacionDTO> lista = this.clasificacionDAO.buscarClasificacionTabla();
        if (lista == null) {
            throw new NegocioException("No se encontraron registros con los filtros");
        }
        return lista;
    }
}
