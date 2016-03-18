<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>产品计划管理 - 管理后台 - 哆哆甜品屋</title>
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
            <h3 class="title">产品计划管理
                <button class="button btn-submit right-floated"
                        onclick="window.location.href='/admin/plan/add'">制定计划</button>
            </h3>

            <div class="normal-div">店面：${shop.name}</div>
            <div class="table-container">
                <table id="js-plan-table" class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th width="25%">起始日期</th>
                        <th width="25%">结束日期</th>
                        <th width="20%">状态</th>
                        <th width="30%">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%@include file="/pages/common/toaster.jsp"%>
</body>

<style>
    table .button {
        margin-right: 5px;
    }
</style>
<script>
    $(document).ready(function() {
        getPlanList();
    });
    function getPlanList() {
        $.ajax({
            type: "POST",
            url: "/admin/plan/listByShop",
            data: {
                id: ${shop.id}
            },
            async: false,
            success: function(data) {
                setList(data.planList);
            },
            error: function() {
                toaster("服务器出现问题，请稍微再试！", "error");
            }
        });
    }

    function setList(planList) {
        var html = "";
        for (var i = 0; i < planList.length; i++) {
            var plan = planList[i];
            var start = new Date(plan.startTime).Format("yyyy-MM-dd");
            var end = new Date(plan.endTime).Format("yyyy-MM-dd");
            html += '<tr class="plan-item" planId="' + plan.id + '">' +
                    '<td>' + start + '</td>' +
                    '<td>' + end + '</td>' +
                    '<td>' + planStatusTranslate(plan.status) + '</td>' +
                    '<td>' +
                    '<button class="button btn-submit" ' +
                    'onclick="window.location.href=\'/admin/plan/detail?id=' + plan.id + '\'">详细</button>';

            if (plan.status != 1) {
                html += '<button class="button btn-cancel" ' +
                        'onclick="window.location.href=\'/admin/plan/edit?id=' + plan.id + '\'">编辑</button>';
            }
        }
        $($("#js-plan-table").find("tbody")[0]).append(html);
    }
</script>
</html>
