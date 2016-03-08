<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>完善个人信息 - 哆哆甜品屋</title>
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

<div class="wrapper">

    <div class="content">
        <div class="mid-panel supply-info-panel">
            <h3 class="title">完善个人信息</h3>
            <label for="js-name-input" class="normal-input-label">姓名</label>
            <input type="text" id="js-name-input" class="normal-input">
            <label for="js-birthday-input" class="normal-input-label">生日</label>
            <input type="text" id="js-birthday-input" class="normal-input date-picker">
            <div class="gender-input">
                <label class="normal-input-label">性别</label>
                <input type="radio" name="gender" value="1" checked><span>男</span>
                <input type="radio" name="gender" value="0"><span>女</span>
            </div>
            <label for="js-province-input" class="normal-input-label">省份</label>
            <input type="text" id="js-province-input" class="normal-input">
            <label for="js-city-input" class="normal-input-label">城市</label>
            <input type="text" id="js-city-input" class="normal-input">
            <label for="js-bank-input" class="normal-input-label">银行卡号</label>
            <input type="text" id="js-bank-input" class="normal-input">
            <button class="button btn-submit right-floated" onclick="submit()">提交</button>
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
                    toaster("完善信息成功！马上自动跳转...", "success");
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
