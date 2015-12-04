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
    price = price.substr(1,price.length);
    var allPriceSpan = $(".tol-price").children("span");
    var allPrice = allPriceSpan.text();
    var num = $(".all-num").text();

    alert(allPrice);
    alert(price);
    alert(num);
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
                allPriceSpan.text(allPrice-price);
                $(".all-num").text(num-1);
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