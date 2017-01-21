/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.EventoAmigo;

/**
 *
 * @author Edgar-Mac
 */
public interface EventoAmigoDAO {

    public List<EventoAmigo> consultarEventoAmigoPorIdCreador(int idCreador);

    public List<EventoAmigo> consultarEventoAmigoPorIdSeguidor(int idSeguidor);

    public List<EventoAmigo> consultarEventoAmigoPorIdCreadoryIdEvento(int idCreador, int idEvento);

    public List<EventoAmigo> consultarEventoAmigoPorIdSeguidoryIdEvento(int idSeguidor, int idEvento);

    public List<EventoAmigo> consultarEventoAmigoPorIdEvento(int idEvento);

    public List<EventoAmigo> consultarNoInvitadosDeEventoAmigo(int idCreador, int idEvento);

    public int agregarEventoAmigoPorId(int idSeguidor, int idEvento);

    public int eliminarEventoAmigoPorId(int idSeguidor, int idEvento);

}
