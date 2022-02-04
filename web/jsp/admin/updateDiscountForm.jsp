<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 30.01.22
  Time: 1:34 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
    <title>Update User's DPL</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<div class="center">
    <%@include file="/jsp/locale.jsp"%>
    <h1>
        <fmt:message key="edit.discount.percentage.level.text"/>
    </h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="edit_discount_percentage_level">
        <div class="txt_field">
            <input name="email" type="text" required pattern="^[A-Za-z.]+\w+@[A-Za-z]{2,}\.(com|org)$">
            <span></span>
            <label>
                <fmt:message key="register.email"/>
            </label>
        </div>
        <div class="txt_field">
            <input name="discountPercentageLevel" type="text" required>
            <span></span>
            <label>
                <fmt:message key="edit.discount.percentage.level"/>
            </label>
        </div>
        <input type="submit" value="<fmt:message key="edit.discount.percentage.level.button"/>">
    </form>
</div>
</body>
</html>