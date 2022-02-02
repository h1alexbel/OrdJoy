<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 2.02.22
  Time: 11:24 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <title>Update Album form</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<div class="center">
    <h1>Enter new credentials</h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="edit_album">
        <div class="txt_field">
            <input name="albumId" type="text" required pattern="^\d{1,}$">
            <span></span>
            <label>Album Id</label>
        </div>
        <div class="txt_field">
            <input name="albumTitle" type="text" required pattern="\w.{1,512}.$">
            <span></span>
            <label>Album name</label>
        </div>
        <input type="submit" value="Update Album">
    </form>
</div>
</body>
</html>