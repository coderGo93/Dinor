<%-- 
    Document   : listadoConMapa
    Created on : Jun 13, 2016, 7:15:58 PM
    Author     : Edgar-Mac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<spring:url value="/upload_picture_profile" var="upload" />
<spring:url value="/request_modify_profile" var="modify" />
<spring:url value="/listRequest" var="listRequest" />
<!DOCTYPE html>

<jsp:include page="cabecera_perfil.jsp"/>
<section>
    <div class="notification2"><span class="infoNotification">No se encontraron lugares con el filtrado</span> <span class="bRequestNotification">Ver solicitudes</span></div>
    <div class="edit-page">
        <div class="form_create">
            <!-- The Modal -->
            <div id="myModal" class="modal">

                <!-- Modal content -->
                <div class="modal-content">
                    <div class="modal-header">
                        <span class="close">×</span>
                        <h2>Cambiar foto de perfil</h2>
                    </div>
                    <div class="modal-body">
                        <form:form action="${upload}" method="post" enctype="multipart/form-data">
                            <center>Imagen <input type="file" name="file"><br></center>
                            <button>Cambiar foto perfil</button>
                        </form:form>
                    </div>

                </div>

            </div>
            <c:choose>
                <c:when test="${empty imagenPerfil}">
                    <div id="fotoPerfil"><span class="glyphicon glyphicon-user " id="photoProfile"></span></div>
                    </c:when>
                    <c:otherwise>
                    <div id="fotoPerfil"><img src="https://s3.amazonaws.com/storagedinor/images/user/+${imagenPerfil}" height="100" width="100" id="photoProfile"></div>

                </c:otherwise>
            </c:choose>

            <input type="text" id="name" placeholder="Nombre" name="name" required/>

            <select class="cs-select cs-skin-border" id="sGender" name="gender" required>
                    <option selected disabled>Sexo</option>
                    <option value="Masculino">Masculino</option>
                    <option value="Femenino">Femenino</option>
                </select>
            <input type="text" id="city" placeholder="Ciudad" name="city" required/>
            <input type="text" id="state" placeholder="Estado" name="state" required/>
            <input type="text" id="country" placeholder="País" name="country" required/>
            <input type="text" id="phone" placeholder="Teléfono" name="phone" required/>
            <input type="text" id="address" placeholder="Dirección" name="address" required/>
            <input type="text" id="email" placeholder="Correo electrónico" name="email" required/>
            <input type="password"  placeholder="Contraseña" name="password" id="password"  autocomplete="off"/>
            <input type="password"  placeholder="Confirmar contraseña" id="password2" name="password2"  autocomplete="off" onkeyup="comprobarContraseña()"/>
            <span id="ifNotMatch">No coinciden las contraseñas</span>
            <span id="ifMatch">Coinciden las contraseñas</span>
            <span id="ifNotFilled2">Faltan datos que llenar</span>
            <button id="rModify">Modificar datos</button>
            
        </div>
    </div>


</section>
<script>
    $("#photoProfile").click(function () {
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
    $("#rModify").click(function () {
        var name = $("#name").val();
        var phone = $("#phone").val();
        var address = $("#address").val();
        var city = $("#city").val();
        var state = $("#state").val();
        var country = $("#country").val();
        var gender = $("#gender").val();
        if (name != "" && phone != "" && address != "" && city != "" && state != "" && country != "" && gender != "") {
            $('#ifNotFilled2').hide();
            $.ajax({
                method: "POST",
                url: "${modify}",
                data: {name: $("#name").val(),
                    email: $("#email").val(),
                    password: $("#password").val(),
                    phone: $("#phone").val(),
                    address: $("#address").val(),
                    city: $("#city").val(),
                    state: $("#state").val(),
                    country: $("#country").val(),
                    gender: $("#sGender :selected").text()},
                complete: function () {
                },
                success: function (data) {

                    if (data == 1) {
                        document.getElementsByClassName('notification2')[0].innerHTML = "Se actualizaron los datos";
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
            $('#ifNotFilled2').show();
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