<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 30.01.22
  Time: 2:14 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Track to Mix</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<div class="center">
    <h1>Add Track to Mix</h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="add_track_to_mix">
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
            <input name="mixName" type="text" required pattern="^\w.{1,512}.$">
            <span></span>
            <label>Mix name</label>
        </div>
        <input type="submit" value="Add Track by this credentials to Mix">
    </form>
</div>
</body>
</html>