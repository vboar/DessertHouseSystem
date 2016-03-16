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
            <h3 class="title">产品计划管理</h3>

            <div class="normal-div" id="select-div">
                <span id="non-check" class="active">未审批</span>
                <span id="check" >已审批</span>
            </div>
            <div class="table-container">
                <table id="js-plan-table" class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th width="30%">店面</th>
                        <th width="15%">起始日期</th>
                        <th width="15%">结束日期</th>
                        <th width="10%">状态</th>
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
    .admin-panel {
        width: 900px;
    }
    table .button {
        margin-right: 5px;
    }
    #select-div span {
        cursor: pointer;
    }
    #select-div span.active {
        color: #d90000;
    }
</style>
<script>
    var shops;
    $(document).ready(function() {
        getAllShops();
        getPlanList(0);
        selectDivInit();
    });
    function selectDivInit() {
        $("#non-check").click(function () {
            $("#check").removeClass("active");
            $(this).addClass("active");
            getPlanList(0);
        });
        $("#check").click(function () {
            $("#non-check").removeClass("active");
            $(this).addClass("active");
            getPlanList(1);
        });
    };

    function getPlanList(status) {
        $.ajax({
            type: "POST",
            url: "/admin/plan/listByStatus",
            data: {
                status: status
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
                    '<td>' + getShopName(plan.shopId) + '</td>' +
                    '<td>' + start + '</td>' +
                    '<td>' + end + '</td>' +
                    '<td>' + planStatusTranslate(plan.status) + '</td>' +
                    '<td>' +
                    '<button class="button btn-submit" ' +
                    'onclick="window.location.href=\'/admin/plan/detail?id=' + plan.id + '\'">详细</button>';

            if (plan.status == 0) {
                html += '<button class="button btn-submit" ' +
                        'onclick="updateStatus(' + plan.id + ', 1)">批准</button>';
                html += '<button class="button btn-cancel" ' +
                        'onclick="updateStatus(' + plan.id + ', 2)">不批准</button>';
            }
        }
        $($("#js-plan-table").find("tbody")[0]).empty().append(html);
    }

    function updateStatus(id, status) {
        $.ajax({
            type: "POST",
            url: "/admin/plan/status",
            data: {
                id: id,
                status: status
            },
            async: false,
            success: function(data) {
                if (data["success"] == false) {
                    toaster(data["error"], "error");
                } else {
                    toaster("处理成功~", "success");
                    setTimeout(function () {
                        window.location.href = "/admin/plan";
                    }, 1000);
                }
            },
            error: function() {
                toaster("服务器出现问题，请稍微再试！", "error");
            }
        });
    }
    function getAllShops() {
        $.ajax({
            type: "POST",
            url: "/admin/shop/list",
            async: false,
            success: function(data) {
                shops = data["shopList"];
            },
            error: function() {
                toaster("服务器出现问题，请稍微再试！", "error");
            }
        });
    }
    function getShopName(id) {
        for (var i = 0; i < shops.length; i++) {
            if (id == shops[i].id) return shops[i].name;
        }
    }
</script>
</html>
