<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 27.01.22
  Time: 3:00 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.88.1">
    <title>Order Form</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/checkout/">
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
    <link href="form-validation.css" rel="stylesheet">
</head>
<body class="bg-light">
<%@include file="userHeader.jsp" %>
<div class="container">
    <main>
        <div class="py-5 text-center">
            <h2>
                <fmt:message key="make.order.form"/>
            </h2>
            <p class="lead">
                <fmt:message key="make.order.form.message"/>
            </p>
        </div>
        <div class="row g-5">
            <div class="col-md-7 col-lg-8">
                <h4 class="mb-3">
                    <fmt:message key="make.order.details"/>
                </h4>
                <form class="needs-validation" novalidate method="post"
                      action="${pageContext.request.contextPath}/frontController"
                      enctype="application/x-www-form-urlencoded">
                    <input type="hidden" name="frontCommand" value="make_order">
                    <div class="row g-3">
                        <div class="col-sm-6">
                            <label for="login" class="form-label">
                                <fmt:message key="make.order.username"/>
                            </label>
                            <input type="text" class="form-control" id="login" value="${sessionScope.user.login}"
                                   required pattern="^\w{3,20}$">
                        </div>

                        <div class="col-sm-6">
                            <label for="email" class="form-label">
                                <fmt:message key="account.email"/>
                            </label>
                            <input type="text" class="form-control" id="email" value="${sessionScope.user.email}"
                                   required pattern="^[A-Za-z.]+\w+@[A-Za-z]{2,}\.(com|org)$">
                        </div>

                        <div class="col-sm-6">
                            <label for="email" class="form-label">
                                <fmt:message key="tracks.track.title"/>
                            </label>
                            <input type="text" class="form-control" name="trackTitle" required pattern="^\w.{1,512}.$">
                        </div>

                        <div class="col-sm-6">
                            <label for="email" class="form-label">
                                <fmt:message key="make.order.price"/>
                            </label>
                            <input type="text" class="form-control" name="price" required pattern="^\d{1,}$">
                        </div>
                    </div>

                    <hr class="my-4">


                    <h4 class="mb-3">
                        <fmt:message key="make.order.payment"/>
                    </h4>

                    <div class="my-3">
                        <div class="form-check">
                            <input id="credit" name="paymentMethod" type="radio" class="form-check-input" checked
                                   required>
                            <label class="form-check-label" for="credit">
                                <fmt:message key="make.order.credit.card.radio"/>
                            </label>
                        </div>
                        <div class="row gy-3">
                            <div class="col-md-6">
                                <label for="cc-number" class="form-label">
                                    <fmt:message key="make.order.credit.card"/>
                                </label>
                                <input type="text" class="form-control" id="cc-number"
                                       placeholder="<fmt:message key="payment.message"/>" required
                                       pattern="^\d{16}$">
                                <div class="invalid-feedback">
                                    <fmt:message key="make.order.credit.card.required"/>
                                </div>
                            </div>
                        </div>
                        <hr class="my-4">
                        <button class="w-100 btn btn-primary btn-lg" type="submit">
                            <fmt:message key="make.order.submit"/>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </main>
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
<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

<script>
    (function () {
        'use strict'
        var forms = document.querySelectorAll('.needs-validation')
        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    if (!form.checkValidity()) {
                        event.preventDefault()
                        event.stopPropagation()
                    }

                    form.classList.add('was-validated')
                }, false)
            })
    })()
</script>
</body>
</html>