/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Edgar-Mac
 */
public class Sitio {
    private int id;
    private String nombre, tipo, longitud, latitud, lugar, ciudad, codigoPostal, direccion, paginaWeb;
    private String telefono, estado, zona, print, filename, printImage, printImageInformation;

    public Sitio() {
    }

    public Sitio(String nombre, String tipo, String longitud, String latitud, String lugar, String ciudad, String codigoPostal, String direccion, String paginaWeb, String telefono, String estado, String zona) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.longitud = longitud;
        this.latitud = latitud;
        this.lugar = lugar;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.direccion = direccion;
        this.paginaWeb = paginaWeb;
        this.telefono = telefono;
        this.estado = estado;
        this.zona = zona;
    }

    public String getPrintImageInformation() {
        return printImageInformation;
    }

    public void setPrintImageInformation(String printImageInformation) {
        this.printImageInformation = printImageInformation;
    }

    public String getPrintImage() {
        return printImage;
    }

    public void setPrintImage(String printImage) {
        this.printImage = printImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getPrint() {
        return print;
    }

    public void setPrint(String print) {
        this.print = print;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    
    
    
}
