/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.SitioDAO;
import dao.SitioFavoritoDAO;
import java.io.UnsupportedEncodingException;
import static java.lang.Integer.parseInt;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.Sitio;
import model.SitioFavorito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Edgar-Mac
 */
@Controller("PlaceController")
public class PlaceController {

    @Autowired(required = true)
    @Qualifier("Sitio")
    private SitioDAO sitioDAO;

    @Autowired()
    @Qualifier("SitioFavorito")
    private SitioFavoritoDAO sitioFavoritoDAO;

    @RequestMapping("/administrar_sitios")
    public String administerPlace(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {

            return "administrar_sitios";

        } else {
            return "inicio";

        }
    }

    @RequestMapping("/modificar_sitio")
    public String updatePlace(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            List<Sitio> info = sitioDAO.consultaImagenSitio(parseInt(request.getParameter("idSitio")));

            model.addAttribute("imagenesSitio", info);

            return "modificar_sitio";

        } else {
            return "inicio";

        }
    }

    @RequestMapping("/sitios")
    public String places(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {

            return "sitios";

        } else {
            return "inicio";

        }
    }

    @RequestMapping("/sitios_favoritos")
    public String placesFavorites(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {

            return "sitios_favoritos";

        } else {
            return "inicio";

        }
    }

    @RequestMapping("/dataPlace")
    @ResponseBody
    public List<Sitio> dataHome(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        List<Sitio> getData = null;
        getData = sitioDAO.consultarSitioTodo(0);

        return getData;

    }

    @RequestMapping("/imagesPlace")
    @ResponseBody
    public List<Sitio> imagesPlace(HttpServletRequest request, ModelMap model) {
        List<Sitio> info = sitioDAO.consultaImagenSitio(parseInt(request.getParameter("idSitio")));

        return info;

    }

    @RequestMapping("/moreDataPlace")
    @ResponseBody
    public List<Sitio> moreData(HttpServletRequest request, ModelMap model) {
        int actual = parseInt(request.getParameter("page"));
        HttpSession session = request.getSession(true);
        List<Sitio> getData = null;
        getData = sitioDAO.consultarSitioTodo(actual);

        return getData;

    }

    @RequestMapping("/imagesData")
    @ResponseBody
    public List<Sitio> imagesData(HttpServletRequest request, ModelMap model) {
        int idSitio = parseInt(request.getParameter("idSitio"));
        List<Sitio> info = sitioDAO.consultaImagenSitio(idSitio);

        return info;

    }

    @RequestMapping("/dataPlaceAdminister")
    @ResponseBody
    public List<Sitio> dataHomeAdminister(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        List<Sitio> info = sitioDAO.consultarSitioCreador((int) session.getAttribute("idUsuario"), 0);

        return info;

    }

    @RequestMapping("/dataModalPlaceAdminister")
    @ResponseBody
    public String dataModalAdminister(HttpServletRequest request, ModelMap model) {
        String resultado;
        String algo = request.getParameter("clase");
        resultado = sitioDAO.consultarSitioNombre(request.getParameter("nombre"), request.getParameter("longitud"), request.getParameter("latitud"));

        return resultado;

    }

    @RequestMapping("/moreDataPlaceAdminister")
    @ResponseBody
    public List<Sitio> moreDataAdminister(HttpServletRequest request, ModelMap model) {
        int actual = parseInt(request.getParameter("page"));
        HttpSession session = request.getSession(true);
        List<Sitio> getData = null;
        getData = sitioDAO.consultarSitioCreador((int) session.getAttribute("idUsuario"), actual);

        return getData;

    }

    @RequestMapping("/dataModalPlace")
    @ResponseBody
    public String dataModal(HttpServletRequest request, ModelMap model) {
        String resultado;
        String algo = request.getParameter("clase");
        resultado = sitioDAO.consultarSitioNombre(request.getParameter("nombre"), request.getParameter("longitud"), request.getParameter("latitud"));

        return resultado;

    }

//    @RequestMapping("/request_modify_place")
//    public String modify_places(HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
//        HttpSession session = request.getSession(true);
//
//        if (session.getAttribute("idUsuario") != null) {
//            int idSitio = parseInt(request.getParameter("idSitio"));
//
//            String tipo = request.getParameter("tipo");
//            String nombre = request.getParameter("nombre");
//            String longitud = request.getParameter("longitud");
//            String latitud = request.getParameter("latitud");
//            String lugar = request.getParameter("lugar");
//            String ciudad = request.getParameter("ciudad");
//            String codigoPostal = request.getParameter("codigoPostal");
//            String direccion = request.getParameter("direccion");
//            String paginaWeb = request.getParameter("paginaWeb");
//            String telefono = request.getParameter("telefono");
//            String estado = request.getParameter("estado");
//            String zona = request.getParameter("zona");
//
//            sitioDAO.modificarSitio(tipo, nombre, longitud,
//                    latitud, lugar, ciudad, codigoPostal, direccion, paginaWeb,
//                    telefono, estado, zona);
//
//            redirectAttributes.addAttribute("idSitio", idSitio);
//            return "redirect:/modificar_sitio";
//
//        } else {
//            return "inicio";
//
//        }
//    }
    @RequestMapping("/eliminar_sitio")
    public String delete_place(HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            int idSitio = parseInt(request.getParameter("idSitio"));

            sitioDAO.eliminarSitio(idSitio);

            return "administrar_sitios";

        } else {
            return "inicio";

        }
    }

    @RequestMapping("/administrar_sitios_favoritos")
    public String administerPlace_Favorite(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            List<SitioFavorito> info = sitioFavoritoDAO.consultarSitioFavoritoCreadorPagina((int) session.getAttribute("idUsuario"), 0);

            return "administrar_sitios_favoritos";

        } else {
            return "inicio";

        }
    }

    @RequestMapping("/modificar_sitio_favorito")
    public String updatePlace_Favorite(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            List<SitioFavorito> info = sitioFavoritoDAO.consultaImagenSitioFavorito(parseInt(request.getParameter("idSitio")));

            model.addAttribute("imagenesSitio", info);

            return "modificar_sitio";

        } else {
            return "inicio";

        }
    }

    @RequestMapping("/moreDataPlaceFavorite")
    @ResponseBody
    public List<SitioFavorito> moreData_Favorite(HttpServletRequest request, ModelMap model) {
        int actual = parseInt(request.getParameter("page"));
        HttpSession session = request.getSession(true);
        List<SitioFavorito> getData = null;
        getData = sitioFavoritoDAO.consultarSitioFavoritoTodo(actual);

        return getData;

    }

    @RequestMapping("/dataModalPlaceFavoriteAdminister")
    @ResponseBody
    public String dataModalAdminister_Favorite(HttpServletRequest request, ModelMap model) {
        String resultado;

        resultado = sitioFavoritoDAO.consultarSitioFavoritoNombre(request.getParameter("nombre"), request.getParameter("longitud"), request.getParameter("latitud"));

        return resultado;

    }

    @RequestMapping("/moreDataPlaceFavoriteAdminister")
    @ResponseBody
    public List<SitioFavorito> moreDataAdminister_Favorite(HttpServletRequest request, ModelMap model) {
        int actual = parseInt(request.getParameter("page"));
        HttpSession session = request.getSession(true);
        List<SitioFavorito> getData = null;
        getData = sitioFavoritoDAO.consultarSitioFavoritoCreadorPagina((int) session.getAttribute("idUsuario"), actual);

        return getData;

    }

    @RequestMapping("/dataModalPlaceFavorite")
    @ResponseBody
    public String dataModal_Favorite(HttpServletRequest request, ModelMap model) {
        String resultado;
        String algo = request.getParameter("clase");
        resultado = sitioFavoritoDAO.consultarSitioFavoritoNombre(request.getParameter("nombre"), request.getParameter("longitud"), request.getParameter("latitud"));

        return resultado;

    }

    @RequestMapping("/request_modify_place_favorite")
    public String modify_places_Favorite(HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        HttpSession session = request.getSession(true);
        request.setCharacterEncoding("UTF-8");
        if (session.getAttribute("idUsuario") != null) {
            int idSitio = parseInt(request.getParameter("idSitio"));

            String tipo = request.getParameter("tipo");
            String nombre = request.getParameter("nombre");
            String longitud = request.getParameter("longitud");
            String latitud = request.getParameter("latitud");
            String usuario = request.getParameter("usuario");
            String ciudad = request.getParameter("ciudad");
            String codigoPostal = request.getParameter("codigoPostal");
            String direccion = request.getParameter("direccion");
            String paginaWeb = request.getParameter("paginaWeb");
            String telefono = request.getParameter("telefono");
            String estado = request.getParameter("estado");
            String zona = request.getParameter("zona");

            sitioFavoritoDAO.modificarSitioFavorito(tipo, usuario, longitud,
                    latitud, ciudad, codigoPostal, direccion, paginaWeb, telefono,
                    estado, zona, nombre);
            redirectAttributes.addAttribute("idSitio", idSitio);

            return "redirect:/modificar_sitio_favorito";

        } else {
            return "inicio";

        }
    }

    @RequestMapping("/request_delete_place_favorite")
    public String delete_place_Favorite(HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            int idSitio = parseInt(request.getParameter("idSitio"));

            sitioFavoritoDAO.eliminarSitioFavorito(idSitio);

            return "redirect:/modificar_sitio_favorito";

        } else {
            return "inicio";

        }
    }

    @RequestMapping("/informacion_sitio")
    public String informationPlace(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            return "informacion_sitio";

        } else {
            return "inicio";

        }
    }
    


    @RequestMapping("/placeInformation")
    @ResponseBody
    public Sitio placeInformation(HttpServletRequest request, ModelMap model) {

        Sitio sitio = sitioDAO.consultarSitioInformacion(parseInt(request.getParameter("idSitio")));

        return sitio;

    }

    @RequestMapping("/dataPlaceFavorite")
    @ResponseBody
    public List<SitioFavorito> dataFavoriteHome(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        List<SitioFavorito> getData = null;
        getData = sitioFavoritoDAO.consultarSitioFavoritoTodo(0);

        return getData;

    }

    @RequestMapping("/imagesPlaceFavorite")
    @ResponseBody
    public List<SitioFavorito> imagesPlaceFavorite(HttpServletRequest request, ModelMap model) {
        List<SitioFavorito> info = sitioFavoritoDAO.consultaImagenSitioFavorito(parseInt(request.getParameter("idSitio")));

        return info;

    }

    @RequestMapping("/imagesDataFavorite")
    @ResponseBody
    public List<SitioFavorito> imagesDataFavorite(HttpServletRequest request, ModelMap model) {
        int idSitio = parseInt(request.getParameter("idSitio"));
        List<SitioFavorito> info = sitioFavoritoDAO.consultaImagenSitioFavorito(idSitio);

        return info;

    }

    @RequestMapping("/addPlace")
    @ResponseBody
    public int addPlace(HttpServletRequest request, ModelMap model) throws UnsupportedEncodingException {
        int exito = 0;
        HttpSession session = request.getSession(true);
        request.setCharacterEncoding("UTF-8");
        int idCreador = (int) session.getAttribute("idUsuario");
        String tipo = request.getParameter("type");
        String nombre = request.getParameter("name");
        String longitud = request.getParameter("longitude");
        String latitud = request.getParameter("latitude");
        String lugar = request.getParameter("suburb");
        String ciudad = request.getParameter("city");
        String codigoPostal = request.getParameter("zip");
        String direccion = request.getParameter("address");
        String paginaWeb = request.getParameter("website");
        String telefono = request.getParameter("phone");
        String estado = request.getParameter("state");
        String zona = request.getParameter("zone");

        exito = sitioDAO.agregarSitio(idCreador, tipo, nombre, longitud, latitud, lugar, ciudad, codigoPostal, direccion, paginaWeb, telefono, estado, zona);

        return exito;

    }

    @RequestMapping("/modifyPlace")
    @ResponseBody
    public int modifyPlace(HttpServletRequest request, ModelMap model) throws UnsupportedEncodingException {
        int exito = 0;
        HttpSession session = request.getSession(true);
        request.setCharacterEncoding("UTF-8");
        int idSitio = parseInt(request.getParameter("idSitio"));
        String tipo = request.getParameter("type");
        String nombre = request.getParameter("name");
        String longitud = request.getParameter("longitude");
        String latitud = request.getParameter("latitude");
        String lugar = request.getParameter("suburb");
        String ciudad = request.getParameter("city");
        String codigoPostal = request.getParameter("zip");
        String direccion = request.getParameter("address");
        String paginaWeb = request.getParameter("website");
        String telefono = request.getParameter("phone");
        String estado = request.getParameter("state");
        String zona = request.getParameter("zone");

        exito = sitioDAO.modificarSitio(idSitio, tipo, nombre, longitud, latitud, lugar, ciudad, codigoPostal, direccion, paginaWeb, telefono, estado, zona);

        return exito;

    }

    @RequestMapping("/existeSitioFavorito")
    @ResponseBody
    public int existeSitioFavorito(HttpServletRequest request, ModelMap model) {
        int existe = 0;
        String nombre = request.getParameter("name");
        String longitud = request.getParameter("longitude");
        String latitud = request.getParameter("latitude");
        existe = sitioFavoritoDAO.existeSitioFavorito(nombre, longitud, latitud);

        return existe;

    }
    
    @RequestMapping("/eliminarSitioFavorito")
    @ResponseBody
    public int eliminarSitioFavorito(HttpServletRequest request, ModelMap model) {
        int existe = 0;
        int idSitio = parseInt(request.getParameter("idSitio"));
        existe = sitioFavoritoDAO.eliminarSitioFavorito(idSitio);

        return existe;

    }

    @RequestMapping("/addFavoritePlace")
    @ResponseBody
    public int addFavoritePlace(HttpServletRequest request, ModelMap model) throws UnsupportedEncodingException {
        int exito = 0;
        int existe = 0;
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(true);
        int maxId = 0;
        int idCreador = (int) session.getAttribute("idUsuario");
        String tipo = request.getParameter("type");
        String nombre = request.getParameter("name");
        String longitud = request.getParameter("longitude");
        String latitud = request.getParameter("latitude");
        String lugar = request.getParameter("suburb");
        String ciudad = request.getParameter("city");
        String codigoPostal = request.getParameter("zip");
        String direccion = request.getParameter("address");
        String paginaWeb = request.getParameter("website");
        String telefono = request.getParameter("phone");
        String estado = request.getParameter("state");
        String zona = request.getParameter("zone");
        int idSitio = parseInt(request.getParameter("idSitio"));

        exito = sitioFavoritoDAO.agregarSitioFavorito(idCreador, tipo, lugar, longitud, latitud, ciudad, codigoPostal, direccion, paginaWeb, telefono, estado, zona, nombre, idSitio);
        
        if(exito == 1){
            maxId = sitioFavoritoDAO.maxId();
        }
        
        return maxId;

    }
}
