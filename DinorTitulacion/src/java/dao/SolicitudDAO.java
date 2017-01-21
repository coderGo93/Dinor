/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Solicitud;

/**
 *
 * @author Edgar-Mac
 */
public interface SolicitudDAO {
    
    public List<Solicitud> consultarSolicitud(int idSolicitado);
        
    public int contadorSolicitudes(String solicitado);
    
    public int contadorSolicitudesPorId(int idSolicitado);
    
    public int compruebaSiTieneSolicitudEntreEllos(String solicitado, String solicitante);
    
    public int compruebaSiTieneSolicitudEntreEllosPorId(int idSolicitado, int idSolicitante);
    
    public int agregarSolicitud(int idSolicitado,int idSolicitante);
    
    public int eliminarSolicitud(int idSolicitado, int idSolicitante);
}
