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
    <title>Track Review Form</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<div class="center">
<%@include file="/jsp/locale.jsp"%>
    <h1><fmt:message key="add.track.review.title"/></h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="add_track_review">
        <div class="txt_field">
            <input name="trackTitle" type="text" required pattern="^[A-Za-zА-Яа-я].{1,512}.$">
            <span></span>
            <label>
                <fmt:message key="tracks.track.title"/>
            </label>
        </div>
        <div class="txt_field">
            <input name="trackReviewText" type="text" required pattern="^[A-Za-zА-Яа-я].{2,512}">
            <span></span>
            <label>
                <fmt:message key="review.text"/>
            </label>
        </div>
        <input type="submit" value="<fmt:message key="add.review.button"/>">
    </form>
</div>
</body>
</html>