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
<spring:url value="/moreDataEventAdminister" var="moreData" />
<spring:url value="/dataModalEventAdminister" var="dataModal" />
<spring:url value="/dataEventAdminister" var="dataEvent" />
<spring:url value="/addEvent" var="addEvent" />
<spring:url value="/listRequest" var="listRequest" />
<!DOCTYPE html>

<jsp:include page="cabecera_perfil.jsp"/>
<section>
    <div class="notification2"><span class="infoNotification">No se encontraron lugares con el filtrado</span> <span class="bRequestNotification">Ver solicitudes</span></div>
    <div id="containerListado">
        <div id="listadoIzquierda">

            <div id="listadoInformacion">     
                <div class="createContainer">
                    <div class="bCreateEvent">
                        <span class="lCreate"> Agregar evento</span>
                    </div>
                    <div class="bCreate_cancelEvent">
                        <span class="lCreate_cancel"> Cancelar</span>
                    </div>
                </div>
                <div class="create-page">
                    <div class="form_create">

                            <input type="text" id="name"  placeholder="Nombre" name="name" required/>
                            <input type="text" id="description"  placeholder="Descripcion" name="descripcion" required/>
                            <input type="text" id="place"  placeholder="Lugar" name="place" required/>
                            <input type="date" id="fechaInicio" placeholder="Fecha de inicio" name="fechaInicio" required/>
                            <input type="date" id="fechaTermino" placeholder="Fecha de término" name="fechaTermino" required/>
                            <span >Hora de inicio</span>
                            <input type="time" id="horaInicio" placeholder="Hora de inicio" name="horaInicio" required/>
                            <span>Hora de término</span>
                            <input type="time" id="horaTermino"  placeholder="Hora de término" name="horaTermino" required/>
                            <input type="hidden" id="latitude" placeholder="Latitud" name="latitude" disabled />
                            <input type="hidden" id="longitude" placeholder="Longitud" name="longitude" disabled />
                            <span id="ifNotFilled">Faltan datos que llenar. Asegúrate de seleccionar un lugar en mapa.</span>
                            <button id="addEvent">Agregar evento</button>

                    </div>
                </div>
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
                            <h3>Sitio/Evento</h3>
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
            url: "${dataEvent}",
            complete: function () {
            },
            success: function (data) {
                $("#list").html("");
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

//MODAL
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
                          
$("#addEvent").click(function () {
        var name = $("#name").val();
        var description = $("#description").val()
        var place = $("#place").val();
        var fechaInicio = $("#fechaInicio").val();
        var fechaTermino = $("#fechaTermino").val();
        var horaInicio = $("#horaInicio").val()
        var horaTermino = $("#horaTermino").val();
        var latitude = $("#latitude").val();
        var longitude = $("#longitude").val();

        if (name != "" && description != "" && place != "" && fechaInicio != "" && fechaTermino != "" && horaInicio != "" && horaTermino != "" && latitude != "" && longitude != "") {
            $('#ifNotFilled').hide();
            $.ajax({
                method: "POST",
                url: "${addEvent}",
                data: {name: $("#name").val(),
                    description: $("#description").val(),
                    place: $("#place").val(),
                    fechaInicio: $("#fechaInicio").val(),
                    fechaTermino: $("#fechaTermino").val(),
                    horaInicio: $("#horaInicio").val(),
                    horaTermino: $("#horaTermino").val(),
                    longitude: $("#longitude").val(),
                    latitude: $("#latitude").val()},
                complete: function () {
                },
                success: function (data) {

                    if (data == 1) {
                        
                        document.getElementsByClassName('notification2')[0].innerHTML = "Se agregó un evento";
                        $(".notification2").css("background", "#56d85b");
                        $('.notification2').slideDown('fast');
                        window.setTimeout(close1, 2000);
                        
                         $('#clickInfo').hide();
        $('.loading-info').show();
        $.ajax({
            method: "POST",
            cache: false,
            url: "${dataEvent}",
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

//MODAL
            }
        });
                        
                        
                    } else {
                        document.getElementsByClassName('notification2')[0].innerHTML = "No se pudo completar la solicitud, faltan datos o están incorrectos";
                        $(".notification2").css("background", "rgba(199, 40, 32, 0.9)");
                        $('.notification2').slideDown('fast');
                        window.setTimeout(close1, 2000);
                    }

                }
            });


        } else {
            $('#ifNotFilled').show();

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
</script>
<jsp:include page="pie.jsp"/>