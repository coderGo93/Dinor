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
public class Evento {
    private int id;
    private String nombreEvento, descripcion, nombreLugar, fechaInicio, fechaTermino;
    private String  latitud, longitud, horaInicio, horaTermino, print, filename, printImage, printImageInformation; 

    public Evento() {
    }

    public Evento(String nombreEvento, String descripcion, String nombreLugar, String fechaInicio, String fechaTermino, String latitud, String longitud, String horaInicio, String horaTermino) {
        this.nombreEvento = nombreEvento;
        this.descripcion = descripcion;
        this.nombreLugar = nombreLugar;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.latitud = latitud;
        this.longitud = longitud;
        this.horaInicio = horaInicio;
        this.horaTermino = horaTermino;
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

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreLugar() {
        return nombreLugar;
    }

    public void setNombreLugar(String nombreLugar) {
        this.nombreLugar = nombreLugar;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(String fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraTermino() {
        return horaTermino;
    }

    public void setHoraTermino(String horaTermino) {
        this.horaTermino = horaTermino;
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
