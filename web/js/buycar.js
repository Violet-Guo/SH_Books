/**
 * Created by chao on 2015/12/1.
 */
function del(inp){
    var id = inp.name;
    $.post(
        "/buycar",
        {
            delNum:id
        }, function (date) {
            if(date=="yes"){
                alert("删除成功");
            }
        }
    )
}