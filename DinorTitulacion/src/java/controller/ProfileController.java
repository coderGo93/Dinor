/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.AmigoDAO;
import dao.EventoDAO;
import dao.PerfilDAO;
import dao.SitioFavoritoDAO;
import dao.SolicitudDAO;
import dao.UsuarioDAO;
import java.io.UnsupportedEncodingException;
import static java.lang.Integer.parseInt;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.Amigo;
import model.Evento;
import model.Perfil;
import model.SitioFavorito;
import model.Solicitud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Edgar-Mac
 */
@Controller("ProfileController")
public class ProfileController {

    @Autowired
    @Qualifier("Usuario")
    private UsuarioDAO usuarioDAO;
    @Autowired
    @Qualifier("Perfil")
    private PerfilDAO perfilDAO;
    @Autowired
    @Qualifier("Solicitud")
    private SolicitudDAO solicitudDAO;
    @Autowired
    @Qualifier("Amigo")
    private AmigoDAO amigoDAO;
    @Autowired
    @Qualifier("Evento")
    private EventoDAO eventoDAO;
    @Autowired
    @Qualifier("SitioFavorito")
    private SitioFavoritoDAO sitioFavoritoDAO;

    @RequestMapping("/administrar")
    public String administer(HttpServletRequest request) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            return "administrar";

        } else {
            return "inicio";

        }
    }

    @RequestMapping("/perfil")
    public String profile(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {

            return "perfil";

        } else {
            return "inicio";

        }
    }

    @RequestMapping("/editar_perfil")
    public String edit_profile(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            String imageKey = usuarioDAO.consultaImagenPerfil((int) session.getAttribute("idUsuario"));

            model.addAttribute("imagenPerfil", imageKey);

            return "editar_perfil";

        } else {
            return "inicio";

        }
    }

    @RequestMapping("/request_modify_profile")
    @ResponseBody
    public int request_modify(HttpServletRequest request, ModelMap model) throws UnsupportedEncodingException {
        HttpSession session = request.getSession(true);
        request.setCharacterEncoding("UTF-8");
        int exito = 0;
        String usuario = session.getAttribute("usuario").toString();
        String nombreCompleto = request.getParameter("name");
        String email = request.getParameter("email");
        String contraseña = request.getParameter("password");
        String contraseñaEncriptado = BCrypt.hashpw(contraseña, BCrypt.gensalt(12));

        exito = usuarioDAO.modificarUsuario(usuario, nombreCompleto, email, contraseñaEncriptado);
        
        if(exito == 1){
            session.removeAttribute("nombreCompleto");
            session.setAttribute("nombreCompleto", nombreCompleto);
        }
        

        //String ciudad, String estado, String pais, String sexo
        int idUsuario = (int) session.getAttribute("idUsuario");
        String telefono = request.getParameter("phone");
        String direccion = request.getParameter("address");
        String ciudad = request.getParameter("city");
        String estado = request.getParameter("state");
        String pais = request.getParameter("country");
        String sexo = request.getParameter("gender");

        exito = perfilDAO.modificarPerfil(idUsuario, telefono, direccion, ciudad, estado, pais, sexo);
        
        
        return exito;

    }

    @RequestMapping("/informacion_usuario")
    public String informationUser(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            return "informacion_usuario";

        } else {
            return "inicio";

        }
    }

    @RequestMapping("/existeSolicitud")
    @ResponseBody
    public int existRequest(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        int idSolicitado = parseInt(request.getParameter("idSolicitado"));
        int idSolicitante = (int) session.getAttribute("idUsuario");

        int existe = solicitudDAO.compruebaSiTieneSolicitudEntreEllosPorId(idSolicitado, idSolicitante);

        return existe;

    }

    @RequestMapping("/sonAmigos")
    @ResponseBody
    public int areFriend(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        int idSolicitado = parseInt(request.getParameter("idSolicitado"));
        int idSolicitante = (int) session.getAttribute("idUsuario");

        int existe = amigoDAO.compruebaSonAmigosPorId(idSolicitante, idSolicitado);

        if (existe == 0) {
            existe = amigoDAO.compruebaSonAmigosPorId(idSolicitado, idSolicitante);
        }

        return existe;

    }

    @RequestMapping("/existeSolicitudSolicitado")
    @ResponseBody
    public int existRequest2(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        int idSolicitado = parseInt(request.getParameter("idSolicitado"));
        int idSolicitante = (int) session.getAttribute("idUsuario");

        int existe = solicitudDAO.compruebaSiTieneSolicitudEntreEllosPorId(idSolicitante, idSolicitado);

        return existe;

    }

    @RequestMapping("/agregarSolicitud")
    @ResponseBody
    public int addRequest(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        int idSolicitado = parseInt(request.getParameter("idSolicitado"));
        int idSolicitante = (int) session.getAttribute("idUsuario");

        int exito = solicitudDAO.agregarSolicitud(idSolicitado, idSolicitante);

        return exito;

    }

    @RequestMapping("/aceptarSolicitud")
    @ResponseBody
    public int acceptRequest(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        int idSolicitado = parseInt(request.getParameter("idSolicitado"));
        int idSolicitante = (int) session.getAttribute("idUsuario");

        int exito = amigoDAO.agregarAmigo(idSolicitante, idSolicitado);

        exito = amigoDAO.agregarAmigo(idSolicitado, idSolicitante);

        exito = solicitudDAO.eliminarSolicitud(idSolicitante, idSolicitado);

        return exito;

    }

    @RequestMapping("/eliminarSolicitud")
    @ResponseBody
    public int deleteRequest(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        int idSolicitado = parseInt(request.getParameter("idSolicitado"));
        int idSolicitante = (int) session.getAttribute("idUsuario");

        int exito = solicitudDAO.eliminarSolicitud(idSolicitado, idSolicitante);

        return exito;

    }

    @RequestMapping("/eliminarSolicitudSolicitado")
    @ResponseBody
    public int deleteRequest2(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        int idSolicitado = parseInt(request.getParameter("idSolicitado"));
        int idSolicitante = (int) session.getAttribute("idUsuario");

        int exito = solicitudDAO.eliminarSolicitud(idSolicitante, idSolicitado);

        return exito;

    }

    @RequestMapping("/eliminarAmigo")
    @ResponseBody
    public int unFriend(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        int idSolicitado = parseInt(request.getParameter("idSolicitado"));
        int idSolicitante = (int) session.getAttribute("idUsuario");

        int exito = amigoDAO.eliminarAmigo(idSolicitante, idSolicitado);
        exito = amigoDAO.eliminarAmigo(idSolicitado, idSolicitante);

        return exito;

    }

    @RequestMapping("/contadorSolicitud")
    @ResponseBody
    public int counterRequest(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        int idSolicitado = (int) session.getAttribute("idUsuario");

        int contador = solicitudDAO.contadorSolicitudesPorId(idSolicitado);

        return contador;

    }
    
    @RequestMapping("/listRequest")
    @ResponseBody
    public List<Solicitud> listRequest(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        int idSolicitado = (int) session.getAttribute("idUsuario");
        
        List<Solicitud> getRequests = solicitudDAO.consultarSolicitud(idSolicitado);
        

        return getRequests;

    }

    @RequestMapping("/userInformation")
    @ResponseBody
    public Perfil userInformation(HttpServletRequest request, ModelMap model) {
        int idUsuario = parseInt(request.getParameter("idUsuario"));
        Perfil perfil = perfilDAO.consultarPerfilIdUsuario(idUsuario);

        return perfil;

    }
    
    @RequestMapping("/userProfile")
    @ResponseBody
    public Perfil userProfile(HttpServletRequest request, ModelMap model) {
        
        HttpSession session = request.getSession(true);
        int idUsuario = (int) session.getAttribute("idUsuario");
        Perfil perfil = perfilDAO.consultarPerfilIdUsuario(idUsuario);

        return perfil;

    }

    @RequestMapping("/imageUser")
    @ResponseBody
    public String imageProfile(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        String imageKey = usuarioDAO.consultaImagenPerfil(parseInt(request.getParameter("idUsuario")));
        if (imageKey == null) {
            return "<img src=\"https://s3.amazonaws.com/storagedinor/images/user/+" + imageKey + "\" height=\"100\" width=\"100\" id=\"photoProfile\">";
        } else {
            return null;
        }

    }

    @RequestMapping("/getDataFriendsUser")
    @ResponseBody
    public List<Amigo> getDataFriendsUser(HttpServletRequest request, ModelMap model) {
        List<Amigo> getFriends = amigoDAO.consultarAmigo(parseInt(request.getParameter("idUsuario")));

        return getFriends;

    }

    @RequestMapping("/getDataPlacesUser")
    @ResponseBody
    public List<SitioFavorito> getDataPlacesUser(HttpServletRequest request, ModelMap model) {
        List<SitioFavorito> getPlaces = sitioFavoritoDAO.consultarSitioFavoritoCreador(parseInt(request.getParameter("idUsuario")));

        return getPlaces;

    }

    @RequestMapping("/getDataEventsUser")
    @ResponseBody
    public List<Evento> getDataEventsUser(HttpServletRequest request, ModelMap model) {
        List<Evento> getEvents = eventoDAO.consultarEventoCreador(parseInt(request.getParameter("idUsuario")));

        return getEvents;

    }

    @RequestMapping("/getDataFriends")
    @ResponseBody
    public List<Amigo> getDataFriends(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        List<Amigo> getFriends = amigoDAO.consultarAmigo((int) session.getAttribute("idUsuario"));

        return getFriends;

    }

    @RequestMapping("/getDataPlaces")
    @ResponseBody
    public List<SitioFavorito> getDataPlaces(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        List<SitioFavorito> getPlaces = sitioFavoritoDAO.consultarSitioFavoritoCreador((int) session.getAttribute("idUsuario"));

        return getPlaces;

    }

    @RequestMapping("/getDataEvents")
    @ResponseBody
    public List<Evento> getDataEvents(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        List<Evento> getEvents = eventoDAO.consultarEventoCreador((int) session.getAttribute("idUsuario"));

        return getEvents;

    }
}
