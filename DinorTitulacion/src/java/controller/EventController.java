/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.EventoAgendadoDAO;
import dao.EventoAmigoDAO;
import dao.EventoDAO;
import java.io.UnsupportedEncodingException;
import static java.lang.Integer.parseInt;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.Evento;
import model.EventoAgendado;
import model.EventoAmigo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Edgar-Mac
 */
@Controller("EventController")
public class EventController {

    @Autowired(required = true)
    private EventoDAO eventoDAO;

    @Autowired(required = true)
    private EventoAgendadoDAO eventoAgendadoDAO;
    
    @Autowired(required = true)
    private EventoAmigoDAO eventoAmigoDAO;

    @RequestMapping("/administrar_eventos")
    public String Administer_Events(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {

            return "administrar_eventos";

        } else {
            return "inicio";

        }
    }
    
     @RequestMapping("/modificar_evento")
    public String modify_event(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            List<Evento> info = eventoDAO.consultaImagenEvento(parseInt(request.getParameter("idEvento")));

            model.addAttribute("imagenesEvento", info);

            return "modificar_evento";

        } else {
            return "inicio";

        }
    }

    @RequestMapping("/eventos")
    public String events(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {

            return "eventos";

        } else {
            return "inicio";

        }
    }

    @RequestMapping("/eventos_agendados")
    public String eventsScheduled(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {

            return "eventos_agendados";

        } else {
            return "inicio";

        }
    }

    @RequestMapping("/moreDataEvent")
    @ResponseBody
    public List<Evento> moreData(HttpServletRequest request, ModelMap model) {
        int actual = parseInt(request.getParameter("page"));
        HttpSession session = request.getSession(true);
        List<Evento> getData = null;
        getData = eventoDAO.consultarEventoTodo(actual);

        return getData;

    }

    @RequestMapping("/dataEvent")
    @ResponseBody
    public List<Evento> dataHome(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        List<Evento> getData = null;
        getData = eventoDAO.consultarEventoTodo(0);

        return getData;

    }

    @RequestMapping("/imagesEventData")
    @ResponseBody
    public List<Evento> imagesData(HttpServletRequest request, ModelMap model) {
        int idSitio = parseInt(request.getParameter("idSitio"));
        List<Evento> info = eventoDAO.consultaImagenEvento(idSitio);

        return info;

    }

    @RequestMapping("/imagesEvent")
    @ResponseBody
    public List<Evento> imagesPlace(HttpServletRequest request, ModelMap model) {
        List<Evento> info = eventoDAO.consultaImagenEvento(parseInt(request.getParameter("idEvento")));

        return info;

    }

    @RequestMapping("/moreDataEventScheduled")
    @ResponseBody
    public List<EventoAgendado> moreDataScheduled(HttpServletRequest request, ModelMap model) {
        int actual = parseInt(request.getParameter("page"));
        HttpSession session = request.getSession(true);
        List<EventoAgendado> getData = null;
        getData = eventoAgendadoDAO.consultarEventoAgendadoTodo(actual);

        return getData;

    }

    @RequestMapping("/dataModalEvent")
    @ResponseBody
    public String dataModal(HttpServletRequest request, ModelMap model) {
        String resultado;
        String algo = request.getParameter("clase");
        resultado = eventoDAO.consultarEventoNombre(request.getParameter("nombre"), request.getParameter("latitud"), request.getParameter("longitud"));

        return resultado;

    }

    @RequestMapping("/dataEventScheduled")
    @ResponseBody
    public List<EventoAgendado> dataHomeScheduled(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        List<EventoAgendado> getData = null;
        getData = eventoAgendadoDAO.consultarEventoAgendadoTodo(0);

        return getData;

    }

    @RequestMapping("/dataEventAdminister")
    @ResponseBody
    public List<Evento> dataHomeAdminister(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        List<Evento> info = eventoDAO.consultarEventoCreadorPagina((int) session.getAttribute("idUsuario"), 0);

        return info;

    }

    @RequestMapping("/moreDataEventAdminister")
    @ResponseBody
    public List<Evento> moreDataAdminister(HttpServletRequest request, ModelMap model) {
        int actual = parseInt(request.getParameter("page"));
        HttpSession session = request.getSession(true);
        List<Evento> getData = null;
        getData = eventoDAO.consultarEventoCreadorPagina((int) session.getAttribute("idUsuario"), actual);

        return getData;

    }

    @RequestMapping("/dataModalEventAdminister")
    @ResponseBody
    public String dataModalAdminister(HttpServletRequest request, ModelMap model) {
        String resultado;
        String algo = request.getParameter("clase");
        resultado = eventoDAO.consultarEventoNombre(request.getParameter("nombre"), request.getParameter("latitud"), request.getParameter("longitud"));

        return resultado;

    }

//    @RequestMapping("/request_modify_event")
//    public String modify_events(HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
//        HttpSession session = request.getSession(true);
//
//        if (session.getAttribute("idUsuario") != null) {
//            int idEvento = parseInt(request.getParameter("idEvento"));
//
//            String nombreEvento = request.getParameter("nombreEvento");
//            String descripcion = request.getParameter("descripcion");
//            String nombreLugar = request.getParameter("nombreLugar");
//            String fechaInicio = request.getParameter("fechaInicio");
//            String fechaTermino = request.getParameter("fechaTermino");
//            String latitud = request.getParameter("latitud");
//            String longitud = request.getParameter("longitud");
//            String horaInicio = request.getParameter("horaInicio");
//            String horaTermino = request.getParameter("horaTermino");
//
//            eventoDAO.modificarEvento(nombreEvento, descripcion, nombreLugar,
//                    fechaInicio, fechaTermino, latitud, longitud, horaInicio, horaTermino);
//
//            redirectAttributes.addAttribute("idEvento", idEvento);
//            return "redirect:/modificar_evento";
//
//        } else {
//            return "inicio";
//
//        }
//    }

    @RequestMapping("/eliminar_evento")
    public String delete_event(HttpServletRequest request, ModelMap model, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            int idEvento = parseInt(request.getParameter("idEvento"));

            eventoDAO.eliminarEvento(idEvento);

            return "administrar_eventos";

        } else {
            return "inicio";

        }
    }

    @RequestMapping("/informacion_evento")
    public String informationEvent(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("idUsuario") != null) {
            return "informacion_evento";

        } else {
            return "inicio";

        }
    }

    @RequestMapping("/eventInformation")
    @ResponseBody
    public Evento placeInformation(HttpServletRequest request, ModelMap model) {

        Evento evento = eventoDAO.consultarEventoInformacion(parseInt(request.getParameter("idEvento")));

        return evento;

    }
    
    @RequestMapping("/getDataNoInvited")
    @ResponseBody
    public List<EventoAmigo> getDataNoInvited(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        int idEvento = parseInt(request.getParameter("idEvento"));
        int idCreador = (int) session.getAttribute("idUsuario");
        
        List<EventoAmigo> getData = null;
        getData = eventoAmigoDAO.consultarNoInvitadosDeEventoAmigo(idCreador,idEvento);

        return getData;

    }
    
    @RequestMapping("/getDataInvited")
    @ResponseBody
    public List<EventoAmigo> getDataInvited(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        int idEvento = parseInt(request.getParameter("idEvento"));
        int idCreador = (int) session.getAttribute("idUsuario");
        
        List<EventoAmigo> getData = null;
        getData = eventoAmigoDAO.consultarEventoAmigoPorIdCreadoryIdEvento(idCreador,idEvento);

        return getData;

    }
    
    @RequestMapping("/addInvite")
    @ResponseBody
    public int addInviteEvent(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession(true);
        int idEvento = parseInt(request.getParameter("idEvento"));
        int idSeguidor = parseInt(request.getParameter("idSeguidor"));
        
        int exito = eventoAmigoDAO.agregarEventoAmigoPorId(idSeguidor,idEvento);

        return exito;

    }
    
    @RequestMapping("/addEvent")
    @ResponseBody
    public int addEvent(HttpServletRequest request, ModelMap model) throws UnsupportedEncodingException {
        //String nombreEvento, String descripcion , String nombreLugar,
        int exito = 0;
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(true);
        
        int idCreador = (int) session.getAttribute("idUsuario");
        String nombreEvento = request.getParameter("name");
        String descripcion = request.getParameter("description");
        String nombreLugar = request.getParameter("place");
        String fechaInicio = request.getParameter("fechaInicio");
        String fechaTermino = request.getParameter("fechaTermino");
        String horaInicio = request.getParameter("horaInicio");
        String horaTermino = request.getParameter("horaTermino");
        String latitud = request.getParameter("latitude");
        String longitud = request.getParameter("longitude");
        
        
        exito = eventoDAO.agregarEvento(idCreador, nombreEvento, descripcion, nombreLugar, fechaInicio, fechaTermino, latitud, longitud, horaInicio, horaTermino);
        

        return exito;

    }
    
    @RequestMapping("/modifyEvent")
    @ResponseBody
    public int modifyEvent(HttpServletRequest request, ModelMap model) throws UnsupportedEncodingException {
        int exito = 0;
        HttpSession session = request.getSession(true);
        request.setCharacterEncoding("UTF-8");
        int idEvento = parseInt(request.getParameter("idEvento"));
        String nombreEvento = request.getParameter("name");
        String descripcion = request.getParameter("description");
        String nombreLugar = request.getParameter("place");
        String fechaInicio = request.getParameter("fechaInicio");
        String fechaTermino = request.getParameter("fechaTermino");
        String horaInicio = request.getParameter("horaInicio");
        String horaTermino = request.getParameter("horaTermino");
        String latitud = request.getParameter("latitude");
        String longitud = request.getParameter("longitude");
        
        exito = eventoDAO.modificarEvento(idEvento, nombreEvento, descripcion, nombreLugar, fechaInicio, fechaTermino, latitud, longitud, horaInicio, horaTermino);
        

        return exito;

    }
}
