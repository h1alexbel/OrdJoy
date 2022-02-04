<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 22.01.22
  Time: 6:14 pm
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
    <title>About</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/carousel/">

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

    <link href="carousel.css" rel="stylesheet">
</head>
<body>

<%@include file="userHeader.jsp" %>
<main>
    <div id="myCarousel" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="0" class="active" aria-current="true"
                    aria-label="Slide 1"></button>
            <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
            <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
        </div>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <svg class="bd-placeholder-img" width="100%" height="100%" xmlns="http://www.w3.org/2000/svg"
                     aria-hidden="true" preserveAspectRatio="xMidYMid slice" focusable="false">
                    <rect width="100%" height="100%" fill="#777"/>
                </svg>

                <div class="container">
                    <div class="carousel-caption text-start">
                        <h1>
                            <ftm:message key="about.order.message"/>
                        </h1>
                        <p>
                            <ftm:message key="about.order.second.message"/>
                        </p>
                        <p><a class="btn btn-lg btn-primary"
                              href="${pageContext.request.contextPath}/jsp/user/makeOrder.jsp">
                            <ftm:message key="about.make.order"/>
                        </a></p>
                    </div>
                </div>
            </div>
            <div class="carousel-item">
                <svg class="bd-placeholder-img" width="100%" height="100%" xmlns="http://www.w3.org/2000/svg"
                     aria-hidden="true" preserveAspectRatio="xMidYMid slice" focusable="false">
                    <rect width="100%" height="100%" fill="#777"/>
                </svg>

                <div class="container">
                    <div class="carousel-caption">
                        <h1>
                            <fmt:message key="about.all.hits.message"/>
                        </h1>
                        <p>
                            <fmt:message key="about.explore.message"/>
                        </p>
                        <p><a class="btn btn-lg btn-primary"
                              href="${pageContext.request.contextPath}/jsp/user/features.jsp">
                            <fmt:message key="about.explore.button"/>
                        </a></p>
                    </div>
                </div>
            </div>
            <div class="carousel-item">
                <svg class="bd-placeholder-img" width="100%" height="100%" xmlns="http://www.w3.org/2000/svg"
                     aria-hidden="true" preserveAspectRatio="xMidYMid slice" focusable="false">
                    <rect width="100%" height="100%" fill="#777"/>
                </svg>

                <div class="container">
                    <div class="carousel-caption text-end">
                        <h1>
                            <fmt:message key="about.support.title"/>
                        </h1>
                        <p>
                            <fmt:message key="about.support.message"/>
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#myCarousel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">
                                            <fmt:message key="about.carousel.prev"/>
            </span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#myCarousel" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">
                                            <fmt:message key="about.carousel.next"/>
            </span>
        </button>
    </div>

    <div class="container marketing">
        <div class="row">
            <div class="col-lg-4">
                <img src="${pageContext.request.contextPath}/img/review.jpeg" class="bd-placeholder-img rounded-circle"
                     width="140" height="140">
                <h2>
                    <fmt:message key="user.content.reviews"/>
                </h2>
                <p>
                    <fmt:message key="about.reviews.message"/>
                </p>
                <p><a class="btn btn-success"
                      href="${pageContext.request.contextPath}/frontController?frontCommand=all_track_reviews">
                    <fmt:message key="user.content.track.reviews"/> &raquo;
                </a></p>
                <p><a class="btn btn-success"
                      href="${pageContext.request.contextPath}/frontController?frontCommand=all_album_reviews">
                    <fmt:message key="user.content.album.reviews"/> &raquo;</a></p>
                <p><a class="btn btn-success"
                      href="${pageContext.request.contextPath}/frontController?frontCommand=all_mix_reviews">
                    <fmt:message key="user.content.mix.reviews"/> &raquo;</a></p>
            </div>
            <div class="col-lg-4">
                <img src="${pageContext.request.contextPath}/img/discount.png" class="bd-placeholder-img rounded-circle"
                     width="140" height="140">
                <h2>
                    <fmt:message key="about.discounts"/>
                </h2>
                <p><a class="btn btn-secondary" href="${pageContext.request.contextPath}/jsp/user/account.jsp">
                    <fmt:message key="about.view.account.message"/> &raquo;</a></p>
            </div>
            <div class="col-lg-4">
                <img src="${pageContext.request.contextPath}/img/contribute.png"
                     class="bd-placeholder-img rounded-circle"
                     width="140" height="140">
                <h2>
                    <fmt:message key="about.contribute"/>
                </h2>
                <p></p>
                <p><a class="btn btn-secondary" href="https://github.com/h1alexbel/OrdJoy/pulls">
                    <fmt:message key="about.pull.request"/> &raquo;</a></p>
            </div>
        </div>

        <hr class="featurette-divider">

        <div class="row featurette">
            <div class="col-md-7">
                <h2 class="featurette-heading">
                    <fmt:message key="about.track.repo"/>
                    <span
                            class="text-muted">
                         <fmt:message key="about.track.repo.info"/>
                    </span></h2>
                <p class="lead"><a href="${pageContext.request.contextPath}/frontController?frontCommand=all_tracks">
                    <fmt:message key="about.track.repo"/> </a></p>
            </div>
            <div class="col-md-5">
                <img src="${pageContext.request.contextPath}/img/track.jpeg"
                     class="bd-placeholder-img"
                     width="500" height="500" role="img" aria-label="Placeholder: Thumbnail"
                     alt="Track photo">
            </div>
        </div>

        <hr class="featurette-divider">

        <div class="row featurette">
            <div class="col-md-7 order-md-2">
                <h2 class="featurette-heading">
                    <fmt:message key="about.album.repo"/>
                    <span
                            class="text-muted"></span></h2>
                <p class="lead"><a href="${pageContext.request.contextPath}/frontController?frontCommand=all_albums">
                    <fmt:message key="about.album.repo.info"/>
                </a></p>
            </div>
            <div class="col-md-5 order-md-1">
                <img src="${pageContext.request.contextPath}/img/albums.jpeg"
                     class="bd-placeholder-img"
                     width="500" height="500" role="img" aria-label="Placeholder: Thumbnail"
                     alt="Album photo">
            </div>
        </div>

        <hr class="featurette-divider">

        <div class="row featurette">
            <div class="col-md-7">
                <h2 class="featurette-heading">
                    <fmt:message key="about.mix.repo"/>
                    <span
                            class="text-muted"><fmt:message key="about.mix.repo.info"/></span></h2>
                <p class="lead"><a href="${pageContext.request.contextPath}/frontController?frontCommand=all_mixes">
                    <fmt:message key="about.mix.repo"/>
                </a></p>
            </div>
            <div class="col-md-5">
                <img src="${pageContext.request.contextPath}/img/mixes.png"
                     class="bd-placeholder-img"
                     width="500" height="500" role="img" aria-label="Placeholder: Thumbnail"
                     alt="Mix photo">
            </div>
        </div>
        <hr class="featurette-divider">
    </div>
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
</main>

<script src="${pageContext.request.contextPath}/resources/static/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>