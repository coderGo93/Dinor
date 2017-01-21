/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.BusquedasDAO;
import dao.PerfilDAO;
import dao.UsuarioDAO;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Busqueda;
import model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Edgar-Mac
 */
@Controller("HomeController")
public class HomeController {

    @Autowired(required = true)
    private BusquedasDAO busquedaDAO;
    @Autowired
    @Qualifier("Usuario")
    private UsuarioDAO usuarioDAO;
    @Autowired
    @Qualifier("Perfil")
    private PerfilDAO perfilDAO;

    private PasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public String home(HttpServletRequest request) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            return "listadoConMapa";

        } else {
            return "inicio";

        }
    }

    @RequestMapping("/existeUsuario")
    @ResponseBody
    public int existeUsuario(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);

        String usuario = request.getParameter("usuario");

        int existe = usuarioDAO.existeUsuario(usuario);

        return existe;

    }

    @RequestMapping("/addAccount")
    public String addAcount(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        String usuario = request.getParameter("user");
        String contraseña = request.getParameter("password");
        String nombreCompleto = request.getParameter("name");
        String email = request.getParameter("email");
        String palabraClave = request.getParameter("keyWord");

        String contraseñaEncriptado = BCrypt.hashpw(contraseña, BCrypt.gensalt(12));

        usuarioDAO.agregarUsuario(nombreCompleto, usuario, email, contraseñaEncriptado, palabraClave);

        int id = usuarioDAO.consuntarIdUsuario(usuario);

        perfilDAO.agregarPerfil(id, "", "", "", "", "", "");

        return "login";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            return "listadoConMapa";

        } else {
            return "login";

        }

    }

    @RequestMapping("/checkLogin")
    public void checkLogin(HttpServletRequest request, RedirectAttributes redirectAttributes, HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException {
        HttpSession session = request.getSession(true);
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("user");
        String password = request.getParameter("password");
        String passwordEncrypted = usuarioDAO.checkPasswordEncrypted(username, password);
        if (passwordEncrypted != null) {
            Usuario login = usuarioDAO.iniciarSesion(username, passwordEncrypted);
            if (login != null) {
                session.setAttribute("idUsuario", login.getId());
                session.setAttribute("nombreCompleto", login.getNombreCompleto());
                session.setAttribute("usuario", login.getUsuario());
                try {
                    response.sendRedirect("listado_mapa");
                } catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                redirectAttributes.addAttribute("wrong", "1");
                try {
                    response.sendRedirect("login");
                } catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else{
            try {
                redirectAttributes.addAttribute("wrong", "1");
                response.sendRedirect("login");
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @RequestMapping("/search")
    @ResponseBody
    public List<Busqueda> getData(HttpServletRequest request) {
        List<Busqueda> getData = busquedaDAO.eventosSitiosListaTodo();

        return getData;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            session.invalidate();
            return "inicio";

        } else {
            return "inicio";

        }

    }

}
