<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.book.buy.vo.OrderFormVo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: chao
  Date: 2015/11/12
  Time: 13:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%ArrayList<OrderFormVo> orderFormVos = (ArrayList<OrderFormVo>) request.getAttribute("orderFormVos");%>
<%int everyPageNum = 5;%><%--读取文章总数和计算分页数--%>
<%request.setAttribute("everyPageNum",everyPageNum);%>
<%String strPage = request.getParameter("thisPage");%>
<%Integer thisPage;%>
<%if(strPage==null||strPage.equals("")){thisPage = 1;}else{%>
<%thisPage = Integer.valueOf(strPage);}%><%--得到当前页数--%>
<%int allNum = orderFormVos.size();%>
<%int pageNum = allNum%everyPageNum==0?allNum/everyPageNum:allNum/everyPageNum+1;//计算总共多少页数%>
<%if(thisPage>pageNum){thisPage=pageNum;}%>
<%if(thisPage<=0){thisPage=1;}%>
<%request.setAttribute("thisPage",thisPage);%>
<html>
<head>
    <title>购物车</title>
    <link type="text/css" rel="stylesheet" href="../../css/all.css">
    <link type="text/css" rel="stylesheet" href="../../css/buycar.css">
    <script src="../../js/jquery.min.js"></script>
    <script src="../../js/buycar.js"></script>
</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"/>
<div id="main">
    <h2>全部商品&nbsp;${orderFormVos.size()}</h2>
    <hr/>
    <!--购物车单的标题，表头-->
    <ul class="goods-ul goods-head">
        <li class="goods-image">图片</li>
        <li class="goods-descript">描述</li>
        <li class="goods-price">价格</li>
        <li class="goods-num">数量</li>
        <li class="goods-action">操作</li>
    </ul>

    <p class="seller-name">卖家:旺仔小馒头</p>
    <c:forEach items="${orderFormVos}" var="orderFormVo" begin="${(thisPage-1)*everyPageNum}" end="${thisPage*everyPageNum}" varStatus="status">
        <c:set value="${orderBookVos.get(status.count-1)}" var="orderBookVo" scope="page"/>
        <ul class="goods-ul">
            <li class="goods-image"><img src="${orderBookVo.imagePath}"/></li>
            <li class="goods-descript">
                <p class="goods-title">${orderBookVo.name}</p>
                <p>${orderBookVo.description}</p>
            </li>
            <li class="goods-price">${orderBookVo.price}</li>
            <li class="goods-num"><input type="number" name="goodsNum" value="${orderFormVo.bookNum}"></li>
            <li class="goods-action">
                <input type="button" name="${orderBookVo.id}" value="删除" onclick="del(this)">
            </li>
        </ul>
    </c:forEach>

    <ul id="page">
        <li><a href="/buycar?thisPage=${requestScope.thisPage-1}">上一页</a></li>
        <%int firstOne = thisPage%10==0?(((thisPage-1)/10)*10+1):((thisPage/10)*10+1);%>
        <%int lastOne = thisPage%10==0?(((thisPage-1)/10+1)*10):((thisPage/10+1)*10);%>
        <%for(int i=firstOne;i<=(lastOne>pageNum?pageNum:lastOne);i++){%>
        <li><a <%if(thisPage==i){out.print("id='thisPage'");}%> href="/buycar?thisPage=<%out.print(i);%>"><%out.print(i);%></a></li>
        <%}%>
        <%if(pageNum%10>0&&pageNum/10>(thisPage-1)/10){out.print("<a href='/buycar?thisPage="+((((thisPage-1)/10)+1)*10+1)+"'>&gt;&gt;</a>");}%>
        <li><a href="/buycar?thisPage=${requestScope.thisPage+1}">下一页</a></li>
    </ul>


    <ul id="total">

        <li class="del-all">全部删除</li>
        <li class="tol-price">共计：<span>￥225</span></li>
        <li class="action-price button">
            <form action="/buycar" method="post">
                <input type="hidden" name="buycarSub" value="yes">
                <input value="结算" type="submit">
            </form>
        </li>
    </ul>
</div>
<jsp:include page="/pages/mainPage/foot.jsp"/>
</body>
</html>
