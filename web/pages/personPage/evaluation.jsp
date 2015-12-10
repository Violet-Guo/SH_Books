<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.book.buy.utils.Paging" %>
<%--
  Created by IntelliJ IDEA.
  User: chao
  Date: 2015/12/5
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>评价</title>
    <link type="text/css" rel="stylesheet" href="../../css/all.css">
    <link type="text/css" rel="stylesheet" href="../../css/buycar.css">
    <link type="text/css" rel="stylesheet" href="../../css/order.css">
    <link type="text/css" rel="stylesheet" href="../../css/evaluation.css">
    <script src="../../js/jquery.min.js"></script>
</head>
<body>
<jsp:include page="../mainPage/head.jsp"/>
<div id="main">
    <ul class="order-head">
        <li>
            <h2>全部订单</h2>
        </li>
    </ul>
    <hr/>
    <!--购物车单的标题，表头-->
    <ul class="goods-ul goods-head">
        <li class="goods-image">图片</li>
        <li class="goods-descript">描述</li>
        <li class="goods-price">价格</li>
        <li class="goods-num">数量</li>
        <li class="goods-action">操作</li>
        <li class="goods-state">状态</li>
    </ul>
    <div id="forReplace">
        <c:forEach items="${buyVos}" var="buyVo" varStatus="status">
            <c:set value="${status.count-1}" var="statusStr"/>
            <c:set value="${orderFormUserMap[statusStr]}" var="orderFormUsers" scope="page"/>
            <c:set value="${orderFormUsers[0]}" var="seller" scope="page"/>
            <div class="goods-all">
                <ul class="order-head main-head">
                    <c:choose>
                        <c:when test="${!seller.name.equals(user.name)}">
                            <li class="user-state buy">买</li>
                        </c:when>
                        <c:otherwise>
                            <li class="user-state sell">卖</li>
                        </c:otherwise>
                    </c:choose>
                    <li>时间：${buyVo.time}</li>
                    <li>订单号:${buyVo.orderID}</li>
                    <li>您的评价对别人很重要！</li>
                    <c:if test="${!state.equals('waiteva')}">
                        <li class="goods-sure">
                            <span>请评价</span>
                        </li>
                    </c:if>
                    <li class="all-price">总价：<span>${orderPriceList.get(status.count-1)}</span></li>
                </ul>
                    <%--@import这里获取不到值--%>
                <c:set value="${orderFormVoMap[statusStr]}" var="orderFormVos" scope="page"/>
                <c:set value="${orderFormBookMap[statusStr]}" var="orderFormBooks" scope="page"/>

                <c:forEach items="${orderFormVos}" var="orderFormVo" varStatus="inStatus">
                    <ul class="order-head sell-head">
                        <li>卖家:${orderFormUsers[inStatus.count-1].username}</li>
                        <li>请评价：</li>
                        <li class="content">
                            <input type="text" name="content" placeholder="填写对‘${orderFormUsers[inStatus.count-1].username}’的评价">
                        </li>
                        <input type="hidden" name="orderID" value="${buyVo.orderID}"/>
                        <li class="button" user="${orderFormUsers[inStatus.count-1].id}" onclick="subeva(this)">提交</li>
                    </ul>
                    <ul class="goods-ul">
                        <c:set value="${orderFormBooks[inStatus.count-1]}" var="book" scope="page"/>
                        <li class="goods-image"><img src="${book.imagePath}"/></li>
                        <li class="goods-descript">
                            <p class="goods-title">${book.name}</p>

                            <p>${book.description}</p>
                        </li>
                        <li class="goods-price">${book.price}元</li>
                        <li class="goods-num">${orderFormVo.bookNum}</li>
                    </ul>
                </c:forEach>
            </div>
        </c:forEach>
        <%((Paging) request.getAttribute("paging")).printPage(out);%>
    </div>
</div>
<script>
    function subeva(li){
        var userID = $(li).attr("user");
        var content = $("input[name='content']").val();
        var orderID = $(li).parent().children("input[name='orderID']").val();
        $.post("/addeva",{
            userID:userID,
            content:content,
            orderID:orderID
        },function(date){
            if(date=="yes"){
                $(li).html("已评价");
                $(li).css("background-color","darkgray");
                $("input[name='content']").attr("disabled","disabled");
                $("input[name='content']").css("background-color","darkgray");
            }else if(date == "have"){
                alert("已经评论过了");
            } else{
                alert("评价失败");
            }
        });
    }
</script>
<jsp:include page="../mainPage/foot.jsp"/>
</body>
</html>
