/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.cinepolisbda;

import dtos.ClienteDTO;
import dtos.FuncionDTO;
import dtos.PeliculaDTO;
import dtos.SalaDTO;
import dtos.SucursalDTO;
import entidad.CiudadEntidad;
import entidad.ClasificacionEntidad;
import entidad.ClienteEntidad;
import entidad.GeneroEntidad;
import entidad.PaisEntidad;
import java.awt.Point;
import java.sql.Date;
import java.sql.SQLDataException;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.CiudadDAO;
import persistencia.ClasificacionDAO;
import persistencia.ClienteDAO;
import persistencia.ConexionBD;
import persistencia.FuncionDAO;
import persistencia.GeneroDAO;
import persistencia.IConexionBD;
import persistencia.PaisDAO;
import persistencia.PeliculaDAO;
import persistencia.PersistenciaException;
import persistencia.SalaDAO;
import persistencia.SucursalDAO;

/**
 *
 * @author filor
 */
public class CinepolisBDA {

    public static void main(String[] args) {

        IConexionBD coneccion = new ConexionBD();
        PaisDAO paisDAO = new PaisDAO(coneccion);

        CiudadDAO ciudadDAO = new CiudadDAO();
        CiudadEntidad ciudad = new CiudadEntidad(1, "Obregon", 50000, 1);

        ClasificacionDAO clasificacionDAO = new ClasificacionDAO();

        GeneroDAO gdao = new GeneroDAO();
        GeneroEntidad ge = new GeneroEntidad(1, "PG-13");
        GeneroEntidad ge2 = new GeneroEntidad(2, "R");

        ClienteDAO cdao = new ClienteDAO();
        ClienteDTO ce = new ClienteDTO(1, "pasword", "dwasdawd@gmail", "Mario", "Castañeda", "Dueewwas", new Date(55 - 9 - 24), 21, 12, 1);

        SucursalDAO sdao = new SucursalDAO();
        SucursalDTO sdto = new SucursalDTO(1, "cendero", 27.483538, -109.959594, 1);
//        
//        try {
//            sdao.guardarConTransacion(sdto);
//        } catch (PersistenciaException ex) {
//            Logger.getLogger(CinepolisBDA.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        SalaDAO salaDAO = new SalaDAO();
        SalaDTO salaDTO = new SalaDTO(1,"VIP",20,20,70,1);
        
        FuncionDAO fdao = new FuncionDAO();
        
        Time time = Time.valueOf("14:23:10");
        FuncionDTO fdto = new FuncionDTO(1,time,Time.valueOf("16:40:30"),time,2,12.3,1,1);
        
        try {
            fdao.guardarConTransacion(fdto);
        } catch (PersistenciaException ex) {
            Logger.getLogger(CinepolisBDA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
