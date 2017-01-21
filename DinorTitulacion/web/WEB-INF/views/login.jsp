<%-- 
    Document   : login
    Created on : Jun 13, 2016, 12:20:54 AM
    Author     : Edgar-Mac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<spring:url value="/resources/css/style.css" var="style" />
<spring:url value="/resources/js/dinor.js" var="js" />
<spring:url value="/listado_mapa" var="listado" />
<spring:url value="/addAccount" var="addAccount" />
<spring:url value="/resources/images/incorrect.png" var="incorrect" />
<spring:url value="/resources/images/correct.png" var="correct" />
<spring:url value="/existeUsuario" var="existeUsuario" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${style}" media="screen" type="text/css" />
        <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

        <title>Inicio sesión - Dinor</title>
    </head>
    <body>
    <center>
        <div class="login-page">
            <div class="form">
                <form:form action="addAccount" method="POST" class="register-form">
                    <input type="text" placeholder="Nombre Completo" name="name" autocomplete="off" required/>
                    <input type="text" id="usuario" placeholder="Usuario" name="user" autocomplete="off" required/><img src="${correct}" id="correct"><img src="${incorrect}" id="incorrect">
                    <input type="text" placeholder="Correo electrónico" name="email" autocomplete="off" required/>
                    <input type="password" id="password" placeholder="Contraseña" name="password" autocomplete="off" required>
                    <input type="password" id="password2" placeholder="Vuelve a escribir contraseña" onkeyup="ifMatchPassword()" name="password2" autocomplete="off" required/>
                    <span id="ifNotMatch2">No coinciden las contraseñas</span>
                    <span id="ifMatch2">Coinciden las contraseñas</span>
                    <input type="password" placeholder="Palabra clave, para la recuperación." name="keyWord" style="margin-top: 10px;" autocomplete="off" required/>
                    <button id="bAddAccount">Crear Cuenta</button>
                    <p class="message">¿Ya tienes cuenta? <a href="#">Iniciar sesión</a></p>
                </form:form>
                <form:form action="checkLogin" method="POST" class="login-form">
                    <input type="text" placeholder="Usuario" name="user" required/>
                    <input type="password" placeholder="Contraseña" name="password" required/>
                    <button>Iniciar Sesión</button>
                    <p class="message">¿Sin cuenta? <a href="#" >Crear cuenta</a></p>

                </form:form>
            </div>
        </div>

    </center>

    <script src='${js}'>
    </script>
    <script type="text/javascript" >
        function ifMatchPassword() {
            var texto = document.getElementById('password').value;
            var comprueba = document.getElementById('password2').value;

            var bAddAccount = document.getElementById('bAddAccount');
            if (texto == comprueba) {
                $('#ifMatch2').show();
                $('#ifNotMatch2').hide();
                bAddAccount.disabled = false;
            } else {
                $('#ifNotMatch2').show();
                $('#ifMatch2').hide();
                bAddAccount.disabled = true;
            }

        }

        $('#usuario').keyup(function () {
            if ($('#usuario').val() == "") {
                $('#usuario').css('background', '#f2f2f2');
                $('#incorrect').css('display', 'none');
                $('#correct').css('display', 'none');
            } else {
                delay(function () {
                    $.ajax({
                        method: "POST",
                        url: "${existeUsuario}",
                        data: {usuario: $('#usuario').val()},
                        complete: function () {
                        },
                        success: function (data) {

                            if (data == 0) {
                                $('#usuario').css('background', 'rgba(106, 230, 103, 0.82)');
                                $('#correct').css('display', 'inline');
                                $('#incorrect').css('display', 'none');
                                $("#bAddAccount").prop("disabled",false);
                            }
                            if (data == 1) {
                                $('#usuario').css('background', 'rgba(220, 131, 131, 0.82)');
                                $('#incorrect').css('display', 'inline');
                                $('#correct').css('display', 'none');
                                $("#bAddAccount").prop("disabled",true);
                            }
                        }
                    });
                }, 500);
            }

        });



        var delay = (function () {
            var timer = 0;
            return function (callback, ms) {
                clearTimeout(timer);
                timer = setTimeout(callback, ms);
            };
        })();


    </script>

</body>
</html>
