<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 21.01.22
  Time: 8:59 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<%@ include file="userHeader.jsp" %>
hello ${sessionScope.user.firstName} ${sessionScope.user.lastName}!
<%@ include file="userFooter.jsp" %>
</body>
</html>
