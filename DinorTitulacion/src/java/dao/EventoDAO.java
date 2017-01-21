/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Evento;

/**
 *
 * @author Edgar-Mac
 */
public interface EventoDAO {
    
    public List<Evento> consultarEvento(String latitud, String longitud);
    
    public String consultarEventoNombre(String nombre, String latitud, String longitud);
    
    public Evento consultarEventoInformacion(int idEvento);
    
    public List<Evento> consultarEventoCreadorPagina(int idCreador, int actual);
    
    public List<Evento> consultarEventoCreador(int idCreador);
    
    public List<Evento> consultarEventoTodo(int actual);
    
    public List<Evento> consultarEventoFecha(String nombre, String fechaInicio);
    
    public int agregarEvento(int idCreador, String nombreEvento, String descripcion , String nombreLugar, String fechaInicio, String fechaTermino, String latitud, String longitud, String horaInicio, String horaTermino);
    
    public int modificarEvento(int idEvento, String nombreEvento, String descripcion, String nombreLugar, String fechaInicio, String fechaTermino, String latitud, String longitud, String horaInicio, String horaTermino);
    
    public int eliminarEvento(int idEvento);
    
    public int agregarImagenEvento(String nombre, int idEvento);
    
    public int existeImagenEvento(int idEvento);
    
    public List<Evento> consultaImagenEvento(int idEvento);
    
    public void eliminarImagenEvento(String nombre,int idEvento);
}
