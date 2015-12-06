<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.book.buy.vo.UserVo" %>
<%@ page import="com.book.buy.dao.InformDao" %>
<%@ page import="com.book.buy.factory.InformDaoImplFactory" %>
<%@ page import="com.book.buy.vo.InformVo" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: chao
  Date: 2015/11/12
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%UserVo userVo = (UserVo) session.getAttribute("user");%>
<%Boolean isLogin;%>
<%
    List<InformVo> informVos = null;
    if (userVo != null) {
        isLogin = true;
        InformDao informDao = InformDaoImplFactory.getInformDaoImpl();
        informVos = informDao.count(userVo.getId());
    } else {
        isLogin = false;
    }
%>
<%request.setAttribute("isLogin", isLogin);%>
<div id="head">
    <div id="top-navigat">
        <div id="menu">
            <c:if test="${isLogin}">
                <ul class="navigat">
                    <li><a href="/PersonInfoServlet">${user.username}</a></li>
                    <li><a href="./InformServlet">消息<%=informVos.size()%></a></li>
                </ul>
            </c:if>
            <c:if test="${!isLogin}">
                <ul class="navigat">
                    <li><a href="/login">请登录!</a></li>
                    <li><a href="/register">注册</a></li>
                </ul>
            </c:if>
            <ul class="navigat top-sell-book">
                <c:if test="${isLogin}">
                    <li><a href="/buycar">购物车</a></li>
                    <li><a href="/order">我的订单</a>
                        <ul>
                            <li><a href="#">待收货</a></li>
                            <li><a href="#">待付款</a></li>
                            <li><a href="#">待评价</a></li>
                        </ul>
                    </li>
                    <li><a href="/publishedbooks">卖书</a></li>
                </c:if>

                <li>
                    <a href="./controlCenter">服务中心</a>
                    <ul>
                        <li><a href="./feedback">反馈</a></li>
                        <li><a href="./InformServlet">通知</a></li>
                        <li><a href="./help">帮助</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
    <div id="top-search">
        <div>
            <a href="/"><h1>二手书<span>郑大</span></h1></a>
            <form method="get" action="/SearchBook" target="_blank">
                <input id="search-input" type="text" name="bookName">
                <input id="submit-button" type="submit">
            </form>
        </div>
    </div>
</div>
