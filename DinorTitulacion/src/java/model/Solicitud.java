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
public class Solicitud {
    private int id, idSolicitado, idSolicitante, activo, contador;
    private String solicitado, solicitante, print;

    public Solicitud() {
    }

    public String getPrint() {
        return print;
    }

    public void setPrint(String print) {
        this.print = print;
    }
    
    public Solicitud( int idSolicitado, int idSolicitante, int activo, int contador, String solicitado, String solicitante) {
        this.idSolicitado = idSolicitado;
        this.idSolicitante = idSolicitante;
        this.activo = activo;
        this.contador = contador;
        this.solicitado = solicitado;
        this.solicitante = solicitante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSolicitado() {
        return idSolicitado;
    }

    public void setIdSolicitado(int idSolicitado) {
        this.idSolicitado = idSolicitado;
    }

    public int getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(int idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public String getSolicitado() {
        return solicitado;
    }

    public void setSolicitado(String solicitado) {
        this.solicitado = solicitado;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }
    
    
}
