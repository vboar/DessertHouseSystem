<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>会员登录 - 哆哆甜品屋</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.4.min.js"></script>
</head>
<body>
    <div class="login">
        <div class="header">
            <div class="content">
                <a href="${pageContext.request.contextPath}/">
                    <img src="${pageContext.request.contextPath}/assets/img/logo.png">
                </a>
                <span class="title">欢迎登录</span>
            </div>
        </div>
        <div class="content">
            <div class="login-panel">
                <div class="panel-content">
                    <h3 class="title">会员登录</h3>
                    <a href="${pageContext.request.contextPath}/register" class="right-floated register"><i class="fa fa-sign-in"></i> 立即注册</a>
                    <div class="clearfix"></div>
                    <form class="login-form">
                        <input class="input-login" type="text" name="login" placeholder="卡号/手机号" />
                        <input class="input-password" type="password" name="password" placeholder="密码" />
                        <button class="btn btn-login right-floated">登录</button>
                        <a class="forget-password" href="#">忘记密码？</a>
                        <div class="clearfix"></div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
<style>
    body {
        overflow-y: hidden;
        background-color: #f1f1f1;
    }
</style>
</html>
