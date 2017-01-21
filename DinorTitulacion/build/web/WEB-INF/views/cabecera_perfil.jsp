<%-- 
    Document   : cabecera
    Created on : Jun 13, 2016, 7:13:33 PM
    Author     : Edgar-Mac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/resources/css/style.css" var="style" />
<spring:url value="/resources/css/bootstrap-theme.min.css" var="bootstrapTheme" />
<spring:url value="/resources/css/bootstrap.min.css" var="bootstrap" />
<spring:url value="/resources/css/font-awesome.min.css" var="fontAwesome" />
<spring:url value="/resources/js/dinor.js" var="js" />
<spring:url value="/resources/js/jquery-3.1.0.min.js" var="jquery" />
<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJS" />
<spring:url value="/administrar_sitios" var="administrar_sitios" />
<spring:url value="/administrar_eventos" var="administrar_eventos" />
<spring:url value="/sitios" var="sitios" />
<spring:url value="/eventos" var="eventos" />
<spring:url value="/perfil" var="perfil" />
<spring:url value="/eventos_agendados" var="eventosAgendados" />
<spring:url value="/sitios_favoritos" var="sitiosFavoritos" />
<spring:url value="/resources/images/dinor_solo_img.png" var="logo" />
<spring:url value="/listado_mapa" var="listadoConMapa" />
<spring:url value="/contadorSolicitud" var="contadorSolicitud" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${style}" media="screen" type="text/css" />
        <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
        <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>


        <link rel="stylesheet" href="jquery-ui/themes/base/jquery-ui.min.css" />
        <script src="http://js.api.here.com/v3/3.0/mapsjs-core.js" type="text/javascript" charset="utf-8"></script>
        <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

        <title>Administrar - Dinor</title>
    </head>
    <body >
        <nav>
            <div class="dropdown">
                <button class="dropbtn">Sitios</button>
                <div class="dropdown-content">
                    <a href="${sitios}">Ver sitios</a>
                    <a href="${sitiosFavoritos}">Ver sitios favoritos</a>
                    <a href="${administrar_sitios}">Administrar sitios</a>
                </div>
            </div>
            <div class="dropdown">
                <button class="dropbtn">Eventos</button>
                <div class="dropdown-content">
                    <a href="${eventos}">Ver eventos</a>
                    <a href="${eventosAgendados}">Ver eventos agendados</a>
                    <a href="${administrar_eventos}">Administrar eventos</a>
                </div>
            </div>
            <div class="dropdown">

                <button class="dropbtn" id="perfil" onclick="location.href = '${perfil}';">Mi perfil</button>

            </div>
                <a href="${listadoConMapa}"><img src="${logo}" id="logo"></a>
        </nav>
    </body>
    <script>
        var lleva = 0;
        var flag = true;
        setInterval(function () {
            $.ajax({
                method: "POST",
                url: "${contadorSolicitud}",
                complete: function () {
                },
                success: function (data) {

                    if (data == 0) {
//                        $(".dropdown").css("background", "#28cb3d");
                    }
                    if (data > 0) {
                        if (lleva != data) {
                            document.getElementsByClassName('notification2')[0].innerHTML = "Tienes una nueva solicitud de amistad";
                            $(".notification2").css("background", "#56d85b");
                            $('.notification2').slideDown('fast');
                            window.setTimeout(close1, 2000);
                            flag = true;
                        }
                        if (flag == true) {
                            flag = false;
                            lleva = data;
                        }




//                        $(".dropdown").css("background", "#c14131");
//                        if (data == 1) {
//                            document.getElementById("bReq").innerHTML = "Tienes " + data + " solicitud";
//                        }
//                        if (data > 1) {
//                            document.getElementById("bReq").innerHTML = "Tienes " + data + " solicitudes";
//                        }
//
//                        $("#bReq").show();
                    }
                    console.log(data)
                }
            });
        }, 200000);
        function close1() {
            $('.notification2').slideUp('fast');
        }
    </script>