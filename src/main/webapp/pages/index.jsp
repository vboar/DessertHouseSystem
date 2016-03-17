<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页 - 哆哆甜品屋</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.4.min.js"></script>
</head>
<body>
<%@include file="common/navbar.jsp"%>
<%@include file="common/header.jsp"%>

<div class="wrapper">

    <div class="content">
        <div class="product-panel">
            <div class="product-card">
                <img src="${pageContext.request.contextPath}/assets/img/product.jpg" class="product-img">
                <div class="product-name">芝士花生冻饼</div>
                <div class="product-price">￥8.80</div>
            </div>
            <div class="product-card">
                <img src="${pageContext.request.contextPath}/assets/img/product.jpg" class="product-img">
                <div class="product-name">芝士花生冻饼</div>
                <div class="product-price">￥8.80</div>
            </div>
            <div class="product-card">
                <img src="${pageContext.request.contextPath}/assets/img/product.jpg" class="product-img">
                <div class="product-name">芝士花生冻饼</div>
                <div class="product-price">￥8.80</div>
            </div>
            <div class="product-card">
                <img src="${pageContext.request.contextPath}/assets/img/product.jpg" class="product-img">
                <div class="product-name">芝士花生冻饼</div>
                <div class="product-price">￥8.80</div>
            </div>
            <div class="product-card">
                <img src="${pageContext.request.contextPath}/assets/img/product.jpg" class="product-img">
                <div class="product-name">芝士花生冻饼</div>
                <div class="product-price">￥8.80</div>
            </div>
        </div>
    </div>

</div>
</body>
</html>
