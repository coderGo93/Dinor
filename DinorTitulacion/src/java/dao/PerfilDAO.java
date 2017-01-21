/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Perfil;

/**
 *
 * @author Edgar-Mac
 */
public interface PerfilDAO {
    
    public Perfil consultarPerfilIdUsuario(int idUsuario);
    
    public void agregarPerfil(int idUsuario,String telefono, String direccion, String ciudad, String estado, String pais, String sexo);
    
    public int modificarPerfil(int idUsuario, String telefono, String direccion, String ciudad, String estado, String pais, String sexo);
    
    public void eliminarPerfil(int idUsuario);
    
}
