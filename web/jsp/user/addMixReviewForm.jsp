<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 30.01.22
  Time: 4:21 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
    <title>Mix Review Form</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<div class="center">
    <%@include file="/jsp/locale.jsp"%>
    <h1>
        <fmt:message key="add.mix.review.title"/>
    </h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="add_mix_review">
        <div class="txt_field">
            <input name="mixName" type="text" required pattern="^[A-Za-zА-Яа-я].{1,512}.$">
            <span></span>
            <label>
                <fmt:message key="add.mix.review.mix.name"/>
            </label>
        </div>
        <div class="txt_field">
            <input name="mixReviewText" type="text" required pattern="^[A-Za-zА-Яа-я].{2,512}">
            <span></span>
            <label> <fmt:message key="review.text"/></label>
        </div>
        <input type="submit" value="<fmt:message key="add.review.button"/>">
    </form>
</div>
</body>
</html>