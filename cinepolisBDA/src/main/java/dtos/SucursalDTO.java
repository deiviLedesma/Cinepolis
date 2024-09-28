/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author filor
 */
public class SucursalDTO {
    private int idSucursal;
    private String nombre;
    private double latitud;
    private double longitud;
    private int idCiudad;

    public SucursalDTO(int idSucursal, String nombre, double latitud, double longitud, int idCiudad) {
        this.idSucursal = idSucursal;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.idCiudad = idCiudad;
    }

    public SucursalDTO() {
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

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }
    
    
}
