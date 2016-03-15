<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>计划详情 - 产品计划管理 - 管理后台 - 哆哆甜品屋</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/datetimepicker/css/jquery.datetimepicker.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.4.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/plugins/datetimepicker/js/jquery.datetimepicker.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
</head>
<body class="admin-body">

<%@include file="../../common/admin_navbar.jsp"%>
<div class="wrapper">
    <div class="content">
        <div class="admin-panel">
            <h3 class="title">计划详情</h3>
            <div class="normal-div">店面名称： ${shop.name}</div>
            <div class="normal-div">起始日期： <span id="start-date-span"></span></div>
            <div class="normal-div">结束日期： <span id="end-date-span"></span></div>

        </div>
    </div>
</div>
<%@include file="/pages/common/toaster.jsp"%>
</body>

<style>
    .admin-panel {
        width: 900px;
    }
    .date-select {
        display: inline-block;
        margin-right: 100px;
    }
    .date-item {
        margin-top: 25px;
    }
    .date-item > .date-text {
        font-size: 17px;
        margin-bottom: 10px;
    }
    .date-item .fa-delete {
        cursor: pointer;
        color: #757575;
    }
</style>
<script>
    var plan;
    var startTime;
    var endTime;

    $(document).ready(function() {
        getPlanData();
        initPlan();
    });

    function initPlan() {
        console.log(plan);
        startTime = new Date(plan.startTime).Format('yyyy-MM-dd');
        endTime = new Date(plan.endTime).Format('yyyy-MM-dd');
        $("#start-date-span").text(startTime);
        $("#end-date-span").text(endTime);
    }

    function getPlanData() {
        $.ajax({
            type: "POST",
            url: "/admin/plan/detail",
            data: {
              id: ${plan.id}
            },
            async: false,
            success: function(data) {
                plan = data["plan"];
            },
            error: function() {
                toaster("服务器出现问题，请稍微再试！", "error");
            }
        });
    }

</script>
</html>
