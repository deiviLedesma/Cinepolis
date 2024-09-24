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
public class CompraFuncionEntidad {
    private int idCompraFuncion;
    private double precio;
    private Time horaFuncion;
    private int idCompra;
    private int idFuncion;

    public CompraFuncionEntidad() {
    }

    public CompraFuncionEntidad(int idCompraFuncion, double precio, Time horaFuncion, int idCompra, int idFuncion) {
        this.idCompraFuncion = idCompraFuncion;
        this.precio = precio;
        this.horaFuncion = horaFuncion;
        this.idCompra = idCompra;
        this.idFuncion = idFuncion;
    }

    public int getIdCompraFuncion() {
        return idCompraFuncion;
    }

    public void setIdCompraFuncion(int idCompraFuncion) {
        this.idCompraFuncion = idCompraFuncion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Time getHoraFuncion() {
        return horaFuncion;
    }

    public void setHoraFuncion(Time horaFuncion) {
        this.horaFuncion = horaFuncion;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(int idFuncion) {
        this.idFuncion = idFuncion;
    }

    @Override
    public String toString() {
        return "CompraFuncionEntidad{" + "idCompraFuncion=" + idCompraFuncion + ", precio=" + precio + ", horaFuncion=" + horaFuncion + ", idCompra=" + idCompra + ", idFuncion=" + idFuncion + '}';
    }
    
    
}
