<%-- 
    Document   : listadoConMapa
    Created on : Jun 13, 2016, 7:15:58 PM
    Author     : Edgar-Mac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/editar_perfil" var="editar_perfil" />
<spring:url value="/imageUser" var="imageUser" />
<spring:url value="/resources/images/defaultUser.png" var="defaultUser" />
<spring:url value="/resources/images/default_place.png" var="defaultPlace" />
<spring:url value="/resources/images/default_event.png" var="defaultEvent" />
<spring:url value="/userInformation" var="userInformation" />
<spring:url value="/existeSolicitud" var="existeSolicitud" />
<spring:url value="/existeSolicitudSolicitado" var="existeSolicitudSolicitado" />
<spring:url value="/agregarSolicitud" var="agregarSolicitud" />
<spring:url value="/aceptarSolicitud" var="aceptarSolicitud" />
<spring:url value="/eliminarSolicitudSolicitado" var="eliminarSolicitudSolicitado" />
<spring:url value="/eliminarSolicitud" var="eliminarSolicitud" />
<spring:url value="/eliminarAmigo" var="eliminarAmigo" />
<spring:url value="/sonAmigos" var="sonAmigos" />
<spring:url value="/getDataFriendsUser" var="getDataFriendsUser" />
<spring:url value="/getDataPlacesUser" var="getDataPlacesUser" />
<spring:url value="/getDataEventsUser" var="getDataEventsUser" />
<spring:url value="/listRequest" var="listRequest" />
<!DOCTYPE html>

<jsp:include page="cabecera_perfil.jsp"/>
<section>
    <div class="notification2"><span class="infoNotification">No se encontraron lugares con el filtrado</span> <span class="bRequestNotification">Ver solicitudes</span></div>
    <div class="datosPerfil">
        <div class="containerProfile">
            <div class="containerPhoto">

                <img src="${defaultUser}"  id="defaultUser" width="100" height="100">

            </div>
            <div class="containerInfoProfile">
                <div class="profileName">
                    <span><h3 id="nombre"></h3></span>
                    <!--                    <span><i><h5 id="nombre">Edgar López</h5></i></span>-->
                    <span id="lugar"></span><br><br>
                </div>
                <div id="containerIfFriends">
                    <span id="bAddFriend">Agregar a amigos</span>
                    <span id="bFriend">Amigos</span>
                    <span id="bUnFriend">Eliminar de mis amigos</span>
                    <span id="bRequest">Solicitud enviada</span>
                    <span id="bUnRequest">Cancelar la solicitud</span>
                    <span id="bAcceptRequest">Aceptar la solicitud</span>
                    <span id="bCancelRequest">Rechazar la solicitud</span>
                </div>
            </div>

        </div>
    </div>
    <div class="lugaresPerfil">
        <div id="containerBotones">
            <div class="containerCounters">
                <ul class="ulCounters">
                    <li id="liAmigos">Mis amigos</li>
                    <li id="liSitios">Mis Sitios favoritos</li>
                    <li id="liEventos">Mis Eventos</li>
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
    $("#bFriend").mouseover(function () {
        $("#bUnFriend").show();
        $("#bFriend").hide();
    });

    $("#bUnFriend").mouseout(function () {
        $("#bFriend").show();
        $("#bUnFriend").hide();
    });
    $("#bRequest").mouseover(function () {
        $("#bUnRequest").show();
        $("#bRequest").hide();
    });

    $("#bUnRequest").mouseout(function () {
        $("#bRequest").show();
        $("#bUnRequest").hide();
    });
    $(document).ready(function () {
        $("#bRequest").hide();
        $("#bFriend").hide();
        $("#bAddFriend").hide();
        $.ajax({
            method: "POST",
            cache: false,
            url: "${imageUser}",
            data: {idUsuario: ${param.idUser}},
            complete: function () {
            },
            success: function (data) {
                if (data) {
                    $(".containerPhoto").append(data);
                    $("#defaultUser").hide();
                }else{
                    $("#defaultUser").show();
                }
               
                
                console.log(data)
            }
        });

        $.ajax({
            method: "POST",
            url: "${userInformation}",
            data: {idUsuario: ${param.idUser}},
            complete: function () {
            },
            success: function (data) {

                if (data != null) {
                    document.getElementById("nombre").innerHTML = data.nombreCompleto;
                    document.getElementById("lugar").innerHTML = data.ciudad + ", " + data.estado + ".";

                }
            }
        });

        $.ajax({
            method: "POST",
            url: "${existeSolicitud}",
            data: {idSolicitado: ${param.idUser}},
            complete: function () {
            },
            success: function (data) {

                if (data == 0) {
                    $.ajax({
                        method: "POST",
                        url: "${existeSolicitudSolicitado}",
                        data: {idSolicitado: ${param.idUser}},
                        complete: function () {
                        },
                        success: function (data) {

                            if (data == 0) {
                                $.ajax({
                                    method: "POST",
                                    url: "${sonAmigos}",
                                    data: {idSolicitado: ${param.idUser}},
                                    complete: function () {
                                    },
                                    success: function (data) {

                                        if (data == 0) {
                                            $("#bAddFriend").show();
                                        }
                                        if (data == 1) {
                                            $("#bFriend").show();
                                            
                                        }
                                        console.log(data)
                                    }
                                });
                            }
                            if (data == 1) {
                                $("#bAcceptRequest").show();
                                $("#bCancelRequest").show();
                            }
                            console.log(data)
                        }
                    });
                }
                if (data == 1) {
                    $("#bRequest").show();
                }
                console.log(data)
            }
        });

        $.ajax({
            method: "POST",
            cache: false,
            url: "${getDataFriendsUser}",
            data: {idUsuario: ${param.idUser}},
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
            url: "${getDataPlacesUser}",
            data: {idUsuario: ${param.idUser}},
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
            url: "${getDataEventsUser}",
            data: {idUsuario: ${param.idUser}},
            complete: function () {
            },
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
                    $("#ulEvents").append(data[i].print);
                }
            }
        });
    });
    $("#bAddFriend").click(function () {
        $.ajax({
            method: "POST",
            url: "${agregarSolicitud}",
            data: {idSolicitado: ${param.idUser}},
            complete: function () {
            },
            success: function (data) {


                if (data == 1) {
                    $("#bAddFriend").hide();
                    $("#bRequest").show();
                }
                console.log(data)
            }
        });
    });
    $("#bUnRequest").click(function () {
        $.ajax({
            method: "POST",
            url: "${eliminarSolicitud}",
            data: {idSolicitado: ${param.idUser}},
            complete: function () {
            },
            success: function (data) {


                if (data == 1) {
                    $("#bAddFriend").show();
                    $("#bRequest").hide();
                }
                console.log(data)
            }
        });
    });
    $("#bCancelRequest").click(function () {
        $.ajax({
            method: "POST",
            url: "${eliminarSolicitudSolicitado}",
            data: {idSolicitado: ${param.idUser}},
            complete: function () {
            },
            success: function (data) {


                if (data == 1) {
                    $("#bAddFriend").show();
                    $("#bAcceptRequest").hide();
                    $("#bCancelRequest").hide();
                }
                console.log(data)
            }
        });
    });
    $("#bAcceptRequest").click(function () {
        $.ajax({
            method: "POST",
            url: "${aceptarSolicitud}",
            data: {idSolicitado: ${param.idUser}},
            complete: function () {
            },
            success: function (data) {


                if (data == 1) {
                    $("#bUnFriend").show();
                    $("#bAcceptRequest").hide();
                    $("#bCancelRequest").hide();
                }
                console.log(data)
            }
        });
    });
    $("#bUnFriend").click(function () {
        $.ajax({
            method: "POST",
            url: "${eliminarAmigo}",
            data: {idSolicitado: ${param.idUser}},
            complete: function () {
            },
            success: function (data) {


                if (data == 1) {
                    $("#bUnFriend").hide();
                    $("#bFriend").hide();
                    $("#bAddFriend").show();
                    
                }
                console.log(data)
            }
        });
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
    
</script>
<jsp:include page="pie.jsp"/>