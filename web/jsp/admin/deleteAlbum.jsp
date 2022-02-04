<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 2.02.22
  Time: 10:27 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Delete album form</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<div class="center">
    <%@include file="/jsp/locale.jsp" %>
    <h1>
        <fmt:message key="delete.album.text"/>
    </h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="delete_album">
        <div class="txt_field">
            <input name="albumId" type="text" required pattern="^\d{1,}$">
            <span></span>
            <label> <fmt:message key="albums.album.id"/></label>
        </div>
        <input type="submit" value="<fmt:message key="delete.album.button"/>">
    </form>
</div>
</body>
</html>