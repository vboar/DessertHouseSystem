<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>销售 - 管理后台 - 哆哆甜品屋</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/datatables/datatables.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <script src="${pageContext.request.contextPath}/assets/js/jquery-2.1.4.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
</head>
<body class="admin-body">

<%@include file="../../common/admin_navbar.jsp"%>
<div class="wrapper">
    <div class="content">
        <div class="admin-panel">
            <h3 class="title">销售</h3>
            <div>
                <div class="normal-div">门店： ${shop.name}</div>
            </div>
            <div style="margin: 20px 0 15px 0;">
                <a href="/admin/sale/order">已预订销售</a>
                <a href="/admin/sale/buy">未预订销售</a>
            </div>
            <div style="margin-bottom: 20px;">
                <label for="js-code-input" class="normal-input-label">请输入会员卡号</label>
                <input type="text" class="normal-input" id="js-code-input" style="width: 250px;"/>
                <button class="button btn-submit" onclick="getBook()">确定</button>
            </div>

            <div id="item-container"></div>


        </div>
    </div>
</div>
<%@include file="/pages/common/toaster.jsp"%>
</body>

<style>
    .book-item {
        margin-bottom: 20px;
        border-bottom: 1px solid #b6b6b6;
        padding-bottom: 10px;
    }
</style>
<script>
    $(document).ready(function() {

    });

    function getBook() {
        $.ajax({
            type: "POST",
            url: "/admin/sale/getBooks",
            data: {
                code: $("#js-code-input").val(),
            },
            success: function(data) {
                var bookList = data.bookList;
                if (bookList.length == 0) {
                    toaster("没有找到今天的订单！", "error");
                } else {
                    loadData(bookList);
                }
            },
            error: function() {
                toaster("服务器出现问题，请稍微再试！", "error");
            }
        });
    }

    function loadData(bookList) {
        $("#item-container").empty();
        for (var i = 0; i < bookList.length; i++) {
            var book = bookList[i];
            var html = "";
            html += '<div class="book-item" bookId="' + book.id + '">' +
                    '<div class="normal-div">订单编号： ' + book.id + '</div>' +
                    '<div class="table-container">' +
                    '<table id="js-table" class="table table-striped table-bordered">' +
                    '<thead>' +
                    '<tr>'+
                    '<th width="40%">产品</th>' +
                    '<th width="30%">单价</th>' +
                    '<th width="30%">数量</th>' +
                    '</tr>' +
                    '</thead>' +
                    '<tbody>';
            for (var j = 0; j < book.bookItems.length; j++) {
                var item = book.bookItems[j];
                html += '<tr>' +
                        '<td>' + item.product.name + '</td>' +
                        '<td>' + item.price + '</td>' +
                        '<td>' + item.number + '</td>' +
                        '</tr>';
            }
            html += '</tbody>' +
                    '</table>' +
                    '<div class="left-floated" style="width: 250px">' +
                    '<div class="normal-div">原始价格：' + book.originalTotal + '</div>' +
                    '<div class="normal-div">折扣：' + book.discount + '</div>' +
                    '<div class="normal-div">折后价格：' + book.actualTotal.toFixed(2) + '</div>' +
                    '<div class="normal-div">赠送积分：' + book.totalPoint + '</div>' +
                    '</div>' +
                    '<div class="left-floated">' +
                    '<button class="button btn-submit" style="margin-right: 15px;"' +
                    ' onclick="pay(this, 1)">卡余额支付</button>' +
                    '<button class="button btn-cancel"' +
                    ' onclick="pay(this, 2)">现金支付</button>' +
                    '</div>' +
                    '<div class="clear-fix"></div>' +
                    '</div>' +
                    '</div>';
            $("#item-container").append(html);
        }
    }

    function pay(obj, type) {
        $.ajax({
            type: "POST",
            url: "/admin/sale/payForBook",
            data: {
                id: $(obj).parents(".book-item").attr("bookId"),
                type: type
            },
            success: function(data) {
                if (data["success"] == false) {
                    toaster(data["error"], "error");
                } else {
                    toaster("支付成功~", "success");
                    setTimeout(function () {
                        window.location.href = "/admin/sale/order";
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
