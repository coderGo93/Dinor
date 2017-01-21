<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/resources/css/style.css" var="style" />
<spring:url value="/resources/images/background_map.png" var="image_background" />
<spring:url value="/resources/images/dinor_solo_img.png" var="logo" />
<spring:url value="/login" var="login" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="${style}" media="screen" type="text/css" />
        <title>Dinor</title>
    </head>

    <body id="index " style="background:linear-gradient(
          rgba(33, 178, 37, 0.45), 
          rgba(33, 178, 37, 0.45)
          ),url(${image_background});background-size:110%;
          width:100%; height:100%;">
        <center>
            <div>
                <img id="logoInicio" src="${logo}">
                <h1 id="dinor">Dinor</h1>
                <br><span id="slogan">Busca los sitios turísticos del lugares que quieres visitar</span>
                <br><br><br><br>
                <a href="${login}"> <span id="textoLogin">Iniciar sesión</span></a>
            </div>
        </center>
    </body>
</html>
