<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>我的缴费 - 哆哆甜品屋</title>
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
            <h3 class="title">我的缴费</h3>

            <div class="normal-div" style="margin-bottom: 20px;">目前余额：
                <span class="number" id="point-span">${customer.customerAccount.balance}</span>
                <button class="button btn-submit"
                    onclick="window.location.href='/user/recharge'">马上充值</button>
            </div>

            <h3 class="title">缴费记录</h3>
            <div class="table-container">
                <table id="js-payment-table" class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th width="50%">时间</th>
                        <th width="50%">金额</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${paymentList}" var="item">
                        <tr>
                            <td><fmt:formatDate value="${item.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>${item.money}</td>
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

