<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 2.02.22
  Time: 11:25 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
    <title>Update Mix form</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<div class="center">
    <%@include file="/jsp/locale.jsp" %>
    <h1>
        <fmt:message key="new.credentials"/>
    </h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="edit_mix">
        <div class="txt_field">
            <input name="mixId" type="text" required pattern="^\d{1,}$">
            <span></span>
            <label>
                <fmt:message key="delete.mix.text"/>
            </label>
        </div>
        <div class="txt_field">
            <input name="mixName" type="text" required pattern="\w.{1,512}.$">
            <span></span>
            <label>
                <fmt:message key="mixes.mix.name"/>
            </label>
        </div>
        <div class="txt_field">
            <input name="mixDescription" type="text" required pattern="\w.{2,512}">
            <span></span>
            <label>
                <fmt:message key="mixes.mix.description"/>
            </label>
        </div>
        <input type="submit" value="<fmt:message key="edit.mix.button"/>">
    </form>
</div>
</body>
</html>