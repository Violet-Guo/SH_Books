<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.book.buy.vo.OrderFormVo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.book.buy.vo.UserVo" %>
<%@ page import="com.book.buy.vo.BookVo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.book.buy.vo.BuyVo" %>
<%@ page import="com.book.buy.utils.Paging" %>
<%--
  Created by IntelliJ IDEA.
  User: chao
  Date: 2015/12/5
  Time: 16:52
  To change this template use File | Settings | File Templates.
--%>
<%--ceshi--%>
<%--<%
    HashMap<Integer, List<OrderFormVo>> orderFormVoMap = new HashMap<>();
    HashMap<Integer, List<UserVo>> orderFormUserMap = new HashMap<>();
    HashMap<Integer, List<BookVo>> orderFormBookMap = new HashMap<>();
    ArrayList<Double> orderPriceList = new ArrayList<>();
    ArrayList<BuyVo> buyVos = new ArrayList<>();

    request.setAttribute("buyVos",buyVos);
    request.setAttribute("orderPriceList",orderPriceList);
    request.setAttribute("orderFormVoMap", orderFormVoMap);
    request.setAttribute("orderFormUserMap", orderFormUserMap);
    request.setAttribute("orderFormBookMap", orderFormBookMap);
%>--%>
<%--ceshi--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订单</title>
    <link type="text/css" rel="stylesheet" href="../../css/all.css">
    <link type="text/css" rel="stylesheet" href="../../css/buycar.css">
    <link type="text/css" rel="stylesheet" href="../../css/order.css">
</head>
<body>
<jsp:include page="../mainPage/head.jsp"/>
<%String state = (String) request.getAttribute("state");%>
<%
    if (state == null) {
        state = "";
    }
%>
<div id="main">
    <ul class="order-head">
        <li>
            <h2><a <%=state.equals("all") ? "class='on'" : ""%> href="/order">全部订单</a></h2>
        </li>
        <li>
            <h2><a <%=state.equals("waitmoney") ? "class='on'" : ""%> href="/order?state=waitmoney">待付款</a></h2>
        </li>
        <%--<li <%=state.equals("waitgoods")?"class='on'":""%>>
            <h2><a href="/order?state=waitgoods">待发货</a></h2>
        </li>--%>
        <li>
            <h2><a <%=state.equals("waitsure") ? "class='on'" : ""%> href="/order?state=waitsure">待确认</a></h2>
        </li>
        <li>
            <h2><a <%=state.equals("waiteva") ? "class='on'" : ""%> href="/order?state=waiteva">待评价</a></h2>
        </li>


        <li class="floatRight">
            <h2>买</h2>
        </li>
        <li class="floatRight">
            <h2>卖</h2>
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
    <c:forEach items="${buyVos}" var="buyVo" varStatus="status">
        <c:set value="${status.count-1}" var="statusStr"/>
        <c:set value="${orderFormUserMap[statusStr]}" var="orderFormUsers" scope="page"/>
        <c:set value="${orderFormUsers[0]}" var="seller" scope="page"/>
        <div class="goods-all">
            <ul class="order-head main-head">
                <c:choose>
                    <c:when test="${seller.name.equals(user.name)}">
                        <li class="user-state buy">买</li>
                    </c:when>
                    <c:otherwise>
                        <li class="user-state sell">卖</li>
                    </c:otherwise>
                </c:choose>
                <li>时间：${buyVo.time}</li>
                <li>订单号：${buyVo.orderID}</li>
                <li class="goods-sure">
                    <form>
                        <input type="hidden" name="isSure" value="yes">
                        <input type="hidden" name="number" value="${buyVo.orderID}">
                        <input type="submit" value="确认收货">
                    </form>
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
                <li class="goods-action">删除</li>
            </ul>
        </c:forEach>
        </div>
    </c:forEach>

    <%((Paging)request.getAttribute("paging")).printPage(out);%>
</div>


<jsp:include page="../mainPage/foot.jsp"/>
</body>
</html>
