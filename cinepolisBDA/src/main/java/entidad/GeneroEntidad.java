/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

/**
 *
 * @author filor
 */
public class GeneroEntidad {
    private int idGenero;
    private String nombre;

    public GeneroEntidad() {
    }

    public GeneroEntidad(int idGenero, String nombre) {
        this.idGenero = idGenero;
        this.nombre = nombre;
    }

    
    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
