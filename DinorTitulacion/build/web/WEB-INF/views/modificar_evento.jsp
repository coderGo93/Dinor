<%-- 
    Document   : listadoConMapa
    Created on : Jun 13, 2016, 7:15:58 PM
    Author     : Edgar-Mac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<spring:url value="/upload_picture_event" var="upload" />
<spring:url value="/modifyEvent" var="modifyEvent" />
<spring:url value="/delete_picture_event" var="deletePicture" />
<spring:url value="/listRequest" var="listRequest" />
<!DOCTYPE html>

<jsp:include page="cabecera_perfil.jsp"/>
<section >
    <div class="notification2"><span class="infoNotification">No se encontraron lugares con el filtrado</span> <span class="bRequestNotification">Ver solicitudes</span></div>
    <div id="containerListado">
        <div id="listadoIzquierda3">
            <div class="edit-page">
                <div class="form_update">
                    <!-- The Modal -->
                    <div id="myModal" class="modal">

                        <!-- Modal content -->
                        <div class="modal-content">
                            <div class="modal-header">
                                <span class="close">×</span>
                                <h2>Agregar foto del sitio</h2>
                            </div>
                            <div class="modal-body">
                                <form:form action="${upload}" method="post" enctype="multipart/form-data">
                                    <center>Imagen <input type="file" name="file"><br></center>
                                    <input type="hidden" name="idEvento" value="${param.idEvento}">
                                    <input type="hidden" name="latitud" value="${param.latitud}">
                                    <input type="hidden" name="longitud" value="${param.longitud}">
                                    <button>Agregar</button>
                                </form:form>
                            </div>

                        </div>

                    </div>



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
                    <button id="modifyEvent">Modificar datos</button>

                </div>
            </div>

        </div>

        <div id="mapa_container_update" onload="initMapUpdate()">
            <div id="containerButtons">
                <span id="bFotos">Imagenes</span>
                <span id="bMapa">Mapa</span>


                <div id="contAgregar">
                    <span id="bAgregarFoto">Agregar imagen</span>
                </div>

            </div>
            <div id="galleryPlaces">
                <c:choose>
                    <c:when test="${empty imagenesEvento}">
                        No hay imagenes
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="imagenesEvento" items="${imagenesEvento}" varStatus="status">
                            <div class="img">
                                <img src="https://s3.amazonaws.com/storagedinor/images/events/+${imagenesEvento.filename}" width="600" height="400">
                                <span id="${status.index}" onclick="pictureId(this.id)" data-image="${imagenesEvento.filename}" class="deletePicture">Eliminar imagen</span>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>

            </div>
            <div id="mapa" style="display:none">

            </div>
        </div>
    </div>
</div>

</section>
<script>
    function pictureId(id) {
        var nImage = document.getElementById(id).getAttribute('data-image');
        $.ajax({
            method: "POST",
            url: "${deletePicture}",
            data: {image: nImage,
                idEvento: '${param.idEvento}'},
            complete: function () {
            },
            success: function (data) {
                location.reload();


            }
        });
    }
    $("#bAgregarFoto").click(function () {
        var modal = document.getElementById('myModal');

// Get the button that opens the modal
        var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
        var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal
        modal.style.display = "block";


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
    $(document).ready(function () {
        $('#bAgregarFoto').show();
        document.getElementById("latitude").value = ${param.latitud};
        document.getElementById("longitude").value = ${param.longitud};
        initMapUpdate(${param.latitud}, ${param.longitud});
    });
    var activoMapaUpdate = 0;
    $('#bMapa').click(function () {
        $('#mapa').show();
        if (activoMapaUpdate == 0) {
            initMapUpdate(${param.latitud}, ${param.longitud});
            activoMapaUpdate = 1;
        }
        $('#galleryPlaces').hide();
        $('#bAgregarFoto').hide();
    });

    $("#modifyEvent").click(function () {
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
                url: "${modifyEvent}",
                data: {name: $("#name").val(),
                    description: $("#description").val(),
                    place: $("#place").val(),
                    fechaInicio: $("#fechaInicio").val(),
                    fechaTermino: $("#fechaTermino").val(),
                    horaInicio: $("#horaInicio").val(),
                    horaTermino: $("#horaTermino").val(),
                    longitude: $("#longitude").val(),
                    latitude: $("#latitude").val(),
                    idEvento:'${param.idEvento}'},
                complete: function () {
                },
                success: function (data) {

                    if (data == 1) {

                        document.getElementsByClassName('notification2')[0].innerHTML = "Se ha modificado un evento";
                        $(".notification2").css("background", "#56d85b");
                        $('.notification2').slideDown('fast');
                        window.setTimeout(close1, 2000);


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