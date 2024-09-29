/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import dtos.ClienteTablaDTO;
import dtos.FiltroTablaDTO;
import dtos.FuncionDTO;
import dtos.SalaDTO;
import entidad.FuncionEntidad;
import entidad.SalaEntidad;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.FuncionDAO;
import persistencia.IFuncionDAO;
import persistencia.PersistenciaException;
import utilerias.Utilidades;

/**
 *
 * @author filor
 */
public class FuncionService {
    private IFuncionDAO funcionDAO;

    public FuncionService() {
        this.funcionDAO = new FuncionDAO();
    }

    public FuncionService(IFuncionDAO funcionDAO) {
        this.funcionDAO = funcionDAO;
    }
    
    public List<FuncionDTO> buscarFuncionesTabla(FiltroTablaDTO filtro) throws NegocioException {
        try {
            this.validarParametrosEnBuscarTabla(filtro);
            int offset = this.obtenerOFFSETMySQL(filtro.getLimit(), filtro.getOffset());
            filtro.setOffset(offset);

            List<FuncionDTO> lista = this.funcionDAO.buscarClientesTabla(filtro);
            if (lista == null) {
                throw new NegocioException("No se encontraron registros con los filtros");
            }
            return lista;
        } catch (PersistenciaException ex) {
            System.out.println(ex.getMessage());
            throw new NegocioException(ex.getMessage());
        }
    }
    
    public FuncionDTO buscarPorId(int id) throws NegocioException, SQLException {
        if (id <= 0) {
            throw new NegocioException("El id recibido es incorrecto");
        }
        FuncionEntidad funcion  = this.funcionDAO.obtenerPorId(id);
        System.out.println(funcion);
        FuncionDTO funcionDTO = this.convertirAFuncionDTO(funcion);
        System.out.println(funcionDTO);
        return funcionDTO;
    }
    
    public FuncionDTO guardar(FuncionDTO dto) throws NegocioException, SQLException {
        try {
            this.validarCampos(dto);
            FuncionEntidad guardado = this.funcionDAO.guardarConTransacion(dto);
            System.out.println(guardado);
            return this.convertirAFuncionDTO(guardado);
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }
    
    public FuncionDTO modificar(FuncionDTO dto) throws NegocioException, SQLException {
        this.validarCampos(dto);
        FuncionEntidad buscado = this.funcionDAO.obtenerPorId(dto.getIdFuncion());
        if (buscado == null) {
            throw new NegocioException("No se pudo obtener la sala con los parametros ingresados");
        }
        FuncionEntidad modificado = this.funcionDAO.obtenerPorId(dto.getIdFuncion());
        System.out.println(modificado);
        return this.convertirAFuncionDTO(modificado);
    }
    
    
    private void validarCampos(FuncionDTO funcion) throws NegocioException {
        Time inicioFuncion = funcion.getHoraIniciaFuncion();
        double precio = funcion.getPrecio();
        if (inicioFuncion == null) {
            throw new NegocioException("La hora de inicio no puede estar en blanco.");
        }
        if (precio<0) {
            throw new NegocioException("El precio no puede ser negativo.");
        }
    }
    
    private FuncionDTO convertirAFuncionDTO(FuncionEntidad entidad) {
        return new FuncionDTO(
                entidad.getIdFuncion(),
                entidad.getHoraIniciaFuncion(),
                entidad.getHoraAcabaFuncion(),
                entidad.getHoraAcabaPelicula(),
                entidad.getDia(),
                entidad.getPrecio(),
                entidad.getDia(),
                entidad.getIdPelicula()
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
