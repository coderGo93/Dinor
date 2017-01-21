/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.EventoAgendado;

/**
 *
 * @author Edgar-Mac
 */
public interface EventoAgendadoDAO {
    
    public List<EventoAgendado> consultarEventoAgendado(int idUsuario);
    
    public List<EventoAgendado> consultarEventoAgendadoTodo(int actual);
    
    public int agregarEventoAgendado(int id_evento, int id_usuario, String nombre, String usuario);
    
    
}
