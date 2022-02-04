<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 22.01.22
  Time: 4:42 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin main page</title>
</head>
<body>
<%@include file="adminHeader.jsp" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<main>
    <h1 class="visually-hidden">Heroes examples</h1>

    <div class="px-4 py-5 my-5 text-center">
        <img class="d-block mx-auto mb-4"
             src="${pageContext.request.contextPath}/img/admin.svg" alt="" width="210"
             height="140">
        <h1 class="display-5 fw-bold">
            <fmt:message key="admin.message"/>
            ${sessionScope.user.login} <fmt:message key="admin.second.message"/></h1>
        <div class="col-lg-6 mx-auto">
            <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                <a href="${pageContext.request.contextPath}/jsp/admin/dashboard.jsp"
                   class="btn btn-primary btn-lg px-4 gap-3" role="button"><fmt:message key="admin.go.dashboard"/></a>
                <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                    <a href="${pageContext.request.contextPath}/jsp/admin/addAdminForm.jsp"
                       class="btn btn-outline-secondary btn-lg px-4" role="button">
                        <fmt:message key="add.admin"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</main>
<%@include file="adminFooter.jsp" %>
</body>
</html>