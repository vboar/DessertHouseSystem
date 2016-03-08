<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="admin-navbar">
    <div class="content">
        <span class="navbar-title" onclick="window.location.href='/admin'">哆哆管理后台</span>
        <ul id="admin-nav-items" class="nav-items">
            <c:choose>
                <c:when test="${sessionScope.get('role') == 1}">
                    <li class="nav-item" onclick="window.location.href='/admin/shop'" url="/admin/shop">店面管理</li>
                    <li class="nav-item" onclick="window.location.href='/admin/user'" url="/admin/user">店员管理</li>
                </c:when>
                <c:when test="${sessionScope.get('role') == 2}">
                    <li class="nav-item" onclick="window.location.href='/admin/sale'">销售</li>
                    <li class="nav-item" onclick="window.location.href='#'">查询会员</li>
                </c:when>
                <c:when test="${sessionScope.get('role') == 3}">
                    <li class="nav-item" onclick="window.location.href='/admin/plan'">产品计划管理</li>
                    <li class="nav-item" onclick="window.location.href='/admin/product'">产品管理</li>
                    <li class="nav-item" onclick="window.location.href='#'">查询会员</li>
                </c:when>
                <c:when test="${sessionScope.get('role') == 4}">
                    <li class="nav-item" onclick="window.location.href='/admin/plan'">产品计划管理</li>
                    <li class="nav-item" onclick="window.location.href='#'">逐月统计</li>
                </c:when>
            </c:choose>
            <li class="nav-item" onclick="window.location.href='/admin/password'">修改密码</li>
        </ul>
        <span class="right-items">
            <i class="right-item fa fa-home" title="查看前台"></i>
            <i class="right-item fa fa-sign-out" title="登出"></i>
        </span>
    </div>
</div>