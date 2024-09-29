/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author filor
 */
public class PaisDTO {
    private int idPais;
    private String nombre;
    private int numeroHabitantes;

    public PaisDTO(int idPais, String nombre, int numeroHabitantes) {
        this.idPais = idPais;
        this.nombre = nombre;
        this.numeroHabitantes = numeroHabitantes;
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
        return "PaisDTO{" + "idPais=" + idPais + ", nombre=" + nombre + ", numeroHabitantes=" + numeroHabitantes + '}';
    }
    
}
