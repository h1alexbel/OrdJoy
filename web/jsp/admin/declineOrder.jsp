<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 2.02.22
  Time: 11:02 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Decline order form</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<div class="center">
    <h1>Enter order's Id</h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="decline_order">
        <div class="txt_field">
            <input name="orderId" type="text" required pattern="^\d{1,}$">
            <span></span>
            <label>Id</label>
        </div>
        <input type="submit" value="Decline order">
    </form>
</div>
</body>
</html>