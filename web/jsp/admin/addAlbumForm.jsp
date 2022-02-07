<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 28.01.22
  Time: 2:11 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
    <title>Add new Album</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<div class="center">
    <%@include file="/jsp/locale.jsp"%>
    <h1>
        <fmt:message key="admin.add.album"/>
    </h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="add_album">
        <div class="txt_field">
            <input name="albumTitle" type="text" required pattern="^[A-Za-zА-Яа-я].{1,512}.$">
            <span></span>
            <label>
                <fmt:message key="albums.album.title"/>
            </label>
        </div>
        <input type="submit" value="<fmt:message key="album.form.create.button"/>">
    </form>
</div>
</body>
</html>