<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 2.02.22
  Time: 11:24 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
    <title>Update Track form</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<div class="center">
    <%@include file="/jsp/locale.jsp" %>
    <h1>
        <fmt:message key="new.credentials"/>
    </h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="edit_track">
        <div class="txt_field">
            <input name="trackId" type="text" required pattern="^\d{1,}$">
            <span></span>
            <label>
                <fmt:message key="tracks.track.id"/>
            </label>
        </div>
        <div class="txt_field">
            <input name="trackTitle" type="text" required pattern="^\w.{1,512}.$">
            <span></span>
            <label>
                <fmt:message key="tracks.track.title"/>
            </label>
        </div>
        <div class="txt_field">
            <input name="trackUrl" type="text" required pattern="^https://\w.+$">
            <span></span>
            <label>
                <fmt:message key="tracks.track.url"/>
            </label>
        </div>
        <div class="txt_field">
            <input name="albumId" type="text" required pattern="^\d{1,}$">
            <span></span>
            <label>
                <fmt:message key="albums.album.id"/>
            </label>
        </div>
        <div class="txt_field">
            <input name="albumTitle" type="text" required pattern="^\w.{1,512}.$">
            <span></span>
            <label>
                <fmt:message key="albums.album.title"/>
            </label>
        </div>
        <input type="submit" value="<fmt:message key="tracks.edit.button"/>">
    </form>
</div>
</body>
</html>