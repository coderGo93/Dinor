<%-- 
    Document   : cabecera
    Created on : Jun 13, 2016, 7:13:33 PM
    Author     : Edgar-Mac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/resources/css/style.css" var="style" />
<spring:url value="/resources/js/dinor.js" var="js" />
<spring:url value="/resources/js/jquery.easy-autocomplete.min.js" var="autoCompleteJS" />
<spring:url value="/resources/css/easy-autocomplete.min.css" var="autoCompleteCSS" />
<spring:url value="/resources/css/easy-autocomplete.themes.min.css" var="autoCompleteThemesCSS" />
<spring:url value="/perfil" var="administrar" />
<spring:url value="/search" var="search" />
<spring:url value="/contadorSolicitud" var="contadorSolicitud" />
<spring:url value="/logout" var="logout" />
<spring:url value="/imageProfile" var="imageProfile" />
<spring:url value="/aceptarSolicitud" var="aceptarSolicitud" />
<spring:url value="/eliminarSolicitudSolicitado" var="eliminarSolicitudSolicitado" />
<spring:url value="/listRequest" var="listRequest" />

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link rel="stylesheet" href="${style}" media="screen" type="text/css" />
        <link rel="stylesheet" href="${autoCompleteCSS}" media="screen" type="text/css" />
        <link rel="stylesheet" href="${autoCompleteThemesCSS}" media="screen" type="text/css" />
        <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
        <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
        <script type="text/javascript" src="${autoCompleteJS}"></script>


        <link rel="stylesheet" href="jquery-ui/themes/base/jquery-ui.min.css" />
        <script src="http://js.api.here.com/v3/3.0/mapsjs-core.js" type="text/javascript" charset="utf-8"></script>
        <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" integrity="sha384-fLW2N01lMqjakBkx3l/M9EahuwpSfeNvV63J5ezn3uZzapT0u7EYsXMjQV+0En5r" crossorigin="anonymous">

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

        <title>Listado - Dinor</title>
    </head>
    <body>
        <!-- The Modal -->
        <div id="myModal" class="modal">

            <!-- Modal content -->
            <div class="modal-content">
                <div class="modal-header">
                    <span class="close">×</span>
                    <h2>Solicitudes</h2>
                </div>
                <div class="modal-body">
                    <center>
                        <table id="listaInfo" style="margin-top: 10px;">
                            <tr class="spacer">
                                <td><b>Anthony </b></td>
                                <td><span id="bAcceptRequest" class="bAcceptRequest" style="display:inline;">Aceptar</span><span id="bDeleteRequest" class="bDeleteRequest">Eliminar</span></td>
                            </tr>
                            <tr class="spacer">
                                <td><b>Alguien </b></td>
                                <td><span id="bAcceptRequest" style="display:inline;">Aceptar</span><span id="bDeleteRequest">Eliminar</span></td>
                            </tr>

                        </table>
                    </center>
                </div>
                <div class="modal-footer">
                    <h3>Solicitudes</h3>
                </div>
            </div>

        </div>
        <nav >
            <div class="box">
                <div class="container-4">
                    <input  id="search" placeholder="Sitios, eventos, ciudad ..." name="search"/>
                    <button class="icon"><i class="fa fa-search"></i></button>
                </div>
            </div>
            <div class="collapse navbar-collapse nav2">

                <ul class="nav navbar-nav navbar-right ">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <span class="glyphicon glyphicon-user"></span> 
                            <strong>${sessionScope.nombreCompleto}</strong>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <div class="navbar-login">
                                    <div class="row">
                                        <div class="col-lg-4">
                                            <p class="text-center" id="imageProfile">
                                                <span class="glyphicon glyphicon-user icon-size" id="defaultUser"></span>

                                            </p>
                                        </div>
                                        <div class="col-lg-8">
                                            <p class="text-left"><strong>${sessionScope.nombreCompleto}</strong></p>
                                            <p class="text-left">
                                                <a href="${administrar}" class="btn btn-primary btn-block btn-sm">Administrar</a>
                                            </p>
                                            <p class="text-left">
                                                <a href="#" class="btn btn-primary btn-block btn-sm " id="bReq"style="background: #5fb53f;border-color: #539826; display:none;">Tienes 2 Solicitudes</a>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <div class="navbar-login navbar-login-session">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <p>
                                                <a href="${logout}" class="btn btn-danger btn-block">Cerrar Sesion</a>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>
    </body>
    <script>
        var lleva = 0;
        var flag = false;
        var modal;
        $(document).ready(function () {

            $.ajax({
                method: "POST",
                cache: false,
                url: "${imageProfile}",
                complete: function () {
                },
                success: function (data) {
                    if (data != "") {
                        $("#imageProfile").append(data);
                    }
                    if (data == "") {
                        $("#defaultUser").css("display", "block")
                    }
                    console.log(data)

                }
            });
            var options = {
                url: "${search}",
                getValue: "nombre",
                list: {
                    match: {
                        enabled: true
                    },
                    onChooseEvent: function () {
                        var value = $("#search").getSelectedItemData().clase;
                        var id = $("#search").getSelectedItemData().id;
                        if (value == "Usuario") {
                            window.location = "informacion_usuario?idUser=" + id;
                        }
                        if (value == "Sitio") {
                            window.location = "informacion_sitio?idSitio=" + id;
                        }
                        if (value == "Evento") {
                            window.location = "informacion_evento?idEvento=" + id;
                        }

                    }
                },
                template: {
                    type: "iconRight",
                    fields: {
                        iconSrc: "print",
                        clase: "clase",
                        id: "id"
                    }
                }
            };
            $("#search").easyAutocomplete(options);


        });
        setInterval(function () {
            $.ajax({
                method: "POST",
                url: "${contadorSolicitud}",
                complete: function () {
                },
                success: function (data) {

                    if (data == 0) {
                        $(".dropdown").css("background", "#28cb3d");
                        $("#bReq").hide();
                        $("#listaInfo tr").remove();
                        lleva = data;

                    }
                    if (flag == true) {
                        flag = false;
                        lleva = data;
                    }
                    if (data) {
                        if (lleva != data && lleva <= data) {
                            $("#listaInfo tr").remove();
                            document.getElementsByClassName('notification')[0].innerHTML = "Tienes una nueva solicitud de amistad";
                            $(".notification").css("background", "#56d85b");
                            $('.notification').slideDown('fast');
                            window.setTimeout(close1, 2000);
                            $.ajax({
                                method: "POST",
                                cache: false,
                                url: "${listRequest}",
                                complete: function () {
                                },
                                success: function (data) {
                                    for (var i = 0; i < data.length; i++) {
                                        $("#listaInfo").append(data[i].print);
                                    }
                                }
                            });
                            flag = true;
                        }


                        $(".dropdown").css("background", "#c14131");
                        if (data == 1) {
                            document.getElementById("bReq").innerHTML = "Tienes " + data + " solicitud";
                        }
                        if (data > 1) {
                            document.getElementById("bReq").innerHTML = "Tienes " + data + " solicitudes";
                        }

                        $("#bReq").show();
                    }
                    console.log(data)
                }
            });
        }, 2000);

        $("#bReq").click(function () {
            modal = document.getElementById('myModal');

// Get the button that opens the modal
            // When the user clicks the button, open the modal

            modal.style.display = "block";


// Get the <span> element that closes the modal
            var span = document.getElementsByClassName("close")[0];



// When the user clicks on <span> (x), close the modal
            span.onclick = function () {
                modal.style.display = "none";
            }

// When the user clicks anywhere outside of the modal, close it
            window.onclick = function (event) {
                if (event.target == modal) {
                    modal.style.display = "none";
                }
            }
        });
        function close1() {
            $('.notification').slideUp('fast');
        }

        function denyRequest(id) {
            var idUser = document.getElementById(id).getAttribute("data-idUser");
            $.ajax({
                method: "POST",
                url: "${eliminarSolicitudSolicitado}",
                data: {idSolicitado: idUser},
                complete: function () {
                },
                success: function (data) {


                    if (data == 1) {
                        modal.style.display = "none";
                    }
                    console.log(data)
                }
            });
        }
        function acceptRequest(id) {
            var idUser = document.getElementById(id).getAttribute("data-idUser");
            $.ajax({
                method: "POST",
                url: "${aceptarSolicitud}",
                data: {idSolicitado: idUser},
                complete: function () {
                },
                success: function (data) {


                    if (data == 1) {
                        modal.style.display = "none";
                    }
                    console.log(data)
                }
            });

        }

    </script>