<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 28.01.22
  Time: 2:11 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <title>Add new Album</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<div class="center">
    <h1>Add new Album</h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="add_album">
        <div class="txt_field">
            <input name="albumTitle" type="text" required pattern="^\w.{1,512}.$">
            <span></span>
            <label>Title</label>
        </div>
        <input type="submit" value="Create Album by this credentials">
    </form>
</div>
</body>
</html>
