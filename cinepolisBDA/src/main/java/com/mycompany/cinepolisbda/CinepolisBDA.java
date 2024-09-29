/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.cinepolisbda;

import dtos.ClienteDTO;
import dtos.FiltroTablaDTO;
import dtos.CompraDTO;
import dtos.CompraFuncionDTO;
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
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.logging.Level;
import java.util.logging.Logger;
import negocio.CiudadService;
import negocio.ClasificacionService;
import negocio.ClienteService;
import negocio.FuncionService;
import negocio.GeneroService;
import negocio.NegocioException;
import negocio.PaisService;
import negocio.SalaService;
import persistencia.CiudadDAO;
import persistencia.ClasificacionDAO;
import persistencia.ClienteDAO;
import persistencia.CompraDAO;
import persistencia.CompraFuncionDAO;
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

    public static void main(String[] args) throws SQLException {


        ClienteService cs = new ClienteService();
        ClienteDTO ce = new ClienteDTO(2, "pasword", "PacoEs@gmail", "Paco2", "Escamilla2", "Dueewwas2", Date.valueOf("2024-09-28"), 21, 12, 1);

//        try {
//            cs.eliminar(4);
//            cs.modificar(ce);
//        } catch (NegocioException ex) {
//            Logger.getLogger(CinepolisBDA.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        ClienteDAO cliente = new ClienteDAO();
        FiltroTablaDTO filtro = new FiltroTablaDTO(2,0,"");
        
        FuncionService fs = new FuncionService();
        
        SalaService ss = new SalaService();
        CiudadService ciudad = new CiudadService();
        
        GeneroService genero = new GeneroService();
        ClasificacionService clasificacion = new ClasificacionService();
        PaisService pais = new PaisService();
        try {
                System.out.println(ciudad.buscarCiudadesTabla().get(0));
            for (int i = 0; i < 2; i++) {
                System.out.println(pais.buscarCiudadesTabla().get(i));
                System.out.println(genero.buscarClasificacionTabla().get(i));
                System.out.println(clasificacion.buscarClasificacionTabla().get(i));
            }
            
        } catch (NegocioException ex) {
            Logger.getLogger(CinepolisBDA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PersistenciaException ex) {
            Logger.getLogger(CinepolisBDA.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
