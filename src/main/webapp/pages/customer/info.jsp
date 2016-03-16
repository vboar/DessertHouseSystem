<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的信息 - 哆哆甜品屋</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/datetimepicker/css/jquery.datetimepicker.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.4.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/plugins/datetimepicker/js/jquery.datetimepicker.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
</head>
<body>
<%@include file="../common/navbar.jsp"%>
<%@include file="../common/dashboard_header.jsp"%>
<div class="wrapper">
    <div class="content">
        <%@include file="../common/dashboard_left.jsp"%>
        <div class="right-content">
            <h3 class="title">我的信息</h3>

            <div style="width: 300px">
                <div class="normal-div">我的会员卡号： <span class="number">${customer.code}</span></div>
                <div class="normal-div">我的手机号： <span class="number">${customer.phone}</span></div>

                <label for="js-name-input" class="normal-input-label">姓名</label>
                <input type="text" id="js-name-input" class="normal-input" value="${customer.customerInfo.name}">
                <label for="js-birthday-input" class="normal-input-label">生日</label>
                <input type="text" id="js-birthday-input" class="normal-input date-picker"
                        value="${customer.customerInfo.birthday}">
                <div class="gender-input">
                    <label class="normal-input-label">性别</label>
                    <c:choose>
                        <c:when test="${customer.customerInfo.gender == 1}">
                            <input type="radio" name="gender" value="1" checked><span>男</span>
                            <input type="radio" name="gender" value="0"><span>女</span>
                        </c:when>
                        <c:when test="${customer.customerInfo.gender == 0}">
                            <input type="radio" name="gender" value="1"><span>男</span>
                            <input type="radio" name="gender" value="0" checked><span>女</span>
                        </c:when>
                    </c:choose>
                </div>
                <label for="js-province-input" class="normal-input-label">省份</label>
                <input type="text" id="js-province-input" class="normal-input"
                        value="${customer.customerInfo.province}">
                <label for="js-city-input" class="normal-input-label">城市</label>
                <input type="text" id="js-city-input" class="normal-input"
                       value="${customer.customerInfo.city}">
                <label for="js-bank-input" class="normal-input-label">银行卡号</label>
                <input type="text" id="js-bank-input" class="normal-input"
                       value="${customer.customerAccount.bankId}">
            </div>
            <button class="button btn-normal right-floated" onclick="submit()">修改</button>
            <div class="clear-fix"></div>
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
    function submit() {
        $.ajax({
            type: "POST",
            url: "/supplyInfo",
            data: {
                name: $("#js-name-input").val(),
                birthday: $("#js-birthday-input").val(),
                gender: $("input[name='gender']:checked").val(),
                province: $("#js-province-input").val(),
                city: $("#js-city-input").val(),
                bank: $("#js-bank-input").val()
            },
            success: function(data) {
                if (data["success"] == false) {
                    toaster(data["error"], "error");
                } else {
                    toaster("修改成功~", "success");
                }
            },
            error: function() {
                toaster("服务器出现问题，请稍微再试！", "error");
            }
        });
    }
</script>
</html>

