/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import dtos.ClienteDTO;
import dtos.FiltroTablaDTO;
import dtos.ClienteTablaDTO;
import entidad.ClienteEntidad;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import persistencia.ClienteDAO;
import persistencia.IClienteDAO;
import persistencia.PersistenciaException;
import utilerias.Utilidades;

/**
 *
 * @author filor
 */
public class ClienteService {
    private IClienteDAO clienteDAO;

    public ClienteService(IClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }
    
    public List<ClienteTablaDTO> buscarClientesTabla(FiltroTablaDTO filtro) throws NegocioException {
        try {
            this.validarParametrosEnBuscarClienteTabla(filtro);
            int offset = this.obtenerOFFSETMySQL(filtro.getLimit(), filtro.getOffset());
            filtro.setOffset(offset);

            List<ClienteTablaDTO> clientesLista = this.clienteDAO.buscarClientesTabla(filtro);
            if (clientesLista == null) {
                throw new NegocioException("No se encontraron registros con los filtros");
            }
            return clientesLista;
        } catch (PersistenciaException ex) {
            System.out.println(ex.getMessage());
            throw new NegocioException(ex.getMessage());
        }
    }
    
    public ClienteDTO buscarPorId(int id) throws NegocioException {
        try {
            if (id <= 0) {
                throw new NegocioException("El id recibido es incorrecto");
            }
            ClienteEntidad clienteBuscado = this.clienteDAO.buscarPorId(id);
            System.out.println(clienteBuscado);
            ClienteDTO clienteDTO = this.convertirAClienteDTO(clienteBuscado);
            System.out.println(clienteDTO);
            return clienteDTO;
        } catch (PersistenciaException ex) {
            System.out.println(ex.getMessage());
            throw new NegocioException(ex.getMessage());
        }
    }
    
    public ClienteDTO buscarPorCorreo(String correo) throws NegocioException {
        try {
            if (correo == null) {
                throw new NegocioException("Ingresar un correo");
            }
            ClienteEntidad clienteBuscado = this.clienteDAO.buscarPorCorreo(correo);
            System.out.println(clienteBuscado);
            ClienteDTO clienteDTO = this.convertirAClienteDTO(clienteBuscado);
            System.out.println(clienteDTO);
            return clienteDTO;
        } catch (PersistenciaException ex) {
            System.out.println(ex.getMessage());
            throw new NegocioException(ex.getMessage());
        }
    }
    
    
    public ClienteDTO guardar(ClienteDTO cliente) throws NegocioException, SQLException {
        try {
            this.validarCampos(cliente);
            ClienteEntidad clienteGuardado = this.clienteDAO.guardar(cliente);
            System.out.println(clienteGuardado);
            return this.convertirAClienteDTO(clienteGuardado);
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }
    
    public ClienteDTO modificar(ClienteDTO cliente) throws NegocioException {
        try {
            this.validarCampos(cliente);
            ClienteEntidad clienteBuscado = this.clienteDAO.buscarPorId(cliente.getIdCliente());
            if (clienteBuscado == null) {
                throw new NegocioException("No se pudo obtener el cliente con los parametros ingresados");
            }
            ClienteEntidad clienteModificado = this.clienteDAO.modificar(cliente);
            System.out.println(clienteModificado);
            return this.convertirAClienteDTO(clienteModificado);
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }
    
    public ClienteDTO eliminar(int id) throws NegocioException {
        try {
            if (id <= 0) {
                throw new NegocioException("El id recibido es incorrecto");
            }
            ClienteEntidad clienteBuscado = this.clienteDAO.buscarPorId(id);
            if (clienteBuscado == null) {
                throw new NegocioException("No se pudo obtener el cliente con la clave ingresada");
            }
            ClienteEntidad clienteEliminado = this.clienteDAO.eliminar(id);
            System.out.println(clienteEliminado);
            return this.convertirAClienteDTO(clienteEliminado);
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }
    
    private void validarCampos(ClienteDTO cliente) throws NegocioException {
        String nombre = cliente.getNombres();
        String apellidoPaterno = cliente.getApellidoPaterno();
        String apellidoMaterno = cliente.getApellidoMaterno();
        String contraseña = cliente.getContrasenia();
        String correo = cliente.getCorreo();
        if (nombre == null || nombre.isEmpty() || nombre.length() > 40) {
            throw new NegocioException("El nombre no debe estar en blanco y tampoco debe de pasar los 40 caracteres.");
        }
        if (apellidoPaterno == null || apellidoPaterno.isEmpty() || apellidoPaterno.length() > 30) {
            throw new NegocioException("El apellido paterno no debe estar en blanco y tampoco debe de pasar los 30 caracteres.");
        }
        if (apellidoMaterno == null || apellidoMaterno.isEmpty() || apellidoMaterno.length() > 30) {
            throw new NegocioException("El apellido materno no debe estar en blanco y tampoco debe de pasar los 30 caracteres.");
        }
        if (contraseña == null || apellidoMaterno.isEmpty() || apellidoMaterno.length() > 50) {
            throw new NegocioException("La contraseña no debe estar en blanco y tampoco debe de pasar los 50 caracteres.");
        }
        if (correo == null || apellidoMaterno.isEmpty() || apellidoMaterno.length() > 40) {
            throw new NegocioException("El correo no debe estar en blanco y tampoco debe de pasar los 40 caracteres.");
        }
    }
    
    private ClienteDTO convertirAClienteDTO(ClienteEntidad cliente) {
        return new ClienteDTO(
                cliente.getIdCliente(),
                cliente.getContrasenia(),
                cliente.getCorreo(),
                cliente.getNombres(),
                cliente.getApellidoPaterno(),
                cliente.getApellidoMaterno(),
                cliente.getFechaNacimiento(),
                cliente.getX(),
                cliente.getY(),
                cliente.getIdCiudad()
        );
    }
    
    private boolean esNumeroNegativo(int numero) {
        return numero < 0;
    }
    
    private int obtenerOFFSETMySQL(int limit, int pagina) {
        return new Utilidades().RegresarOFFSETMySQL(limit, pagina);
    }
    
    private void validarParametrosEnBuscarClienteTabla(FiltroTablaDTO filtro) throws NegocioException {
        if (this.esNumeroNegativo(filtro.getLimit())) {
            throw new NegocioException("El parametro limite no puede ser negativo");
        }
        if (this.esNumeroNegativo(filtro.getOffset())) {
            throw new NegocioException("El parametro pagina no puede ser negativo");
        }
    }
}
