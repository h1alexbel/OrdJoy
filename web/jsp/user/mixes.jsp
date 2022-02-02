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
    <title>Mixes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/bootstrap.css">
</head>
<body>
<%@include file="userHeader.jsp" %>

<table class="table">
    <thead>
    <tr>
        <th scope="col">#</th>
        <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
            <th scope="col">Mix Id</th>
        </c:if>
        <th scope="col">Mix name</th>
        <th scope="col">Description</th>
    </tr>
    <c:forEach var="mix" varStatus="status" items="${requestScope.mixes}">
        <tr>
            <th scope="row">${status.count}</th>
            <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
                <td>${mix.id}</td>
            </c:if>
            <td>${mix.name}</td>
            <td>${mix.description}</td>
            <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
                <td><a href="${pageContext.request.contextPath}/jsp/admin/deleteMix.jsp" role="button"
                       class="btn btn-danger">Delete
                    Mix</a></td>
            </c:if>
            <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
                <td><a href="${pageContext.request.contextPath}/jsp/admin/editMix.jsp" role="button"
                       class="btn btn-info">Edit
                    Mix</a></td>
            </c:if>
            <c:if test="${sessionScope.user.role eq 'CLIENT_ROLE'}">
                <td><a href="${pageContext.request.contextPath}/jsp/user/addMixReviewForm.jsp" role="button"
                       class="btn btn-warning">Add
                    Review</a></td>
            </c:if>
        </tr>
    </c:forEach>
    </thead>
</table>
<%@include file="userFooter.jsp" %>
</body>
</html>