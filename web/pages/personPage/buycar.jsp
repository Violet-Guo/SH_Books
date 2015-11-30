<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.book.buy.vo.OrderFormVo" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: chao
  Date: 2015/11/12
  Time: 13:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%List<OrderFormVo> orderFormVos = (List<OrderFormVo>) request.getAttribute("orderFormVos");%>
<%request.setAttribute("orderFormVos",orderFormVos);%>http://desktop.youku.com/ugc/youkumac_7-28_2.dmg

<%int everyPageNum = 5;%><%--读取文章总数和计算分页数--%>
<%request.setAttribute("everyPageNum",everyPageNum);%>
<%String strPage = request.getParameter("thisPage");%>
<%Integer thisPage;%>
<%if(strPage==null||strPage.equals("")){thisPage = 1;}else{%>
<%thisPage = Integer.valueOf(strPage);}%><%--得到当前页数--%>

<%int allNum = getArticle.getArticleConunt((String) request.getAttribute("leftNav"));%>
<%int pageNum = allNum%everyPageNum==0?allNum/everyPageNum:allNum/everyPageNum+1;//计算总共多少页数%>
<%if(thisPage>pageNum){thisPage=pageNum;}%>
<%if(thisPage<=0){thisPage=1;}%>
<%request.setAttribute("thisPage",thisPage);%>
<html>
<head>
    <title>购物车</title>
    <link type="text/css" rel="stylesheet" href="../../css/all.css">
    <link type="text/css" rel="stylesheet" href="../../css/buycar.css">
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
    <c:forEach items="${orderFormVos}" var="orderFormVo" begin="${(thisPage-1)*everyPageNum}" end="${thisPage*everyPageNum}">
        <ul class="goods-ul">
            <li class="goods-image"><img src="${}"/></li>
            <li class="goods-descript">
                <p class="goods-title">我是一只小小鸟</p>

                <p>啥啥啥书5成新啥啥书5成新啥啥书5成新啥啥书5成新啥啥书5成新啥啥书5成新</p>
            </li>
            <li class="goods-price">20.00元</li>
            <li class="goods-num"><input type="number" name="goodsNum" value="2"></li>
            <li class="goods-action">删除</li>
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
        <li class="action-price">结算</li>
    </ul>
</div>
<jsp:include page="/pages/mainPage/foot.jsp"/>
</body>
</html>
