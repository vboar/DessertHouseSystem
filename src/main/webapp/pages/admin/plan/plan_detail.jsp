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
            <h3 class="title">计划详情 <span id="status-span"></span></h3>
            <div class="button-group btn-group-title">
                <c:choose>
                    <c:when test="${sessionScope.get('role') == 4 && plan.status == 0}">
                        <button class="button btn-submit" onclick="updateStatus(1)">批准</button>
                        <button class="button btn-cancel" onclick="updateStatus(2)">不批准</button>

                    </c:when>
                    <c:when test="${sessionScope.get('role') == 3 && plan.status != 1}">
                        <button class="button btn-submit"
                                onclick="window.location.href='/admin/plan/edit?id=${plan.id}'">编辑</button>
                    </c:when>
                </c:choose>
                <button class="button btn-cancel" onclick="window.location.href='/admin/plan'">返回</button>
            </div>
            <div class="normal-div">店面名称： ${shop.name}</div>
            <div class="normal-div">起始日期： <span id="start-date-span"></span></div>
            <div class="normal-div">结束日期： <span id="end-date-span"></span></div>

            <div id="date-items"></div>

        </div>
    </div>
</div>
<%@include file="/pages/common/toaster.jsp"%>
</body>

<style>
    .admin-panel {
        width: 900px;
    }
    .btn-group-title {
        float: right;
        position: relative;
        bottom: 50px;
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
    #status-span {
        font-size: 16px;
        color: #d90000;
    }
</style>
<script>
    var plan;
    var startTime;
    var endTime;

    $(document).ready(function() {
        getPlanData();
        initPlan();
        dateInitItem();
        planItemInit();
    });

    function initPlan() {
        startTime = new Date(plan.startTime).Format('yyyy-MM-dd');
        endTime = new Date(plan.endTime).Format('yyyy-MM-dd');
        $("#start-date-span").text(startTime);
        $("#end-date-span").text(endTime);
        $("#status-span").text(planStatusTranslate(plan.status));
    }

    function dateInitItem() {
        $("#date-items").empty();

        // 生成日期
        var startDate = new Date(startTime.replace(/-/g, "/"));
        var endDate = new Date(endTime.replace(/-/g, "/"));
        var dates = new Array();
        dates.push(startDate);
        var tempDate = new Date(startDate.valueOf()+ 1*24*60*60*1000);
        while (tempDate.getTime() < endDate.getTime()) {
            dates.push(new Date(tempDate));
            tempDate = new Date(tempDate.valueOf()+ 1*24*60*60*1000);
        }
        if (startDate.getDate() != endDate.getDate()) {
            dates.push(endDate);
        }

        var html = "";
        for (var i = 0; i < dates.length; i++) {
            var d = dates[i];
            html +=
                    '<div class="date-item" date="' + d.Format("yyyy-MM-dd") + '">' +
                    '<div class="date-text">' + (d.getMonth()+1) + '月' +
                    d.getDate() + '日' + ' 星期' + getWeek(d.getDay()) + '</div>' +
                    '<table class="edit-plan-table">' +
                    '<thead>' +
                    '<tr>' +
                    '<th width="40%">产品</th>' +
                    '<th width="20%">出售价格</th>' +
                    '<th width="20%">出售数量</th>' +
                    '<th width="20%">获得积分</th>' +
                    '</tr>' +
                    '</thead>' +
                    '<tbody>' +
                    '</tbody>' +
                    '</table>' +
                    '</div>';
        }
        $("#date-items").append(html);

    }

    function planItemInit() {
        var planItems = plan.planItems;
        var dateItems = $(".date-item");
        for (var i = 0; i < planItems.length; i++) {
            var date = planItems[i].date;
            var item = planItems[i];
            dateItems.each(function () {
                if ($(this).attr("date") == date) {
                    $($(this).find("tbody")[0]).append(
                            generateItem(item.product.name, item.price, item.number, item.point)
                    );
                }
            });
        }
    }

    function generateItem(productName, price, num, point) {
        var html =
                '<tr class="item">' +
                '<td>' + productName + '</td>' +
                '<td>' + price + '</td>' +
                '<td>' + num + '</td>' +
                '<td>' + point + '</td>' +
                '</tr>';
        return html;
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

    function updateStatus(status) {
        $.ajax({
            type: "POST",
            url: "/admin/plan/status",
            data: {
                id: ${plan.id},
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

</script>
</html>
