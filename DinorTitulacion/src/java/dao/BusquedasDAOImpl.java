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
import model.Busqueda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edgar-Mac
 */
@Service("Busquedas")
public class BusquedasDAOImpl implements BusquedasDAO {

    @Autowired
    private DataSource dataSource;
    private int cont = 1;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Busqueda> busquedaSitiosEventos(String name) {
        String query = "SELECT ts.id, ts.nombre, tcl.nombre AS Clase FROM TBL_Sitios ts, TBL_Clases tcl\n"
                + "                WHERE ts.nombre LIKE ? AND tcl.id=ts.idClase\n"
                + "                UNION ALL SELECT te.id, te.nombreEvento,  tcl2.nombre AS Clase  "
                + "FROM TBL_Eventos te, TBL_Clases tcl2 "
                + " WHERE te.nombreEvento  LIKE ? AND tcl2.id=te.idClase\n"
                + "                UNION ALL SELECT tu.id, tu.usuario,  tcl3.nombre AS Clase  "
                + "FROM TBL_Usuarios tu, TBL_Clases tcl3"
                + " WHERE tu.usuario LIKE ? AND tcl3.id=tu.idClase";
        String imagenEvento = "<img src=\"resources/images/calendar.png\"  height=\"42\" width=\"42\">";
        String imagenLugar = "<img src=\"resources/images/map-marker.png\"  height=\"42\" width=\"42\">";
        String imagenUsuario = "<img src=\"resources/images/user.png\"  height=\"42\" width=\"42\">";

        List<Busqueda> getData = new ArrayList<Busqueda>();
        Busqueda info = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, name + "%");
            ps.setString(2, name + "%");
            ps.setString(3, name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                info = new Busqueda();
                if (rs.getString("Clase").equals("Usuario")) {
                    info.setId(rs.getInt("id"));
                    info.setClase("Usuario");
                    info.setNombre(rs.getString("nombre"));
                    info.setPrint("resources/images/user.png");
                }
                if (rs.getString("Clase").equals("Evento")) {
                    info.setId(rs.getInt("id"));
                    info.setClase("Evento");
                    info.setNombre(rs.getString("nombre"));
                    info.setPrint("resources/images/calendar.png");
                }
                if (rs.getString("Clase").equals("Sitio")) {
                    info.setId(rs.getInt("id"));
                    info.setClase("Sitio");
                    info.setNombre(rs.getString("nombre"));
                    info.setPrint("resources/images/map-marker.png");
                }

                getData.add(info);

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getData;
    }

    @Override
    public List<Busqueda> eventosSitiosLista(int actual) {
        String query = "(SELECT ts.id , ts.nombre, ts.lugar AS 'descripcion', "
                + "ts.latitud, ts.longitud, tc.nombre AS 'clase' FROM TBL_Sitios ts, TBL_Clases tc  WHERE tc.id=ts.idClase )"
                + "UNION (SELECT te.id, te.nombreEvento AS 'nombre' , "
                + "te.descripcion, te.latitud, te.longitud, tc.nombre AS 'clase' FROM TBL_Eventos te, TBL_Clases tc "
                + "WHERE te.publico=1 AND tc.id=te.idClase) LIMIT ?,6 ";
        Busqueda info = null;
        List<Busqueda> getInfo = new ArrayList<Busqueda>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, actual);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                actual++;
                info = new Busqueda();
                info.setRango(cont);
                info.setId(rs.getInt("id"));
                info.setLatitud(rs.getString("latitud"));
                info.setLongitud(rs.getString("longitud"));
                info.setDescripcion(rs.getString("descripcion"));
                info.setNombre(rs.getString("nombre"));
                info.setClase(rs.getString("clase"));
                if (info.getClase().equals("Evento")) {
                    info.setPrint("<a href='informacion_evento?idEvento=" + info.getId() + "' class='link'><li id=\"" + actual + "\" class=\"result\" style=\"background-color: #f2f2f2;\"data-latitud='" + info.getLatitud() + "' data-longitud='" + info.getLongitud() + "' data-nombre='" + info.getNombre() + "'' data-clase='" + info.getClase() + "' data-idSitio='" + info.getId() + "'>\n"
                            + "                            <div class=\"indexResult\">\n"
                            + "                                <span class='spanList'>" + actual + "</span><img src='resources/images/default_event.png' class='imgList'>\n"
                            + "                            </div>\n"
                            + "                            <div class=\"containerInfo\"> \n"
                            + "                                <div class=\"blockTitle\">\n"
                            + "                                    <div class=\"title\">\n"
                            + "                                        <h2>" + info.getNombre() + "</h2>\n"
                            + "                                    </div>\n"
                            + "                                </div>\n"
                            + "                                <div class=\"infoBlock\">\n"
                            + "                                    <div class=\"infoResult\">\n"
                            + "                                        <span>" + info.getDescripcion() + "</span>\n"
                            + "                                        <span class=\"latitud\">" + info.getLatitud() + "</span>\n"
                            + "                                        <span class=\"longitud\">" + info.getLongitud() + "</span>\n"
                            + "                                    </div>\n"
                            + "                                </div>\n"
                            + "                            </div>\n"
                            + "                        </li></a>");
                }
                
                if (info.getClase().equals("Sitio")) {
                    info.setPrint("<a href='informacion_sitio?idSitio=" + info.getId() + "' class='link'><li id=\"" + actual + "\" class=\"result\" style=\"background-color: #f2f2f2;\"data-latitud='" + info.getLatitud() + "' data-longitud='" + info.getLongitud() + "' data-nombre='" + info.getNombre() + "'' data-clase='" + info.getClase() + "' data-idSitio='" + info.getId() + "'>\n"
                            + "                            <div class=\"indexResult\">\n"
                            + "                                <span class='spanList'>" + actual + "</span><img src='resources/images/default_place.png' class='imgList'>\n"
                            + "                            </div>\n"
                            + "                            <div class=\"containerInfo\"> \n"
                            + "                                <div class=\"blockTitle\">\n"
                            + "                                    <div class=\"title\">\n"
                            + "                                        <h2>" + info.getNombre() + "</h2>\n"
                            + "                                    </div>\n"
                            + "                                </div>\n"
                            + "                                <div class=\"infoBlock\">\n"
                            + "                                    <div class=\"infoResult\">\n"
                            + "                                        <span>" + info.getDescripcion() + "</span>\n"
                            + "                                        <span class=\"latitud\">" + info.getLatitud() + "</span>\n"
                            + "                                        <span class=\"longitud\">" + info.getLongitud() + "</span>\n"
                            + "                                    </div>\n"
                            + "                                </div>\n"
                            + "                            </div>\n"
                            + "                        </li></a>");
                }

                getInfo.add(info);

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getInfo;
    }

    @Override
    public String modalSitioEvento(String name, String latitud, String longitud, String clase) {

        String query = "(SELECT  * FROM TBL_Sitios ts  "
                + "WHERE ts.latitud =? AND ts.longitud=? AND ts.nombre=?)"
                + "UNION (SELECT * FROM TBL_Eventos te "
                + "WHERE te.publico=1 AND te.latitud =? AND te.longitud=?"
                + " AND te.nombreEvento=?) ";
        if (clase.equals("Evento")) {
            query = "SELECT * FROM TBL_Eventos te "
                    + "WHERE te.publico=1 AND te.latitud =? AND te.longitud=?"
                    + " AND te.nombreEvento=?";
        } else {
            query = "SELECT  * FROM TBL_Sitios ts  "
                    + "WHERE ts.latitud =? AND ts.longitud=? AND ts.nombre=?";
        }
        String print = "";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, latitud);
            ps.setString(2, longitud);
            ps.setString(3, name);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (clase.equals("Evento")) {
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
                            + "                            </table></td><td><div id=\"gallImages\" style=\"max-width:400px;position:relative;\">\n"
                            + "\n"
                            + "<span class=\"btn-floating\" style=\"position:absolute;top:45%;left:0;display:none;\" onclick=\"plusDivs(-1)\"><</span>\n"
                            + "<span class=\"btn-floating\" style=\"position:absolute;top:45%;right:0;display:none;\" onclick=\"plusDivs(1)\">></span>\n"
                            + "\n"
                            + "</div></td></tr>\n"
                            + "                        </table></div>\n"
                            + "                        <div class=\"modal-footer\">\n"
                            + "                            <h3>Evento</h3>\n"
                            + "                        </div></div>";
                } else {
                    print = "<div class=\"modal-header\">\n"
                            + "                            <span class=\"close\">×</span>\n"
                            + "                            <h2>" + rs.getString("nombre") + "</h2>\n"
                            + "                        </div>\n"
                            + "                        <div class=\"modal-body\"><table><tr><td><table id=\"listaInfo\">\n"
                            + "                                <tr>\n"
                            + "                                    <td><b>Nombre: </b></td>\n"
                            + "                                    <td>" + rs.getString("nombre") + "</td>\n"
                            + "                                </tr>\n"
                            + "                                <tr>\n"
                            + "                                    <td><b>Lugar: </b></td>\n"
                            + "                                    <td>" + rs.getString("lugar") + "</td>\n"
                            + "                                </tr>\n"
                            + "                                <tr>\n"
                            + "                                    <td><b>Dirección: </b></td>\n"
                            + "                                    <td>" + rs.getString("direccion") + "</td>\n"
                            + "                                </tr>\n"
                            + "                                <tr>\n"
                            + "                                    <td><b>Ciudad: </b></td>\n"
                            + "                                    <td>" + rs.getString("ciudad") + "</td>\n"
                            + "                                </tr>\n"
                            + "                                <tr>\n"
                            + "                                    <td><b>Estado: </b></td>\n"
                            + "                                    <td>" + rs.getString("estado") + "</td>\n"
                            + "                                </tr>\n"
                            + "                                <tr>\n"
                            + "                                    <td><b>Código postal: </b></td>\n"
                            + "                                    <td>" + rs.getString("codigoPostal") + "</td>\n"
                            + "                                </tr>\n"
                            + "                                <tr>\n"
                            + "                                    <td><b>Telefono: </b></td>\n"
                            + "                                    <td>" + rs.getString("telefono") + "</td>\n"
                            + "                                </tr>\n"
                            + "                                <tr>\n"
                            + "                                    <td><b>Pagina web: </b></td>\n"
                            + "                                    <td>" + rs.getString("paginaWeb") + "</td>\n"
                            + "                                </tr>\n"
                            + "                                <tr>\n"
                            + "                                    <td><b>Zona: </b></td>\n"
                            + "                                    <td>" + rs.getString("zona") + "</td>\n"
                            + "                                </tr>\n"
                            + "                            </table></td><td><div id=\"gallImages\" style=\"max-width:400px;position:relative;\">\n"
                            + "\n"
                            + "<span class=\"btn-floating\" style=\"position:absolute;top:45%;left:0;display:none;\" onclick=\"plusDivs(-1)\"><</span>\n"
                            + "<span class=\"btn-floating\" style=\"position:absolute;top:45%;right:0;display:none;\" onclick=\"plusDivs(1)\">></span>\n"
                            + "\n"
                            + "</div></td></tr>\n"
                            + "                        </table></div>\n"
                            + "                        <div class=\"modal-footer\">\n"
                            + "                            <h3>Sitio</h3>\n"
                            + "                        </div></div>";
                }

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
    public List<Busqueda> consultaImagenEventoSitio(int idSitio, String clase) {
        String query = "";
        if (clase.equals("Evento")) {
            query = "SELECT * FROM TBL_Images_Events WHERE idSitio = ? AND enabled = 1";
        } else {
            query = "SELECT * FROM TBL_Images_Places WHERE idSitio = ? AND enabled = 1";
        }

        Connection con = null;
        PreparedStatement ps = null;
        Busqueda sitio = null;
        List<Busqueda> getPlaces = new ArrayList<Busqueda>();
        int cont = 0;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idSitio);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cont++;
                sitio = new Busqueda();
                sitio.setFilename(rs.getString("filename"));
                if (cont == 1) {
                    if (clase.equals("Evento")) {
                        sitio.setPrintImage("<img class=\"mySlides\" src=\"https://s3.amazonaws.com/storagedinor/images/events/+" + rs.getString("filename") + "\" style=\"width:100%;display:block;\">");
                    } else {
                        sitio.setPrintImage("<img class=\"mySlides\" src=\"https://s3.amazonaws.com/storagedinor/images/places/+" + rs.getString("filename") + "\" style=\"width:100%;display:block;\">");
                    }

                } else if (clase.equals("Evento")) {
                    sitio.setPrintImage("<img class=\"mySlides\" src=\"https://s3.amazonaws.com/storagedinor/images/events/+" + rs.getString("filename") + "\" style=\"width:100%;display:none;\">");
                } else {
                    sitio.setPrintImage("<img class=\"mySlides\" src=\"https://s3.amazonaws.com/storagedinor/images/places/+" + rs.getString("filename") + "\" style=\"width:100%;display:none;\">");
                }

                getPlaces.add(sitio);

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
    public List<Busqueda> eventosSitiosListaTodo() {
        String query = "SELECT ts.id, ts.nombre, tcl.nombre AS Clase FROM TBL_Sitios ts, TBL_Clases tcl\n"
                + "                WHERE  tcl.id=ts.idClase\n"
                + "                UNION ALL SELECT te.id, te.nombreEvento,  tcl2.nombre AS Clase  "
                + "FROM TBL_Eventos te, TBL_Clases tcl2 "
                + " WHERE  tcl2.id=te.idClase\n"
                + "                UNION ALL SELECT tu.id, tu.usuario,  tcl3.nombre AS Clase  "
                + "FROM TBL_Usuarios tu, TBL_Clases tcl3"
                + " WHERE  tcl3.id=tu.idClase";

        List<Busqueda> getData = new ArrayList<Busqueda>();
        Busqueda info = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                info = new Busqueda();
                if (rs.getString("Clase").equals("Usuario")) {
                    info.setId(rs.getInt("id"));
                    info.setClase("Usuario");
                    info.setNombre(rs.getString("nombre"));
                    info.setPrint("resources/images/user.png");
                }
                if (rs.getString("Clase").equals("Evento")) {
                    info.setId(rs.getInt("id"));
                    info.setClase("Evento");
                    info.setNombre(rs.getString("nombre"));
                    info.setPrint("resources/images/calendar.png");
                }
                if (rs.getString("Clase").equals("Sitio")) {
                    info.setId(rs.getInt("id"));
                    info.setClase("Sitio");
                    info.setNombre(rs.getString("nombre"));
                    info.setPrint("resources/images/map-marker.png");
                }

                getData.add(info);

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getData;
    }

}
