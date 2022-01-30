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
        <th scope="col">Album Title</th>
    </tr>
    <c:forEach var="album" varStatus="status" items="${requestScope.albums}">
        <tr>
            <th scope="row">${status.count}</th>
            <td>${album.title}</td>
            <c:if test="${sessionScope.user.role eq 'ADMIN_ROLE'}">
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/frontController"
                          enctype="application/x-www-form-urlencoded">
                        <input type="hidden" name="frontCommand" value="delete_album">
                        <input type="submit" class="btn btn-outline-danger" value="Delete Album">
                    </form>
                </td>
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