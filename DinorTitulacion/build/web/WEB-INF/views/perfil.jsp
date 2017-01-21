<%-- 
    Document   : listadoConMapa
    Created on : Jun 13, 2016, 7:15:58 PM
    Author     : Edgar-Mac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/editar_perfil" var="editar_perfil" />
<spring:url value="/imageProfile" var="imageProfile" />
<spring:url value="/contadorSolicitud" var="contadorSolicitud" />
<spring:url value="/getDataFriends" var="getDataFriends" />
<spring:url value="/getDataPlaces" var="getDataPlaces" />
<spring:url value="/getDataEvents" var="getDataEvents" />
<spring:url value="/aceptarSolicitud" var="aceptarSolicitud" />
<spring:url value="/eliminarSolicitudSolicitado" var="eliminarSolicitudSolicitado" />
<spring:url value="/listRequest" var="listRequest" />
<spring:url value="/userProfile" var="userProfile" />
<!DOCTYPE html>

<jsp:include page="cabecera_perfil.jsp"/>
<section>
    <div class="notification2"><span class="infoNotification">No se encontraron lugares con el filtrado</span> <span class="bRequestNotification">Ver solicitudes</span></div>
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
                        <tr>
                            <td><b>Anthony </b></td>
                            <td><span id="bAcceptRequest">Aceptar</span><span id="bDeleteRequest">Eliminar</span></td>
                        </tr>
                        <tr>
                            <td><b>Alguien </b></td>
                            <td><span id="bAcceptRequest">Aceptar</span><span id="bDeleteRequest">Eliminar</span></td>
                        </tr>

                    </table>
                </center>
            </div>
            <div class="modal-footer">
                <h3>Solicitudes</h3>
            </div>
        </div>

    </div>
    <div class="datosPerfil">
        <div class="containerProfile">
            <div class="containerPhoto">

                <span class="glyphicon glyphicon-user icon-size"  id="defaultUser"></span>

            </div>
            <div class="containerInfoProfile">
                <div class="profileName" id="infoProfile">
                </div>

                <div id="containerIfFriends">
                    <span id="noCounterRequest">No tienes solicitudes</span>
                    <span id="bCounterRequest">Tienes 2 solicitudes</span>

                </div>
            </div>

        </div>
    </div>
    <div class="lugaresPerfil">
        <div id="containerBotones">
            <div class="containerCounters">
                <ul class="ulCounters">
                    <li id="liAmigos">Mis amigos</li>
                    <li id="liSitios">Mis sitios favoritos</li>
                    <li id="liEventos">Mis eventos</li>
                </ul>

            </div>

        </div>
        <div id="containerFriends">
            <ul id="ulFriends">

            </ul>
        </div>
        <div id="containerPlaces">
            <ul id="ulPlaces">


            </ul>
        </div>
        <div id="containerEvents">
            <ul id="ulEvents">

            </ul>
        </div>
    </div>



</section>

<script type="text/javascript">
    var lleva = 0;
    var flag = false;
    var modal;

    $(document).ready(function () {
        $.ajax({
            method: "POST",
            cache: false,
            url: "${userProfile}",
            complete: function () {
            },
            success: function (data) {
                document.getElementById('infoProfile').innerHTML = data.print;
            }
        });

        $.ajax({
            method: "POST",
            cache: false,
            url: "${imageProfile}",
            complete: function () {
            },
            success: function (data) {
                if (data != null) {
                    $(".containerPhoto").append(data);
                    $("#defaultUser").css('display', 'none');
                }
                if (data == null) {
                    $("#defaultUser").css('display', 'block');
                }
            }
        });
        $.ajax({
            method: "POST",
            url: "${contadorSolicitud}",
            complete: function () {
            },
            success: function (data) {
                $("#listaInfo tr").remove();
                if (data == 0) {
                    $("#noCounterRequest").show();
                }

                if (data > 0) {
                    $("#noCounterRequest").hide();
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
                    if (data == 1) {
                        document.getElementById("bCounterRequest").innerHTML = "Tienes " + data + " solicitud";
                    }
                    if (data > 1) {
                        document.getElementById("bCounterRequest").innerHTML = "Tienes " + data + " solicitudes";
                    }

                    $("#bCounterRequest").show();
                }
            }
        });

        $.ajax({
            method: "POST",
            cache: false,
            url: "${getDataFriends}",
            complete: function () {
            },
            success: function (data) {

                for (var i = 0; i < data.length; i++) {
                    $("#ulFriends").append(data[i].print);
                }
            }
        });

        $.ajax({
            method: "POST",
            cache: false,
            url: "${getDataPlaces}",
            complete: function () {
            },
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
                    $("#ulPlaces").append(data[i].print);
                }
            }
        });

        $.ajax({
            method: "POST",
            cache: false,
            url: "${getDataEvents}",
            complete: function () {
            },
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
                    $("#ulEvents").append(data[i].print);
                }
            }
        });
    });
    $("#bCounterRequest").click(function () {
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
    $(".bRequestNotification").click(function () {
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
    setInterval(function () {
        $.ajax({
            method: "POST",
            url: "${contadorSolicitud}",
            complete: function () {
            },
            success: function (data) {

                if (data == 0) {
                    $("#bCounterRequest").hide();

                    $("#noCounterRequest").show();
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
                        document.getElementsByClassName('infoNotification')[0].innerHTML = "Tienes una nueva solicitud de amistad";
                        $(".notification2").css("background", "#56d85b");
                        $('.notification2').slideDown('fast');
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


                    if (data == 1) {
                        document.getElementById("bCounterRequest").innerHTML = "Tienes " + data + " solicitud";
                    }
                    if (data > 1) {
                        document.getElementById("bCounterRequest").innerHTML = "Tienes " + data + " solicitudes";
                    }
                    $("#noCounterRequest").hide();
                    $("#bCounterRequest").show();
                }
            }
        });
    }, 2000);
    function close1() {
        $('.notification2').slideUp('fast');
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
            }
        });

    }
</script>
<jsp:include page="pie.jsp"/>