/**
 * Created by chao on 2015/11/5.
 */

//------------------------------计算顶部导航栏的长度和里面的li的长度
$(document).ready(function(){
    setTopPop();
});

function setTopPop(){
    var width = $("#major-all").width()-10;
    var num = $("#major-all li").length;
    $("#major-all li").first().css("border-left-width","5px");
    $("#major-all li").last().css("border-right-width","5px");
    $("#major-all li").width(width/num);
    $("#major-all li").each(function(index){
        if(index==0||index==$("#major-all li").length-1){
            $(this).mouseover(function() {
                var wid = $(this).css("width");
                wid = parseFloat(wid);
                $(this).width(wid - 5);
                $(this).height($(this).height() + 5);
            });
            $(this).mouseout(function() {
                var wid = $(this).css("width");
                wid = parseFloat(wid);
                $(this).width(wid + 5);
                $(this).height($(this).height() - 5);
            })
        }else{
            $(this).mouseover(function() {
                var wid = $(this).css("width");
                wid = parseFloat(wid);
                $(this).width(wid - 10);
                $(this).height($(this).height() + 5);
            });
            $(this).mouseout(function() {
                var wid = $(this).css("width");
                wid = parseFloat(wid);
                $(this).width(wid + 10);
                $(this).height($(this).height() - 5);
            })
        }
    });
}

$(".hotBookDiv ul").ready(function(){
    var width = $(".hotBookDiv ul").width();
    $(".hotBookDiv ul li").width(width/5);
    $(".hotBook-title,.hotBook-title p").width(width/5-20);
    $(".hotBookDiv ul li").mouseenter(function(){
        $(this).children(".hotBook-title").animate({bottom:0},"slow");
    });
    $(".hotBookDiv ul li").mouseleave(function(){
        $(this).children(".hotBook-title").animate({bottom:-100},"slow");
    });
});