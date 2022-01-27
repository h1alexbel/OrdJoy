<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 26.01.22
  Time: 6:56 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.88.1">
    <title>Welcome</title>
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
        <img class="d-block mx-auto mb-4"
             src="${pageContext.request.contextPath}/img/greeting.svg" alt="" width="210"
             height="140">
        <h1 class="display-5 fw-bold">Hello ${sessionScope.user.firstName} ${sessionScope.user.lastName}!</h1>
        <div class="col-lg-6 mx-auto">
            <p class="lead mb-4">We feel great to meet you! Welcome!</p>
            <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                <a href="${pageContext.request.contextPath}/jsp/user/user.jsp"
                   class="btn btn-primary btn-lg px-4 gap-3" role="button">Go to Home page</a>
                <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                    <a href="${pageContext.request.contextPath}/jsp/user/makeOrder.jsp"
                       class="btn btn-outline-secondary btn-lg px-4" role="button">Make Order</a>
                </div>
            </div>
        </div>
    </div>
</main>
<%@include file="userFooter.jsp" %>
</body>
</html>