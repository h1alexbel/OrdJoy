<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 28.01.22
  Time: 1:44 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add new Admin form</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<div class="center">
    <h1>Add new Admin</h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="add_admin">
        <div class="txt_field">
            <input name="email" type="email" required pattern="^[A-Za-z.]+\w+@[A-Za-z]{2,}\.(com|org)$">
            <span></span>
            <label>Email</label>
        </div>
        <div class="txt_field">
            <input name="firstName" type="text" required pattern="^[A-Za-z]{2,20}$">
            <span></span>
            <label>First Name</label>
        </div>
        <div class="txt_field">
            <input name="lastName" type="text" required pattern="^[A-Za-z]{2,20}$">
            <span></span>
            <label>Last Name</label>
        </div>
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
        <div class="txt_field">
            <input name="age" type="text" required pattern="^\d{1,2}$">
            <span></span>
            <label>Age</label>
        </div>
        <div class="txt_field">
            <input name="cardNumber" type="text" required pattern="^\d{16}$">
            <span></span>
            <label>Card Number for pays</label>
        </div>
        <input type="submit" value="Create Admin by this credentials">
    </form>
</div>
</body>
</html>