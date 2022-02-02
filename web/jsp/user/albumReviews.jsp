<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 1.02.22
  Time: 6:24 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Album Reviews</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/bootstrap.css">
</head>
<body>
<c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
    <%@include file="/jsp/admin/adminHeader.jsp" %>
</c:if>

<c:if test="${sessionScope.user.role eq 'CLIENT_ROLE'}">
    <%@include file="userHeader.jsp" %>
</c:if>

<table class="table">
    <thead>
    <tr>
        <th scope="col">#</th>
        <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
            <th scope="col">Id</th>
        </c:if>
        <th scope="col">Album title</th>
        <th scope="col">Review text</th>
        <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
            <th scope="col">User's id</th>
        </c:if>
        <th scope="col">Login</th>
        <th scope="col">User's Full Name</th>
    </tr>
    <c:forEach var="albumReview" varStatus="status" items="${requestScope.albumReviews}">
        <tr>
            <th scope="row">${status.count}</th>
            <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
                <td>${albumReview.id}</td>
            </c:if>
            <td>${albumReview.album.title}</td>
            <td>${albumReview.reviewText}</td>
            <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
                <td>${albumReview.userAccount.id}</td>
            </c:if>
            <td>${albumReview.userAccount.login}</td>
            <td>${albumReview.userAccount.firstName} ${albumReview.userAccount.lastName}</td>
        </tr>
    </c:forEach>
    </thead>
</table>

<c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
    <div class="container fixed-bottom">
        <footer class="py-3 my-4">
            <ul class="nav justify-content-center border-bottom pb-3 mb-3">
                <li class="nav-item"><a href="${pageContext.request.contextPath}/jsp/admin/admin.jsp"
                                        class="nav-link px-2 text-muted">Home</a></li>
                <li class="nav-item"><a href="${pageContext.request.contextPath}/jsp/admin/dashboard.jsp"
                                        class="nav-link px-2 text-muted">Dashboard</a></li>
                <li class="nav-item"><a
                        href="${pageContext.request.contextPath}/frontController?frontCommand=all_orders"
                        class="nav-link px-2 text-muted">Orders</a></li>
                <li class="nav-item"><a href="${pageContext.request.contextPath}/frontController?frontCommand=all_users"
                                        class="nav-link px-2 text-muted">Users</a></li>
            </ul>
            <p class="text-center text-muted">&copy; 2021 OrdJoy, Inc</p>
        </footer>
    </div>
</c:if>

<c:if test="${sessionScope.user.role eq 'CLIENT_ROLE'}">
    <div class="container fixed-bottom">
        <footer class="py-3 my-4">
            <ul class="nav justify-content-center border-bottom pb-3 mb-3">
                <li class="nav-item"><a href="${pageContext.request.contextPath}/jsp/user/user.jsp"
                                        class="nav-link px-2 text-muted">Home</a></li>
                <li class="nav-item"><a href="${pageContext.request.contextPath}/jsp/user/features.jsp"
                                        class="nav-link px-2 text-muted">Features</a></li>
                <li class="nav-item"><a href="${pageContext.request.contextPath}/jsp/user/about.jsp"
                                        class="nav-link px-2 text-muted">About</a></li>
                <li class="nav-item"><a href="https://github.com/h1alexbel/OrdJoy"
                                        class="nav-link px-2 text-muted">GitHub</a></li>
            </ul>
            <p class="text-center text-muted">&copy; 2021 OrdJoy, Inc</p>
        </footer>
    </div>
</c:if>
<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.js"></script>
</body>
</html>