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
            } else if(date == "happen") {
                if(confirm("这本书已经在您的购物车里了,是否去看?")){
                    window.location.href="/buycar";
                }
            }else if(date=="overnum"){
                alert("您添加的书的数量和你购物车里此书的数量总数大于此书的数量,请重新选择数量");
            }else {
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



