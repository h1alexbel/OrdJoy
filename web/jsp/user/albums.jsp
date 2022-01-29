<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 27.01.22
  Time: 4:48 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Albums</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/bootstrap.css">
</head>
<body>
<%@include file="userHeader.jsp" %>
<table class="table">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Album Title</th>
    </tr>
    <c:forEach var="album" varStatus="status" items="${requestScope.albums}">
        <tr>
            <th scope="row">${status.count}</th>
            <td>${album.title}</td>
        </tr>
    </c:forEach>
    </thead>
</table>
<%@include file="userFooter.jsp" %>
</body>
</html>