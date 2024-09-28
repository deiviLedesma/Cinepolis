/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

import java.awt.Point;
import java.sql.Date;

/**
 *
 * @author filor
 */
public class ClienteEntidad {
    private int idCliente;
    private String contrasenia;
    private String correo;
    private String nombres;
    private String ApellidoPaterno;
    private String ApellidoMaterno;
    private Date fechaNacimiento;
    private double x;
    private double y;
    private int idCiudad;

    public ClienteEntidad() {
    }

    public ClienteEntidad(int idCliente, String contrasenia, String correo, String nombres, String ApellidoPaterno, String ApellidoMaterno, Date fechaNacimiento, double x, double y, int idCiudad) {
        this.idCliente = idCliente;
        this.contrasenia = contrasenia;
        this.correo = correo;
        this.nombres = nombres;
        this.ApellidoPaterno = ApellidoPaterno;
        this.ApellidoMaterno = ApellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.x = x;
        this.y = y;
        this.idCiudad = idCiudad;
    }

    

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    @Override
    public String toString() {
        return "ClienteEntidad{" + "idCliente=" + idCliente + ", nombres=" + nombres + ", ApellidoPaterno=" + ApellidoPaterno + '}';
    }

    
}
