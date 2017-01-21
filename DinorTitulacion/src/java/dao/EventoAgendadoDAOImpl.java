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
import model.EventoAgendado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edgar-Mac
 */
@Service("EventoAgendado")
public class EventoAgendadoDAOImpl implements EventoAgendadoDAO {

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<EventoAgendado> consultarEventoAgendado(int idUsuario) {
        String query = "SELECT * FROM TBL_EventosAgendados WHERE idUsuario = ?";
        EventoAgendado eventoAgendado = null;
        List<EventoAgendado> getMarkedEvents = new ArrayList<EventoAgendado>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                eventoAgendado = new EventoAgendado();
                eventoAgendado.setId_usuario(rs.getInt("idUsuario"));
                eventoAgendado.setUsuario(rs.getString("usuario"));
                eventoAgendado.setNombreEvento(rs.getString("nombre"));
                eventoAgendado.setEvento(rs.getInt("idEvento"));
                eventoAgendado.setAgendado(rs.getInt("agendado"));
                getMarkedEvents.add(eventoAgendado);

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getMarkedEvents;
    }

    @Override
    public int agregarEventoAgendado(int id_evento, int id_usuario, String nombre, String usuario) {
        String sql = "INSERT INTO TBL_EventosAgendados(idEvento, idUsuario, nombre, usuario, agendado)  "
                + "VALUES (?, ?,?, ?, 1)";
        Connection conn = null;
        int exito = 0;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id_evento);
            ps.setInt(2, id_usuario);
            ps.setString(3, nombre);
            ps.setString(4, usuario);
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
    public List<EventoAgendado> consultarEventoAgendadoTodo(int actual) {
        String query = "SELECT tea.*, te.latitud, te.longitud, te.descripcion FROM TBL_EventosAgendados tea, TBL_Eventos te WHERE tea.idEvento=te.id AND agendado = 1 LIMIT ?,6";
        EventoAgendado eventoAgendado = null;
        List<EventoAgendado> getEvents = new ArrayList<EventoAgendado>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, actual);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                actual++;
                eventoAgendado = new EventoAgendado();
                eventoAgendado.setId(rs.getInt("id"));
                eventoAgendado.setId_usuario(rs.getInt("idUsuario"));
                eventoAgendado.setUsuario(rs.getString("usuario"));
                eventoAgendado.setNombreEvento(rs.getString("nombre"));
                eventoAgendado.setEvento(rs.getInt("idEvento"));
                eventoAgendado.setAgendado(rs.getInt("agendado"));
                eventoAgendado.setPrint("<li id=\"" + actual + "\" class=\"result\" style=\"background-color: #f2f2f2;\"data-latitud='" + eventoAgendado.getLatitud() + "' data-longitud='" + eventoAgendado.getLongitud() + "' data-nombre='" + eventoAgendado.getNombreEvento() + "'>\n"
                        + "                            <div class=\"indexResult\">\n"
                        + "                                <span>" + actual + "</span>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"containerInfo\"> \n"
                        + "                                <div class=\"blockTitle\">\n"
                        + "                                    <div class=\"title\">\n"
                        + "                                        <h2>" + eventoAgendado.getNombreEvento() + "</h2>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                                <div class=\"infoBlock\">\n"
                        + "                                    <div class=\"infoResult\">\n"
                        + "                                        <span>" + eventoAgendado.getDescripcion() + "</span>\n"
                        + "                                        <span class=\"latitud\">" + eventoAgendado.getLatitud() + "</span>\n"
                        + "                                        <span class=\"longitud\">" + eventoAgendado.getLongitud() + "</span>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                        </li>");
                getEvents.add(eventoAgendado);

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getEvents;
    }

}
