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
    <title>下订单</title>
    <link type="text/css" rel="stylesheet" href="../../css/all.css">
    <link type="text/css" rel="stylesheet" href="../../css/buycar.css">
    <link type="text/css" rel="stylesheet" href="../../css/order.css">
</head>
<body>
<jsp:include page="../mainPage/head.jsp"/>

<div id="main">
    <ul class="order-head">
        <li>
            <h2>下订单</h2>
        </li>
        <%--<li>
            <h2>地址：${locationVo.dorName}${locationVo.dorNum}楼${locationVo.floorNum}</h2>
        </li>--%>
        <li class="button"><a href="/changePersonInfo" class="change-address"><h2>修改地址</h2></a></li>
        <%//-------@import更改地址的链接不一定正确%>
    </ul>
    <hr/>
    <!--购物车单的标题，表头-->
    <ul class="goods-ul goods-head">
        <li class="goods-image">图片</li>
        <li class="goods-descript">描述</li>
        <li class="goods-price">价格</li>
        <li class="goods-num">数量</li>
        <%--<li class="goods-action">操作</li>
        <li class="goods-state">状态</li>--%>
    </ul>

    <c:forEach items="${buyVos}" var="buyVo" varStatus="status">
        <c:set value="${status.count-1}" var="statusStr"/>
        <c:set value="${orderFormUserMap[statusStr]}" var="orderFormUsers" scope="page"/>
        <c:set value="${orderFormUsers[0]}" var="seller" scope="page"/>
        <div class="goods-all">
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
                </ul>
            </c:forEach>
        </div>
    </c:forEach>
    <%((Paging) request.getAttribute("paging")).printPage(out);%>
    <ul id="total">
        <%--计算价格--%>
        <%--<li class="del-all">
            <form action="/buycar" method="post">
                <input type="hidden" name="delAll" value="yes">
                <input value="全部删除" type="submit">
            </form>
        </li>--%>
        <li class="tol-price">共计：<span>${allPrice}</span></li>
        <li class="action-price button">
            <a href="/order">查看订单</a>
        </li>
    </ul>
</div>


<jsp:include page="../mainPage/foot.jsp"/>
</body>
</html>
