<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 21.01.22
  Time: 8:56 pm
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
    <title>Header</title>
    <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/headers/">
    <link href="${pageContext.request.contextPath}/resources/static/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <meta name="theme-color" content="#7952b3">
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
    <link href="${pageContext.request.contextPath}headers.css" rel="stylesheet">
</head>
<body>
<main>
    <h1 class="visually-hidden">Headers examples</h1>
    <div class="b-example-divider"></div>

    <header class="p-3 bg-dark text-white">
        <div class="container">
            <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                    <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap">
                        <use xlink:href="#bootstrap"/>
                    </svg>
                </a>

                <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                    <li><a href="${pageContext.request.contextPath}/jsp/user/user.jsp"
                           class="nav-link px-2 text-white">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/jsp/user/features.jsp"
                           class="nav-link px-2 text-white">Features</a>
                    </li>
                    <li><a href="${pageContext.request.contextPath}/jsp/user/about.jsp"
                           class="nav-link px-2 text-white">About</a>
                    </li>
                    <li><a href="https://github.com/h1alexbel/OrdJoy" class="nav-link px-2 text-white">GitHub</a></li>
                </ul>
                <form class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
                    <input type="search" class="form-control form-control-dark" placeholder="Search new Hits..."
                           aria-label="Search">
                </form>
                <div class="text-end">
                    <a href="${pageContext.request.contextPath}/jsp/user/account.jsp" class="btn btn-outline-light me-2"
                       role="button">Your Account</a>
                    <a href="${pageContext.request.contextPath}/jsp/user/makeOrder.jsp"
                       class="btn btn-outline-primary" role="button">Order Track</a>

                    <div class="text-xxl-center">
                        <form method="post" action="${pageContext.request.contextPath}/frontController"
                              enctype="application/x-www-form-urlencoded">
                            <input type="hidden" name="frontCommand" value="logout">
                            <input type="submit" class="btn btn-info" value="Logout">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </header>
    <div class="b-example-divider"></div>
</main>
<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>