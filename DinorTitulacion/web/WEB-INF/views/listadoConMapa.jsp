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
<spring:url value="/moreData" var="moreData" />
<spring:url value="/getData" var="getData" />
<spring:url value="/dataModal" var="dataModal" />
<spring:url value="/imagesMapData" var="imagesData" />
<spring:url value="/dataPlaceEvent" var="dataPlace" />
<spring:url value="/obtener_filtro" var="obtener_filtro" />
<spring:url value="/datosConFiltro" var="datosConFiltro" />
<spring:url value="/moreDataFiltered" var="moreDataFiltered" />
<spring:url value="/listRequest" var="listRequest" />


<jsp:include page="cabecera.jsp"/>
<section>
    <div class="notification"><span class="infoNotification">No se encontraron lugares con el filtrado</span> <span class="bRequestNotification">Ver solicitudes</span></div>
    <div id="containerListado">
        <div id="listadoIzquierda">
            <div class="container_filter">

                <select class="cs-select cs-skin-border" id="filtroCiudad">
                    <option selected disabled>Ciudad</option>
                </select>
                <select class="cs-select cs-skin-border" id="filtroEstado">
                    <option selected disabled>Estado</option>
                </select>
                <select class="cs-select cs-skin-border" id="filtroCategoria">
                    <option selected disabled>Categoria</option>
                </select>
                <select class="cs-select cs-skin-border" id="filtroZona">
                    <option selected disabled>Zona</option>

                </select>
                <a href="#" class="btn green filtrar">Filtrar</a>
                <a href="#" class=" red sinFiltro">Sin filtro</a>
            </div>
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
    var activoSinFiltro = 0;
    var activoDatosNormales = 0;
    var activoCargarLugaresConFiltro = 0;
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
                for (var i = 0; i < data.length; i++) {
                    $("#list").append(data[i].print);
                    flagMap = true;
                }
                console.log(data)
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
            url: "${obtener_filtro}",
            complete: function () {
            },
            success: function (data) {

                for (var i = 0; i < data.length; i++) {
                    if (data[i].ciudad != "") {
                        $("#filtroCiudad").append("<option value=" + data[i].ciudad + ">" + data[i].ciudad + "</option>");
                    }
                    if (data[i].tipo != "") {
                        $("#filtroCategoria").append("<option value=" + data[i].tipo + ">" + data[i].tipo + "</option>");
                    }
                    if (data[i].estado != "") {
                        $("#filtroEstado").append("<option value=" + data[i].estado + ">" + data[i].estado + "</option>");
                    }
                    if (data[i].zona != "") {
                        $("#filtroZona").append("<option value=" + data[i].zona + ">" + data[i].zona + "</option>");
                    }

                }

            }
        });
    });
    $("#clickInfo").click(function () {
        $('.loading-info').show();
        $('#clickInfo').hide();
        if (activoCargarLugaresConFiltro == 0) {

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
        }


        if (activoCargarLugaresConFiltro == 1) {

            $.ajax({
                method: "POST",
                cache: false,
                url: "${moreDataFiltered}",
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
        }
    });



    $(".filtrar").click(function () {

        $('.loading-info').show();
        $('#clickInfo').hide();

        document.getElementById("list").innerHTML = "";
        $.ajax({
            method: "POST",
            cache: false,
            url: "${datosConFiltro}",
            data: {categoria: $("#filtroCategoria option:selected").text(),
                ciudad: $("#filtroCiudad option:selected").text(),
                zona: $("#filtroZona option:selected").text(),
                estado: $("#filtroEstado option:selected").text()},
            complete: function () {
            },
            success: function (data) {

                if (data.length == 0) {
                    
                    document.getElementsByClassName('infoNotification')[0].innerHTML = "No se encontraron lugares con el filtrado";
                    $(".notification").css("background", "rgba(199, 40, 32, 0.9)");
                    $(".bRequestNotification").hide();
                    $('.notification').slideDown('fast');
                    window.setTimeout(close1, 2000);
                    $.ajax({
                        method: "POST",
                        cache: false,
                        url: "${dataPlace}",
                        complete: function () {
                        },
                        success: function (data) {
                            for (var i = 0; i < data.length; i++) {
                                $("#list").append(data[i].print);
                                flagMap = true;
                            }

                            if (flagMap == true) {
                                $('#mapa').show();
                                initMap();
                                $('.loading-info').hide();
                                $('#clickInfo').show();

                                $(".sinFiltro").hide();

                            }
                        }

                    });
                }

                for (var i = 0; i < data.length; i++) {
                    $("#list").append(data[i].print);
                    activoSinFiltro = 1;

                }


                if (activoSinFiltro == 1) {
                    $('#mapa').show();
                    initMap();
                    $(".sinFiltro").show();
                    $('.loading-info').hide();
                    $('#clickInfo').show();
                    activoCargarLugaresConFiltro = 1;
                    track_page = 6;
                }


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
        $('.notification').slideUp('fast');
    }
    
    $(".sinFiltro").click(function () {
        $('.loading-info').show();
        $('#clickInfo').hide();
        document.getElementById("list").innerHTML = "";
        track_page = 6;

        $.ajax({
            method: "POST",
            cache: false,
            url: "${dataPlace}",
            complete: function () {
            },
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
                    $("#list").append(data[i].print);
                    flagMap = true;
                }

                if (flagMap == true) {
                    $('#mapa').show();
                    initMap();
                    $('.loading-info').hide();
                    $('#clickInfo').show();
                    $(".sinFiltro").hide();
                    activoCargarLugaresConFiltro = 0;
                }
            }
        });


    });
</script>

<jsp:include page="pie.jsp"/>