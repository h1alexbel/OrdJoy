<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 20.01.22
  Time: 8:49 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Log in to OrdJoy</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<%@include file="jsp/locale.jsp"%>
<div class="center">
    <h1><fmt:message key="login.title"/>
    </h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="login">
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
        <input type="submit" value="<fmt:message key="login.submit"/>">
        <div class="signup_link">
            <fmt:message key="login.reg.membership"/>
            <a href="${pageContext.request.contextPath}register.jsp"><fmt:message key="register.submit"/></a>
        </div>
    </form>
</div>
</body>
</html>