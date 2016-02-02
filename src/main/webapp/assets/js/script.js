$(document).ready(function () {

});

function toaster(message, type) {
    var toaster = $("#toaster");
    toaster.append('<div class="toast-item"><div class="message">' + message + '</div></div>');
    var thisItem = toaster.children().last();
    if (type == "success") thisItem.addClass("success");
    else if (type == "error") thisItem.addClass("error");
    thisItem.fadeIn();
    setTimeout(function() {
        thisItem.slideUp(function() {
            thisItem.remove();
        });
    }, 2000);
}