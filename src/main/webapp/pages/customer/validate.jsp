<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>完善个人信息 - 哆哆甜品屋</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.4.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
</head>
<body>
<%@include file="../common/navbar.jsp"%>

<div class="wrapper">

    <div class="content">
        <div class="mid-panel validate-panel">
            <h3 class="title">激活会员资格</h3>
            <p class="tips">注意：请一次性充值200元以上以激活会员资格。金额将从您的银行卡中扣取。</p>
            <div class="code-div">您的会员卡号： <span class="number">${customer.code}</span></div>
            <div>您的银行卡号： <span class="number">${customer.customerAccount.bankId}</span></div>
            <label for="js-money-input" class="normal-input-label">充值金额（>=200元）：</label>
            <input type="text" id="js-money-input" class="normal-input">
            <button class="btn btn-submit right-floated" onclick="submit()">提交</button>
            <div class="clearfix"></div>
        </div>
    </div>

</div>

<%@include file="/pages/common/toaster.jsp"%>

</body>
<style>
    body {
        background-color: #f5f5f5;
    }
    .wrapper > .content {
        width:450px;
        background-color: #fff;
    }
</style>
<script>
    $(document).ready(function() {

    });

    function submit() {
        $.ajax({
            type: "POST",
            url: "/validate",
            data: {

            },
            success: function(data) {
                if (data["success"] == false) {
                    toaster(data["error"], "error");
                } else {
                    toaster("会员激活成功！马上跳转到首页...", "success");
                    setTimeout(function () {
                        window.location.href = "/validate";
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

