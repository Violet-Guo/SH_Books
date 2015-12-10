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
    <title>订单</title>
    <link type="text/css" rel="stylesheet" href="../../css/all.css">
    <link type="text/css" rel="stylesheet" href="../../css/buycar.css">
    <link type="text/css" rel="stylesheet" href="../../css/order.css">
    <link type="text/css" rel="stylesheet" href="../../css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="../../css/bootstrap-switch.min.css">

    <script src="../../js/jquery.min.js"></script>
    <%--<script src="../../js/bootstrap.min.js"></script>--%>
    <script src="../../js/bootstrap-switch.min.js"></script>
    <script>
        //----------这里写取人收货后的post
        function sure(inp) {
            var orderID = $(inp).attr("id");
            var isSure = "yes";
            $.post("/addorder", {
                orderID: orderID,
                isSure: isSure
            }, function (date) {
                if (date == "yes") {
                    alert("确认成功");
                    $(inp).val("去评价");
                    $(inp).removeAttr("onclick");
                    $(inp).click(function () {
                        window.location.href = '/evaluation?orderID=' + orderID;
                    });
                }
            });
        }
    </script>
</head>
<body>
<jsp:include page="../mainPage/head.jsp"/>
<%String state = (String) request.getAttribute("state");%>
<%
    if (state == null) {
        state = "all";
    }
%>
<div id="main">
    <ul class="order-head">
        <li>
            <h2><a <%=state.equals("all") ? "class='on'" : ""%> href="/order">全部订单</a></h2>
        </li>
        <li>
            <h2><a <%=state.equals("waitsure") ? "class='on'" : ""%> href="/order?state=waitsure">待确认</a></h2>
        </li>
        <li>
            <h2><a <%=state.equals("waiteva") ? "class='on'" : ""%> href="/order?state=waiteva">待评价</a></h2>
        </li>
        <div class="floatRight">
            <input data-on-text="买" data-off-text="卖" data-on-color="success" data-off-color="warning" name="switch"
                   type="checkbox" checked>
        </div>
    </ul>
    <hr/>
    <!--购物车单的标题，表头-->
    <ul class="goods-ul goods-head">
        <li class="goods-image">图片</li>
        <li class="goods-descript">描述</li>
        <li class="goods-price">价格</li>
        <li class="goods-num">数量</li>
        <li class="goods-action">操作</li>
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
                    <li>订单号：${buyVo.orderID}</li>
                        <li class="goods-sure">
                            <c:if test="${buyVo.sureTime==null}">
                                <input type="button" id="${buyVo.orderID}" onclick="sure(this)" value="确认收货">
                            </c:if>
                            <c:if test="${buyVo.sureTime!=null&&buyVo.hasEva==0}">
                                <form method="get" action="/order">
                                    <input type="hidden" name="isEva" value="true">
                                    <input type="hidden" name="orderID" value="${buyVo.orderID}">
                                    <input type="submit" id="${buyVo.orderID}" value="去评价">
                                </form>
                            </c:if>
                            <c:if test="${buyVo.hasEva==1}">
                                <span>已评价</span>
                            </c:if>
                        </li>

                    <li class="all-price">总价：<span>${orderPriceList.get(status.count-1)}</span></li>
                </ul>
                    <%--@import这里获取不到值--%>
                <c:set value="${orderFormVoMap[statusStr]}" var="orderFormVos" scope="page"/>
                <c:set value="${orderFormBookMap[statusStr]}" var="orderFormBooks" scope="page"/>

                <c:forEach items="${orderFormVos}" var="orderFormVo" varStatus="inStatus">
                    <ul class="order-head sell-head">
                        <li>卖家:${orderFormUsers[inStatus.count-1].username}</li>
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
                        <c:if test="${buyVo.hasEva==1}">
                            <li class="goods-action">删除</li>
                        </c:if>
                    </ul>
                </c:forEach>
            </div>
        </c:forEach>
        <c:if test="${buyVos.size()==0}">
            <div class="center-alert">
                订单是空的哦!
            </div>
        </c:if>
        <%((Paging) request.getAttribute("paging")).printPage(out);%>
    </div>
</div>
<script>
    $("[name='switch']").bootstrapSwitch();
    $('input[name="switch"]').on('switchChange.bootstrapSwitch', function (event, state) {
        //---true是买家 false是卖家
        if (state) {
            //----------这里要刷新当前的状态，同时附加买家卖家状态
            $("#forReplace").load("/order?state=${state}&isbuyer=true #forReplace");
        } else {
            $("#forReplace").load("/order?state=${state}&isbuyer=false #forReplace");
        }
    });
</script>
<jsp:include page="../mainPage/foot.jsp"/>
</body>
</html>
