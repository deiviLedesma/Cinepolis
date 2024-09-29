/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import dtos.CiudadDTO;
import dtos.FiltroTablaDTO;
import dtos.SalaDTO;
import java.util.List;
import persistencia.CiudadDAO;
import persistencia.ICiudadDAO;
import persistencia.PersistenciaException;

/**
 *
 * @author filor
 */
public class CiudadService {
    private ICiudadDAO ciudadDAO;

    public CiudadService() {
        this.ciudadDAO = new CiudadDAO();
    }

    public CiudadService(ICiudadDAO ciudadDAO) {
        this.ciudadDAO = ciudadDAO;
    }
    
    public List<CiudadDTO> buscarCiudadesTabla() throws NegocioException, PersistenciaException {
        List<CiudadDTO> lista = this.ciudadDAO.buscarCiudadTabla();
        if (lista == null) {
            throw new NegocioException("No se encontraron registros con los filtros");
        }
        return lista;
    }
}
