/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Sitio;

/**
 *
 * @author Edgar-Mac
 */
public interface SitioDAO {

    public List<Sitio> consultarSitioCategoria(String categoria);
    
    public List<Sitio> consultarSitioCreador(int creador,int actual);
    
    public List<Sitio> consultarSitioTodo(int actual);

    public String consultarSitioNombre(String nombre, String longitud, String latitud);
    
    public Sitio consultarSitioInformacion(int idSitio);
    
    public List<Sitio> consultarSitioFiltrosSinRepetir();


    public List<Sitio> consultarSitioPorFiltro(String categoria, String estado, String zona, String ciudad, int actual);

    public int agregarSitio(int idCreador, String tipo, String nombre, String longitud, String latitud, String lugar, String ciudad, String codigoPostal, String direccion, String paginaWeb, String telefono, String estado, String zona);
    
    public int modificarSitio(int idSitio, String tipo,String nombre, String longitud, String latitud, String lugar, String ciudad, String codigoPostal, String direccion, String paginaWeb, String telefono, String estado, String zona);
    
    public int eliminarSitio(int idSitio);
    
    public void agregarImagenSitio(String nombre, int idSitio);
    
    public int existeImagenSitio(int idSitio);
    
    public List<Sitio> consultaImagenSitio(int idSitio);
    
    public void eliminarImagenSitio(String nombre,int idSitio);

}
