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
        <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
        <th scope="col">Album Id</th>
        </c:if>
        <th scope="col">Album Title</th>
    </tr>
    <c:forEach var="album" varStatus="status" items="${requestScope.albums}">
        <tr>
            <th scope="row">${status.count}</th>
            <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
                <td>${album.id}</td>
            </c:if>
            <td>${album.title}</td>
            <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
                <td><a href="${pageContext.request.contextPath}/jsp/admin/deleteAlbum.jsp" role="button"
                       class="btn btn-danger">Delete
                    Album</a></td>
            </c:if>
            <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
                <td><a href="${pageContext.request.contextPath}/jsp/admin/editAlbum.jsp" role="button"
                       class="btn btn-info">Edit
                    Album</a></td>
            </c:if>
            <c:if test="${sessionScope.user.role eq 'CLIENT_ROLE'}">
                <td><a href="${pageContext.request.contextPath}/jsp/user/addAlbumReviewForm.jsp" role="button"
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