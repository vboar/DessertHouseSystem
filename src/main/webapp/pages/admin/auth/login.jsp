<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>欢迎登入后台系统 - 哆哆甜品屋</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.4.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
</head>
<body>
    <div class="wrapper">
        <div class="content admin-login-panel">
            <div class="panel-content">
                <h3 class="title">哆哆甜品屋后台系统</h3>
                <form class="login-form">
                    <input class="input-username" type="text" name="username" placeholder="用户名" />
                    <input class="input-password" type="password" name="password" placeholder="密码" />
                    <button type="button" class="btn btn-login right-floated" onclick="loginForm()">登录</button>
                    <span class="tips">注意：如果忘记密码, 请发送邮件至admin@duoduo.com。</span>
                    <div class="clearfix"></div>
                </form>
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

    function loginForm() {
        $.ajax({
            type: "POST",
            url: "/admin/login",
            data: $('.login-form').serialize(),
            success: function(data) {
                if (data["success"] == false) {
                    toaster(data["error"], "error");
                    $(".input-password").val("");
                } else {
                    toaster("登录成功！马上自动跳转...", "success");
                    setTimeout(function () {
                        window.location.href = "/admin";
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
