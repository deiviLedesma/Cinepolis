/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia;

import dtos.GeneroDTO;
import java.util.List;

/**
 *
 * @author filor
 */
public interface IGeneroDAO {
    void insertarGenero(GeneroDTO genero)throws PersistenciaException;
    
    List<GeneroDTO> buscarGeneroTabla() throws PersistenciaException;
}
