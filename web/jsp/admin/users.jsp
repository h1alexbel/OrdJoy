<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 28.01.22
  Time: 6:01 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <th scope="col">#</th>
        <th scope="col">
            <fmt:message key="login.username"/>
        </th>
        <th scope="col">
            <fmt:message key="users.role"/>
        </th>
        <th scope="col">
            <fmt:message key="register.firstname"/>
        </th>
        <th scope="col">
            <fmt:message key="register.lastname"/>
        </th>
        <th scope="col">
            <fmt:message key="register.email"/>
        </th>
        <th scope="col">
            <fmt:message key="users.current.dpl"/>
        </th>
    </tr>
    <c:forEach var="user" varStatus="status" items="${requestScope.users}">
        <tr>
            <c:if test="${user.role eq 'CLIENT_ROLE'}">
                <th scope="row">${status.count}</th>
                <td>${user.login}</td>
                <td>${user.role}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.email}</td>
                <td>${user.discountPercentageLevel}</td>
                <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
                    <td><a href="${pageContext.request.contextPath}/jsp/admin/updateDiscountForm.jsp" role="button"
                           class="btn btn-outline-info">
                        <fmt:message key="edit.discount.percentage.level.button"/>
                    </a></td>
                </c:if>
            </c:if>
        </tr>
    </c:forEach>
    </thead>
</table>
<div class="container fixed-bottom">
    <footer class="py-3 my-4">
        <ul class="nav justify-content-center border-bottom pb-3 mb-3">
            <li class="nav-item"><a href="${pageContext.request.contextPath}/jsp/admin/admin.jsp"
                                    class="nav-link px-2 text-muted">
                <fmt:message key="admin.home"/>
            </a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/jsp/admin/dashboard.jsp"
                                    class="nav-link px-2 text-muted">
                <fmt:message key="admin.dashboard"/>
            </a></li>
            <li class="nav-item"><a
                    href="${pageContext.request.contextPath}/frontController?frontCommand=all_orders"
                    class="nav-link px-2 text-muted">
                <fmt:message key="admin.orders"/>
            </a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/frontController?frontCommand=all_users"
                                    class="nav-link px-2 text-muted">
                <fmt:message key="admin.customers"/>
            </a></li>
        </ul>
        <p class="text-center text-muted">&copy; 2021 OrdJoy, Inc</p>
    </footer>
</div>
<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.js"></script>
</body>
</html>