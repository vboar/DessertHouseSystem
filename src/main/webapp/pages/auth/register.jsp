<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>会员注册 - 哆哆甜品屋</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.4.min.js"></script>
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
                    <button class="btn btn-register right-floated">注册</button>
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
