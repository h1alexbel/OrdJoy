<%--
  Created by IntelliJ IDEA.
  User: alexeybelyavsky
  Date: 25.01.22
  Time: 8:39 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.88.1">
    <title>User Content</title>
    <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/blog/">

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

    <link href="https://fonts.googleapis.com/css?family=Playfair&#43;Display:700,900&amp;display=swap" rel="stylesheet">
    <link href="blog.css" rel="stylesheet">
</head>
<body>

<main class="container">
    <div class="p-4 p-md-5 mb-4 text-white rounded bg-dark">
        <div class="col-md-6 px-0">
            <h1 class="display-4 fst-italic">What's new?</h1>
            <p class="lead my-3"></p>
        </div>
    </div>
    <div class="row g-5">
        <div class="col-md-8">
            <article class="blog-post">
                <h2 class="blog-post-title">New feature</h2>
                <p class="blog-post-meta">January 29, 2022 by <a href="https://github.com/h1alexbel">Alexey</a></p>

                <p>Hello folks! By the January 29, 2022 we added some new features. Some updates that You can see right
                    now! :</p>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/frontController?frontCommand=all_tracks">Track
                        updates</a></li>
                    <li>Admin can add another admin very easy, just filling out the form. Relevant button was added:)
                    </li>
                </ul>
                <p>Find some bug or You have some suggestions? Contact us: ordjoy.team@gmail.com</p>
            </article>
        </div>

        <div class="col-md-4">
            <div class="position-sticky" style="top: 2rem;">
                <div class="p-4 mb-3 bg-light rounded">
                    <h4 class="fst-italic">About</h4>
                    <p class="mb-0">We publish and offer to You only that tracks, that pass the full verify stage and
                        our listing</p>
                </div>

                <div class="p-4">
                    <h4 class="fst-italic">Our goods</h4>
                    <ol class="list-unstyled mb-0">
                        <li><a href="${pageContext.request.contextPath}/frontController?frontCommand=all_tracks">Track
                            List</a></li>
                        <li><a href="${pageContext.request.contextPath}/frontController?frontCommand=all_mixes">Mix
                            List</a></li>
                        <li><a href="${pageContext.request.contextPath}/frontController?frontCommand=all_albums">Album
                            List</a></li>
                    </ol>
                </div>

                <div class="p-4">
                    <h4 class="fst-italic">Reviews</h4>
                    <ol class="list-unstyled mb-0">
                        <li><a href="${pageContext.request.contextPath}/frontController?frontCommand=all_track_reviews">Track
                            Reviews</a></li>
                        <li><a href="${pageContext.request.contextPath}/frontController?frontCommand=all_mix_reviews">Mix
                            Reviews</a></li>
                        <li><a href="${pageContext.request.contextPath}/frontController?frontCommand=all_album_reviews">Album
                            Reviews</a></li>
                    </ol>
                </div>

                <div class="p-4">
                    <h4 class="fst-italic">Elsewhere</h4>
                    <ol class="list-unstyled">
                        <li><a href="https://github.com/h1alexbel/OrdJoy">OrdJoy's GitHub</a></li>
                        <li><a href="https://github.com/h1alexbel">Developer's GitHub</a></li>
                        <li><a href="https://www.linkedin.com/in/aliaksei-bialiauski-49b2a821a/">LinkedIn</a></li>
                    </ol>
                </div>
            </div>
        </div>
    </div>
    <footer class="py-3 my-4">
        <ul class="nav justify-content-center border-bottom pb-3 mb-3">
            <li class="nav-item"><a href="${pageContext.request.contextPath}/jsp/user/user.jsp"
                                    class="nav-link px-2 text-muted">Home</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/jsp/user/features.jsp"
                                    class="nav-link px-2 text-muted">Features</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/jsp/user/about.jsp"
                                    class="nav-link px-2 text-muted">About</a></li>
            <li class="nav-item"><a href="https://github.com/h1alexbel/OrdJoy"
                                    class="nav-link px-2 text-muted">GitHub</a></li>
        </ul>
        <p class="text-center text-muted">&copy; 2021 OrdJoy, Inc</p>
    </footer>
</main>
</body>
</html>