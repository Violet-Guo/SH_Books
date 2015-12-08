/**
 * Created by violet on 2015/12/7.
 */
$(document).ready(function(){
    $(".quick").click(function(){
        var bookID = $(this).attr("bookID");
        var bookNum = $(".bookNum").val();
        $.post("/addorder",{
            bookID:bookID,
            bookNum:bookNum,
            isQuick:yes
        },function(date){
            if(date==yes){
                alert("下单成功");
            }
        })
    });

    $(".addbuycar").click(function(){
        var bookID = $(this).attr("bookID");
        var bookNum = $(".bookNum").val();
        $.post("/addbuycar",{
            bookID:bookID,
            bookNum:bookNum
        },function(date){
            if(date==yes){
                alert("添加到购物车成功");
            }
        })
    })
});

function change() {
    var input = $("#bookNum");
    var big = input.attr("big");
    var now = input.val();
    if(now > big)
    {
        alert("只有"+ big +"本");
        input.val(big);
    }
    if(now <= 0){
        input.val(1);
    }
}



