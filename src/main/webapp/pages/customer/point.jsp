<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>我的积分 - 哆哆甜品屋</title>
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
            <h3 class="title">我的积分</h3>

            <div class="normal-div">目前积分：
                <span class="number" id="point-span">${customer.customerAccount.point}</span></div>

            <div class="normal-div">积分兑换：
                <input type="number" id="point-number-input">
                <button class="button btn-submit" onclick="exchange()">兑换</button>
            </div>

            <div class="normal-div" style="margin-bottom: 20px;">
                注：100积分能够兑换1元余额。每次至少兑换100积分。
            </div>

            <h3 class="title">积分记录</h3>
            <div class="table-container">
                <table id="js-plan-table" class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th width="30%">时间</th>
                        <th width="20%">类型</th>
                        <th width="20%">积分</th>
                        <th width="30%">备注</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pointList}" var="item">
                        <tr>
                            <td><fmt:formatDate value="${item.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                                <c:if test="${item.type == 1}">积分兑换</c:if>
                                <c:if test="${item.type == 0}">消费获得</c:if>
                            </td>
                            <td>
                                <c:if test="${item.type == 1}">-</c:if>
                                <c:if test="${item.type == 0}">+</c:if>
                                ${item.point}
                            </td>
                            <td><c:if test="${item.type == 0}">消费单：${item.consumption.id}</c:if></td>
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
    .normal-div > .number {
        color: #d76863;
    }
    #point-number-input {
        outline: none;
    }
</style>
<script>
    $(document).ready(function() {

    });
    function exchange() {
        $.ajax({
            type: "POST",
            url: "/user/point/exchange",
            data: {
                point: $("#point-number-input").val()
            },
            success: function(data) {
                if (data["success"] == false) {
                    toaster(data["error"], "error");
                } else {
                    toaster("兑换成功~", "success");
                    setTimeout(function () {
                        window.location.href = "/user/point";
                    }, 1000);
                }
                $("#point-number-input").val("");
                $("#point-span").text(data.point);
            },
            error: function() {
                toaster("服务器出现问题，请稍微再试！", "error");
            }
        });
    }
</script>
</html>

