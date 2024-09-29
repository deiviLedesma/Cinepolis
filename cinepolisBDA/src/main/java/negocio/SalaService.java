/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import dtos.ClienteDTO;
import dtos.FiltroTablaDTO;
import dtos.FuncionDTO;
import dtos.SalaDTO;
import entidad.ClienteEntidad;
import entidad.SalaEntidad;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.ISalaDAO;
import persistencia.PersistenciaException;
import persistencia.SalaDAO;
import utilerias.Utilidades;

/**
 *
 * @author filor
 */
public class SalaService {
    private ISalaDAO salaDAO;

    public SalaService() {
        this.salaDAO = new SalaDAO();
    }

    public SalaService(ISalaDAO salaDAO) {
        this.salaDAO = salaDAO;
    }
    
    public List<SalaDTO> buscarFuncionesTabla(FiltroTablaDTO filtro) throws NegocioException {
        try {
            this.validarParametrosEnBuscarTabla(filtro);
            int offset = this.obtenerOFFSETMySQL(filtro.getLimit(), filtro.getOffset());
            filtro.setOffset(offset);

            List<SalaDTO> lista = this.salaDAO.buscarSalaTabla(filtro);
            if (lista == null) {
                throw new NegocioException("No se encontraron registros con los filtros");
            }
            return lista;
        } catch (PersistenciaException ex) {
            System.out.println(ex.getMessage());
            throw new NegocioException(ex.getMessage());
        }
    }
    
    public SalaDTO buscarPorId(int id) throws NegocioException, SQLException {
        if (id <= 0) {
            throw new NegocioException("El id recibido es incorrecto");
        }
        SalaEntidad salaBuscada  = this.salaDAO.obtenerSalaPorId(id);
        System.out.println(salaBuscada);
        SalaDTO salaDTO = this.convertirASalaDTO(salaBuscada);
        System.out.println(salaDTO);
        return salaDTO;
    }
    
    public SalaDTO guardar(SalaDTO sala) throws NegocioException, SQLException {
        try {
            this.validarCampos(sala);
            SalaEntidad salaGuardado = this.salaDAO.guardar(sala);
            System.out.println(salaGuardado);
            return this.convertirASalaDTO(salaGuardado);
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }
    
    public SalaDTO modificar(SalaDTO sala) throws NegocioException, SQLException {
        this.validarCampos(sala);
        SalaEntidad salaBuscada = this.salaDAO.obtenerSalaPorId(sala.getIdSala());
        if (salaBuscada == null) {
            throw new NegocioException("No se pudo obtener la sala con los parametros ingresados");
        }
        SalaEntidad salaModificada = this.salaDAO.actualizarSala(salaBuscada);
        System.out.println(salaModificada);
        return this.convertirASalaDTO(salaModificada);
    }
    
    public SalaDTO eliminar(int id) throws NegocioException {
        try {
            if (id <= 0) {
                throw new NegocioException("El id recibido es incorrecto");
            }
            SalaEntidad salaBuscada = this.salaDAO.obtenerSalaPorId(id);
            if (salaBuscada == null) {
                throw new NegocioException("No se pudo obtener la sala con la clave ingresada");
            }
            SalaEntidad salaEliminada = this.salaDAO.eliminarSala(id);
            System.out.println(salaEliminada);
            return this.convertirASalaDTO(salaEliminada);
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(SalaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void validarCampos(SalaDTO sala) throws NegocioException {
        String nombre = sala.getNombre();
        double precio = sala.getPrecioActual();
        int asientos = sala.getAsientosDisponibles();
        if (nombre == null || nombre.isEmpty() || nombre.length() > 40) {
            throw new NegocioException("El nombre no debe estar en blanco y tampoco debe de pasar los 40 caracteres.");
        }
        if (precio<0) {
            throw new NegocioException("El precio no puede ser negativo.");
        }
        if (asientos<0) {
            throw new NegocioException("La cantidad asientos no puede ser negativa.");
        }
    }
    
    private SalaDTO convertirASalaDTO(SalaEntidad sala) {
        return new SalaDTO(
                sala.getIdSala(),
                sala.getNombre(),
                sala.getAsientosDisponibles(),
                sala.getTiempoLimpiezaEnMinutos(),
                sala.getPrecioActual(),
                sala.getIdSucursal()
        );
    }
    
    private boolean esNumeroNegativo(int numero) {
        return numero < 0;
    }
    
    private int obtenerOFFSETMySQL(int limit, int pagina) {
        return new Utilidades().RegresarOFFSETMySQL(limit, pagina);
    }
    
    private void validarParametrosEnBuscarTabla(FiltroTablaDTO filtro) throws NegocioException {
        if (this.esNumeroNegativo(filtro.getLimit())) {
            throw new NegocioException("El parametro limite no puede ser negativo");
        }
        if (this.esNumeroNegativo(filtro.getOffset())) {
            throw new NegocioException("El parametro pagina no puede ser negativo");
        }
    }
}
