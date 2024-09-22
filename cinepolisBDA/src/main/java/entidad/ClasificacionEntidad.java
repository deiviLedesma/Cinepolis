/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

/**
 *
 * @author filor
 */
public class ClasificacionEntidad {
    private int idClasificacion;
    private String nombre;

    public ClasificacionEntidad() {
    }

    public ClasificacionEntidad(int idClasificacion, String nombre) {
        this.idClasificacion = idClasificacion;
        this.nombre = nombre;
    }

    public int getIdClasificacion() {
        return idClasificacion;
    }

    public void setIdClasificacion(int idClasificacion) {
        this.idClasificacion = idClasificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "ClasificacionEntidad{" + "idClasificacion=" + idClasificacion + ", nombre=" + nombre + '}';
    }
    
    

}
