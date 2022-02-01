<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 28.01.22
  Time: 6:48 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>All Orders | Analytics</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/bootstrap.css">
</head>
<body>
<%@include file="adminHeader.jsp" %>
<table class="table">
    <thead>
    <tr>
        <th scope="col">#</th>
        <th scope="col">Id</th>
        <th scope="col">Track</th>
        <th scope="col">User's id</th>
        <th scope="col">User's email</th>
        <th scope="col">User's Full Name</th>
        <th scope="col">Order price</th>
    </tr>
    <c:forEach var="order" varStatus="status" items="${requestScope.orders}">
        <tr>
            <th scope="row">${status.count}</th>
            <td>${order.id}</td>
            <td>${order.track.title}</td>
            <td>${order.userAccount.id}</td>
            <td>${order.userAccount.email}</td>
            <td>${order.userAccount.firstName} ${order.userAccount.lastName}</td>
            <td>${order.price}$</td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/frontController"
                      enctype="application/x-www-form-urlencoded">
                    <input type="hidden" name="frontCommand" value="run_order">
                    <input type="submit" class="btn btn-outline-success" value="Run task">
                </form>
            </td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/frontController"
                      enctype="application/x-www-form-urlencoded">
                    <input type="hidden" name="frontCommand" value="decline_order">
                    <input type="submit" class="btn btn-outline-warning" value="Decline order">
                </form>
            </td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/frontController"
                      enctype="application/x-www-form-urlencoded">
                    <input type="hidden" name="frontCommand" value="delete_order">
                    <input type="submit" class="btn btn-outline-danger" value="Delete order">
                </form>
            </td>
        </tr>
    </c:forEach>
    </thead>
</table>
<footer class="py-3 my-4">
    <ul class="nav justify-content-center border-bottom pb-3 mb-3">
        <li class="nav-item"><a href="${pageContext.request.contextPath}/jsp/admin/admin.jsp"
                                class="nav-link px-2 text-muted">Home</a></li>
        <li class="nav-item"><a href="${pageContext.request.contextPath}/jsp/admin/dashboard.jsp"
                                class="nav-link px-2 text-muted">Dashboard</a></li>
        <li class="nav-item"><a href="${pageContext.request.contextPath}/frontController?frontCommand=all_orders"
                                class="nav-link px-2 text-muted">Orders</a></li>
        <li class="nav-item"><a href="${pageContext.request.contextPath}/frontController?frontCommand=all_users"
                                class="nav-link px-2 text-muted">Customers</a></li>
    </ul>
    <p class="text-center text-muted">&copy; 2021 OrdJoy, Inc</p>
</footer>
<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.js"></script>
</body>
</html>