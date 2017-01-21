<%-- 
    Document   : listadoConMapa
    Created on : Jun 13, 2016, 7:15:58 PM
    Author     : Edgar-Mac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<spring:url value="/placeInformation" var="placeInformation" />
<spring:url value="/resources/images/ajax-loader.gif" var="loader" />
<spring:url value="/imagesPlace" var="imagesPlace" />
<spring:url value="/listRequest" var="listRequest" />
<spring:url value="/existeSitioFavorito" var="existeSitioFavorito" />
<spring:url value="/addFavoritePlace" var="addFavoritePlace" />
<spring:url value="/eliminarSitioFavorito" var="eliminarSitioFavorito" />
<!DOCTYPE html>

<jsp:include page="cabecera_perfil.jsp"/>
<section >
    <div class="notification2"><span class="infoNotification">No se encontraron lugares con el filtrado</span> <span class="bRequestNotification">Ver solicitudes</span></div>
    <div id="containerListado">
        <div id="listadoIzquierda2">
            <a href="" class="bBack" onclick="goBack()">Regresar</a>
            <div id="information">
                <!-- The Modal -->
                <div class="loading-info"><img src="${loader}" /></div>


            </div>

        </div>

        <div id="mapa_container_update" >
            <span id="bFavorite">★ </span><span id="tFavorite">Guardar como favorito</span>
            <div id="containerButtons">
                <span id="bFotosInformation">Imagenes</span>
                <span id="bMapaInformation">Mapa</span>


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
    var activoFavorito = 0;
    var idSitioFavorito = 0;
    var maxId = 0;
    var latitud, longitud, nombre, lugar, ciudad, codigoPostal, direccion, paginaWeb, telefono, estado, zona, tipo;
    $(document).ready(function () {
        $(".loading-info").show();
        $("#noPictures").hide();
        $.ajax({
            method: "POST",
            cache: false,
            url: "${placeInformation}",
            data: {idSitio: ${param.idSitio}},
            complete: function () {
            },
            success: function (data) {

                if (data != null) {
                    document.getElementById("information").innerHTML = data.print;
                    initMapInformation(data.latitud, data.longitud);
                    $(".loading-info").hide();
                    latitud = data.latitud;
                    longitud = data.longitud;
                    nombre = data.nombre;
                    tipo = data.tipo;
                    lugar = data.lugar;
                    ciudad = data.ciudad;
                    codigoPostal = data.codigoPostal;
                    direccion = data.direccion;
                    paginaWeb = data.paginaWeb;
                    telefono = data.telefono;
                    estado = data.estado;
                    zona = data.zona;

                    $.ajax({
                        method: "POST",
                        cache: false,
                        url: "${existeSitioFavorito}",
                        data: {latitude: latitud,
                            longitude: longitud,
                            name: nombre},
                        complete: function () {
                        },
                        success: function (data) {
                            if (data != 0) {
                                activoFavorito = 1;
                                idSitioFavorito = data;
                                $("#bFavorite").css("color", "#80d67c");
                                $("#bFavorite").css("display", "block");
                                document.getElementById('tFavorite').textContent = "Eliminar de favoritos";
                                $("#bFavorite").hover(function () {
                                    $("#bFavorite").css("color", "black");
                                }, function () {
                                    $("#bFavorite").css("color", "#80d67c");
                                });
                            } else {
                                activoFavorito = 0;
                                $("#bFavorite").css("color", "black");
                                $("#bFavorite").css("display", "block");
                                document.getElementById('tFavorite').textContent = "Guardar como favorito";
                                $("#bFavorite").hover(function () {
                                    $("#bFavorite").css("color", "#80d67c");
                                }, function () {
                                    $("#bFavorite").css("color", "black");
                                });
                            }
                        }
                    });

                }



                console.log(data)

//MODAL
            }
        });

        $.ajax({
            method: "POST",
            cache: false,
            url: "${imagesPlace}",
            data: {idSitio: ${param.idSitio}},
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

    $("#bFavorite").hover(function () {
        $("#tFavorite").css("display", "block");
    }, function () {
        $("#tFavorite").css("display", "none");
    });


    $("#bFavorite").click(function () {
        if (activoFavorito == 0) {
            $.ajax({
                method: "POST",
                cache: false,
                url: "${addFavoritePlace}",
                data: {latitude: latitud,
                    longitude: longitud,
                    name: nombre,
                    type: tipo,
                    suburb: lugar,
                    city: ciudad,
                    zip: codigoPostal,
                    address: direccion,
                    website: paginaWeb,
                    phone: telefono,
                    state: estado,
                    zone: zona,
                    idSitio: ${param.idSitio}},
                complete: function () {
                },
                success: function (data) {
                    if (data != 0) {
                        $("#bFavorite").css("color", "#80d67c");
                        $("#bFavorite").css("display", "block");
                        document.getElementById('tFavorite').textContent = "Eliminar de favoritos";
                        $("#bFavorite").hover(function () {
                            $("#bFavorite").css("color", "black");
                        }, function () {
                            $("#bFavorite").css("color", "#80d67c");
                        });
                        activoFavorito = 1;
                        maxId = data;
                    } else {
                        $("#bFavorite").css("color", "black");
                        $("#bFavorite").css("display", "block");
                        document.getElementById('tFavorite').textContent = "Guardar como favorito";
                        $("#bFavorite").hover(function () {
                            $("#bFavorite").css("color", "#80d67c");
                        }, function () {
                            $("#bFavorite").css("color", "black");
                        });
                    }

//MODAL
                }
            });
        }
        if (activoFavorito == 1) {
            if (maxId != 0) {
                $.ajax({
                    method: "POST",
                    cache: false,
                    url: "${eliminarSitioFavorito}",
                    data: {idSitio: maxId},
                    complete: function () {
                    },
                    success: function (data) {
                        if (data == 1) {

                            $("#bFavorite").css("color", "black");
                            $("#bFavorite").css("display", "block");
                            document.getElementById('tFavorite').textContent = "Guardar como favorito";
                            $("#bFavorite").hover(function () {
                                $("#bFavorite").css("color", "#80d67c");
                            }, function () {
                                $("#bFavorite").css("color", "black");
                            });
                            activoFavorito = 0;
                        } else {
                            $("#bFavorite").css("color", "#80d67c");
                            $("#bFavorite").css("display", "block");
                            document.getElementById('tFavorite').textContent = "Eliminar de favoritos";
                            $("#bFavorite").hover(function () {
                                $("#bFavorite").css("color", "black");
                            }, function () {
                                $("#bFavorite").css("color", "#80d67c");
                            });
                        }

//MODAL
                    }
                });
            } else {
                $.ajax({
                    method: "POST",
                    cache: false,
                    url: "${eliminarSitioFavorito}",
                    data: {idSitio: idSitioFavorito},
                    complete: function () {
                    },
                    success: function (data) {
                        if (data == 1) {

                            $("#bFavorite").css("color", "black");
                            $("#bFavorite").css("display", "block");
                            document.getElementById('tFavorite').textContent = "Guardar como favorito";
                            $("#bFavorite").hover(function () {
                                $("#bFavorite").css("color", "#80d67c");
                            }, function () {
                                $("#bFavorite").css("color", "black");
                            });
                            activoFavorito = 0;
                        } else {
                            $("#bFavorite").css("color", "#80d67c");
                            $("#bFavorite").css("display", "block");
                            document.getElementById('tFavorite').textContent = "Eliminar de favoritos";
                            $("#bFavorite").hover(function () {
                                $("#bFavorite").css("color", "black");
                            }, function () {
                                $("#bFavorite").css("color", "#80d67c");
                            });
                        }

//MODAL
                    }
                });
            }

        }

    });
</script>
<jsp:include page="pie.jsp"/>