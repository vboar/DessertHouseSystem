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
                           id="js-start-date-input"/>
                </div>
                <div class="date-select">
                    <label for="js-end-date-input">结束日期</label>
                    <input type="text" class="normal-input date-picker"
                           id="js-end-date-input"/>
                </div>
            </div>

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
                    <tr>
                        <td>
                            <select class="product-select">
                                <option>1</option>
                                <option>1</option>
                                <option>1</option>
                            </select>
                        </td>
                        <td><input type="text"></td>
                        <td><input type="number"></td>
                        <td><input type="number"></td>
                        <td><i class="fa fa-close fa-delete"></i></td>
                    </tr>
                    </tbody>
                </table>
                <button class="button btn-submit right-floated add-button">
                    <i class="fa fa-plus"></i>
                </button>
                <div class="clear-fix"></div>
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
    .date-item > .date-text::before {
        content: "- ";
    }
    .date-item .fa-delete {
        cursor: pointer;
        color: #757575;
    }
</style>
<script>
    $(document).ready(function() {
        validateDatePicker();
    });
    function validateDatePicker() {
        $(".date-picker").each(function() {
            $(this).change(function () {
                var start = $("#js-start-date-input").val();
                var end = $("#js-end-date-input").val();
                if (start > end && start != "" && end != "") {
                    $("#js-start-date-input").val(end);
                    toaster("起始日期不能大于结束日期！", "error");
                }
            });
        });
    }
    function submit() {

        $.ajax({
            type: "POST",
            url: "/admin/plan/add",
            data: data,
            processData: false,
            contentType: false,
            success: function(data) {
                if (data["success"] == false) {
                    toaster(data["error"], "error");
                } else {
                    toaster("新增成功~", "success");
                    setTimeout(function () {
                        window.location.href = "/admin/product";
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
