<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>制定产品计划 - 产品计划管理 - 管理后台 - 哆哆甜品屋</title>
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
            <h3 class="title">制定产品计划</h3>
            <div class="normal-div">店面名称： ${shop.name}</div>
            <div class="normal-div">
                <div class="date-select">
                    <label for="js-start-date-input">起始日期</label>
                    <input type="text" class="normal-input date-picker"
                           id="js-start-date-input" value="${dates[0]}"/>
                </div>
                <div class="date-select">
                    <label for="js-end-date-input">结束日期</label>
                    <input type="text" class="normal-input date-picker"
                           id="js-end-date-input" value="${dates[1]}"/>
                </div>
            </div>

            <div id="date-items">
                <div class="date-item">
                    <div class="date-text">2016-03-10 星期四</div>
                    <table class="edit-plan-table">
                        <thead>
                        <tr>
                            <th width="40%">选择产品</th>
                            <th width="15%">出售价格</th>
                            <th width="15%">出售数量</th>
                            <th width="15%">获得积分</th>
                            <th width="15%">删除</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                    <button class="button btn-submit right-floated add-button">
                        <i class="fa fa-plus"></i>
                    </button>
                    <div class="clear-fix"></div>
                </div>
            </div>

            <div class="button-group right-floated">
                <button class="button btn-cancel"
                        onclick="window.location.href='/admin/plan'">返回</button>
                <button class="button btn-submit" onclick="submit()">提交</button>
            </div>
            <div class="clear-fix"></div>
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
    var products;

    $(document).ready(function() {
        validateDatePicker();
        getProductsData();
        dateInitItem();
    });
    function validateDatePicker() {
        $(".date-picker").each(function() {
            $(this).change(function () {
                var start = $("#js-start-date-input").val();
                var end = $("#js-end-date-input").val();
                if (start > end && start != "" && end != "") {
                    $("#js-start-date-input").val(end);
                    toaster("起始日期不能大于结束日期！", "error");
                } else {
                    dateInitItem();
                }
            });
        });
    }
    function getProductsData() {
        $.ajax({
            type: "POST",
            url: "/admin/plan/products",
            async: false,
            success: function(data) {
                products = data["products"];
            },
            error: function() {
                toaster("服务器出现问题，请稍微再试！", "error");
            }
        });
    }
    function dateInitItem() {
        $("#date-items").empty();

        // 生成日期
        var startDate = new Date($("#js-start-date-input").val().replace(/-/g, "/"));
        var endDate = new Date($("#js-end-date-input").val().replace(/-/g, "/"));
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
                                '<th width="40%">选择产品</th>' +
                                '<th width="15%">出售价格</th>' +
                                '<th width="15%">出售数量</th>' +
                                '<th width="15%">获得积分</th>' +
                                '<th width="15%">删除</th>' +
                            '</tr>' +
                        '</thead>' +
                        '<tbody>' + generateItem() +
                        '</tbody>' +
                    '</table>' +
                    '<button class="button btn-submit right-floated add-button" onclick="addItem(this)">' +
                        '<i class="fa fa-plus"></i>' +
                    '</button>' +
                    '<div class="clear-fix"></div>' +
                '</div>';
        }
        $("#date-items").append(html);

    }

    function generateItem() {
        var html =
                '<tr class="item">' +
                '<td>' + generateSelect() + '</td>' +
                '<td><input type="text" class="price-input"></td>' +
                '<td><input type="number" class="number-input"></td>' +
                '<td><input type="number" class="point-input"></td>' +
                '<td><i class="fa fa-close fa-delete" onclick="removeItem(this)"></i></td>' +
                '</tr>';

        function generateSelect() {
            var html = '<select class="product-select">';
            for (var i = 0; i < products.length; i++) {
                html += '<option value="' + products[i].id + '">' + products[i].name + '</option>';
            }
            html += '</select>';
            return html;
        }

        return html;
    }

    function addItem(obj) {
        $($(obj).prev().find("tbody")[0]).append(generateItem());
    }

    function removeItem(obj) {
        $(obj).parents("tr").remove();
    }

    function submit() {

        var data = new Object();
        data.startDate = $("#js-start-date-input").val();
        data.endDate = $("#js-end-date-input").val();

        var items = [];

        $("#date-items").children().each(function () {
            $(this).find(".item").each(function () {
                var item = new Object();
                item.date = $(this).parents(".date-item").attr("date");

                var productId = $($(this).find(".product-select")[0]).val();
                var price = $($(this).find(".price-input")[0]).val();
                var number = $($(this).find(".number-input")[0]).val();
                var point = $($(this).find(".point-input")[0]).val();

                if (price == "" || number == "" || point == "") {

                } else {
                    item.productId = productId;
                    item.price = price;
                    item.number = number;
                    item.point = point;

                    items.push(item);
                }


            });
        });
        data.items = items;

        $.ajax({
            type: "POST",
            url: "/admin/plan/add",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function(data) {
                if (data["success"] == false) {
                    toaster(data["error"], "error");
                } else {
                    toaster("制定成功~", "success");
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
