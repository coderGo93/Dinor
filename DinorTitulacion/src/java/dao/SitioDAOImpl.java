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
import model.Sitio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edgar-Mac
 */
@Service("Sitio")
public class SitioDAOImpl implements SitioDAO {

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Sitio> consultarSitioCategoria(String categoria) {
        String query = "SELECT * FROM TBL_Sitios WHERE tipo = ?";
        Sitio sitio = null;
        List<Sitio> setPlaces = new ArrayList<Sitio>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, categoria);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sitio = new Sitio();
                sitio.setId(rs.getInt("id"));
                sitio.setNombre(rs.getString("nombre"));
                sitio.setTipo(rs.getString("tipo"));
                sitio.setLatitud(rs.getString("latitud"));
                sitio.setLongitud(rs.getString("longitud"));
                sitio.setLugar(rs.getString("lugar"));
                sitio.setCiudad(rs.getString("ciudad"));
                sitio.setCodigoPostal(rs.getString("codigoPostal"));
                sitio.setDireccion(rs.getString("direccion"));
                sitio.setPaginaWeb(rs.getString("paginaWeb"));
                sitio.setTelefono(rs.getString("telefono"));
                sitio.setEstado(rs.getString("estado"));
                sitio.setZona(rs.getString("zona"));
                setPlaces.add(sitio);

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return setPlaces;
    }

    @Override
    public String consultarSitioNombre(String nombre, String longitud, String latitud) {
        String query = "SELECT  * FROM TBL_Sitios ts  "
                + "WHERE ts.latitud =? AND ts.longitud=? AND ts.nombre=?";

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

                print = "<div class=\"modal-header\"'>\n"
                        + "                            <span class=\"close\">×</span>\n"
                        + "                            <h2>" + rs.getString("nombre") + "</h2>\n"
                        + "                        </div>\n"
                        + "                        <div class=\"modal-body\"><table><tr><td><table id=\"listaInfo\">\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Nombre: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("nombre") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Lugar: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("lugar") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Dirección: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("direccion") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Ciudad: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("ciudad") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Estado: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("estado") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Código postal: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("codigoPostal") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Telefono: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("telefono") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td><b>Pagina web: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("paginaWeb") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Zona: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("zona") + "</td>\n"
                        + "                                </tr>\n"
                        + "                            </table></td><td><div id=\"gallImages\" style=\"max-width:400px;position:relative;\">\n"
                        + "\n"
                        + "<a class=\"btn-floating\" style=\"position:absolute;top:45%;left:0;display:none;\" onclick=\"plusDivs(-1)\"><</a>\n"
                        + "<a class=\"btn-floating\" style=\"position:absolute;top:45%;right:0;display:none;\" onclick=\"plusDivs(1)\">></a>\n"
                        + "\n"
                        + "</div></td></tr>\n"
                        + "                        </table></div>\n"
                        + "                        <div class=\"modal-footer\">\n"
                        + "                            <h3>Sitio</h3>\n"
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
    public List<Sitio> consultarSitioPorFiltro(String categoria, String estado, String zona, String ciudad, int actual) {
        String query;
        if (zona == "Otra") {
            query = "SELECT * FROM TBL_Sitios WHERE tipo = ? AND estado = ? AND ciudad = ? LIMIT ?,6";
        } else {
            query = "SELECT * FROM TBL_Sitios WHERE tipo = ? AND estado = ? AND ciudad = ? AND zona = ? LIMIT ?,6";
        }

        Sitio sitio = null;
        List<Sitio> getPlaces = new ArrayList<Sitio>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            if (zona == "Otra") {
                ps.setString(1, categoria);
                ps.setString(2, estado);
                ps.setString(3, ciudad);
                ps.setInt(4, actual);

            } else {
                ps.setString(1, categoria);
                ps.setString(2, estado);
                ps.setString(3, ciudad);
                ps.setString(4, zona);
                ps.setInt(5, actual);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                actual++;
                sitio = new Sitio();
                sitio.setId(rs.getInt("id"));
                sitio.setNombre(rs.getString("nombre"));
                sitio.setTipo(rs.getString("tipo"));
                sitio.setLatitud(rs.getString("latitud"));
                sitio.setLongitud(rs.getString("longitud"));
                sitio.setLugar(rs.getString("lugar"));
                sitio.setCiudad(rs.getString("ciudad"));
                sitio.setCodigoPostal(rs.getString("codigoPostal"));
                sitio.setDireccion(rs.getString("direccion"));
                sitio.setPaginaWeb(rs.getString("paginaWeb"));
                sitio.setTelefono(rs.getString("telefono"));
                sitio.setEstado(rs.getString("estado"));
                sitio.setZona(rs.getString("zona"));
                sitio.setPrint("<a href='informacion_sitio?idSitio=" + sitio.getId() + "' class='link'><li id=\"" + actual + "\" class=\"result\" style=\"background-color: #f2f2f2;\"data-latitud='" + sitio.getLatitud() + "' data-longitud='" + sitio.getLongitud() + "' data-nombre='" + sitio.getNombre() + "' data-idSitio='" + sitio.getId() + "'>\n"
                        + "                            <div class=\"indexResult\">\n"
                        + "                                <span class='spanList'>" + actual + "</span><img src='resources/images/default_place.png' class='imgList'>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"containerInfo\"> \n"
                        + "                                <div class=\"blockTitle\">\n"
                        + "                                    <div class=\"title\">\n"
                        + "                                        <h2>" + sitio.getNombre() + "</h2>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                                <div class=\"infoBlock\">\n"
                        + "                                    <div class=\"infoResult\">\n"
                        + "                                        <span>" + sitio.getLugar() + "</span>\n"
                        + "                                        <span class=\"latitud\">" + sitio.getLatitud() + "</span>\n"
                        + "                                        <span class=\"longitud\">" + sitio.getLongitud() + "</span>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                        </li></a>");
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
    public int agregarSitio(int idCreador, String tipo, String nombre, String longitud, String latitud, String lugar, String ciudad, String codigoPostal, String direccion, String paginaWeb, String telefono, String estado, String zona) {
        String sql = "INSERT INTO TBL_Sitios(tipo, longitud, "
                + "latitud, lugar, ciudad, codigoPostal, direccion ,"
                + " paginaWeb, telefono, estado , zona,nombre, idCreador, idClase)  "
                + "VALUES (?, ?, ?, ?, ?, ?, ? , ?, ?, ? , ?, ?, ?, 1)";
        Connection conn = null;
        int exito = 0;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tipo);
            ps.setString(2, longitud);
            ps.setString(3, latitud);
            ps.setString(4, lugar);
            ps.setString(5, ciudad);
            ps.setString(6, codigoPostal);
            ps.setString(7, direccion);
            ps.setString(8, paginaWeb);
            ps.setString(9, telefono);
            ps.setString(10, estado);
            ps.setString(11, zona);
            ps.setString(12, nombre);
            ps.setInt(13, idCreador);
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
    public int modificarSitio(int idSitio, String tipo, String nombre, String longitud, String latitud, String lugar, String ciudad, String codigoPostal, String direccion, String paginaWeb, String telefono, String estado, String zona) {
        String sql = "UPDATE TBL_Sitios SET nombre = ?, longitud =  ?, latitud = ?, "
                + "lugar = ?, ciudad =  ?, codigoPostal = ?, direccion = ? ,"
                + " paginaWeb = ?, telefono = ?, estado = ? , zona = ?, tipo = ? "
                + "WHERE id = ?";
        Connection conn = null;
        int exito = 0;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, longitud);
            ps.setString(3, latitud);
            ps.setString(4, lugar);
            ps.setString(5, ciudad);
            ps.setString(6, codigoPostal);
            ps.setString(7, direccion);
            ps.setString(8, paginaWeb);
            ps.setString(9, telefono);
            ps.setString(10, estado);
            ps.setString(11, zona);
            ps.setString(12, tipo);
            ps.setInt(13, idSitio);
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
                    exito = 1;
                }
            }
        }

        return exito;
    }

    @Override
    public int eliminarSitio(int idSitio) {
        String sql = "DELETE FROM TBL_Sitios WHERE id = ?";
        Connection conn = null;
        int exito = 0;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idSitio);

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
    public List<Sitio> consultarSitioCreador(int creador, int actual) {
        String query = "SELECT * FROM TBL_Sitios WHERE idCreador = ? LIMIT ?,6";
        Sitio sitio = null;
        List<Sitio> setPlaces = new ArrayList<Sitio>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, creador);
            ps.setInt(2, actual);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                actual++;
                sitio = new Sitio();
                sitio.setId(rs.getInt("id"));
                sitio.setNombre(rs.getString("nombre"));
                sitio.setTipo(rs.getString("tipo"));
                sitio.setLatitud(rs.getString("latitud"));
                sitio.setLongitud(rs.getString("longitud"));
                sitio.setLugar(rs.getString("lugar"));
                sitio.setCiudad(rs.getString("ciudad"));
                sitio.setCodigoPostal(rs.getString("codigoPostal"));
                sitio.setDireccion(rs.getString("direccion"));
                sitio.setPaginaWeb(rs.getString("paginaWeb"));
                sitio.setTelefono(rs.getString("telefono"));
                sitio.setEstado(rs.getString("estado"));
                sitio.setZona(rs.getString("zona"));
                sitio.setPrint("<li id=\"" + actual + "\" class=\"result\" \" style=\"background-color: #f2f2f2;\"data-latitud='" + sitio.getLatitud() + "' data-longitud='" + sitio.getLongitud() + "' data-nombre='" + sitio.getNombre() + "'>\n"
                        + "                            <div class=\"indexResult\">\n"
                        + "                                <span class='spanList'>" + actual + "</span><img src='resources/images/default_place.png' class='imgList'>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"containerInfo\"> \n"
                        + "                                <div class=\"blockTitle\">\n"
                        + "                                    <div class=\"title\">\n"
                        + "                                        <h2>" + sitio.getNombre() + "</h2>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                                <div class=\"infoBlock\">\n"
                        + "                                    <div class=\"infoResult\">\n"
                        + "                                        <span>" + sitio.getLugar() + "</span>\n"
                        + "                                        <span class=\"latitud\">" + sitio.getLatitud() + "</span>\n"
                        + "                                        <span class=\"longitud\">" + sitio.getLongitud() + "</span>\n"
                        + "                                    </div>\n"
                        + "                                <div class=\"buttonContainer\">\n"
                        + "                                    <div class=\"bModify\">\n"
                        + "                                        <a  class='cModify' href=\"modificar_sitio?idSitio=" + sitio.getId() +"&latitud="+sitio.getLatitud()+"&longitud="+sitio.getLongitud()+"\"<span class=\"\"lModify>Modificar</span></a>\n"
                        + "                                    </div>\n"
                        + "                                    <div class=\"bDelete\">\n"
                        + "                                         <a class='cDelete' href=\"eliminar_sitio?idSitio=" + sitio.getId() + "\"<span class=\"lDelete\">Eliminar</span></a>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                        </li>");
                setPlaces.add(sitio);

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return setPlaces;
    }

    @Override
    public List<Sitio> consultarSitioTodo(int actual) {
        String query = "SELECT * FROM TBL_Sitios LIMIT ?,6";
        Sitio sitio = null;
        List<Sitio> setPlaces = new ArrayList<Sitio>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, actual);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                actual++;
                sitio = new Sitio();
                sitio.setId(rs.getInt("id"));
                sitio.setNombre(rs.getString("nombre"));
                sitio.setTipo(rs.getString("tipo"));
                sitio.setLatitud(rs.getString("latitud"));
                sitio.setLongitud(rs.getString("longitud"));
                sitio.setLugar(rs.getString("lugar"));
                sitio.setCiudad(rs.getString("ciudad"));
                sitio.setCodigoPostal(rs.getString("codigoPostal"));
                sitio.setDireccion(rs.getString("direccion"));
                sitio.setPaginaWeb(rs.getString("paginaWeb"));
                sitio.setTelefono(rs.getString("telefono"));
                sitio.setEstado(rs.getString("estado"));
                sitio.setZona(rs.getString("zona"));
                sitio.setPrint("<a href='informacion_sitio?idSitio=" + sitio.getId() + "' class='link'><li id=\"" + actual + "\" class=\"result\" style=\"background-color: #f2f2f2;\"data-latitud='" + sitio.getLatitud() + "' data-longitud='" + sitio.getLongitud() + "' data-nombre='" + sitio.getNombre() + "' data-idSitio='" + sitio.getId() + "'>\n"
                        + "                            <div class=\"indexResult\">\n"
                        + "                                <span class='spanList'>" + actual + "</span><img src='resources/images/default_place.png' class='imgList'>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"containerInfo\"> \n"
                        + "                                <div class=\"blockTitle\">\n"
                        + "                                    <div class=\"title\">\n"
                        + "                                        <h2>" + sitio.getNombre() + "</h2>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                                <div class=\"infoBlock\">\n"
                        + "                                    <div class=\"infoResult\">\n"
                        + "                                        <span>" + sitio.getLugar() + "</span>\n"
                        + "                                        <span class=\"latitud\">" + sitio.getLatitud() + "</span>\n"
                        + "                                        <span class=\"longitud\">" + sitio.getLongitud() + "</span>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                            </div>\n"
                        + "                        </li></a>");
                setPlaces.add(sitio);

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return setPlaces;
    }

    @Override
    public void agregarImagenSitio(String nombre, int idSitio) {
        String sql = "INSERT INTO TBL_Images_Places(idSitio, filename, enabled)  "
                + "VALUES (?, ?, 1)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idSitio);
            ps.setString(2, nombre);

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
    public int existeImagenSitio(int idSitio) {
        String query = "SELECT * FROM TBL_Images_Places WHERE idSitio = ? AND enabled = 1";
        Connection con = null;
        PreparedStatement ps = null;
        int exists = 0;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idSitio);
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
    public List<Sitio> consultaImagenSitio(int idSitio) {
        String query = "SELECT * FROM TBL_Images_Places WHERE idSitio = ? AND enabled = 1";
        Connection con = null;
        PreparedStatement ps = null;
        Sitio sitio = null;
        List<Sitio> getPlaces = new ArrayList<Sitio>();
        int cont = 0;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idSitio);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cont++;
                sitio = new Sitio();
                sitio.setFilename(rs.getString("filename"));
                sitio.setPrintImageInformation("<div class=\"img\">\n"
                        + "                    <img src=\"https://s3.amazonaws.com/storagedinor/images/places/+" + rs.getString("filename") + "\" width=\"600\" height=\"400\">\n"
                        + "                </div>");
                if (cont == 1) {
                    sitio.setPrintImage("<img class=\"mySlides\" src=\"https://s3.amazonaws.com/storagedinor/images/places/+" + rs.getString("filename") + "\" style=\"width:100%;display:block;\">");
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
    public void eliminarImagenSitio(String nombre, int idSitio) {
        String sql = "UPDATE TBL_Images_Places SET  enabled =  0 WHERE filename = ? AND idSitio = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setInt(2, idSitio);

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
    public Sitio consultarSitioInformacion(int idSitio) {
        String query = "SELECT  * FROM TBL_Sitios ts  "
                + "WHERE ts.id= ?";

        String print = "";
        Sitio sitio = new Sitio();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idSitio);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sitio.setLatitud(rs.getString("latitud"));
                sitio.setLongitud(rs.getString("longitud"));
                sitio.setNombre(rs.getString("nombre"));
                sitio.setCiudad(rs.getString("ciudad"));
                sitio.setCodigoPostal(rs.getString("codigoPostal"));
                sitio.setDireccion(rs.getString("direccion"));
                sitio.setEstado(rs.getString("estado"));
                sitio.setPaginaWeb(rs.getString("paginaWeb"));
                sitio.setTipo(rs.getString("tipo"));
                sitio.setTelefono(rs.getString("telefono"));
                sitio.setZona(rs.getString("zona"));
                sitio.setLugar(rs.getString("lugar"));
                print = "<table id=\"tableInformation\">\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Nombre: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("nombre") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Lugar: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("lugar") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Direccion: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("direccion") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Ciudad: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("ciudad") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Estado: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("estado") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Zona: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("zona") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td class='spaceTd'><b>Télefono: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("telefono") + "</td>\n"
                        + "                                </tr>\n"
                        + "                                    <td class='spaceTd'><b>Pagina Web: </b></td>\n"
                        + "                                    <td class='spaceTd'>" + rs.getString("paginaWeb") + "</td>\n"
                        + "                                </tr>\n"
                        + "                            </table>";
                sitio.setPrint(print);

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sitio;
    }

    @Override
    public List<Sitio> consultarSitioFiltrosSinRepetir() {
        String query = "SELECT tipo, ciudad, estado, zona FROM TBL_Sitios ";
        Sitio sitio = null;
        List<Sitio> setPlaces = new ArrayList<Sitio>();
        Connection con = null;
        int i = 0;
        int activo = 1;
        int activoTipo = 0;
        int activoZona = 0;
        int activoCiudad = 0;
        int activoEstado = 0;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sitio = new Sitio();

                if (activo == 0) {
                    i++;
                    for (int x = 0; x < i; x++) {
                        if (setPlaces.get(x).getTipo().equalsIgnoreCase(rs.getString("tipo"))) {
                            activoTipo = 1;
                            break;
                        }
                    }
                    for (int x = 0; x < i; x++) {
                        if (setPlaces.get(x).getCiudad().equalsIgnoreCase(rs.getString("ciudad"))) {
                            activoCiudad = 1;
                            break;
                        }
                    }
                    for (int x = 0; x < i; x++) {
                        if (setPlaces.get(x).getZona().equalsIgnoreCase(rs.getString("zona"))) {
                            activoZona = 1;
                            break;
                        }
                    }
                    for (int x = 0; x < i; x++) {

                        if (setPlaces.get(x).getEstado().equalsIgnoreCase(rs.getString("estado"))) {
                            activoEstado = 1;
                            break;
                        }
                    }
                    if (activoTipo == 1) {
                        sitio.setTipo("");
                        activoTipo = 0;
                    } else {
                        sitio.setTipo(rs.getString("tipo"));
                    }

                    if (activoCiudad == 1) {
                        sitio.setCiudad("");
                        activoCiudad = 0;
                    } else {
                        sitio.setCiudad(rs.getString("ciudad"));
                    }

                    if (activoEstado == 1) {
                        sitio.setEstado("");
                        activoEstado = 0;
                    } else {
                        sitio.setEstado(rs.getString("estado"));
                    }

                    if (activoZona == 1) {
                        sitio.setZona("");
                        activoZona = 0;
                    } else {
                        sitio.setZona(rs.getString("zona"));
                    }

                }

                if (activo == 1) {
                    sitio.setTipo(rs.getString("tipo"));

                    sitio.setCiudad(rs.getString("ciudad"));

                    sitio.setEstado(rs.getString("estado"));

                    sitio.setZona(rs.getString("zona"));

                    activo = 0;
                }

                setPlaces.add(sitio);

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return setPlaces;
    }

}
