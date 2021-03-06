<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 30.01.22
  Time: 2:49 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <th scope="col">
            <fmt:message key="login.username"/>
        </th>
        <th scope="col">
            <fmt:message key="register.email"/>
        </th>
        <th scope="col">
            <fmt:message key="reviews.full.name"/>
        </th>
    </tr>
    <c:forEach var="user" items="${requestScope.users}">
            <tr>
                <td>${user.id}</td>
                <td>${user.login}</td>
                <td>${user.email}</td>
                <td>${user.firstName} ${user.lastName}</td>
                <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
                    <td><a href="${pageContext.request.contextPath}/jsp/admin/deleteAdmin.jsp" role="button"
                           class="btn btn-outline-danger">
                        <fmt:message key="admins.delete.button"/>
                    </a></td>
                </c:if>
            </tr>
    </c:forEach>
    </thead>
</table>
<div class="text-center">
    <c:forEach var="pageNo" begin="0" end="${requestScope.noOfPages}">
        <a href="${pageContext.request.contextPath}/frontController?frontCommand=all_admins&pageNo=${pageNo}&offset=${20 * pageNo}"
           role="button"
           class="btn btn-primary">${pageNo + 1}</a>
    </c:forEach>
</div>
<%@include file="adminFooter.jsp" %>
<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.js"></script>
</body>
</html>