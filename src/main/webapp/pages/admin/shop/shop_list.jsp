<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>店面管理 - 管理后台 - 哆哆甜品屋</title>
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
            <h3 class="title">店面管理
                <button class="button btn-submit right-floated"
                        onclick="window.location.href='/admin/shop/add'">新增店面</button>
            </h3>

            <div class="table-container">
                <table id="js-shop-table" class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th width="10%">编号</th>
                        <th width="30%">名称</th>
                        <th width="25%">描述</th>
                        <th width="20%">地址</th>
                        <th width="15%">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${shopList}" var="item">
                    <tr class="shop-item" shopId="${item.id}">
                        <td>${item.id}</td>
                        <td class="table-name" title="查看店面详情">${item.name}</td>
                        <td>${item.description}</td>
                        <td>${item.address}</td>
                        <td>
                            <button class="button btn-submit" onclick="edit(this)">编辑</button>
                            <button class="button btn-cancel" onclick="deleteShop(this)">删除</button>
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
    .admin-panel {
        width: 900px;
    }
    .table-name {
        cursor: pointer;
    }
    .table-name:hover {
        text-decoration: underline;
    }
</style>
<script>
    $(document).ready(function() {
        loadTable();
    });
    function loadTable() {
        $("#js-shop-table").dataTable({
            "language": {
                url: '${pageContext.request.contextPath}/assets/plugins/datatables/Chinese.json'
            }
        });
        $(".table-name").each(function() {
            $(this).click(function() {
                var id = $(this).parents(".shop-item").attr("shopId");
                window.location.href = "/admin/shop/detail?id=" + id;
            })
        });
    }
    function edit(obj) {
        var id = $(obj).parents(".shop-item").attr("shopId");
        window.location.href = "/admin/shop/edit?id=" + id;
    }
    function deleteShop(obj) {
        $.ajax({
            type: "POST",
            url: "/admin/shop/delete",
            data: {
                id: $(obj).parents(".shop-item").attr("shopId")
            },
            success: function(data) {
                if (data["success"] == false) {
                    toaster(data["error"], "error");
                } else {
                    $(obj).parents(".shop-item").remove();
                    toaster("删除成功~", "success");
                }
            },
            error: function() {
                toaster("服务器出现问题，请稍微再试！", "error");
            }
        });
    }
</script>
</html>
