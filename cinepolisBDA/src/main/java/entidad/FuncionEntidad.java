/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

import java.sql.Time;

/**
 *
 * @author filor
 */
public class FuncionEntidad {
    private int idFuncion;
    private Time horaIniciaFuncion;
    private Time horaAcabaFuncion;
    private Time horaAcabaPelicula;
    
    private int dia;
    private double precio;
    private int idSala;
    private int idPelicula;

    public FuncionEntidad() {
    }

    public FuncionEntidad(int idFuncion, Time horaIniciaFuncion, Time horaAcabaFuncion, Time horaAcabaPelicula, int dia, double precio, int idSala, int idPelicula) {
        this.idFuncion = idFuncion;
        this.horaIniciaFuncion = horaIniciaFuncion;
        this.horaAcabaFuncion = horaAcabaFuncion;
        this.horaAcabaPelicula = horaAcabaPelicula;
        this.dia = dia;
        this.precio = precio;
        this.idSala = idSala;
        this.idPelicula = idPelicula;
    }

    public int getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(int idFuncion) {
        this.idFuncion = idFuncion;
    }

    public Time getHoraIniciaFuncion() {
        return horaIniciaFuncion;
    }

    public void setHoraIniciaFuncion(Time horaIniciaFuncion) {
        this.horaIniciaFuncion = horaIniciaFuncion;
    }

    public Time getHoraAcabaFuncion() {
        return horaAcabaFuncion;
    }

    public void setHoraAcabaFuncion(Time horaAcabaFuncion) {
        this.horaAcabaFuncion = horaAcabaFuncion;
    }

    public Time getHoraAcabaPelicula() {
        return horaAcabaPelicula;
    }

    public void setHoraAcabaPelicula(Time horaAcabaPelicula) {
        this.horaAcabaPelicula = horaAcabaPelicula;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    @Override
    public String toString() {
        return "FuncionEntidad{" + "idFuncion=" + idFuncion + ", horaIniciaFuncion=" + horaIniciaFuncion + ", horaAcabaFuncion=" + horaAcabaFuncion + ", horaAcabaPelicula=" + horaAcabaPelicula + ", dia=" + dia + ", precio=" + precio + ", idSala=" + idSala + ", idPelicula=" + idPelicula + '}';
    }
    
    
}
