/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

/**
 *
 * @author filor
 */
public class CiudadEntidad {
    private int idCiudad;
    private String nombre;
    private int numeroHabitantes;
    private int idPais;

    public CiudadEntidad() {
    }

    public CiudadEntidad(int idCiudad, String nombre, int numeroHabitantes, int idPais) {
        this.idCiudad = idCiudad;
        this.nombre = nombre;
        this.numeroHabitantes = numeroHabitantes;
        this.idPais = idPais;
    }

    
    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroHabitantes() {
        return numeroHabitantes;
    }

    public void setNumeroHabitantes(int numeroHabitantes) {
        this.numeroHabitantes = numeroHabitantes;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    @Override
    public String toString() {
        return "CiudadEntidad{" + "idCiudad=" + idCiudad + ", nombre=" + nombre + ", numeroHabitantes=" + numeroHabitantes + ", idPais=" + idPais + '}';
    }
    
    
}
