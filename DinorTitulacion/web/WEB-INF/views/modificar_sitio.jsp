<%-- 
    Document   : listadoConMapa
    Created on : Jun 13, 2016, 7:15:58 PM
    Author     : Edgar-Mac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<spring:url value="/upload_picture_place" var="upload" />
<spring:url value="/modifyPlace" var="modifyPlace" />
<spring:url value="/delete_picture_place" var="deletePicture" />
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
                                    <input type="hidden" name="idSitio" value="${param.idSitio}">
                                    <input type="hidden" name="latitud" value="${param.latitud}">
                                    <input type="hidden" name="longitud" value="${param.longitud}">
                                    <button>Agregar</button>
                                </form:form>
                            </div>

                        </div>

                    </div>



                    <input type="text" id="name" placeholder="Nombre" name="name" required/>
                    <input type="text" id="city" placeholder="Ciudad" name="city" required/>
                    <input type="text" id="state" placeholder="Estado" name="state" required/>
                    <!--<input type="text" id="type" placeholder="Categoria" name="tipo" required/>-->
                    <select class="cs-select cs-skin-border" id="filtroCategoria2">
                        <option value="" selected disabled>Categoría</option>
                        <option value="Diversión" >Diversión</option>
                        <option value="Restaurante" >Restaurante</option>
                        <option  value="Club nocturno">Club nocturno</option>
                    </select>
                    <!--<input type="text" id="zone" placeholder="Zona" name="zone" required/>-->
                    <select class="cs-select cs-skin-border" id="filtroZona2">
                        <option value="" selected disabled>Zona</option>
                        <option value="Histórica" >Histórica</option>
                        <option  value="Fiesta">Fiesta</option>
                        <option  value="Museo">Museo</option>
                        <option  value="Parque">Parque</option>
                        <option  value="Otra">Otra</option>
                    </select>
                    <input type="text" id="address" placeholder="Dirección" name="address" required/>
                    <input type="hidden" id="suburb" placeholder="Colonia" name="suburb" required/>
                    <input type="text" id="zip" placeholder="Código postal" name="cp" required/>
                    <input type="text" id="website" placeholder="Página web" name="web" required/>
                    <input type="text" id="phone" placeholder="Teléfono" name="phone" required/>
                    <input type="hidden" id="latitude" placeholder="Latitud" name="latitude" disabled />
                    <input type="hidden" id="longitude" placeholder="Longitud" name="longitude" disabled />

                    <span id="ifNotFilled">Faltan datos que llenar. Asegúrate de seleccionar un lugar en mapa.</span>
                    <button id="modifyPlace" style="margin-top: 20%;">Modificar datos</button>

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
                    <c:when test="${empty imagenesSitio}">
                        No hay imagenes
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="imagenesSitio" items="${imagenesSitio}" varStatus="status">
                            <div class="img">
                                <img src="https://s3.amazonaws.com/storagedinor/images/places/+${imagenesSitio.filename}" width="600" height="400">
                                <span id="${status.index}" onclick="pictureId(this.id)" data-image="${imagenesSitio.filename}" class="deletePicture">Eliminar imagen</span>
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
                idSitio: '${param.idSitio}'},
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

    $("#modifyPlace").click(function () {
        var name = $("#name").val();
        var type = $("#filtroCategoria2").val()
        var address = $("#address").val();
        var city = $("#city").val();
        var state = $("#state").val();
        var zone = $("#filtroZona2").val()
        var zip = $("#zip").val();
        var website = $("#website").val();
        var latitude = $("#latitude").val();
        var longitude = $("#longitude").val();
        var phone = $("#phone").val();
        var phone = $("#suburb").val();

        if (name != "" && type != "" && address != "" && city != "" && state != "" && zone != "" && zip != "" || website != "" && latitude != "" && longitude != "" && phone != "") {
            $('#ifNotFilled').hide();
            $.ajax({
                method: "POST",
                url: "${modifyPlace}",
                data: {name: $("#name").val(),
                    type: $("#filtroCategoria").val(),
                    address: $("#address").val(),
                    city: $("#city").val(),
                    state: $("#state").val(),
                    zone: $("#filtroZona").val(),
                    zip: $("#zip").val(),
                    website: $("#website").val(),
                    longitude: $("#longitude").val(),
                    latitude: $("#latitude").val(),
                    phone: $("#phone").val(),
                    suburb: $("#suburb").val(),
                    idSitio: '${param.idSitio}'},
                complete: function () {
                },
                success: function (data) {

                    if (data == 1) {

                        document.getElementsByClassName('notification2')[0].innerHTML = "Se ha modificado un sitio";
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