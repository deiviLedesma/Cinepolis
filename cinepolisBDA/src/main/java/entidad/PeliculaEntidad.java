/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidad;

/**
 *
 * @author filor
 */
public class PeliculaEntidad {
    private int idPelicula;
    private String titulo;
    private int duracionEnMinutos;
    private String sinopsis;
    private String imagen;
    private String trailer;
    private int idGenero;
    private int idClasificacion;
    private int idPais;

    public PeliculaEntidad(int idPelicula, String titulo, int duracionEnMinutos, String sinopsis, String imagen, String trailer, int idGenero, int idClasificacion) {
        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.duracionEnMinutos = duracionEnMinutos;
        this.sinopsis = sinopsis;
        this.imagen = imagen;
        this.trailer = trailer;
        this.idGenero = idGenero;
        this.idClasificacion = idClasificacion;
    }

    public PeliculaEntidad() {
    }

    public PeliculaEntidad(int idPelicula, String titulo, int duracionEnMinutos, String sinopsis, String imagen, String trailer, int idGenero, int idClasificacion, int idPais) {
        this.idPelicula = idPelicula;
        this.titulo = titulo;
        this.duracionEnMinutos = duracionEnMinutos;
        this.sinopsis = sinopsis;
        this.imagen = imagen;
        this.trailer = trailer;
        this.idGenero = idGenero;
        this.idClasificacion = idClasificacion;
        this.idPais = idPais;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public int getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracionEnMinutos() {
        return duracionEnMinutos;
    }

    public void setDuracionEnMinutos(int duracionEnMinutos) {
        this.duracionEnMinutos = duracionEnMinutos;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }

    public int getIdClasificacion() {
        return idClasificacion;
    }

    public void setIdClasificacion(int idClasificacion) {
        this.idClasificacion = idClasificacion;
    }
    
    
}
