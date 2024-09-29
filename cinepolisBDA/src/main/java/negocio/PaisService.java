/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import dtos.CiudadDTO;
import dtos.PaisDTO;
import java.util.List;
import persistencia.IPaisDAO;
import persistencia.PaisDAO;
import persistencia.PersistenciaException;

/**
 *
 * @author filor
 */
public class PaisService {

    private IPaisDAO paisDAO;

    public PaisService() {
        this.paisDAO = new PaisDAO();
    }

    public PaisService(IPaisDAO paisDAO) {
        this.paisDAO = paisDAO;
    }
    
     public List<PaisDTO> buscarCiudadesTabla() throws NegocioException, PersistenciaException {
        List<PaisDTO> lista = this.paisDAO.buscarPaisTabla();
        if (lista == null) {
            throw new NegocioException("No se encontraron registros con los filtros");
        }
        return lista;
    }
}
