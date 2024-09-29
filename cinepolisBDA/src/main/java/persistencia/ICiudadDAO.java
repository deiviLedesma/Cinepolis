/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia;

import dtos.CiudadDTO;

/**
 *
 * @author filor
 */
public interface ICiudadDAO {
    void insertarCiudad(CiudadDTO ciudad)throws PersistenciaException;
}
