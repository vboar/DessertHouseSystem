<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header class="navbar">
    <div class="content">
        <div class="left-floated left-items">
            选择门店： 南京新街口旗舰店
        </div>
        <ul class="right-floated right-items">
            <li>
                <a href="${pageContext.request.contextPath}/login" class="login">你好, 请登录</a>&nbsp;&nbsp
                <a href="${pageContext.request.contextPath}/register" class="register">免费注册</a>
            </li>
            <li class="spacer"></li>
            <li><a href="${pageContext.request.contextPath}/shopping-cart"><i class="fa fa-shopping-cart"></i> 购物车</a></li>
            <li class="spacer"></li>
            <li><a href="#">我的订单</a></li>
            <li class="spacer"></li>
            <li><a href="#">我的哆哆</a></li>
            <li class="spacer"></li>
            <li><a href="#">客户服务</a></li>
            <li class="spacer"></li>
            <li><a href="${pageContext.request.contextPath}/admin/login">后台登入</a></li>
        </ul>
        <div class="clearfix"></div>
    </div>
</header>
