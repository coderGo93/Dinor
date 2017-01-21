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
import model.Perfil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edgar-Mac
 */
@Service("Perfil")
public class PerfilDAOImpl implements PerfilDAO {

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Perfil consultarPerfilIdUsuario(int idUsuario) {
        String query = "SELECT TBL_Perfiles.*, TBL_Usuarios.usuario, TBL_Usuarios.nombreCompleto  "
                + "FROM TBL_Perfiles, TBL_Usuarios "
                + "WHERE TBL_Perfiles.idUsuario=TBL_Usuarios.id AND TBL_Usuarios.id= ?";
        Perfil perfil = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                perfil = new Perfil();
                perfil.setId(rs.getInt("id"));
                perfil.setIdUsuario(rs.getInt("idUsuario"));
                perfil.setSexo(rs.getString("sexo"));
                perfil.setCiudad(rs.getString("ciudad"));
                perfil.setDireccion(rs.getString("direccion"));
                perfil.setTelefono(rs.getString("telefono"));
                perfil.setEstado(rs.getString("estado"));
                perfil.setPais(rs.getString("pais"));
                perfil.setUsuario(rs.getString("usuario"));
                perfil.setNombreCompleto(rs.getString("nombreCompleto"));
                if(rs.getString("ciudad").isEmpty() || rs.getString("ciudad") == null || rs.getString("ciudad").trim().equals("")){
                    perfil.setPrint("<span><h3>"+perfil.getNombreCompleto()+"</h3></span>\n" +
"                    <span></span><br><br>\n" +
"                    <span><a href=\"editar_perfil\">Editar perfil</a></span>");
                }else{
                    perfil.setPrint("<span><h3>"+perfil.getNombreCompleto()+"</h3></span>\n" +
"                    <span>"+perfil.getCiudad()+", "+perfil.getEstado()+".</span><br><br>\n" +
"                    <span><a href=\"editar_perfil\">Editar perfil</a></span>");
                }
                

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return perfil;
    }

    @Override
    public void agregarPerfil(int idUsuario, String telefono, String direccion, String ciudad, String estado, String pais, String sexo) {
        String sql = "INSERT INTO TBL_Perfiles(idUsuario, telefono, direccion, ciudad, estado, pais, sexo)  "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ps.setString(2, telefono);
            ps.setString(3, direccion);
            ps.setString(4, ciudad);
            ps.setString(5, estado);
            ps.setString(6, pais);
            ps.setString(7, sexo);

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
    public int modificarPerfil(int idUsuario, String telefono, String direccion, String ciudad, String estado, String pais, String sexo) {
        String sql = "UPDATE TBL_Perfiles SET telefono = ?, direccion =  ?, ciudad = ?,"
                + " estado = ?, pais =  ?, sexo = ? WHERE idUsuario = ?";
        Connection conn = null;
        int exito = 0;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, telefono);
            ps.setString(2, direccion);
            ps.setString(3, ciudad);
            ps.setString(4, estado);
            ps.setString(5, pais);
            ps.setString(6, sexo);
            ps.setInt(7, idUsuario);

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
    public void eliminarPerfil(int idUsuario) {
        String sql = "DELETE FROM TBL_Perfiles WHERE idUsuario = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idUsuario);

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

}
