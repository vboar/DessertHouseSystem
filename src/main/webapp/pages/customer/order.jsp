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
            <h3 class="title">我的订单</h3>

            <div class="table-container">
                <table id="js-table" class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th width="7%">编号</th>
                        <th width="25%">下订单时间</th>
                        <th width="15%">购买日期</th>
                        <th width="13%">折后价格</th>
                        <th width="13%">订单状态</th>
                        <th width="20%">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="item">
                        <tr>
                            <td>${item.id}</td>
                            <td><fmt:formatDate value="${item.createTime}"
                                                pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>${item.buyDate}</td>
                            <td><fmt:formatNumber value="${item.actualTotal}" pattern="##.##" minFractionDigits="2"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${item.status == 0}">已预订</c:when>
                                    <c:when test="${item.status == 1}">已购买</c:when>
                                    <c:when test="${item.status == 2}">已取消</c:when>
                                    <c:when test="${item.status == 3}">已过期</c:when>
                                </c:choose>
                            </td>
                            <td>
                                <button class="button" onclick="window.location.href
                                    ='/user/order/detail?id=${item.id}'">详细</button>
                                <c:if test="${item.status == 0}">
                                    <button class="button" onclick="cancel(this)">取消</button>
                                </c:if>
                            </td>
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


