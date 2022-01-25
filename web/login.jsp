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
    <title>Log in to OrdJoy</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<div class="center">
    <h1>Login in to OrdJoy</h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="login">
        <div class="txt_field">
            <input name="login" type="text" required pattern="^\w{3,20}$">
            <span></span>
            <label>Username</label>
        </div>
        <div class="txt_field">
            <input name="password" type="password" required pattern="^\w{2,64}$">
            <span></span>
            <label>Password</label>
        </div>
        <input type="submit" value="Login">
        <div class="signup_link">
            Not a member? <a href="${pageContext.request.contextPath}register.jsp">Signup</a>
        </div>
    </form>
</div>
</body>
</html>