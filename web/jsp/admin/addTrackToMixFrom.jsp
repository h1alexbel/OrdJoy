<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 30.01.22
  Time: 2:14 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Add Track to Mix</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<div class="center">
    <%@include file="/jsp/locale.jsp" %>
    <h1>
        <fmt:message key="admin.add.track.to.mix"/>
    </h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="add_track_to_mix">
        <div class="txt_field">
            <input name="trackTitle" type="text" required pattern="^[A-Za-zА-Яа-я].{1,512}.$">
            <span></span>
            <label>
                <fmt:message key="tracks.track.title"/>
            </label>
        </div>
        <div class="txt_field">
            <input name="mixName" type="text" required pattern="^^[A-Za-zА-Яа-я].{1,512}.$">
            <span></span>
            <label>
                <fmt:message key="mixes.mix.name"/>
            </label>
        </div>
        <input type="submit" value="<fmt:message key="track.to.mix.create.button"/>">
    </form>
</div>
</body>
</html>