<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>会员注册 - 哆哆甜品屋</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.4.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
</head>
<body>
<div class="register">
    <div class="header">
        <div class="content">
            <a href="${pageContext.request.contextPath}/">
                <img src="${pageContext.request.contextPath}/assets/img/logo.png">
            </a>
            <span class="title">会员注册</span>
        </div>
    </div>
    <div class="content">
        <div class="register-panel">
            <div class="panel-content">
                <h3 class="title">会员注册</h3>
                <a href="${pageContext.request.contextPath}/login" class="right-floated login"><i class="fa fa-sign-in"></i> 马上登录</a>
                <div class="clearfix"></div>
                <form class="register-form">
                    <input class="input-phone" type="text" name="phone" placeholder="输入手机号" />
                    <input class="input-password" type="password" name="password" placeholder="输入密码" />
                    <input class="input-password-again" type="password" name="passwordAgain" placeholder="再次输入密码" />
                    <button type="button" class="button btn-register right-floated" onclick="registerForm()">注册</button>
                    <div class="clear-fix"></div>
                </form>
            </div>
        </div>
    </div>
</div>
<%@include file="/pages/common/toaster.jsp"%>
</body>
<style>
    body {
        overflow-y: hidden;
        background-color: #f1f1f1;
    }
</style>
<script>
    $(document).ready(function() {

    });

    function registerForm() {
        $.ajax({
            type: "POST",
            url: "/register",
            data: $('.register-form').serialize(),
            success: function(data) {
                if (data["success"] == false) {
                    toaster(data["error"], "error");
                } else {
                    toaster("注册成功！马上自动跳转...", "success");
                    setTimeout(function () {
                        window.location.href = "/supplyInfo";
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
