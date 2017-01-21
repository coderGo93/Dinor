/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Usuario;
import org.springframework.cache.annotation.Cacheable;

/**
 *
 * @author Edgar-Mac
 */
public interface UsuarioDAO {
    
    public Usuario consultarUsuario(String usuario);
    
    public Usuario iniciarSesion(String usuario, String contraseña);
    
    public void agregarImagenPerfil(String nombre, int idUsuario);
    
    public int existeImagenPerfil(int idUsuario);
    
    public int consuntarIdUsuario(String usuario);
    
    public int existeUsuario(String usuario);
    
    public String consultaImagenPerfil(int idUsuario);
    
    public void actualizaImagenPerfil(String nombre,int idUsuario);
    
    public void agregarUsuario(String nombreCompleto, String usuario, String email, String contraseña, String palabraClave);
    
    public int modificarUsuario(String usuario,String nombreCompleto , String email, String contraseña);
    
    public void eliminarUsuario(String usuario);
    
    public String checkPasswordEncrypted(String usuario, String contraseña);
    
}
