<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 28.01.22
  Time: 1:44 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add new Admin form</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<div class="center">
    <%@include file="/jsp/locale.jsp" %>
    <h1>
        <fmt:message key="add.admin"/>
    </h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="add_admin">
        <div class="txt_field">
            <input name="email" type="email" required pattern="^[A-Za-z.]+\w+@[A-Za-z]{2,}\.(com|org)$">
            <span></span>
            <label>
                <fmt:message key="register.email"/>
            </label>
        </div>
        <div class="txt_field">
            <input name="firstName" type="text" required pattern="^[A-Za-zА-Яа-я]{2,20}$">
            <span></span>
            <label>
                <fmt:message key="register.firstname"/>
            </label>
        </div>
        <div class="txt_field">
            <input name="lastName" type="text" required pattern="^[A-Za-zА-Яа-я]{2,20}$">
            <span></span>
            <label>
                <fmt:message key="register.lastname"/>
            </label>
        </div>
        <div class="txt_field">
            <input name="login" type="text" required pattern="^[A-Za-zА-Яа-я1-9]{3,20}$">
            <span></span>
            <label>
                <fmt:message key="login.username"/>
            </label>
        </div>
        <div class="txt_field">
            <input name="password" type="password" required pattern="^\w{2,64}$">
            <span></span>
            <label>
                <fmt:message key="login.password"/>
            </label>
        </div>
        <div class="txt_field">
            <input name="age" type="text" required pattern="^\d{1,2}$">
            <span></span>
            <label>
                <fmt:message key="register.age"/>
            </label>
        </div>
        <div class="txt_field">
            <input name="cardNumber" type="text" required pattern="^\d{16}$">
            <span></span>
            <label>
                <fmt:message key="register.card"/>
            </label>
        </div>
        <input type="submit" value="<fmt:message key="admin.form.create.button"/>">
    </form>
</div>
</body>
</html>