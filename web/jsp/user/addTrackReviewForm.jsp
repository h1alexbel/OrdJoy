<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 30.01.22
  Time: 4:21 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <title>Track Review Form</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<div class="center">
    <h1>Add Track review</h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="add_track_review">
        <div class="txt_field">
            <input name="mixName" type="text" required pattern="^\w.{1,512}.$">
            <span></span>
            <label>Track title</label>
        </div>
        <div class="txt_field">
            <input name="trackReviewText" type="text" required pattern="^\w.{2,512}">
            <span></span>
            <label>Review text</label>
        </div>
        <input type="submit" value="Add review">
    </form>
</div>
</body>
</html>