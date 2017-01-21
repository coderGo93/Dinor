/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.SitioFavorito;

/**
 *
 * @author Edgar-Mac
 */
public interface SitioFavoritoDAO {
    
    public List<SitioFavorito> consultarSitioFavoritoPorUsuario(String usuario);
    
    public List<SitioFavorito> consultarSitioFavoritoCreadorPagina(int creador,int actual);
    
    public List<SitioFavorito> consultarSitioFavoritoCreador(int creador);
    
    public List<SitioFavorito> consultarSitioFavoritoTodo(int actual);
    
    public int maxId();
    
    public String consultarSitioFavoritoNombre(String nombre, String longitud, String latitud);
    
    public int existeSitioFavorito(String nombre, String longitud, String latitud);
    
    public int agregarSitioFavorito(int idCreador, String tipo,String lugar, String longitud, String latitud, String ciudad, String codigoPostal, String direccion, String paginaWeb, String telefono, String estado, String zona, String nombre, int idSitio);
    
    public void modificarSitioFavorito(String tipo, String usuario, String longitud, String latitud, String ciudad, String codigoPostal, String direccion, String paginaWeb, String telefono, String estado, String zona, String nombre);
    
    public int eliminarSitioFavorito(int idSitio);
    
    public int agregarImagenSitioFavorito(String nombre, int idSitioFavorito);
    
    public int existeImagenSitioFavorito(int idSitioFavorito);
    
    public List<SitioFavorito> consultaImagenSitioFavorito(int idSitioFavorito);
    
    public int eliminarImagenSitioFavorito(String nombre,int idSitioFavorito);
    
}
