/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Busqueda;
import org.springframework.cache.annotation.Cacheable;

/**
 *
 * @author Edgar-Mac
 */
public interface BusquedasDAO {
    
    @Cacheable("listado_mapa")
    public List<Busqueda> busquedaSitiosEventos(String name);
    
    public List<Busqueda> eventosSitiosLista(int actual);
    
    public List<Busqueda> eventosSitiosListaTodo();
    
    public String modalSitioEvento(String name, String latitud, String longitud, String clase);
    
    public List<Busqueda> consultaImagenEventoSitio(int idSitio, String clase);
    
}
