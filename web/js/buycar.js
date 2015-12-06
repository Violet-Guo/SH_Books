/**
 * Created by chao on 2015/12/1.
 */
$(document).ready(function () {
    $._messengerDefaults = {
        extraClasses: 'messenger-fixed messenger-theme-flat messenger-on-top'
    };
});
function del(inp){
    if(!confirm("确认删除？")){
        return;
    }
    var ul = $(inp).parent().parent();
    var div = ul.parent();
    var length = div.children(".goods-ul").length;
    var id = inp.name;
    var price = ul.children(".goods-price").text();
    var allPriceSpan = $(".tol-price").children("span");
    var allPrice = allPriceSpan.text();
    var num = $(".all-num").text();

    $.post(
        "/buycar",
        {
            delNum:id
        }, function (date) {
            if(date=="yes"){
                if(length==1){
                    ani(div);
                }else{
                    ani(ul);
                }
            }
        }
    );
    function ani(ele){
        ele.animate({"left":"-1200"},800,function(){
            ele.animate({"height":"0"},500, function () {
                ele.remove();
                var p = allPrice - price;
                allPriceSpan.text(p.toFixed(1));
                $(".all-num").text(num-1);
                if((num-1)==0){
                    $("#total").replaceWith("<div class='is-empty-info'>您的购物车空空如也，赶快去买本书吧</div>");
                }
            })
        });
    }
}
function judgeOver(inp){
    var num = $(inp).attr("big");
    var nowNum = $(inp).val();
    if(nowNum>num){
        alert("只有"+num+"本");
        $(inp).val(num);
    }
}