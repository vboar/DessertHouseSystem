$(document).ready(function () {
    adminLightItem();
    datePicker();
});


function toaster(message, type) {
    var toaster = $("#toaster");
    toaster.append('<div class="toast-item"><div class="message">' + message + '</div>' +
        '<i class="close fa fa-close"></i></div>');
    var thisItem = toaster.children().last();
    $(thisItem.children(".close").eq(0)).bind("click", function () {
        thisItem.slideUp(function() {
            thisItem.remove();
        });
    });
    if (type == "success") thisItem.addClass("success");
    else if (type == "error") thisItem.addClass("error");
    thisItem.fadeIn();
    setTimeout(function() {
        thisItem.slideUp(function() {
            thisItem.remove();
        });
    }, 3000);
}

function adminLightItem() {
    if ($("#admin-nav-items")) {
        var items = $("#admin-nav-items").children();
        for(var i = 0; i < items.length; i++) {
            var item = $(items[i]);
            var url = window.location.href;
            console.log(item.attr("url"));
            if (url.indexOf(item.attr("url")) != -1) {
                item.addClass("active");
                break;
            }
        }

    }
}

function datePicker() {
    try {
        $('.date-picker').each(function () {
            $(this).datetimepicker({
                lang:'ch',
                timepicker:false,
                format:'Y-m-d',
                formatDate:'Y/m/d',
                minDate: '1916/01/01',
                maxDate:'+1970/03/01',
                yearStart: 1916,
                yearEnd: 2016,
                scrollInput: false
            })
        });
    } catch (e) {
        console.log(e);
    }
}