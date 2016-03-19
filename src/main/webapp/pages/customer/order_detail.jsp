<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>我的订单 - 哆哆甜品屋</title>
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
        <%@include file="../common/dashboard_left.jsp"%>
        <div class="right-content">
            <h3 class="title">我的订单&nbsp;
                <span class="status-span">
                    <c:choose>
                        <c:when test="${book.status == 0}">[已预订]</c:when>
                        <c:when test="${book.status == 1}">[已购买]</c:when>
                        <c:when test="${book.status == 2}">[已取消]</c:when>
                        <c:when test="${book.status == 3}">[已过期]</c:when>
                    </c:choose>
                </span>
            </h3>
            <div class="right-floated button-group">
                <c:if test="${item.status == 0}">
                    <button class="button" onclick="cancel(this)">取消</button>
                </c:if>
                <button class="button" onclick="window.location.href='/user/order'">返回</button>
            </div>
            <div class="normal-div">门店：${shop.name}</div>
            <div class="normal-div">预定时间：
                <fmt:formatDate value="${book.createTime}"
                pattern="yyyy-MM-dd HH:mm:ss"/>
            </div>
            <div class="normal-div">购买日期：${book.buyDate}</div>
            <div class="normal-div">折扣：${book.discount}</div>
            <div class="normal-div">原始价格：${book.originalTotal}</div>
            <div class="normal-div">折后价格：${book.actualTotal}</div>
            <div class="normal-div" style="margin-bottom: 20px;">赠送积分：${book.totalPoint}</div>


            <h3 class="title">产品列表</h3>
            <div class="table-container">
                <table id="js-table" class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th width="40%">产品</th>
                        <th width="30%">单价</th>
                        <th width="30%">数量</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${book.bookItems}" var="item">
                        <tr>
                            <td><a href="/product?id=${item.product.id}">${item.product.name}</a></td>
                            <td>${item.price}</td>
                            <td>${item.number}</td>
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
    .status-span {
        color: #d76863;
    }
    .button-group {
        position: relative;
        bottom: 35px;
    }
</style>
<script>
    $(document).ready(function() {

    });

    function cancel(obj) {
        var result = confirm("您是否真的要取消订单？");
        if (result) {
            $.ajax({
                type: "POST",
                url: "/user/order/cancel",
                data: {
                    id: $(obj).parents("tr").children("td").eq(0).text()
                },
                success: function(data) {
                    if (data["success"] == false) {
                        toaster(data["error"], "error");
                    } else {
                        toaster("取消成功~", "success");
                        setTimeout(function () {
                            window.location.href = "/user/order";
                        }, 1000);
                    }
                },
                error: function() {
                    toaster("服务器出现问题，请稍微再试！", "error");
                }
            });
        }
    }

</script>
</html>


