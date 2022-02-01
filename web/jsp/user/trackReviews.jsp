<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 30.01.22
  Time: 12:05 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Track Reviews</title>
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
        <th scope="col">Track title</th>
        <th scope="col">Track URL</th>
        <th scope="col">Review text</th>
        <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
            <th scope="col">User's id</th>
        </c:if>
        <th scope="col">Login</th>
        <th scope="col">User's Full Name</th>
    </tr>
    <c:forEach var="trackReview" varStatus="status" items="${requestScope.trackReviews}">
        <tr>
            <th scope="row">${status.count}</th>
            <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
                <td>${trackReview.id}</td>
            </c:if>
            <td>${trackReview.track.title}</td>
            <td><a href="${trackReview.track.url}">Listen Free</a></td>
            <td>${trackReview.reviewText}</td>
            <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
                <td>${trackReview.userAccount.id}</td>
            </c:if>
            <td>${trackReview.userAccount.login}</td>
            <td>${trackReview.userAccount.firstName} ${trackReview.userAccount.lastName}</td>
            <c:if test="${sessionScope.user.role eq 'CLIENT_ROLE'}">
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/frontController"
                          enctype="application/x-www-form-urlencoded">
                        <input type="hidden" name="frontCommand" value="delete_track_review">
                        <input type="submit" class="btn btn-outline-danger" value="Delete Review">
                    </form>
                </td>
            </c:if>
            <c:if test="${sessionScope.user.role eq 'CLIENT_ROLE'}">
                <td><a href="${pageContext.request.contextPath}/jsp/user/editTrackReviewForm.jsp" role="button"
                       class="btn btn-outline-info">Edit
                    Review</a></td>
            </c:if>
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