<%-- 
    Document   : listadoConMapa
    Created on : Jun 13, 2016, 7:15:58 PM
    Author     : Edgar-Mac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<spring:url value="/resources/images/ajax-loader.gif" var="loader" />
<spring:url value="/moreDataPlaceFavorite" var="moreData" />
<spring:url value="/dataModalPlaceFavorite" var="dataModal" />
<spring:url value="/imagesMapData" var="imagesData" />
<spring:url value="/dataPlaceFavorite" var="dataPlace" />
<spring:url value="/imageProfile" var="imageProfile" />
<spring:url value="/listRequest" var="listRequest" />


<jsp:include page="cabecera_perfil.jsp"/>
<section>
    <div class="notification2"><span class="infoNotification">No se encontraron lugares con el filtrado</span> <span class="bRequestNotification">Ver solicitudes</span></div>
    <div id="containerListado">
        <div id="listadoIzquierda" class="normal">
            <div id="listadoInformacion">     
                <ul class="container_results" id="list">
                </ul>
                <div id="clickInfo"><span>Cargar más lugares</span></div>
                <div class="loading-info"><img src="${loader}" /></div>
                <!-- The Modal -->
                <div id="myModal" class="modal">

                    <!-- Modal content -->
                    <div class="modal-content">
                        <div class="modal-header">
                            <span class="close">×</span>
                            <h2>Nombre</h2>
                        </div>
                        <div class="modal-body">
                            <table id="listaInfo">
                                <tr>
                                    <td><b>Nombre: </b></td>
                                    <td>Some text in the Modal Body</td>
                                </tr>
                                <tr>
                                    <td><b>Lugar: </b></td>
                                    <td>Some text in the Modal Body</td>
                                </tr>
                                <tr>
                                    <td><b>Descripción: </b></td>
                                    <td>Some text in the Modal Body</td>
                                </tr>
                                <tr>
                                    <td><b>Fecha de inicio: </b></td>
                                    <td>Some text in the Modal Body</td>
                                </tr>
                                <tr>
                                    <td><b>Fecha de término </b></td>
                                    <td>Some text in the Modal Body</td>
                                </tr>
                                <tr>
                                    <td><b>Hora del inicio: </b></td>
                                    <td>Some text in the Modal Body</td>
                                </tr>
                                <tr>
                                    <td><b>Hora del término: </b></td>
                                    <td>Some text in the Modal Body</td>
                                </tr>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <h3>Sitio favorito</h3>
                        </div>
                    </div>

                </div>
            </div>

            <div id="mapa_container">
                <div id="mapa" style="display:none;">

                </div>
            </div>
        </div>
    </div>



</section>

<script type="text/javascript">

    var track_page = 6; //track user scroll as page number, right now page number is 1
    var flag = false;
    $(document).ready(function () {
        $('#clickInfo').hide();
        $('.loading-info').show();
        $.ajax({
            method: "POST",
            cache: false,
            url: "${dataPlace}",
            complete: function () {
            },
            success: function (data) {
                if (data.length == 0) {
                    $('.loading-info').hide();
                    $('#mapa').show();
                    initMapDefault();
                    document.getElementById("clickInfo").innerHTML = "No hay lugares que mostrar";
                    $('#clickInfo').show();
                }
                for (var i = 0; i < data.length; i++) {
                    $("#list").append(data[i].print);
                    flagMap = true;
                }

                if (flagMap == true) {
                    $('#mapa').show();
                    initMap();
                    $('.loading-info').hide();
                    $('#clickInfo').show();
                }
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
                    $("#imageProfile").append(data);
                }
                if (data == null) {
                    $("#defaultUser").show;
                }

            }
        });


    });
    $("#clickInfo").click(function () {
        $('.loading-info').show();
        $('#clickInfo').hide();

        $.ajax({
            method: "POST",
            cache: false,
            url: "${moreData}",
            data: {page: track_page},
            complete: function () {
            },
            success: function (data) {
                var activo = 0;
                for (var i = 0; i < data.length; i++) {
                    $("#list").append(data[i].print);
                    activo = 1
                }
                if (activo == 1) {
                    initMap();
                    $('.loading-info').hide();
                    document.getElementById("clickInfo").innerHTML = "Cargar más lugares";
                    $('#clickInfo').show();
                }

                if (data.length == 0) {
                    $('.loading-info').hide();
                    document.getElementById("clickInfo").innerHTML = "No hay mas lugares que mostrar";
                    $('#clickInfo').show();
                } else {
                    track_page = track_page + 6;
                }
            }
        });




    });


    function clickResult(id) {

        var mydiv = document.getElementById(id)

        //alert(mydiv.getAttribute("data-nombre"));

        $.ajax({
            method: "POST",
            cache: false,
            url: "${dataModal}",
            data: {latitud: mydiv.getAttribute("data-latitud"),
                longitud: mydiv.getAttribute("data-longitud"),
                nombre: mydiv.getAttribute("data-nombre"),
                clase: mydiv.getAttribute("data-clase")},
            complete: function () {
            },
            success: function (data) {
                document.getElementById("myModal").innerHTML = data;
                //MODAL

// Get the modal
                var modal = document.getElementById('myModal');

// Get the button that opens the modal
                var btn = document.getElementById(id);
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

//MODAL
            }
        });

        $('.btn-floating').hide();
        $.ajax({
            method: "POST",
            cache: false,
            url: "${imagesData}",
            data: {idSitio: mydiv.getAttribute("data-idSitio"),
                clase: mydiv.getAttribute("data-clase")},
            complete: function () {
            },
            success: function (data) {
                var activoBotones = 0;

                for (var i = 0; i < data.length; i++) {
                    $("#gallImages").append(data[i].printImage);
                    activoBotones = 1;
                }
                if (activoBotones = 1)
                {
                    $('.btn-floating').show();

                }
                if (activoBotones = 0) {
                    $('.btn-floating').hide();
                }


                console.log(data)

//MODAL
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