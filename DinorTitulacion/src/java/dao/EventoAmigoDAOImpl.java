/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import model.EventoAmigo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edgar-Mac
 */
@Service("EventoAmigo")
public class EventoAmigoDAOImpl implements EventoAmigoDAO {

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<EventoAmigo> consultarEventoAmigoPorIdCreador(int idCreador) {

        String query = "SELECT E2.id,U.id as 'idCreador',U.usuario  as 'creador',U2.id as "
                + "'idSeguidor',U2.usuario as 'seguidor', E.id as 'idEvento', "
                + "E.nombreEvento as 'nombreEvento', E.latitud,E.longitud,E.descripcion,"
                + "E.nombreLugar,E.fechaInicio,E.fechaTermino,E.horaInicio,E.horaTermino "
                + "FROM TBL_Usuarios U INNER JOIN TBL_Eventos E ON U.id=E.idCreador"
                + " INNER JOIN TBL_EventosAmigos E2 ON E.id=E2.idEvento "
                + "INNER JOIN TBL_Usuarios U2  ON U2.id=E2.idUsuarioAmigo "
                + "WHERE E2.activo = 1 AND E.activo = 1 AND E.publico=0 AND E.privado=1 "
                + " AND U.id=? ORDER BY seguidor asc;";
        EventoAmigo evento = null;
        List<EventoAmigo> getEvents = new ArrayList<EventoAmigo>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idCreador);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                evento = new EventoAmigo();
                evento.setId(rs.getInt("id"));
                evento.setIdEvento(rs.getInt("idEvento"));
                evento.setIdCreador(rs.getInt("idCreador"));
                evento.setIdSeguidor(rs.getInt("idSeguidor"));
                evento.setCreador(rs.getString("creador"));
                evento.setSeguidor(rs.getString("seguidor"));
                evento.setLatitud(rs.getString("latitud"));
                evento.setLongitud(rs.getString("longitud"));
                evento.setNombreEvento(rs.getString("nombreEventoAmigo"));
                evento.setDescripcion(rs.getString("descripcion"));
                evento.setNombreLugar(rs.getString("nombreLugar"));
                evento.setFechaInicio(rs.getString("fechaInicio"));
                evento.setFechaTermino(rs.getString("fechaTermino"));
                evento.setHoraInicio(rs.getString("horaInicio"));
                evento.setHoraTermino(rs.getString("horaTermino"));
                getEvents.add(evento);

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getEvents;
    }

    @Override
    public List<EventoAmigo> consultarEventoAmigoPorIdSeguidor(int idSeguidor) {

        String query = "SELECT E2.id,U.id as 'idCreador',U.usuario  as 'creador',"
                + "U2.id as 'idSeguidor',U2.usuario as 'seguidor', E.id as 'idEvento',"
                + " E.nombreEvento as 'nombreEvento', E.latitud,E.longitud,E.descripcion,"
                + "E.nombreLugar,E.fechaInicio,E.fechaTermino,E.horaInicio,E.horaTermino "
                + "FROM TBL_Usuarios U INNER JOIN TBL_Eventos E ON U.id=E.idCreador "
                + "INNER JOIN TBL_EventosAmigos E2 ON E.id=E2.idEvento "
                + "INNER JOIN TBL_Usuarios U2  ON U2.id=E2.idUsuarioAmigo "
                + "WHERE E2.activo = 1 AND E.activo = 1 AND E.publico=0 AND E.privado=1 "
                + " AND U2.id= ?  ORDER BY creador asc";
        EventoAmigo evento = null;
        List<EventoAmigo> getEvents = new ArrayList<EventoAmigo>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idSeguidor);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                evento = new EventoAmigo();
                evento.setId(rs.getInt("id"));
                evento.setIdEvento(rs.getInt("idEvento"));
                evento.setIdCreador(rs.getInt("idCreador"));
                evento.setIdSeguidor(rs.getInt("idSeguidor"));
                evento.setCreador(rs.getString("creador"));
                evento.setSeguidor(rs.getString("seguidor"));
                evento.setLatitud(rs.getString("latitud"));
                evento.setLongitud(rs.getString("longitud"));
                evento.setNombreEvento(rs.getString("nombreEventoAmigo"));
                evento.setDescripcion(rs.getString("descripcion"));
                evento.setNombreLugar(rs.getString("nombreLugar"));
                evento.setFechaInicio(rs.getString("fechaInicio"));
                evento.setFechaTermino(rs.getString("fechaTermino"));
                evento.setHoraInicio(rs.getString("horaInicio"));
                evento.setHoraTermino(rs.getString("horaTermino"));
                getEvents.add(evento);

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getEvents;
    }

    @Override
    public List<EventoAmigo> consultarEventoAmigoPorIdCreadoryIdEvento(int idCreador, int idEvento) {
        String query = "SELECT E2.id,U.id as 'idCreador',U.usuario  as 'creador',U2.id as 'idSeguidor',"
                + "U2.usuario as 'seguidor', E.id as 'idEvento', E.nombreEvento as 'nombreEvento', "
                + "E.latitud,E.longitud,E.descripcion,E.nombreLugar,E.fechaInicio,E.fechaTermino,"
                + "E.horaInicio,E.horaTermino FROM TBL_Usuarios U "
                + "INNER JOIN TBL_Eventos E ON U.id=E.idCreador "
                + "INNER JOIN TBL_EventosAmigos E2 ON E.id=E2.idEvento "
                + "INNER JOIN TBL_Usuarios U2  ON U2.id=E2.idUsuarioAmigo"
                + " WHERE E2.activo = 1 AND E.activo = 1 AND E.publico=0 AND E.privado=1  "
                + "AND U.id=? AND E.id=? ORDER BY nombreEvento asc;";
        EventoAmigo evento = null;
        List<EventoAmigo> getEvents = new ArrayList<EventoAmigo>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idCreador);
            ps.setInt(2, idEvento);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                evento = new EventoAmigo();
                evento.setId(rs.getInt("id"));
                evento.setIdEvento(rs.getInt("idEvento"));
                evento.setIdCreador(rs.getInt("idCreador"));
                evento.setIdSeguidor(rs.getInt("idSeguidor"));
                evento.setCreador(rs.getString("creador"));
                evento.setSeguidor(rs.getString("seguidor"));
                evento.setLatitud(rs.getString("latitud"));
                evento.setLongitud(rs.getString("longitud"));
                evento.setNombreEvento(rs.getString("nombreEvento"));
                evento.setDescripcion(rs.getString("descripcion"));
                evento.setNombreLugar(rs.getString("nombreLugar"));
                evento.setFechaInicio(rs.getString("fechaInicio"));
                evento.setFechaTermino(rs.getString("fechaTermino"));
                evento.setHoraInicio(rs.getString("horaInicio"));
                evento.setHoraTermino(rs.getString("horaTermino"));
                evento.setPrint("<li class=\"liFriends\" id=\""+evento.getIdSeguidor()+"\"><img src=\"resources/images/defaultUser.png\" height=\"50\" width=\"50\"><span class=\"tFriend\">"+rs.getString("seguidor")+"</span></li>");
                getEvents.add(evento);

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getEvents;
    }

    @Override
    public List<EventoAmigo> consultarEventoAmigoPorIdSeguidoryIdEvento(int idSeguidor, int idEvento) {
        String query = "SELECT E2.id,U.id as 'idCreador',U.usuario  as 'creador',U2.id as 'idSeguidor'"
                + ",U2.usuario as 'seguidor', E.id as 'idEvento', E.nombreEvento as 'nombreEvento',"
                + " E.latitud,E.longitud,E.descripcion,E.nombreLugar,E.fechaInicio,E.fechaTermino,"
                + "E.horaInicio,E.horaTermino FROM TBL_Usuarios U INNER JOIN TBL_Eventos E "
                + "ON U.id=E.idCreador INNER JOIN TBL_EventosAmigos E2 ON E.id=E2.idEvento "
                + "INNER JOIN TBL_Usuarios U2  ON U2.id=E2.idUsuarioAmigo "
                + "WHERE E2.activo = 1 AND E.activo = 1 AND E.publico=0 AND E.privado=1 "
                + " AND U2.id= ? AND E.id= ? ORDER BY nombreEvento ASC;";
        EventoAmigo evento = null;
        List<EventoAmigo> getEvents = new ArrayList<EventoAmigo>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idSeguidor);
            ps.setInt(2, idEvento);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                evento = new EventoAmigo();
                evento.setId(rs.getInt("id"));
                evento.setIdEvento(rs.getInt("idEvento"));
                evento.setIdCreador(rs.getInt("idCreador"));
                evento.setIdSeguidor(rs.getInt("idSeguidor"));
                evento.setCreador(rs.getString("creador"));
                evento.setSeguidor(rs.getString("seguidor"));
                evento.setLatitud(rs.getString("latitud"));
                evento.setLongitud(rs.getString("longitud"));
                evento.setNombreEvento(rs.getString("nombreEventoAmigo"));
                evento.setDescripcion(rs.getString("descripcion"));
                evento.setNombreLugar(rs.getString("nombreLugar"));
                evento.setFechaInicio(rs.getString("fechaInicio"));
                evento.setFechaTermino(rs.getString("fechaTermino"));
                evento.setHoraInicio(rs.getString("horaInicio"));
                evento.setHoraTermino(rs.getString("horaTermino"));
                getEvents.add(evento);

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getEvents;
    }

    @Override
    public List<EventoAmigo> consultarEventoAmigoPorIdEvento(int idEvento) {
        String query = "SELECT E2.id,U.id as 'idCreador',U.usuario  as 'creador',"
                + "U2.id as 'idSeguidor',U2.usuario as 'seguidor', E.id as 'idEvento',"
                + " E.nombreEvento as 'nombreEvento', E.latitud,E.longitud,E.descripcion,"
                + "E.nombreLugar,E.fechaInicio,E.fechaTermino,E.horaInicio,E.horaTermino "
                + "FROM TBL_Usuarios U INNER JOIN TBL_Eventos E ON U.id=E.idCreador "
                + "INNER JOIN TBL_EventosAmigos E2 ON E.id=E2.idEvento "
                + "INNER JOIN TBL_Usuarios U2  ON U2.id=E2.idUsuarioAmigo"
                + " WHERE E2.activo = 1 AND E.activo = 1 AND E.publico=0 AND E.privado=1  AND "
                + "E.id= ? ORDER BY nombreEvento ASC;";
        EventoAmigo evento = null;
        List<EventoAmigo> getEvents = new ArrayList<EventoAmigo>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idEvento);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                evento = new EventoAmigo();
                evento.setId(rs.getInt("id"));
                evento.setIdEvento(rs.getInt("idEvento"));
                evento.setIdCreador(rs.getInt("idCreador"));
                evento.setIdSeguidor(rs.getInt("idSeguidor"));
                evento.setCreador(rs.getString("creador"));
                evento.setSeguidor(rs.getString("seguidor"));
                evento.setLatitud(rs.getString("latitud"));
                evento.setLongitud(rs.getString("longitud"));
                evento.setNombreEvento(rs.getString("nombreEventoAmigo"));
                evento.setDescripcion(rs.getString("descripcion"));
                evento.setNombreLugar(rs.getString("nombreLugar"));
                evento.setFechaInicio(rs.getString("fechaInicio"));
                evento.setFechaTermino(rs.getString("fechaTermino"));
                evento.setHoraInicio(rs.getString("horaInicio"));
                evento.setHoraTermino(rs.getString("horaTermino"));
                getEvents.add(evento);

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getEvents;
    }

    @Override
    public List<EventoAmigo> consultarNoInvitadosDeEventoAmigo(int idCreador, int idEvento) {
        String query = "SELECT A.idUsuarioAmigo as 'idAmigo', U.usuario as 'amigo' "
                + "FROM TBL_Amigos A, TBL_Usuarios U"
                + " WHERE NOT EXISTS(SELECT EA.idUsuarioAmigo FROM TBL_EventosAmigos EA "
                + "WHERE A.idUsuarioAmigo=EA.idUsuarioAmigo AND EA.idEvento= ?)"
                + " AND A.idUsuarioAmigo=U.id AND A.idUsuario=(SELECT U2.id "
                + "FROM TBL_Usuarios U2 WHERE U2.id= ?) ORDER BY amigo ASC;";
        EventoAmigo evento = null;
        List<EventoAmigo> getEvents = new ArrayList<EventoAmigo>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idEvento);
            ps.setInt(2, idCreador);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                evento = new EventoAmigo();
                evento.setIdSeguidor(rs.getInt("idAmigo"));
                evento.setSeguidor(rs.getString("amigo"));
                evento.setPrint("<li class=\"liFriends\" id=\""+evento.getIdSeguidor()+"\"><img src=\"resources/images/defaultUser.png\" height=\"50\" width=\"50\"><span class=\"tFriend\">"+rs.getString("amigo")+"</span> <span id='"+evento.getIdSeguidor()+"' class='bInvite' onclick='clickInvite(this.id)'>Invitar</span></li>");
                getEvents.add(evento);

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getEvents;
    }

    @Override
    public int agregarEventoAmigoPorId(int idSeguidor, int idEvento) {
        String sql = "INSERT INTO TBL_EventosAmigos"
                + "(idUsuarioAmigo, idEvento,activo)  VALUES ( ?, ?, 1)";
        Connection conn = null;
        int exito = 0;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idSeguidor);
            ps.setInt(2, idEvento);

            ps.executeUpdate();
            exito = 1;
            ps.close();

        } catch (SQLException e) {
            exito = 0;
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    exito = 0;
                }
            }
        }

        return exito;
    }

    @Override
    public int eliminarEventoAmigoPorId(int idSeguidor, int idEvento) {
        String sql = "DELETE FROM TBL_EventosAmigos "
                + "WHERE idUsuarioAmigo = ? AND idEvento= ?";
        Connection conn = null;
        int exito = 0;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idSeguidor);
            ps.setInt(2, idEvento);

            ps.executeUpdate();
            exito = 1;
            ps.close();

        } catch (SQLException e) {
            exito = 0;
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    exito = 0;
                }
            }
        }

        return exito;
    }

}
