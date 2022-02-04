<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 30.01.22
  Time: 12:05 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <th scope="col">
            <fmt:message key="tracks.track.title"/>
        </th>
        <th scope="col">Review text</th>
        <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
            <th scope="col">
                <fmt:message key="reviews.users.id"/>
            </th>
        </c:if>
        <th scope="col">
            <fmt:message key="reviews.user.login"/>
        </th>
        <th scope="col">
            <fmt:message key="reviews.full.name"/>
        </th>
    </tr>
    <c:forEach var="trackReview" varStatus="status" items="${requestScope.trackReviews}">
        <tr>
            <th scope="row">${status.count}</th>
            <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
                <td>${trackReview.id}</td>
            </c:if>
            <td>${trackReview.track.title}</td>
            <td>${trackReview.reviewText}</td>
            <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
                <td>${trackReview.userAccount.id}</td>
            </c:if>
            <td>${trackReview.userAccount.login}</td>
            <td>${trackReview.userAccount.firstName} ${trackReview.userAccount.lastName}</td>
        </tr>
    </c:forEach>
    </thead>
</table>

<c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
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
</c:if>

<c:if test="${sessionScope.user.role eq 'CLIENT_ROLE'}">
    <div class="container fixed-bottom">
        <footer class="py-3 my-4">
        <ul class="nav justify-content-center border-bottom pb-3 mb-3">
            <li class="nav-item"><a href="${pageContext.request.contextPath}/jsp/user/user.jsp"
                                    class="nav-link px-2 text-muted">
                <fmt:message key="user.home"/>
            </a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/jsp/user/features.jsp"
                                    class="nav-link px-2 text-muted">
                <fmt:message key="user.features"/>
            </a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/jsp/user/about.jsp"
                                    class="nav-link px-2 text-muted">
                <fmt:message key="user.about"/>
            </a></li>
            <li class="nav-item"><a href="https://github.com/h1alexbel/OrdJoy"
                                    class="nav-link px-2 text-muted">
                <fmt:message key="user.github"/>
            </a></li>
        </ul>
        <p class="text-center text-muted">&copy; 2021 OrdJoy, Inc</p>
        </footer>
    </div>
</c:if>
<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.js"></script>
</body>
</html>