/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import dtos.ClasificacionDTO;
import dtos.GeneroDTO;
import java.util.List;
import persistencia.GeneroDAO;
import persistencia.IGeneroDAO;
import persistencia.PersistenciaException;

/**
 *
 * @author filor
 */
public class GeneroService {
    private IGeneroDAO genero;

    public GeneroService() {
        this.genero = new GeneroDAO();
    }

    public GeneroService(IGeneroDAO genero) {
        this.genero = genero;
    }
    
    public List<GeneroDTO> buscarClasificacionTabla() throws NegocioException, PersistenciaException {
        List<GeneroDTO> lista = this.genero.buscarGeneroTabla();
        if (lista == null) {
            throw new NegocioException("No se encontraron registros con los filtros");
        }
        return lista;
    }
    
}
