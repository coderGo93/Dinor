<%-- 
    Document   : listadoConMapa
    Created on : Jun 13, 2016, 7:15:58 PM
    Author     : Edgar-Mac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<spring:url value="/eventInformation" var="eventInformation" />
<spring:url value="/resources/images/ajax-loader.gif" var="loader" />
<spring:url value="/imagesEvent" var="imagesEvent" />
<spring:url value="/getDataNoInvited" var="getDataNoInvited" />
<spring:url value="/getDataInvited" var="getDataInvited" />
<spring:url value="/addInvite" var="addInvite" />
<spring:url value="/listRequest" var="listRequest" />
<!DOCTYPE html>

<jsp:include page="cabecera_perfil.jsp"/>
<section >
    <div class="notification2"><span class="infoNotification">No se encontraron lugares con el filtrado</span> <span class="bRequestNotification">Ver solicitudes</span></div>
     <div id="containerListado">
        <div class="otro">
            <a href="" class="bBack2" onclick="goBack()">Regresar</a>
            <div id="information2">
                <!-- The Modal -->
                <div class="loading-info"><img src="${loader}" /></div>


            </div>

        </div>

        <div id="mapa_container_update" >
            <div id="containerButtons">
                <span id="bFotosInformation">Imagenes</span>
                <span id="bMapaInformation">Mapa</span>
                <span id="bInviteFriend">Invitar a amigos</span>


            </div>
            <div id="listInviteFriends">
                <div id="containerInvitedFriends">
                    <span class="tInviteFriends">Amigos que has invitado</span>
                    <div class="loading-info2"><img src="${loader}" /></div>
                    <ul id="ulFriendsInvited">
                        <!--                        <li class="liFriends" id="1"><img src="resources/images/defaultUser.png" height="50" width="50"><span class="tFriend">algo</span></li>-->
                    </ul>
                </div>
                <div id="containerNotInvitedFriends">
                    <span class="tInviteFriends">Amigos que no has invitado</span>
                    <div class="loading-info2"><img src="${loader}" /></div>
                    <ul id="ulFriendsNotInvited">
                        <!--                        <li class="liFriends" id="1"><img src="resources/images/defaultUser.png" height="50" width="50"><span class="tFriend">fe</span> <span id="bInvite">Invitar</span></li>-->

                    </ul>
                </div>
            </div>
            <div id="galleryPlaces">
                <span id="noPictures">No hay imagenes</span>


            </div>
            <div id="mapaInformation" style="">

            </div>
        </div>
    </div>
</div>

</section>
<script>

    $(document).ready(function () {
        $(".loading-info").show();
        $("#noPictures").hide();
        $.ajax({
            method: "POST",
            cache: false,
            url: "${eventInformation}",
            data: {idEvento: ${param.idEvento}},
            complete: function () {
            },
            success: function (data) {

                if (data != null) {
                    document.getElementById("information2").innerHTML = data.print;
                    initMapInformation(data.latitud, data.longitud);
                    $(".loading-info").hide();
                }



                console.log(data)

//MODAL
            }
        });
        $.ajax({
            method: "POST",
            cache: false,
            url: "${imagesEvent}",
            data: {idEvento: ${param.idEvento}},
            complete: function () {
            },
            success: function (data) {

                for (var i = 0; i < data.length; i++) {
                    $("#galleryPlaces").append(data[i].printImageInformation);
                }
                if (data.length == 0) {
                    $("#noPictures").show();
                }

                console.log(data)

//MODAL
            }
        });


    });
    var flag = 0;
    $("#bInviteFriend").click(function () {
        document.getElementById("ulFriendsNotInvited").innerHTML = "";
        document.getElementById("ulFriendsInvited").innerHTML = "";
        $("#mapaInformation").hide();
        $("#galleryPlaces").hide();
        $("#listInviteFriends").show();
        $(".loading-info2").show();
        $.ajax({
            method: "POST",
            cache: false,
            url: "${getDataNoInvited}",
            data: {idEvento: "${param.idEvento}",
            },
            complete: function () {
            },
            success: function (data) {
                console.log(data)
                for (var i = 0; i < data.length; i++) {
                    $("#ulFriendsNotInvited").append(data[i].print);
                    flag = 1;
                }
                if (flag == 1) {
                    $(".loading-info2").hide();

                }
                if(data.length == 0){
                    document.getElementsByClassName("loading-info2")[0].innerHTML = "No hay amigos que mostrar"
                }
            }
        });

        $.ajax({
            method: "POST",
            cache: false,
            url: "${getDataInvited}",
            data: {idEvento: "${param.idEvento}",
            },
            complete: function () {
            },
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
                    $("#ulFriendsInvited").append(data[i].print);
                    flag = 1;
                }
                if (flag == 1) {
                    $(".loading-info2").hide();

                }
                if(data.length == 0){
                    document.getElementsByClassName("loading-info2")[1].innerHTML = "No hay amigos que mostrar"
                }
            }
        });

    });

    function clickInvite(id) {
        $.ajax({
            method: "POST",
            cache: false,
            url: "${addInvite}",
            data: {idEvento: "${param.idEvento}",
                idSeguidor: id,
            },
            complete: function () {
            },
            success: function (data) {
                if (data == 1) {

                    document.getElementById("ulFriendsNotInvited").innerHTML = "";
                    document.getElementById("ulFriendsInvited").innerHTML = "";
                    $(".loading-info2").show();

                    $.ajax({
                        method: "POST",
                        cache: false,
                        url: "${getDataNoInvited}",
                        data: {idEvento: "${param.idEvento}",
                        },
                        complete: function () {
                        },
                        success: function (data) {
                            for (var i = 0; i < data.length; i++) {
                                $("#ulFriendsNotInvited").append(data[i].print);
                                flag = 1;
                            }
                            if (flag == 1) {
                                $(".loading-info2").hide();

                            }
                        }
                    });

                    $.ajax({
                        method: "POST",
                        cache: false,
                        url: "${getDataInvited}",
                        data: {idEvento: "${param.idEvento}",
                        },
                        complete: function () {
                        },
                        success: function (data) {
                            for (var i = 0; i < data.length; i++) {
                                $("#ulFriendsInvited").append(data[i].print);
                                flag = 1;
                            }
                            if (flag == 1) {
                                $(".loading-info2").hide();

                            }
                        }
                    });
                }
            }
        });
    }
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