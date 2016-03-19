
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的购物车 - 哆哆甜品屋</title>
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
        <div class="cart-container">
            <c:if test="${list.size() == 0 || list == null}">
                购物车暂时为空~~~
            </c:if>

            <c:forEach items="${list}" var="item">
                <div class="cart-item" date="${item.date}" shopId="${item.shopId}">
                    <div class="normal-div">日期：${item.date}&nbsp;&nbsp;&nbsp;门店：${item.shopName}</div>

                    <div class="table-container">
                        <table id="js-table" class="table table-striped table-bordered">
                            <thead>
                            <tr>
                                <th width="30%">产品</th>
                                <th width="15%">单价</th>
                                <th width="15%">数量</th>
                                <th width="15%">赠送积分</th>
                                <th width="25%">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${item.items}" var="temp">
                                <tr productId="${temp.productId}">
                                    <td><a href="/product?id=${temp.productId}">${temp.productName}</a></td>
                                    <td>${temp.productPrice}</td>
                                    <td>${temp.number}</td>
                                    <td>${temp.productPoint}</td>
                                    <td><button class="button" onclick="deleteItem(this)">删除</button></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <button class="button right-floated" onclick="book(this)">立即预订</button>
                        <div class="clear-fix" style="margin-bottom: 10px;"></div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<%@include file="/pages/common/toaster.jsp"%>

</body>
<style>
    body {
        background-color: #f5f5f5;
    }
    .cart-container {
        background-color: #fff;
        padding: 20px;
    }
    .cart-item {
        border-bottom: 1px solid #9e9e9e;
        margin-bottom: 20px;
    }
</style>
<script>
    $(document).ready(function() {

    });

    function deleteItem(obj) {
        var id = $(obj).parents("tr").attr("productId");
        var date = $(obj).parents(".cart-item").attr("date");

        $.ajax({
            type: "POST",
            url: "/shoppingCart/delete",
            data: {
                productId: id,
                date: date
            },
            success: function(data) {
                if (data["success"] == false) {
                    toaster(data["error"], "error");
                } else {
                    toaster("删除成功~", "success");
                    $(obj).parents("tr").remove();
                }
            },
            error: function() {
                toaster("服务器出现问题，请稍微再试！", "error");
            }
        });

    }

    function book(obj) {
        var shopId = $(obj).parents(".cart-item").attr("shopId");
        var date = $(obj).parents(".cart-item").attr("date");

        $.ajax({
            type: "POST",
            url: "/product/book",
            data: {
                shopId: shopId,
                date: date
            },
            success: function(data) {
                if (data["success"] == false) {
                    toaster(data["error"], "error");
                } else {
                    toaster("预订成功~", "success");
                    setTimeout(function () {
                        window.location.href = "/user/order?id=" + data.id;
                    }, 1000);
                }
            },
            error: function() {
                toaster("服务器出现问题，请稍微再试！", "error");
            }
        });
    }
</script>
</html>

