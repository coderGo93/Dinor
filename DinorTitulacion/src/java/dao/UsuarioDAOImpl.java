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
import javax.sql.DataSource;
import model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 *
 * @author Edgar-Mac
 */
@Service("Usuario")
public class UsuarioDAOImpl implements UsuarioDAO {

    @Autowired
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Usuario consultarUsuario(String usuario) {
        String query = "SELECT * FROM TBL_Usuarios WHERE usuario = ?";
        Usuario user = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new Usuario();
                user.setId(rs.getInt("id"));
                user.setUsuario(rs.getString("usuario"));
                user.setNombreCompleto(rs.getString("nombreCompleto"));
                user.setContraseña(rs.getString("password"));
                user.setPalabraClave(rs.getString("palabraClave"));
                user.setEmail(rs.getString("email"));

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public Usuario iniciarSesion(String usuario, String password) {

        String query = "SELECT * FROM TBL_Usuarios WHERE usuario = ? and password = ?";
        Usuario user = null;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, usuario);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new Usuario();
                user.setId(rs.getInt("id"));
                user.setUsuario(rs.getString("usuario"));
                user.setNombreCompleto(rs.getString("nombreCompleto"));
                user.setContraseña(rs.getString("password"));
                user.setPalabraClave(rs.getString("palabraClave"));
                user.setEmail(rs.getString("email"));

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public void agregarUsuario(String nombreCompleto, String usuario, String email, String password, String palabraClave) {
        String sql = "INSERT INTO TBL_Usuarios(nombreCompleto, usuario, email, password, palabraClave, idClase)  "
                + "VALUES (?, ?, ?, ?, ?, 3)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombreCompleto);
            ps.setString(2, usuario);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setString(5, palabraClave);
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
    public int modificarUsuario(String usuario, String nombreCompleto, String email, String password) {
        String sql = "";
        Connection conn = null;
        int exito = 0;
        if (!password.equals("")) {
            sql = "UPDATE TBL_Usuarios SET nombreCompleto = ?, email =  ?, password =  ? "
                    + "WHERE usuario = ?";
        } else {
            sql = "UPDATE TBL_Usuarios SET nombreCompleto = ?, email =  ? "
                    + "WHERE usuario = ?";
        }
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            if (!password.equals("")) {
                ps.setString(1, nombreCompleto);
                ps.setString(2, email);
                ps.setString(3, password);
                ps.setString(4, usuario);
            } else {
                ps.setString(1, nombreCompleto);
                ps.setString(2, email);
                ps.setString(3, usuario);
            }

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
    public void eliminarUsuario(String usuario) {
        String sql = "DELETE FROM TBL_Usuarios WHERE usuario = @usuario";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);

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
    public void agregarImagenPerfil(String nombre, int idUsuario) {
        String sql = "INSERT INTO TBL_Images_User(idUsuario, filename, enabled)  "
                + "VALUES (?, ?, 1)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idUsuario);
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
    public int existeImagenPerfil(int idUsuario) {
        String query = "SELECT * FROM TBL_Images_User WHERE idUsuario = ? AND enabled = 1";
        Connection con = null;
        PreparedStatement ps = null;
        int exists = 0;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idUsuario);
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
    public String consultaImagenPerfil(int idUsuario) {
        String query = "SELECT * FROM TBL_Images_User WHERE idUsuario = ? AND enabled = 1";
        Connection con = null;
        PreparedStatement ps = null;
        String resultado = "";
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                resultado = rs.getString("filename");

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    @Override
    public void actualizaImagenPerfil(String nombre, int idUsuario) {
        String sql = "UPDATE TBL_Images_User SET filename = ?, enabled =  1 WHERE idUsuario = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setInt(2, idUsuario);

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
    public int consuntarIdUsuario(String usuario) {

        String query = "SELECT * FROM TBL_Usuarios WHERE usuario = ?";
        Usuario user = null;
        Connection con = null;
        PreparedStatement ps = null;
        int id = 0;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, usuario);
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
    public int existeUsuario(String usuario) {

        String query = "SELECT * FROM TBL_Usuarios WHERE usuario = ?";
        Usuario user = null;
        Connection con = null;
        PreparedStatement ps = null;
        int existe = 0;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                existe = 1;

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return existe;
    }

    @Override
    public String checkPasswordEncrypted(String usuario, String contraseña) {
        String query = "SELECT password FROM TBL_Usuarios WHERE usuario = ? ";
        Usuario user = null;
        Connection con = null;
        PreparedStatement ps = null;
        String correcto = null;
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                
                if(BCrypt.checkpw(contraseña, rs.getString("password"))){
                    correcto = rs.getString("password");
                }else{
                    correcto = null;
                }

            }
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return correcto;
    }

}
