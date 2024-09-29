/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia;

import dtos.PaisDTO;
import java.util.List;

/**
 *
 * @author filor
 */
public interface IPaisDAO {
    void insertarPais(PaisDTO paisEntidad) throws PersistenciaException;
    
    List<PaisDTO> buscarPaisTabla() throws PersistenciaException;
}
