<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 30.01.22
  Time: 2:49 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>All Users | Analytics</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/bootstrap.css">
</head>
<body>
<%@include file="adminHeader.jsp" %>
<table class="table">
    <thead>
    <tr>
        <th scope="col">Id</th>
        <th scope="col">Login</th>
        <th scope="col">Email</th>
        <th scope="col">Full Name</th>
    </tr>
    <c:forEach var="user" items="${requestScope.users}">
        <c:if test="${user.role eq 'ADMIN_ROLE'}">
            <tr>
                <td>${user.id}</td>
                <td>${user.login}</td>
                <td>${user.email}</td>
                <td>${user.firstName} ${user.lastName}</td>
                <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
                    <td><a href="${pageContext.request.contextPath}/jsp/admin/deleteAdmin.jsp" role="button"
                           class="btn btn-outline-danger">Delete admin</a></td>
                </c:if>
            </tr>
        </c:if>
    </c:forEach>
    </thead>
</table>
<%@include file="adminFooter.jsp" %>
<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.js"></script>
</body>
</html>