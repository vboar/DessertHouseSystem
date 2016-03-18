<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页 - 哆哆甜品屋</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.4.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
</head>
<body>
<%@include file="common/navbar.jsp"%>
<%@include file="common/header.jsp"%>

<div class="wrapper">

    <div class="content">
        <div class="product-panel" id="product-panel"></div>
    </div>

</div>
<script>
    $(document).ready(function () {
        var shopId = window.localStorage.getItem("shopId");
        if (shopId == null || shopId == undefined) shopId = 1;
        getProducts(shopId, null);
        searchInit();
    });

    function getProducts(shopId, key) {
        $.ajax({
            type: "GET",
            url: "/getProducts",
            data: {
                shopId: shopId,
                key: key
            },
            success: function(data) {
                $("#product-panel").empty();
                var list = data.list;
                for (var i = 0; i < list.length; i++) {
                    var html =
                        '<div class="product-card" productId="' + list[i].id + '">' +
                        '<img src="' + list[i].imgPath + '" class="product-img">' +
                        '<div class="product-name">' + list[i].name + '</div>' +
                        '<div class="product-price">￥' + list[i].minPrice + '</div>' +
                        '</div>';
                    $("#product-panel").append(html);
                }
                $("#product-panel").children(".product-card").click(function () {
                    window.location.href = "/product?id=" + $(this).attr("productId");
                });


            },
            error: function() {
                toaster("服务器出现问题，请稍微再试！", "error");
            }
        });
    }
    
    function loadProducts() {
        var shopId = window.localStorage.getItem("shopId");
        getProducts(shopId, null);
        $("#js-search-input").val("");
    }

    function searchInit() {
        $("#js-btn-search").click(function () {
            var shopId = window.localStorage.getItem("shopId");
            getProducts(shopId, $("#js-search-input").val());
        });
    }

</script>
</body>
</html>
