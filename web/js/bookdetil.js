/**
 * Created by violet on 2015/12/7.
 */
$(document).ready(function () {

    $(".addbuycar").click(function () {
        var bookID = $("#bookID").val();
        var bookNum = $("#bookNum").val();
        $.post("/addbuycar", {
            bookID: bookID,
            bookNum: bookNum
        }, function (date) {
            if (date == "yes") {
                if (confirm("添加到购物车成功,去结算？")) {
                    window.location.href = '/buycar';
                }
            } else if (date == "login") {
                if(confirm("您还没有登陆,是否登陆")){
                    window.location.href="/login";
                }
            } else {
                alert("错误，请重试，或者重新登陆");
            }
        })
    })
});

function change() {
    var input = $("#bookNum");
    var big = input.attr("big");
    var now = input.val();
    if (now > big) {
        alert("只有" + big + "本");
        input.val(big);
    }
    if (now <= 0) {
        input.val(1);
    }
}



