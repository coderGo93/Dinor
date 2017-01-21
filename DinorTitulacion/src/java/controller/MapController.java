/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.BusquedasDAO;
import dao.UsuarioDAO;
import static java.lang.Integer.parseInt;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Busqueda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.SitioDAO;
import model.Sitio;


/**
 *
 * @author Edgar-Mac
 */
@Controller("MapController")
public class MapController {

    @Autowired(required = true)
    private BusquedasDAO busquedaDAO;
    @Autowired
    @Qualifier("Usuario")
    private UsuarioDAO usuarioDAO;
    @Autowired
    @Qualifier("Sitio")
    private SitioDAO sitioDAO;
    
    
    @RequestMapping("/listado_mapa")
    public String listMap(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            return "listadoConMapa";

        } else {
            return "inicio";

        }

    }
    
    @RequestMapping(value="/dataPlaceEvent")
    @ResponseBody
    public List<Busqueda> dataHome(HttpServletRequest request, ModelMap model) {
        List<Busqueda> getData = null;
        getData = busquedaDAO.eventosSitiosLista(0);

        return getData;

    }
    
    @RequestMapping("/imageProfile")
    @ResponseBody
    public String imageProfile(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        String imageKey = usuarioDAO.consultaImagenPerfil((int) session.getAttribute("idUsuario"));
        
        if(imageKey != ""){
            return "<img src=\"https://s3.amazonaws.com/storagedinor/images/user/+"+imageKey+"\" height=\"100\" width=\"100\" id=\"photoProfile\">";
        }else{
            return "";
        }

        

    }

    @RequestMapping("/moreData")
    @ResponseBody
    public List<Busqueda> moreData(HttpServletRequest request, ModelMap model) {
        int actual = parseInt(request.getParameter("page"));
        List<Busqueda> getData = null;
        getData = busquedaDAO.eventosSitiosLista(actual);

        return getData;

    }
    
    @RequestMapping("/imagesMapData")
    @ResponseBody
    public List<Busqueda> imagesData(HttpServletRequest request, ModelMap model) {
        int idSitio = parseInt(request.getParameter("idSitio"));
        List<Busqueda> info = busquedaDAO.consultaImagenEventoSitio(idSitio, request.getParameter("clase"));

        return info;

    }
    
    @RequestMapping("/dataModal")
    @ResponseBody
    public String dataModal(HttpServletRequest request, ModelMap model) {
        String resultado;
        String algo = request.getParameter("clase");
        resultado = busquedaDAO.modalSitioEvento(request.getParameter("nombre"), request.getParameter("latitud"), request.getParameter("longitud"),request.getParameter("clase"));

        return resultado;

    }
    
    
    @RequestMapping("/obtener_filtro")
    @ResponseBody
    public List<Sitio> getDataFilter(HttpServletRequest request) {
        List<Sitio> getData = sitioDAO.consultarSitioFiltrosSinRepetir();
        
        
        return getData;
    }
    
    @RequestMapping("/datosConFiltro")
    @ResponseBody
    public List<Sitio> getDataFiltered(HttpServletRequest request) {
        String categoria = request.getParameter("categoria");
        String estado = request.getParameter("estado");
        String zona = request.getParameter("zona");
        String ciudad = request.getParameter("ciudad");
        
        List<Sitio> getData = sitioDAO.consultarSitioPorFiltro(categoria, estado, zona, ciudad, 0);
        
        
        return getData;
    }
    
    @RequestMapping("/moreDataFiltered")
    @ResponseBody
    public List<Sitio> moreDataFiltered(HttpServletRequest request) {
        String categoria = request.getParameter("categoria");
        String estado = request.getParameter("estado");
        String zona = request.getParameter("zona");
        String ciudad = request.getParameter("ciudad");
        int actual = parseInt(request.getParameter("page"));
        
        List<Sitio> getData = sitioDAO.consultarSitioPorFiltro(categoria, estado, zona, ciudad, actual);
        
        
        return getData;
    }
}
