<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 30.01.22
  Time: 1:34 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Form to update Your DPL</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/design.css">
</head>
<body>
<div class="center">
    <h1>Request to update Your DPL</h1>
    <form method="post" action="${pageContext.request.contextPath}/frontController"
          enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="frontCommand" value="update_discount">
        <div class="txt_field">
            <input name="discountPercentageLevel" type="text" required>
            <span></span>
            <label>DPL, that you want</label>
        </div>
        <input type="submit" value="Send request to update your DPL">
    </form>
</div>
</body>
</html>