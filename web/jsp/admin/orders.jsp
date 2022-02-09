<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 28.01.22
  Time: 6:48 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <th scope="col">
            <fmt:message key="orders.track"/>
        </th>
        <th scope="col">
            <fmt:message key="reviews.users.id"/>
        </th>
        <th scope="col">
            <fmt:message key="register.email"/>

        </th>
        <th scope="col">
            <fmt:message key="reviews.full.name"/>
        </th>
        <th scope="col">
            <fmt:message key="orders.price"/>
        </th>
        <th scope="col">
            <fmt:message key="orders.status"/>
        </th>
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
            <td>${order.status}</td>
            <td><a href="${pageContext.request.contextPath}/jsp/admin/doOrder.jsp" role="button"
                   class="btn btn-outline-success">
                <fmt:message key="orders.run.button"/>
            </a></td>
            <td><a href="${pageContext.request.contextPath}/jsp/admin/declineOrder.jsp" role="button"
                   class="btn btn-outline-warning">
                <fmt:message key="decline.order.button"/>
            </a></td>
            <td><a href="${pageContext.request.contextPath}/jsp/admin/deleteOrder.jsp" role="button"
                   class="btn btn-outline-danger">
                <fmt:message key="delete.order.button"/>
            </a></td>
        </tr>
    </c:forEach>
    </thead>
</table>
<div class="text-center">
    <c:forEach var="pageNo" begin="0" end="${requestScope.noOfPages}">
        <a href="${pageContext.request.contextPath}/frontController?frontCommand=all_orders&pageNo=${pageNo}&offset=${20 * pageNo}"
           role="button"
           class="btn btn-primary">${pageNo + 1}</a>
    </c:forEach>
</div>
<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.js"></script>
</body>
</html>