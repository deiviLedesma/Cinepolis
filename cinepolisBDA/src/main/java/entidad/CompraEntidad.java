/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;
import java.sql.Time;
import java.sql.Date;
import java.time.LocalDateTime;
/**
 *
 * @author filor
 */
public class CompraEntidad {
    private int idCompra;
    private String codigoCompra;
    private LocalDateTime fechaHoraCompra;
    private String nombreCliente;
    private String correoCliente;
    private int cantidadAsientos;
    private String metodoDePago;
    private double costoTotal;
    private int idCliente;

    public CompraEntidad() {
    }

    public CompraEntidad(int idCompra, String codigoCompra, LocalDateTime fechaHoraCompra, String nombreCliente, String correoCliente, int cantidadAsientos, String metodoDePago, double costoTotal, int idCliente) {
        this.idCompra = idCompra;
        this.codigoCompra = codigoCompra;
        this.fechaHoraCompra = fechaHoraCompra;
        this.nombreCliente = nombreCliente;
        this.correoCliente = correoCliente;
        this.cantidadAsientos = cantidadAsientos;
        this.metodoDePago = metodoDePago;
        this.costoTotal = costoTotal;
        this.idCliente = idCliente;
    }

    

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public String getCodigoCompra() {
        return codigoCompra;
    }

    public void setCodigoCompra(String codigoCompra) {
        this.codigoCompra = codigoCompra;
    }

    public LocalDateTime getFechaHoraCompra() {
        return fechaHoraCompra;
    }

    public void setFechaHoraCompra(LocalDateTime fechaHoraCompra) {
        this.fechaHoraCompra = fechaHoraCompra;
    }


    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public int getCantidadAsientos() {
        return cantidadAsientos;
    }

    public void setCantidadAsientos(int cantidadAsientos) {
        this.cantidadAsientos = cantidadAsientos;
    }

    public String getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(String metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return "CompraEntidad{" + "idCompra=" + idCompra + ", codigoCompra=" + codigoCompra + ", fechaHoraCompra=" + fechaHoraCompra + ", nombreCliente=" + nombreCliente + ", correoCliente=" + correoCliente + ", cantidadAsientos=" + cantidadAsientos + ", metodoDePago=" + metodoDePago + ", costoTotal=" + costoTotal + ", idCliente=" + idCliente + '}';
    }
    
     
    
}
