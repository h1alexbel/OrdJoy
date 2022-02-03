<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 30.01.22
  Time: 1:34 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <title>Update User's DPL</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<div class="center">
    <h1>Update DPL</h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="edit_discount_percentage_level">
        <div class="txt_field">
            <input name="email" type="text" required pattern="^[A-Za-z.]+\w+@[A-Za-z]{2,}\.(com|org)$">
            <span></span>
            <label>Email</label>
        </div>
        <div class="txt_field">
            <input name="discountPercentageLevel" type="text" required>
            <span></span>
            <label>new DPL</label>
        </div>
        <input type="submit" value="Update">
    </form>
</div>
</body>
</html>