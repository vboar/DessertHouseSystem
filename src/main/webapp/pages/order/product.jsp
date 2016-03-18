<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${product.name} - ${product.shop.name} - 哆哆甜品屋</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/datatables/datatables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.4.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
</head>
<body>
<%@include file="../common/navbar.jsp"%>
<%@include file="../common/dashboard_header.jsp"%>
<div class="wrapper">
    <div class="content">
        <div class="product-container">
            <div class="product-intro">
                <img src="${product.img}" class="product-img" />
                <div class="intro">
                    <div class="normal-div">产品： ${product.name}</div>
                    <div class="normal-div" style="margin-bottom: 30px">简介： ${product.description}</div>
                    <div class="normal-div">门店： ${product.shop.name}</div>
                    <div class="normal-div">门店简介： ${product.shop.description}</div>
                    <div class="normal-div">门店地址： ${product.shop.address}</div>
                </div>
                <div class="clear-fix"></div>
            </div>

            <div class="table-container">
                <table id="js-table" class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th width="20%">日期</th>
                        <th width="15%">单价</th>
                        <th width="15%">赠送积分</th>
                        <th width="15%">剩余数量</th>
                        <th width="15%">购买数量</th>
                        <th width="20%">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${planItems}" var="item">
                        <tr>
                        <td hidden>${item.product.id}</td>
                        <td>${item.date}</td>
                        <td>￥ ${item.price}</td>
                        <td>${item.point}</td>
                        <td>${item.remaining}</td>
                        <td><input type="number" min="0" max="${item.remaining}" value="1"
                                   class="number-input"/></td>
                        <td><button class="button button-shopping-cart">加入购物车</button></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

        </div>
    </div>
</div>

<%@include file="/pages/common/toaster.jsp"%>

</body>
<style>
    body {
        background-color: #f5f5f5;
    }
    .product-container {
        background-color: #fff;
        padding: 20px;
    }
    .product-img {
        float: left;
        width: 180px;
        height: 180px;
    }
    .intro {
        float: left;
        margin-left: 20px;
    }
    .number-input {
        width: 70px;
        outline: none;
    }
</style>
<script>
    $(document).ready(function() {
        initItem();
    });
    
    function initItem() {
        $(".button-shopping-cart").click(function () {
            var tds = $(this).parents("tr").children();
            var productId = tds.eq(0).text();
            var num = tds.eq(5).children().eq(0).val();
            var date = tds.eq(1).text();

            $.ajax({
                type: "POST",
                url: "/shoppingCart/add",
                data: {
                    productId: productId,
                    num: num,
                    date: date
                },
                success: function(data) {
                    if (data["success"] == false) {
                        toaster(data["error"], "error");
                    } else {
                        toaster("成功加入购物车", "success");
                    }
                },
                error: function() {
                    toaster("服务器出现问题，请稍微再试！", "error");
                }
            });

        });
    }
</script>
</html>

