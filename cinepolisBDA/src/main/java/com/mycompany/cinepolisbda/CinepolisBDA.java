/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cinepolisbda;

import entidad.CiudadEntidad;
import entidad.ClasificacionEntidad;
import entidad.ClienteEntidad;
import entidad.GeneroEntidad;
import entidad.PaisEntidad;
import java.awt.Point;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.CiudadDAO;
import persistencia.ClasificacionDAO;
import persistencia.ClienteDAO;
import persistencia.ConexionBD;
import persistencia.GeneroDAO;
import persistencia.IConexionBD;
import persistencia.PaisDAO;
import persistencia.PersistenciaException;

/**
 *
 * @author filor
 */
public class CinepolisBDA {

    public static void main(String[] args) {
        
        IConexionBD coneccion = new ConexionBD();
        PaisDAO paisDAO = new PaisDAO(coneccion);
        
        CiudadDAO ciudadDAO = new CiudadDAO();
        CiudadEntidad ciudad = new CiudadEntidad(1,"Obregon",50000,1);
        
        ClasificacionDAO clasificacionDAO = new ClasificacionDAO();
        
        
        GeneroDAO gdao = new GeneroDAO();
        GeneroEntidad ge = new GeneroEntidad(1,"PG-13");
        GeneroEntidad ge2 = new GeneroEntidad(2,"R");
        
        ClienteDAO cdao = new ClienteDAO();
        ClienteEntidad ce = new ClienteEntidad(1, "contraseña","correo@gmail", "manuel","Lopez","Dueñas",new Date(55-9-24),new Point(),1);
        try {
            cdao.insertarCliente(ce);
        } catch (PersistenciaException ex) {
            Logger.getLogger(CinepolisBDA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
