/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Amigo;

/**
 *
 * @author Edgar-Mac
 */
public interface AmigoDAO {
    
    public List<Amigo> consultarAmigo(int idPrincipal);
    
    public int contadorAmigos(String principal);
    
    public int compruebaSonAmigos(String principal, String amigo);
    
    public int compruebaSonAmigosPorId(int idPrincipal, int idAmigo);
    
    public int agregarAmigo(int idPrincipal,int idAmigo);
    
    public int eliminarAmigo(int idPrincipal, int idAmigo);
    
}
