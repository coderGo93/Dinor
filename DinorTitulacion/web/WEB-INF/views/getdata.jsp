<%-- 
    Document   : getdata
    Created on : Jul 14, 2016, 3:42:50 PM
    Author     : Edgar-Mac
--%>

<%@page import="dao.BusquedasDAO"%>
<%@page import="model.Busqueda"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% 
    
        int actual = Integer.parseInt(request.getParameter("page"));
        BusquedasDAO busquedaDAO = null;
        String dato = "";
        List<Busqueda> getData = null;
        getData = busquedaDAO.eventosSitiosLista(actual, actual + 4);
        for(int i = 0; i <getData.size() ; i++){
            dato = "<li id=\"" + actual + "\" class=\"result\" onclick=\"clickResult(this.id)\">\n"
                    + "                            <div class=\"indexResult\">\n"
                    + "                                <span>" + actual + "</span>\n"
                    + "                            </div>\n"
                    + "                            <div class=\"containerInfo\"> \n"
                    + "                                <div class=\"blockTitle\">\n"
                    + "                                    <div class=\"title\">\n"
                    + "                                        <h2>" + getData.get(i).getNombre() + "</h2>\n"
                    + "                                    </div>\n"
                    + "                                </div>\n"
                    + "                                <div class=\"infoBlock\">\n"
                    + "                                    <div class=\"infoResult\">\n"
                    + "                                        <span>" + getData.get(i).getDescripcion() + "</span>\n"
                    + "                                        <span class=\"latitud\">" + getData.get(i).getLatitud() + "</span>\n"
                    + "                                        <span class=\"longitud\">" + getData.get(i).getLongitud() + "</span>\n"
                    + "                                    </div>\n"
                    + "                                </div>\n"
                    + "                            </div>\n"
                    + "                        </li>";
            out.println(dato);
        }
            
        

%>