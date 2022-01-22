<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 20.01.22
  Time: 8:49 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Join us today</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<div class="center">
    <h1>Join us today</h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="registerUser">
        <div class="txt_field">
            <input name="email" type="email" required pattern="^[A-Za-z.]+\w+@[A-Za-z]{2,}\.(com|org)$">
            <span></span>
            <label>Email</label>
        </div>
        <div class="txt_field">
            <input name="userName" type="text" required pattern="^\w{3,20}$">
            <span></span>
            <label>Username</label>
        </div>
        <div class="txt_field">
            <input name="password" type="password" required pattern="^\w{2,64}$">
            <span></span>
            <label>Password</label>
        </div>
        <input type="submit" value="Signup">
    </form>
</div>
</body>
</html>