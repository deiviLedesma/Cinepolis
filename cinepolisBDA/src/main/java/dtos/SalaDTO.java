/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author filor
 */
public class SalaDTO {
    private int idSala;
    private String nombre;
    private int asientosDisponibles;
    private int tiempoLimpiezaEnMinutos;
    private float precioActual;
    private int idSucursal;

    public SalaDTO() {
    }

    public SalaDTO(int idSala, String nombre, int asientosDisponibles, int tiempoLimpiezaEnMinutos, float precioActual, int idSucursal) {
        this.idSala = idSala;
        this.nombre = nombre;
        this.asientosDisponibles = asientosDisponibles;
        this.tiempoLimpiezaEnMinutos = tiempoLimpiezaEnMinutos;
        this.precioActual = precioActual;
        this.idSucursal = idSucursal;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAsientosDisponibles() {
        return asientosDisponibles;
    }

    public void setAsientosDisponibles(int asientosDisponibles) {
        this.asientosDisponibles = asientosDisponibles;
    }

    public int getTiempoLimpiezaEnMinutos() {
        return tiempoLimpiezaEnMinutos;
    }

    public void setTiempoLimpiezaEnMinutos(int tiempoLimpiezaEnMinutos) {
        this.tiempoLimpiezaEnMinutos = tiempoLimpiezaEnMinutos;
    }

    public float getPrecioActual() {
        return precioActual;
    }

    public void setPrecioActual(float precioActual) {
        this.precioActual = precioActual;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    @Override
    public String toString() {
        return "SalaDTO{" + "idSala=" + idSala + ", nombre=" + nombre + ", precioActual=" + precioActual + ", idSucursal=" + idSucursal + '}';
    }
    
    
}
