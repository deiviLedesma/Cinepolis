/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

/**
 *
 * @author filor
 */
public class PaisEntidad {
    private int idPais;
    private String nombre;
    private int numeroHabitantes;

    public PaisEntidad(int idPais, String nombre, int numeroHabitantes) {
        this.idPais = idPais;
        this.nombre = nombre;
        this.numeroHabitantes = numeroHabitantes;
    }

    public PaisEntidad() {
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
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

    @Override
    public String toString() {
        return "PaisEntidad{" + "idPais=" + idPais + ", nombre=" + nombre + ", numeroHabitantes=" + numeroHabitantes + '}';
    }
    
    
}
