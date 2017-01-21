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
import model.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edgar-Mac
 */
@Service("Evento")
public class EventoDAOImpl implements EventoDAO {

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Evento> consultarEvento(String latitud, String longitud) {
        String query = "SELECT * FROM TBL_Eventos WHERE latitud = ? and longitud = ?";
        Evento evento = null;
        List<Evento> getEvents = new ArrayList<Evento>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, latitud);
            ps.setString(2, longitud);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                evento = new Evento();
                evento.setId(rs.getInt("id"));
                evento.setLatitud(rs.getString("latitud"));
                evento.setLongitud(rs.getString("longitud"));
                evento.setNombreEvento(rs.getString("nombreEvento"));
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
    public List<Evento> consultarEventoFecha(String nombre, String fechaInicio) {
        String query = "SELECT * FROM TBL_Eventos WHERE nombreEvento = ? and fechaInicio = ?";
        Evento evento = null;
        List<Evento> getEvents = new ArrayList<Evento>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, nombre);
            ps.setString(2, fechaInicio);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                evento = new Evento();
                evento.setId(rs.getInt("id"));
                evento.setLatitud(rs.getString("latitud"));
                evento.setLongitud(rs.getString("longitud"));
                evento.setNombreEvento(rs.getString("nombreEvento"));
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
    public int agregarEvento(int idCreador, String nombreEvento, String descripcion, String nombreLugar, String fechaInicio, String fechaTermino, String latitud, String longitud, String horaInicio, String horaTermino) {
        String sql = "INSERT INTO TBL_Eventos(nombreEvento, descripcion, nombreLugar, fechaInicio, fechaTermino ,"
                + " latitud, longitud, horaInicio, horaTermino, idCreador, activo, publico, privado, idClase) "
                + " VALUES (?, ?, ?, ?, ?,"
                + " ?, ?, ?,  ?, ?, 1, 0, 1, 2)";
        Connection conn = null;
        int exito = 0;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombreEvento);
            ps.setString(2, descripcion);
            ps.setString(3, nombreLugar);
            ps.setString(4, fechaInicio);
            ps.setString(5, fechaTermino);
            ps.setString(6, latitud);
            ps.setString(7, longitud);
            ps.setString(8, horaInicio);
            ps.setString(9, horaTermino);
            ps.setInt(10, idCreador);

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
    public int modificarEvento(int idEvento, String nombreEvento, String descripcion, String nombreLugar, String fechaInicio, String fechaTermino, String latitud, String longitud, String horaInicio, String horaTermino) {
        String sql = "UPDATE TBL_Eventos SET descripcion = ?, nombreLugar =  ?, "
                + "fechaInicio =  ?, fechaTermino = ?, latitud = ?,"
                + " longitud = ?, horaInicio = ?, horaTermino = ?, nombreEvento = ?"
                + " WHERE id = ?";
        Connection conn = null;
        int exito = 0;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, descripcion);
            ps.setString(2, nombreLugar);
            ps.setString(3, fechaInicio);
            ps.setString(4, fechaTermino);
            ps.setString(5, latitud);
            ps.setString(6, longitud);
            ps.setString(7, horaInicio);
            ps.setString(8, horaTermino);
            ps.setString(9, nombreEvento);
            ps.setInt(10, idEvento);

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
    public int eliminarEvento(int idEvento) {
        String sql = "DELETE FROM TBL_Eventos WHERE id = ?";
        Connection conn = null;
        int exito = 0;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idEvento);

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
    public String consultarEventoNombre(String nombre, String latitud, String longitud) {
        String query = "SELECT * FROM TBL_Eventos te "
                + "WHERE te.publico=1 AND te.latitud =? AND te.longitud=?"
                + " AND te.nombreEvento=?";

        String print = "";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, latitud);
            ps.setString(2, longitud);
            ps.setString(3, nombre);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                print = "<div class=\"modal-header\">\n"
                        + "                            <span class=\"close\">×</span>\n"
                        + "                            <h2>" + rs.getString("nombreEvento") + "</h2>\n"
                        + "                        </div>\n"
                        + "                        <div class=\"modal-body\"><table><tr><td><table id=\"listaInfo\">\n"
                        + "                                <tr>\n"
                        + "                                    <td><b>Nombre: </b></td>\n"
                        + "                                    <td>" + rs.getString("nombreEvento") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td><b>Lugar: </b></td>\n"
                        + "                                    <td>" + rs.getString("nombreLugar") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td><b>Descripción: </b></td>\n"
                        + "                                    <td>" + rs.getString("descripcion") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td><b>Fecha de inicio: </b></td>\n"
                        + "                                    <td>" + rs.getString("fechaInicio") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td><b>Fecha de término </b></td>\n"
                        + "                                    <td>" + rs.getString("fechaTermino") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td><b>Hora del inicio: </b></td>\n"
                        + "                                    <td>" + rs.getString("horaInicio") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td><b>Hora del término: </b></td>\n"
                        + "                                    <td>" + rs.getString("horaTermino") + "</td>\n"
                        + "                                </tr>\n"
                        + "                            </table></td><td><div id=\"gallImages\" style=\"max-width:400px;poeventon:relative;\">\n"
                        + "\n"
                        + "<a class=\"btn-floating\" style=\"poeventon:absolute;top:45%;left:0;display:none;\" onclick=\"plusDivs(-1)\"><</a>\n"
                        + "<a class=\"btn-floating\" style=\"poeventon:absolute;top:45%;right:0;display:none;\" onclick=\"plusDivs(1)\">></a>\n"
                        + "\n"
                        + "</div></td></tr>\n"
                        + "                        </table></div>\n"
                        + "                        <div class=\"modal-footer\">\n"
                        + "                            <h3>Evento</h3>\n"
                        + "                        </div></div>";

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return print;
    }

    @Override
    public List<Evento> consultarEventoCreadorPagina(int idCreador, int actual) {

        String query = "SELECT * FROM TBL_Eventos WHERE idCreador = ? LIMIT ?,6";
        Evento evento = null;
        List<Evento> getEvents = new ArrayList<Evento>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idCreador);
            ps.setInt(2, actual);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                actual++;
                evento = new Evento();
                evento.setId(rs.getInt("id"));
                evento.setLatitud(rs.getString("latitud"));
                evento.setLongitud(rs.getString("longitud"));
                evento.setNombreEvento(rs.getString("nombreEvento"));
                evento.setDescripcion(rs.getString("descripcion"));
                evento.setNombreLugar(rs.getString("nombreLugar"));
                evento.setFechaInicio(rs.getString("fechaInicio"));
                evento.setFechaTermino(rs.getString("fechaTermino"));
                evento.setHoraInicio(rs.getString("horaInicio"));
                evento.setHoraTermino(rs.getString("horaTermino"));
                evento.setPrint("<li id=\"" + actual + "\" class=\"result\"  style=\"background-color: #f2f2f2;\"data-latitud='" + evento.getLatitud() + "' data-longitud='" + evento.getLongitud() + "' data-nombre='" + evento.getNombreEvento() + "'>\n"
                        + "                            <div class=\"indexResult\">\n"
                        + "                                <span class='spanList'>" + actual + "</span><img src='resources/images/default_event.png' class='imgList'>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"containerInfo\"> \n"
                        + "                                <div class=\"blockTitle\">\n"
                        + "                                    <div class=\"title\">\n"
                        + "                                        <h2>" + evento.getNombreEvento() + "</h2>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                                <div class=\"infoBlock\">\n"
                        + "                                    <div class=\"infoResult\">\n"
                        + "                                        <span>" + evento.getDescripcion() + "</span>\n"
                        + "                                        <span class=\"latitud\">" + evento.getLatitud() + "</span>\n"
                        + "                                        <span class=\"longitud\">" + evento.getLongitud() + "</span>\n"
                        + "                                    </div>\n"
                        + "                                <div class=\"buttonContainer\">\n"
                        + "                                    <div class=\"bModify\">\n"
                        + "                                        <a class='cModify' href=\"modificar_evento?idEvento=" + evento.getId() + "&latitud="+evento.getLatitud()+"&longitud="+evento.getLongitud()+"\"<span class=\"\"lModify>Modificar</span></a>\n"
                        + "                                    </div>\n"
                        + "                                    <div class=\"bDelete\">\n"
                        + "                                        <a class='cDelete' href=\"eliminar_evento?idEvento=" + evento.getId() + "\"<span class=\"lDelete\">Eliminar</span></a>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                        </li>");
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
    public List<Evento> consultarEventoTodo(int actual) {
        String query = "SELECT * FROM TBL_Eventos LIMIT ?,6";
        Evento evento = null;
        List<Evento> getEvents = new ArrayList<Evento>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, actual);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                actual++;
                evento = new Evento();
                evento.setId(rs.getInt("id"));
                evento.setLatitud(rs.getString("latitud"));
                evento.setLongitud(rs.getString("longitud"));
                evento.setNombreEvento(rs.getString("nombreEvento"));
                evento.setDescripcion(rs.getString("descripcion"));
                evento.setNombreLugar(rs.getString("nombreLugar"));
                evento.setFechaInicio(rs.getString("fechaInicio"));
                evento.setFechaTermino(rs.getString("fechaTermino"));
                evento.setHoraInicio(rs.getString("horaInicio"));
                evento.setHoraTermino(rs.getString("horaTermino"));
                evento.setPrint("<a href='informacion_evento?idEvento=" + evento.getId() + "' class='link'><li id=\"" + actual + "\" class=\"result\" style=\"background-color: #f2f2f2;\"data-latitud='" + evento.getLatitud() + "' data-longitud='" + evento.getLongitud() + "' data-nombre='" + evento.getNombreEvento() + "'>\n"
                        + "                            <div class=\"indexResult\">\n"
                        + "                                <span class='spanList'>" + actual + "</span><img src='resources/images/default_event.png' class='imgList'>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"containerInfo\"> \n"
                        + "                                <div class=\"blockTitle\">\n"
                        + "                                    <div class=\"title\">\n"
                        + "                                        <h2>" + evento.getNombreEvento() + "</h2>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                                <div class=\"infoBlock\">\n"
                        + "                                    <div class=\"infoResult\">\n"
                        + "                                        <span>" + evento.getDescripcion() + "</span>\n"
                        + "                                        <span class=\"latitud\">" + evento.getLatitud() + "</span>\n"
                        + "                                        <span class=\"longitud\">" + evento.getLongitud() + "</span>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                        </li></a>");
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
    public int agregarImagenEvento(String nombre, int idEvento) {
        String sql = "INSERT INTO TBL_Images_Events(idEvento, filename, enabled)  "
                + "VALUES (?, ?, 1)";
        Connection conn = null;
        int exito = 0;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idEvento);
            ps.setString(2, nombre);

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
    public int existeImagenEvento(int idEvento) {
        String query = "SELECT * FROM TBL_Images_Events WHERE idEvento = ? AND enabled = 1";
        Connection con = null;
        PreparedStatement ps = null;
        int exists = 0;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idEvento);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                exists = 1;

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }

    @Override
    public List<Evento> consultaImagenEvento(int idEvento) {
        String query = "SELECT * FROM TBL_Images_Events WHERE idEvento = ? AND enabled = 1";
        Connection con = null;
        int cont = 0;
        PreparedStatement ps = null;
        Evento evento = null;
        List<Evento> getPlaces = new ArrayList<Evento>();
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idEvento);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cont++;
                evento = new Evento();
                evento.setFilename(rs.getString("filename"));
                evento.setPrintImageInformation("<div class=\"img\">\n"
                        + "                    <img src=\"https://s3.amazonaws.com/storagedinor/images/events/+" + rs.getString("filename") + "\" width=\"600\" height=\"400\">\n"
                        + "                </div>");
                if (cont == 1) {
                    evento.setPrintImage("<img class=\"mySlides\" src=\"https://s3.amazonaws.com/storagedinor/images/events/+" + rs.getString("filename") + "\" style=\"width:100%;display:block;\">");
                } else {
                    evento.setPrintImage("<img class=\"mySlides\" src=\"https://s3.amazonaws.com/storagedinor/images/events/+" + rs.getString("filename") + "\" style=\"width:100%;display:none;\">");
                }
                getPlaces.add(evento);

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getPlaces;
    }

    @Override
    public void eliminarImagenEvento(String nombre, int idEvento) {
        String sql = "UPDATE TBL_Images_Events SET  enabled =  0 WHERE filename = ? AND idEvento = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setInt(2, idEvento);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    @Override
    public Evento consultarEventoInformacion(int idEvento) {
        String query = "SELECT  * FROM TBL_Eventos ts  "
                + "WHERE ts.id= ?";

        String print = "";
        Evento evento = new Evento();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idEvento);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                evento.setLatitud(rs.getString("latitud"));
                evento.setLongitud(rs.getString("longitud"));
                print = "<table id=\"tableInformation\">\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Nombre: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("nombreEvento") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Lugar: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("nombreLugar") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Descripción: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("descripcion") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Fecha de inicio: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("fechaInicio") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Fecha de término: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("fechaTermino") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Hora del inicio: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("horaInicio") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Hora de término: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("horaTermino") + "</td>\n"
                        + "                                </tr>\n"
                        + "                            </table>";
                evento.setPrint(print);

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return evento;
    }

    @Override
    public List<Evento> consultarEventoCreador(int idCreador) {

        String query = "SELECT * FROM TBL_Eventos WHERE idCreador = ?";
        Evento evento = null;
        List<Evento> getEvents = new ArrayList<Evento>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idCreador);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                evento = new Evento();
                evento.setId(rs.getInt("id"));
                evento.setLatitud(rs.getString("latitud"));
                evento.setLongitud(rs.getString("longitud"));
                evento.setNombreEvento(rs.getString("nombreEvento"));
                evento.setDescripcion(rs.getString("descripcion"));
                evento.setNombreLugar(rs.getString("nombreLugar"));
                evento.setFechaInicio(rs.getString("fechaInicio"));
                evento.setFechaTermino(rs.getString("fechaTermino"));
                evento.setHoraInicio(rs.getString("horaInicio"));
                evento.setHoraTermino(rs.getString("horaTermino"));
                evento.setPrint("<a href='informacion_evento?idEvento=" + evento.getId() + "' id='liAhref'><li class=\"liEvents\" id=\"1\"><img src=\"resources/images/default_event.png\" height=\"50\" width=\"50\"><span class=\"tEvent\">" + rs.getString("nombreEvento") + "</span></li></a>");
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

}
