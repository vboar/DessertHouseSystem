<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>我的消费 - 哆哆甜品屋</title>
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
            <h3 class="title">我的消费</h3>

            <div class="table-container">
                <table id="js-table" class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th width="15%">订单</th>
                        <th width="35%">创建时间</th>
                        <th width="20%">支付方式</th>
                        <th width="15%">消费金额</th>
                        <th width="15%">赠送积分</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${consumptionList}" var="item">
                        <tr>
                            <td><a href="/user/order/detail?id=${item.book.id}">
                                ${item.book.id}</a></td>
                            <td><fmt:formatDate value="${item.time}"
                                                pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                                <c:if test="${item.payType == 1}">卡余额</c:if>
                                <c:if test="${item.payType == 2}">现金</c:if>
                            </td>
                            <td>
                                <fmt:formatNumber value="${item.money}"
                                                  pattern="##.##" minFractionDigits="2"/>
                            </td>
                            <td>${item.point}</td>
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
</style>
<script>
    $(document).ready(function() {

    });
</script>
</html>

