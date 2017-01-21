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
<spring:url value="/moreDataPlaceAdminister" var="moreData" />
<spring:url value="/dataModalPlaceAdminister" var="dataModal" />
<spring:url value="/modificar_sitio" var="modificar_sitio" />
<spring:url value="/dataPlaceAdminister" var="dataPlace" />
<spring:url value="/addPlace" var="addPlace" />
<spring:url value="/listRequest" var="listRequest" />
<!DOCTYPE html>

<jsp:include page="cabecera_perfil.jsp"/>
<section>
    <div class="notification2"><span class="infoNotification">No se encontraron lugares con el filtrado</span> <span class="bRequestNotification">Ver solicitudes</span></div>
    <div id="containerListado">
        <div id="listadoIzquierda">

            <div id="listadoInformacion">     
                <div class="createContainer">
                    <div class="bCreate">
                        <span class="lCreate"> Agregar sitio</span>
                    </div>
                    <div class="bCreate_cancel">
                        <span class="lCreate_cancel"> Cancelar</span>
                    </div>
                </div>
                <div class="create-page">
                    <div class="form_create">

                        <input type="text" id="name" placeholder="Nombre" name="name" required/>
                        <input type="text" id="city" placeholder="Ciudad" name="city" required/>
                        <input type="text" id="state" placeholder="Estado" name="state" required/>
                        <!--<input type="text" id="type" placeholder="Categoria" name="tipo" required/>-->
                        <select class="cs-select cs-skin-border" id="filtroCategoria2" required>
                            <option value="" selected disabled>Categoría</option>
                            <option value="Diversión" >Diversión</option>
                            <option value="Restaurante" >Restaurante</option>
                            <option  value="Club nocturno">Club nocturno</option>
                        </select>
                        <!--<input type="text" id="zone" placeholder="Zona" name="zone" required/>-->
                        <select class="cs-select cs-skin-border" id="filtroZona2" required>
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
                        <button id="addPlace" style="margin-top: 20%;"> Agregar sitio</button>

                    </div>
                </div>
                <ul class="container_results" id="list">
                    <c:forEach var="sitiosLista" items="${sitiosLista}" varStatus="status">
                        <li id="${status.count}" class="result" onclick="clickResult(this.id)" data-latitud="${sitiosLista.latitud}" data-longitud="${sitiosLista.longitud}" data-nombre="${sitiosLista.nombre}">
                            <div class="indexResult">
                                <span>${status.count}</span>
                            </div>
                            <div class="containerInfo"> 
                                <div class="blockTitle">
                                    <div class="title">
                                        <h2>${sitiosLista.nombre}</h2>
                                    </div>
                                </div>
                                <div class="infoBlock">
                                    <div class="infoResult">
                                        <span>${sitiosLista.lugar}</span>
                                        <span class="latitud">${sitiosLista.latitud}</span>
                                        <span class="longitud">${sitiosLista.longitud}</span>
                                    </div>
                                    <div class="buttonContainer">
                                        <div class="bModify">
                                            <a href="${modificar_sitio}?idSitio=${sitiosLista.id}"<span class=""lModify>Modificar</span></a>
                                        </div>
                                        <div class="bDelete">
                                            <span class="lDelete">Eliminar</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <c:set var="actual" scope="page" value="${status.count}"/>
                    </c:forEach>

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
                <div id="mapa" style="display:none">

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





    $("#addPlace").click(function () {
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
        var suburb = $("#suburb").val();

        if (name != "" && type != null && address != "" && city != "" && state != "" && zone != null && zip != "" || website != "" && latitude != "" && longitude != "" && phone != "") {
            $('#ifNotFilled').hide();
            $.ajax({
                method: "POST",
                url: "${addPlace}",
                data: {name: name,
                    type: type,
                    address: address,
                    city: city,
                    state: state,
                    zone: zone,
                    zip: zip,
                    website: website,
                    longitude: longitude,
                    latitude: latitude,
                    phone: phone,
                    suburb: suburb},
                complete: function () {
                },
                success: function (data) {

                    if (data == 1) {

                        document.getElementsByClassName('notification2')[0].innerHTML = "Se agregó un sitio";
                        $(".notification2").css("background", "#56d85b");
                        $('.notification2').slideDown('fast');
                        window.setTimeout(close1, 2000);

                        $.ajax({
                            method: "POST",
                            cache: false,
                            url: "${dataPlace}",
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
    function close1() {
        $('.notification2').slideUp('fast');
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
</script>

<jsp:include page="pie.jsp"/>