<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.book.buy.utils.Paging" %>
<%--
  Created by IntelliJ IDEA.
  User: chao
  Date: 2015/11/12
  Time: 13:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%Paging paging = (Paging) request.getAttribute("paging");%>
<html>
<head>
    <title>购物车</title>
    <link type="text/css" rel="stylesheet" href="../../css/all.css">
    <link type="text/css" rel="stylesheet" href="../../css/buycar.css">
    <link type="text/css" rel="stylesheet" href="../../css/messenger.css">
    <link type="text/css" rel="stylesheet" href="../../css/messenger-theme-flat.css">
    <script src="../../js/jquery.min.js"></script>
    <script src="../../js/messenger.min.js"></script>
    <script src="../../js/buycar.js"></script>
</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"/>
<div id="main">
    <h2>全部商品&nbsp;<span class="all-num">${orderFormVos.size()}</span></h2>
    <hr/>
    <!--购物车单的标题，表头-->
    <ul class="goods-ul goods-head">
        <li class="goods-image">图片</li>
        <li class="goods-descript">描述</li>
        <li class="goods-price">价格</li>
        <li class="goods-num">数量</li>
        <li class="goods-action">操作</li>
    </ul>
    <c:if test="${orderFormVos.size()!=0}">
        <c:set var="username" value="${''}" scope="page"/>
        <c:set value="${false}" var="isPrint" scope="page"/>
        <c:forEach items="${orderFormVos}" var="orderFormVo" varStatus="status">
            <c:set value="${orderBookVos.get(status.count-1)}" var="orderBookVo" scope="page"/>
            <c:set value="${orderUserVos.get(status.count-1)}" var="orderUserVo" scope="page"/>
            <c:if test="${!orderUserVo.username.equals(username)}">
                <c:if test="${isPrint}">
                    </div>
                    <c:set value="${false}" var="isPrint" scope="page"/>
                </c:if>
                    <div><%--这里打印div把每个卖家的块包到一起方便移除--%>
                        <c:set value="${true}" var="isPrint" scope="page"/>
                        <p class="seller-name">卖家：${orderUserVo.username}</p>
            </c:if>
            <c:set value="${orderUserVo.username}" var="username" scope="page"/>
            <ul class="goods-ul">
                <li class="goods-image"><img src="${orderBookVo.imagePath}"/></li>
                <li class="goods-descript">
                    <p class="goods-title">${orderBookVo.name}</p>
                    <p>${orderBookVo.description}</p>
                </li>
                <li class="goods-price">${orderBookVo.price}</li>
                <li class="goods-num">
                    <input type="number" onchange="judgeOver(this)" big="${orderBookVo.bookNum}" name="goodsNum" value="${orderFormVo.bookNum}">
                </li>
                <li class="goods-action">
                    <input type="button" name="${orderFormVo.id}" value="删除" onclick="del(this)">
                </li>
            </ul>
        </c:forEach>
        <c:if test="${isPrint}">
            </div>
        </c:if>
        <%paging.printPage(out);%>

        <ul id="total">
            <%--计算价格--%>
            <li class="del-all">
                <form action="/buycar" method="post">
                    <input type="hidden" name="delAll" value="yes">
                    <input value="全部删除" type="submit" onclick="return confirm('确认全部删除？');">
                </form>
            </li>
            <li class="tol-price">共计：<span>${allPrice}</span></li>
            <li class="action-price button">
                <form action="/buycar" method="post">
                    <input type="hidden" name="buycarSub" value="yes">
                    <input value="生成订单" type="submit">
                </form>
            </li>
        </ul>
    </c:if>
    <c:if test="${orderFormVos.size()==0}">
        <div class="is-empty-info">您的购物车空空如也，赶快去买本书吧</div>
    </c:if>
</div>
<jsp:include page="/pages/mainPage/foot.jsp"/>
</body>
</html>
