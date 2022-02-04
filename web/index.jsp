<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 22.01.22
  Time: 6:05 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Deployment Info</title>
    <link rel="stylesheet" href="resources/static/css/bootstrap.css">
</head>
<body>
<%@include file="jsp/locale.jsp" %>
<div class="text-md-center">
    <fmt:message key="index.deployment.info"/>
    <h2>
        <a href="https://github.com/h1alexbel/OrdJoy/blob/main/README.md">
            <fmt:message key="index.deployment.message"/>
        </a>
    </h2>
</div>
</body>
</html>