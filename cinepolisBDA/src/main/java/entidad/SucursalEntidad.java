/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

import java.awt.Point;

/**
 *
 * @author filor
 */
public class SucursalEntidad {
    private int idSucursal;
    private String nombre;
    private Point ubicacion;
    private int idCiudad;

    public SucursalEntidad() {
    }

    public SucursalEntidad(int idSucursal, String nombre, Point ubicacion, int idCiudad) {
        this.idSucursal = idSucursal;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.idCiudad = idCiudad;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Point getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Point ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    @Override
    public String toString() {
        return "SucursalEntidad{" + "idSucursal=" + idSucursal + ", nombre=" + nombre + ", ubicacion=" + ubicacion + ", idCiudad=" + idCiudad + '}';
    }
    
    
}
