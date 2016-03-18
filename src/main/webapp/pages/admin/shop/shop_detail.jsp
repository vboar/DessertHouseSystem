<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>店面详情 - 店面管理 - 管理后台 - 哆哆甜品屋</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/favicon.ico">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/datatables/datatables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.4.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/plugins/datatables/datatables.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
</head>
<body class="admin-body">
<%@include file="../../common/admin_navbar.jsp"%>
<div class="wrapper">
    <div class="content">
        <div class="admin-panel">
            <h3 class="title">店面详情</h3>
            <div class="button-group btn-group-title">
                <button class="button btn-submit"
                        onclick="window.location.href='/admin/shop/edit?id=${shop.id}'">编辑</button>
                <button class="button btn-cancel" onclick="window.location.href='/admin/shop'">返回</button>
            </div>
            <div class="info-panel">
                <span>店面名称： ${shop.name}</span>
                <span>店面编号： ${shop.id}</span>
                <span>店面描述： ${shop.description}</span>
                <span>店面地址： ${shop.address}</span>
            </div>
            <h3 class="title user-list-title">店员列表
                <button class="button btn-submit right-floated"
                        onclick="window.location.href='/admin/user'">店员管理</button>
            </h3>

            <div class="table-container">
                <table id="js-user-table" class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th width="10%">编号</th>
                        <th width="25%">用户名</th>
                        <th width="25%">姓名</th>
                        <th width="30%">角色</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${shop.users}" var="item">
                        <tr class="user-item" userId="${item.id}">
                            <td>${item.id}</td>
                            <td>${item.username}</td>
                            <td>${item.name}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${item.role == 2}">分店服务员</c:when>
                                    <c:when test="${item.role == 3}">总店服务员</c:when>
                                    <c:when test="${item.role == 4}">总经理</c:when>
                                </c:choose>
                            </td>
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
    .info-panel > span {
        display: block;
        margin: 10px 0;
    }
    .btn-group-title {
        float: right;
        position: relative;
        bottom: 50px;
    }
    .user-list-title {
        margin-top: 20px !important;
    }
</style>
<script>
    $(document).ready(function() {

    });
</script>
</html>
