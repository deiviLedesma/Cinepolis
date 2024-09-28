/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.sql.Date;

/**
 *
 * @author filor
 */
public class ClienteTablaDTO {
    private int idCliente;
    private String nombres;
    private String ApellidoPaterno;
    private String ApellidoMaterno;
    private Date fechaNacimiento;
    private int idCiudad;

    public ClienteTablaDTO() {
    }

    public ClienteTablaDTO(int idCliente, String nombres, String ApellidoPaterno, String ApellidoMaterno, Date fechaNacimiento, int idCiudad) {
        this.idCliente = idCliente;
        this.nombres = nombres;
        this.ApellidoPaterno = ApellidoPaterno;
        this.ApellidoMaterno = ApellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.idCiudad = idCiudad;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    @Override
    public String toString() {
        return "ClienteTablaDTO{" + "idCliente=" + idCliente + ", nombres=" + nombres + ", ApellidoPaterno=" + ApellidoPaterno + '}';
    }
    
    
}
