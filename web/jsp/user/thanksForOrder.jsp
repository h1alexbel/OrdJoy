<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 31.01.22
  Time: 9:37 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.88.1">
    <title>Thanks for Order</title>
    <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/heroes/">
    <link href="${pageContext.request.contextPath}/resources/static/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }
    </style>
    <link href="heroes.css" rel="stylesheet">
</head>
<body>
<%@include file="userHeader.jsp" %>
<main>
    <h1 class="visually-hidden">Heroes examples</h1>

    <div class="px-4 py-5 my-5 text-center">
        <h1 class="display-5 fw-bold">
            <fmt:message key="thanks.for.order.message"/>
            ${sessionScope.user.firstName} ${sessionScope.user.lastName}! </h1>
        <div class="col-lg-6 mx-auto">
            <p class="lead mb-4"><fmt:message
                    key="thanks.for.orders.track.title"/> ${sessionScope.order.track.title}</p>
            <p class="lead mb-4"><fmt:message
                    key="thanks.for.order.price"/>${sessionScope.order.price}$</p>
            <p class="lead mb-4"><fmt:message
                    key="thanks.for.orders.album.title"/> ${sessionScope.order.track.album.title}</p>
            <p class="lead mb-4"><a href="${sessionScope.order.track.url}"><fmt:message
                    key="thanks.for.order.link"/></a></p>
            <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                <a href="${pageContext.request.contextPath}/jsp/user/user.jsp"
                   class="btn btn-primary btn-lg px-4 gap-3" role="button"><fmt:message key="go.home"/></a>
                <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                    <a href="${pageContext.request.contextPath}/jsp/user/makeOrder.jsp"
                       class="btn btn-outline-secondary btn-lg px-4" role="button">
                        <fmt:message key="thanks.for.order.make.another.button"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</main>
<%@include file="userFooter.jsp" %>
</body>
</html>