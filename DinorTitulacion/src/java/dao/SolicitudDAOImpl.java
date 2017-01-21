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
import model.Solicitud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edgar-Mac
 */
@Service("Solicitud")
public class SolicitudDAOImpl implements SolicitudDAO {

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Solicitud> consultarSolicitud(int idSolicitado) {
        String query = "SELECT S.id, U.id as 'idSolicitado',U.usuario  as 'solicitado',S.idUsuarioAmigo as 'idSolicitante',U2.usuario as 'solicitante', S.activo "
                + "FROM TBL_Usuarios U INNER JOIN TBL_Solicitudes S ON U.id=S.idUsuario "
                + "INNER JOIN TBL_Usuarios U2 on S.idUsuarioAmigo=U2.id WHERE U.id=? AND activo=1";
        Solicitud solicitud = null;
        List<Solicitud> getRequests = new ArrayList<Solicitud>();
        Connection con = null;
        PreparedStatement ps = null;
        int cont = 0;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idSolicitado);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                solicitud = new Solicitud();
                solicitud.setId(rs.getInt("id"));
                solicitud.setIdSolicitado(rs.getInt("idSolicitado"));
                solicitud.setIdSolicitante(rs.getInt("idSolicitante"));
                solicitud.setSolicitado(rs.getString("solicitado"));
                solicitud.setSolicitante(rs.getString("solicitante"));
                solicitud.setActivo(rs.getInt("activo"));
                solicitud.setPrint("<tr class=\"spacer\">\n" +
"                                <td><b>"+solicitud.getSolicitante()+" </b></td>\n" +
"                                <td><span id='"+cont+"' class='bAcceptRequest' data-idUser='"+solicitud.getIdSolicitante()+"' style=\"display:inline;\" onclick='acceptRequest(this.id)'>Aceptar</span><span id='"+cont+"' class='bDeleteRequest' data-idUser='"+solicitud.getIdSolicitante()+"' onclick='denyRequest(this.id)'>Eliminar</span></td>\n" +
"                            </tr>");
                getRequests.add(solicitud);
                cont++;

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getRequests;
    }

    @Override
    public int contadorSolicitudes(String solicitado) {
        String query = "SELECT COUNT(S.id) AS contador FROM TBL_Usuarios U INNER JOIN TBL_Solicitudes S ON U.id=S.idUsuario"
                + " INNER JOIN TBL_Usuarios U2 on S.idUsuarioAmigo=U2.id "
                + "WHERE U.usuario=? AND activo=1";
        Connection con = null;
        PreparedStatement ps = null;
        int counter = 0;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, solicitado);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                counter = rs.getInt("contador");
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }

    @Override
    public int compruebaSiTieneSolicitudEntreEllos(String solicitado, String solicitante) {
        String query = "SELECT S.id, U.id as 'idSolicitado',U.usuario  as 'solicitado',S.idUsuarioAmigo as 'idSolicitante',"
                + "U2.usuario as 'solicitante', S.activo FROM TBL_Usuarios U "
                + "INNER JOIN TBL_Solicitudes S ON U.id=S.idUsuario "
                + "INNER JOIN TBL_Usuarios U2 on S.idUsuarioAmigo=U2.id"
                + " WHERE U.usuario=? AND U2.usuario= ? AND activo=1";
        Connection con = null;
        PreparedStatement ps = null;
        int exists = 0;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, solicitado);
            ps.setString(1, solicitante);
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

    public int compruebaSiTieneSolicitudEntreEllosPorId(int idSolicitado, int idSolicitante) {
        String query = "SELECT S.id, U.id as 'idSolicitado',U.usuario  as 'solicitado',S.idUsuarioAmigo as 'idSolicitante',"
                + "U2.usuario as 'solicitante', S.activo FROM TBL_Usuarios U "
                + "INNER JOIN TBL_Solicitudes S ON U.id=S.idUsuario "
                + "INNER JOIN TBL_Usuarios U2 on S.idUsuarioAmigo=U2.id"
                + " WHERE U.id=? AND U2.id= ? AND activo=1";
        Connection con = null;
        PreparedStatement ps = null;
        int exists = 0;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idSolicitado);
            ps.setInt(2, idSolicitante);
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
    public int agregarSolicitud(int idSolicitado, int idSolicitante) {
        String sql = "INSERT INTO TBL_Solicitudes(idUsuario,idUsuarioAmigo,activo)  VALUES (?, ?, 1)";
        Connection conn = null;
        int exito = 0;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idSolicitado);
            ps.setInt(2, idSolicitante);
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
    public int eliminarSolicitud(int idSolicitado, int idSolicitante) {
        String sql = "DELETE FROM TBL_Solicitudes WHERE idUsuario = ? AND idUsuarioAmigo = ?";
        Connection conn = null;
        int exito = 0;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idSolicitado);
            ps.setInt(2, idSolicitante);
            ps.executeUpdate();
            ps.close();
            exito = 1;
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
    
    public int contadorSolicitudesPorId(int idSolicitado) {
        String query = "SELECT COUNT(S.id) AS contador FROM TBL_Usuarios U INNER JOIN TBL_Solicitudes S ON U.id=S.idUsuario"
                + " INNER JOIN TBL_Usuarios U2 on S.idUsuarioAmigo=U2.id "
                + "WHERE U.id=? AND activo=1";
        Connection con = null;
        PreparedStatement ps = null;
        int counter = 0;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idSolicitado);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                counter = rs.getInt("contador");
            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }
}
