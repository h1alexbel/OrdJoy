<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 3.02.22
  Time: 12:30 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en_US'}" scope="session"/>
<fmt:setBundle basename="localization/localization" scope="session"/>

<div>
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/frontController?frontCommand=switch_lang&lang=en">EN</a>
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/frontController?frontCommand=switch_lang&lang=ru">RU</a>
</div>