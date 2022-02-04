<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 23.01.22
  Time: 8:38 pm
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
    <title>Dashboard | Manage OrdJoy platform</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/dashboard/">

    <link href="${pageContext.request.contextPath}/resources/static/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <link rel="apple-touch-icon" href="/docs/5.1/assets/img/favicons/apple-touch-icon.png" sizes="180x180">
    <link rel="icon" href="/docs/5.1/assets/img/favicons/favicon-32x32.png" sizes="32x32" type="image/png">
    <link rel="icon" href="/docs/5.1/assets/img/favicons/favicon-16x16.png" sizes="16x16" type="image/png">
    <link rel="manifest" href="/docs/5.1/assets/img/favicons/manifest.json">
    <link rel="mask-icon" href="/docs/5.1/assets/img/favicons/safari-pinned-tab.svg" color="#7952b3">
    <link rel="icon" href="/docs/5.1/assets/img/favicons/favicon.ico">
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

    <link href="dashboard.css" rel="stylesheet">
</head>
<body>

<%@include file="adminHeader.jsp" %>

<div class="container-fluid">
    <div class="row">
        <nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
            <div class="position-sticky pt-3">
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page"
                           href="${pageContext.request.contextPath}/jsp/admin/admin.jsp">
                            <span data-feather="home"></span>
                            <fmt:message key="admin.home"/>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/frontController?frontCommand=all_orders">
                            <span data-feather="shopping-cart"></span>
                            <fmt:message key="admin.orders"/>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/frontController?frontCommand=all_tracks">
                            <span data-feather="file"></span>
                            <fmt:message key="dashboard.tracks"/>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/frontController?frontCommand=all_albums">
                            <span data-feather="file"></span>
                            <fmt:message key="dashboard.albums"/>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/frontController?frontCommand=all_mixes">
                            <span data-feather="file"></span>
                            <fmt:message key="dashboard.mixes"/>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/frontController?frontCommand=all_users">
                            <span data-feather="users"></span>
                            <fmt:message key="admin.customers"/>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/frontController?frontCommand=all_admins">
                            <span data-feather="users"></span>
                            <fmt:message key="dashboard.admins"/>
                        </a>
                    </li>
                </ul>

                <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
                    <span>
                        <fmt:message key="dashboard.actions"/>
                    </span>
                </h6>
                <ul class="nav flex-column mb-2">
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/frontController?frontCommand=all_mix_reviews">
                            <span data-feather="file-text"></span>
                            <fmt:message key="dashboard.mix.reviews"/>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/frontController?frontCommand=all_track_reviews">
                            <span data-feather="file-text"></span>
                            <fmt:message key="dashboard.track.reviews"/>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/frontController?frontCommand=all_album_reviews">
                            <span data-feather="file-text"></span>
                            <fmt:message key="dashboard.album.reviews"/>
                        </a>
                    </li>
                </ul>
            </div>
        </nav>

        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2"><fmt:message key="dashboard.current"/>
                    ${sessionScope.user.login}</h1>
            </div>
            <canvas class="my-4 w-100" id="myChart" width="900" height="380"></canvas>
        </main>
    </div>
</div>

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
        <li class="nav-item"><a href="${pageContext.request.contextPath}/frontController?frontCommand=all_orders"
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

<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

<script src="https://cdn.jsdelivr.net/npm/feather-icons@4.28.0/dist/feather.min.js"
        integrity="sha384-uO3SXW5IuS1ZpFPKugNNWqTZRRglnUJK6UAZ/gxOX80nxEkN9NcGZTftn6RzhGWE"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js"
        integrity="sha384-zNy6FEbO50N+Cg5wap8IKA4M/ZnLJgzc6w2NqACZaK0u0FXfOWRRJOnQtpZun8ha"
        crossorigin="anonymous"></script>
<script>

    (function () {
        'use strict'

        feather.replace({'aria-hidden': 'true'})

        var ctx = document.getElementById('myChart')
        var myChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: [
                    'Sunday',
                    'Monday',
                    'Tuesday',
                    'Wednesday',
                    'Thursday',
                    'Friday',
                    'Saturday'
                ],
                datasets: [{
                    data: [
                        15339,
                        21345,
                        18483,
                        24003,
                        23489,
                        24092,
                        12034
                    ],
                    lineTension: 0,
                    backgroundColor: 'transparent',
                    borderColor: '#007bff',
                    borderWidth: 4,
                    pointBackgroundColor: '#007bff'
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: false
                        }
                    }]
                },
                legend: {
                    display: false
                }
            }
        })
    })()
</script>
</body>
</html>