<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 28.01.22
  Time: 2:11 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new Track</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<div class="center">
    <h1>Add new Track</h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="add_track">
        <div class="txt_field">
            <input name="trackTitle" type="text" required pattern="^\w.{1,512}.$">
            <span></span>
            <label>Track title</label>
        </div>
        <div class="txt_field">
            <input name="trackUrl" type="text" required pattern="^https://\w.+$">
            <span></span>
            <label>Track URL</label>
        </div>
        <div class="txt_field">
            <input name="albumTitle" type="text" required pattern="^\w.{1,512}.$">
            <span></span>
            <label>Album title</label>
        </div>
        <input type="submit" value="Create Track by this credentials">
    </form>
</div>
</body>
</html>