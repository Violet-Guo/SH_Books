/**
 * Created by chao on 2015/12/1.
 */
function del(inp){
    if(!confirm("确认删除？")){
        return;
    }
    var id = inp.name;
    $.post(
        "/buycar",
        {
            delNum:id
        }, function (date) {
            if(date=="yes"){
                alert("删除成功");
                window.location.reload();
            }
        }
    )
}
function judgeOver(inp){
    var num = $(inp).attr("big");
    var nowNum = $(inp).val();
    if(nowNum>num){
        alert("只有"+num+"本");
        $(inp).val(num);
    }
}