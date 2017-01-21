<%-- 
    Document   : pie
    Created on : Jun 13, 2016, 7:15:16 PM
    Author     : Edgar-Mac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="/resources/js/dinor.js" var="js" />
<footer>
    
    <script src="${js}"
        type="text/javascript" charset="utf-8"></script>
<script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyD7bKJe8yRiS4O5d08ZmWvSSdzK3aRM-L0&callback=initMap">
    </script>
</footer>
</body>
</html>

