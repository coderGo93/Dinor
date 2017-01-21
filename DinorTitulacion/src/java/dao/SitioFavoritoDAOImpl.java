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
import model.SitioFavorito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edgar-Mac
 */
@Service("SitioFavorito")
public class SitioFavoritoDAOImpl implements SitioFavoritoDAO {

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<SitioFavorito> consultarSitioFavoritoPorUsuario(String usuario) {
        String query = "SELECT * FROM TBL_SitiosFavoritos WHERE usuario = ? and favorito = 1";
        SitioFavorito sitioFavorito = null;
        List<SitioFavorito> getPlacesFavorites = new ArrayList<SitioFavorito>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sitioFavorito = new SitioFavorito();
                sitioFavorito.setId(rs.getInt("id"));
                sitioFavorito.setTipo(rs.getString("tipo"));
                sitioFavorito.setFavorito(rs.getInt("favorito"));
                sitioFavorito.setLatitud(rs.getString("latitud"));
                sitioFavorito.setLongitud(rs.getString("longitud"));
                sitioFavorito.setCiudad(rs.getString("ciudad"));
                sitioFavorito.setCodigoPostal(rs.getString("codigoPostal"));
                sitioFavorito.setDireccion(rs.getString("direccion"));
                sitioFavorito.setPaginaWeb(rs.getString("paginaWeb"));
                sitioFavorito.setTelefono(rs.getString("telefono"));
                sitioFavorito.setEstado(rs.getString("estado"));
                sitioFavorito.setZona(rs.getString("zona"));
                sitioFavorito.setNombre(rs.getString("nombre"));

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getPlacesFavorites;
    }

    @Override
    public String consultarSitioFavoritoNombre(String nombre, String longitud, String latitud) {
        String query = "SELECT * FROM TBL_SitiosFavoritos WHERE nombre = ? AND longitud = ? AND latitud = ? AND favorito = 1";
        String print = "";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, nombre);
            ps.setString(2, longitud);
            ps.setString(3, latitud);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                print = "<div class=\"modal-header\">\n"
                        + "                            <span class=\"close\">×</span>\n"
                        + "                            <h2>" + rs.getString("nombre") + "</h2>\n"
                        + "                        </div>\n"
                        + "                        <div class=\"modal-body\"><table id=\"listaInfo\">\n"
                        + "                                <tr>\n"
                        + "                                    <td><b>Nombre: </b></td>\n"
                        + "                                    <td>" + rs.getString("nombre") + "</td>\n"
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
                        + "                            </table>\n"
                        + "                        </div>\n"
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
    public int agregarSitioFavorito(int idCreador, String tipo, String lugar, String longitud, String latitud, String ciudad, String codigoPostal, String direccion, String paginaWeb, String telefono, String estado, String zona, String nombre, int idSitio) {
        String sql = "INSERT INTO TBL_SitiosFavoritos(favorito,tipo,lugar, longitud, latitud, ciudad, codigoPostal, direccion , paginaWeb, telefono, estado , zona, nombre, idUsuario, idClase, idSitio)  "
                + "VALUES (1,? ,? , ? , ? , ? , ?, ?, ?, ?, ? , ?, ?, ?, 4, ?)";
        Connection conn = null;
        int exito = 0;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tipo);
            ps.setString(2, lugar);
            ps.setString(3, longitud);
            ps.setString(4, latitud);
            ps.setString(5, ciudad);
            ps.setString(6, codigoPostal);
            ps.setString(7, direccion);
            ps.setString(8, paginaWeb);
            ps.setString(9, telefono);
            ps.setString(10, estado);
            ps.setString(11, zona);
            ps.setString(12, nombre);
            ps.setInt(13, idCreador);
            ps.setInt(14, idSitio);
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
    public void modificarSitioFavorito(String tipo, String usuario, String longitud, String latitud, String ciudad, String codigoPostal, String direccion, String paginaWeb, String telefono, String estado, String zona, String nombre) {
        String sql = "UPDATE TBL_SitiosFavoritos SET tipo=?, longitud =  ?, latitud = ?, ciudad =  ?, "
                + "codigoPostal = ?, direccion = ? , paginaWeb = ?, telefono = ?, "
                + "estado = ? , zona = ? WHERE usuario = ? AND nombre = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tipo);
            ps.setString(2, usuario);
            ps.setString(3, longitud);
            ps.setString(4, latitud);
            ps.setString(5, ciudad);
            ps.setString(6, codigoPostal);
            ps.setString(7, direccion);
            ps.setString(8, paginaWeb);
            ps.setString(9, telefono);
            ps.setString(10, estado);
            ps.setString(11, zona);
            ps.setString(12, nombre);
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
    public int eliminarSitioFavorito(int idSitio) {
        String sql = "UPDATE TBL_SitiosFavoritos SET favorito = 0 WHERE id = ?";
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
    public int agregarImagenSitioFavorito(String nombre, int idSitioFavorito) {
        String sql = "INSERT INTO TBL_Images_Favorite_Places(idSitio, filename, enabled)  "
                + "VALUES (?, ?, 1)";
        Connection conn = null;
        int exito = 0;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idSitioFavorito);
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
    public int existeImagenSitioFavorito(int idSitioFavorito) {
        String query = "SELECT * FROM TBL_Images_Favorite_Places WHERE idSitio = ? AND enabled = 1";
        Connection con = null;
        PreparedStatement ps = null;
        int exists = 0;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idSitioFavorito);
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
    public List<SitioFavorito> consultaImagenSitioFavorito(int idSitioFavorito) {

        String query = "SELECT * FROM TBL_Images_Favorite_Places WHERE idSitio = ? AND enabled = 1";
        Connection con = null;
        PreparedStatement ps = null;
        SitioFavorito sitio = null;
        List<SitioFavorito> getPlaces = new ArrayList<SitioFavorito>();
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idSitioFavorito);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sitio = new SitioFavorito();
                sitio.setFilename(rs.getString("filename"));
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
    public int eliminarImagenSitioFavorito(String nombre, int idSitioFavorito) {
        String sql = "UPDATE TBL_Images_Favorite_Places SET  enabled =  0 WHERE filename = ? AND idSitio = ?";
        Connection conn = null;
        int exito = 0;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setInt(2, idSitioFavorito);

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
    public List<SitioFavorito> consultarSitioFavoritoCreadorPagina(int creador, int actual) {
        String query = "SELECT * FROM TBL_SitiosFavoritos WHERE idUsuario = ? LIMIT ?,6";
        SitioFavorito sitio = null;
        List<SitioFavorito> setPlaces = new ArrayList<SitioFavorito>();
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
                sitio = new SitioFavorito();
                sitio.setId(rs.getInt("id"));
                sitio.setNombre(rs.getString("nombre"));
                sitio.setTipo(rs.getString("tipo"));
                sitio.setLatitud(rs.getString("latitud"));
                sitio.setLongitud(rs.getString("longitud"));
                sitio.setCiudad(rs.getString("ciudad"));
                sitio.setCodigoPostal(rs.getString("codigoPostal"));
                sitio.setDireccion(rs.getString("direccion"));
                sitio.setPaginaWeb(rs.getString("paginaWeb"));
                sitio.setTelefono(rs.getString("telefono"));
                sitio.setEstado(rs.getString("estado"));
                sitio.setZona(rs.getString("zona"));
                sitio.setPrint("<li id=\"" + actual + "\" class=\"result\" style=\"background-color: #f2f2f2;\"data-latitud='" + sitio.getLatitud() + "' data-longitud='" + sitio.getLongitud() + "' data-nombre='" + sitio.getNombre() + "'>\n"
                        + "                            <div class=\"indexResult\">\n"
                        + "                                <span>" + actual + "</span>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"containerInfo\"> \n"
                        + "                                <div class=\"blockTitle\">\n"
                        + "                                    <div class=\"title\">\n"
                        + "                                        <h2>" + sitio.getNombre() + "</h2>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                                <div class=\"infoBlock\">\n"
                        + "                                    <div class=\"infoResult\">\n"
                        + "                                        <span>" + sitio.getPaginaWeb() + "</span>\n"
                        + "                                        <span class=\"latitud\">" + sitio.getLatitud() + "</span>\n"
                        + "                                        <span class=\"longitud\">" + sitio.getLongitud() + "</span>\n"
                        + "                                    </div>\n"
                        + "                                <div class=\"buttonContainer\">\n"
                        + "                                    <div class=\"bModify\">\n"
                        + "                                        <span class=\"\"lModify>Modificar</span>\n"
                        + "                                    </div>\n"
                        + "                                    <div class=\"bDelete\">\n"
                        + "                                        <span class=\"lDelete\">Eliminar</span>\n"
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
    public List<SitioFavorito> consultarSitioFavoritoTodo(int actual) {
        String query = "SELECT * FROM TBL_SitiosFavoritos WHERE favorito = 1 LIMIT ?,6";
        SitioFavorito sitio = null;
        List<SitioFavorito> setPlaces = new ArrayList<SitioFavorito>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, actual);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                actual++;
                sitio = new SitioFavorito();
                sitio.setId(rs.getInt("id"));
                sitio.setNombre(rs.getString("nombre"));
                sitio.setTipo(rs.getString("tipo"));
                sitio.setLatitud(rs.getString("latitud"));
                sitio.setLongitud(rs.getString("longitud"));
                sitio.setCiudad(rs.getString("ciudad"));
                sitio.setCodigoPostal(rs.getString("codigoPostal"));
                sitio.setDireccion(rs.getString("direccion"));
                sitio.setPaginaWeb(rs.getString("paginaWeb"));
                sitio.setTelefono(rs.getString("telefono"));
                sitio.setEstado(rs.getString("estado"));
                sitio.setZona(rs.getString("zona"));
                sitio.setLugar(rs.getString("lugar"));
                sitio.setPrint("<a href='informacion_sitio?idSitio=" + rs.getInt("idSitio") + "' class='link'><li id=\"" + actual + "\" class=\"result\" style=\"background-color: #f2f2f2;\"data-latitud='" + sitio.getLatitud() + "' data-longitud='" + sitio.getLongitud() + "' data-nombre='" + sitio.getNombre() + "' data-idSitio='" + sitio.getId() + "'>\n"
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
    public List<SitioFavorito> consultarSitioFavoritoCreador(int creador) {
        String query = "SELECT * FROM TBL_SitiosFavoritos WHERE idUsuario = ? AND favorito = 1";
        SitioFavorito sitio = null;
        List<SitioFavorito> setPlaces = new ArrayList<SitioFavorito>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, creador);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sitio = new SitioFavorito();
                sitio.setId(rs.getInt("id"));
                sitio.setNombre(rs.getString("nombre"));
                sitio.setTipo(rs.getString("tipo"));
                sitio.setLatitud(rs.getString("latitud"));
                sitio.setLongitud(rs.getString("longitud"));
                sitio.setCiudad(rs.getString("ciudad"));
                sitio.setCodigoPostal(rs.getString("codigoPostal"));
                sitio.setDireccion(rs.getString("direccion"));
                sitio.setPaginaWeb(rs.getString("paginaWeb"));
                sitio.setTelefono(rs.getString("telefono"));
                sitio.setEstado(rs.getString("estado"));
                sitio.setZona(rs.getString("zona"));
                sitio.setPrint("<a href='informacion_sitio?idSitio=" + rs.getInt("idSitio") + "' id='liAhref'><li class=\"liFriends\" id=\"1\"><img src=\"resources/images/default_place.png\" height=\"50\" width=\"50\"><span class=\"tPlace\">" + sitio.getNombre() + "</span></li></a>");
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
    public int existeSitioFavorito(String nombre, String longitud, String latitud) {
        String query = "SELECT id FROM TBL_SitiosFavoritos WHERE nombre = ? AND longitud = ? AND latitud = ? AND favorito = 1";
        Connection con = null;
        PreparedStatement ps = null;
        int id = 0;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, nombre);
            ps.setString(2, longitud);
            ps.setString(3, latitud);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    @Override
    public int maxId() {
        String query = "SELECT Max(id) AS id FROM TBL_SitiosFavoritos WHERE  favorito = 1";
        Connection con = null;
        PreparedStatement ps = null;
        int id = 0;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

}
