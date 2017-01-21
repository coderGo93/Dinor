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
import model.Amigo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edgar-Mac
 */
@Service("Amigo")
public class AmigoDAOImpl implements AmigoDAO {

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Amigo> consultarAmigo(int idPrincipal) {
        String query = "SELECT A.id, U.id as 'idPrincipal',U.usuario  as 'principal',"
                + "A.idUsuarioAmigo as 'idAmigo',U2.usuario as 'amigo' "
                + "FROM TBL_Usuarios U INNER JOIN TBL_Amigos A ON U.id=A.idUsuario "
                + "INNER JOIN TBL_Usuarios U2 on A.idUsuarioAmigo=U2.id WHERE U.id=?";
        Amigo amigo = null;
        List<Amigo> getFriends = new ArrayList<Amigo>();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idPrincipal);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                amigo = new Amigo();
                amigo.setId(rs.getInt("id"));
                amigo.setIdAmigo(rs.getInt("idAmigo"));
                amigo.setIdPrincipal(rs.getInt("idPrincipal"));
                amigo.setPrincipal(rs.getString("principal"));
                amigo.setAmigo(rs.getString("amigo"));
                amigo.setPrint("<a href='informacion_usuario?idUser=" + amigo.getIdAmigo() + "' id='liAhref'><li class=\"liFriends\" id=\"1\"><img src=\"resources/images/defaultUser.png\" height=\"50\" width=\"50\"><span class=\"tFriend\">" + amigo.getAmigo() + "</span></li></a>");
                getFriends.add(amigo);

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return getFriends;
    }

    @Override
    public int contadorAmigos(String principal) {
        String query = "SELECT COUNT( U.id) as 'contador' FROM TBL_Usuarios U "
                + "INNER JOIN TBL_Amigos A ON U.id=A.idUsuario "
                + "INNER JOIN TBL_Usuarios U2 on A.idUsuarioAmigo=U2.id"
                + " WHERE U.usuario=@principal";
        Connection con = null;
        PreparedStatement ps = null;
        int counter = 0;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, principal);
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
    public int agregarAmigo(int idPrincipal, int idAmigo) {
        String sql = "INSERT INTO TBL_Amigos(idUsuario, idUsuarioAmigo)  VALUES (?, ?)";
        Connection conn = null;
        int exito = 0;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idPrincipal);
            ps.setInt(2, idAmigo);

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

    @Override
    public int eliminarAmigo(int idPrincipal, int idAmigo) {
        String sql = "DELETE FROM TBL_Amigos WHERE idUsuario = ? AND idUsuarioAmigo=?";
        Connection conn = null;
        int exito = 0;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idPrincipal);
            ps.setInt(2, idAmigo);

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
    public int compruebaSonAmigos(String principal, String amigo) {
        String query = "SELECT A.id, U.id as 'idPrincipal',U.usuario  as 'principal',"
                + "A.idUsuarioAmigo as 'idAmigo',U2.usuario as 'amigo'"
                + " FROM TBL_Usuarios U "
                + "INNER JOIN TBL_Amigos A ON U.id=A.idUsuario "
                + "INNER JOIN TBL_Usuarios U2 on A.idUsuarioAmigo=U2.id"
                + " WHERE U.usuario=? AND U2.usuario=?";
        Connection con = null;
        PreparedStatement ps = null;
        int exists = 0;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, principal);
            ps.setString(2, amigo);
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
    public int compruebaSonAmigosPorId(int idPrincipal, int idAmigo) {
        String query = "SELECT A.id, U.id as 'idPrincipal',U.usuario  as 'principal',"
                + "A.idUsuarioAmigo as 'idAmigo',U2.usuario as 'amigo'"
                + " FROM TBL_Usuarios U "
                + "INNER JOIN TBL_Amigos A ON U.id=A.idUsuario "
                + "INNER JOIN TBL_Usuarios U2 on A.idUsuarioAmigo=U2.id"
                + " WHERE U.id=? AND U2.id=?";
        Connection con = null;
        PreparedStatement ps = null;
        int exists = 0;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idPrincipal);
            ps.setInt(2, idAmigo);
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

}
