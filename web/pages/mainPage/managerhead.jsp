<%@ page import="com.book.buy.vo.ManagerVo" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: violet
  Date: 2015/11/4
  Time: 22:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
<link rel="stylesheet" type="text/css" href="/css/all.css">
<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
--%>
<%
    ManagerVo admin = (ManagerVo)request.getSession().getAttribute("admin");
    Boolean isLogin;
    if (admin != null){
        isLogin = true;
    } else {
        isLogin = false;
    }

    request.setAttribute("isLogin", isLogin);
%>

<div id="head">
    <div id="top-navigat">
        <div id="menu">
            <ul class="navigat">
                <li><a style="color: white" href="/AdminIndex">主页</a></li>
                <li><a style="color: white" href="/getallcomp">投诉</a></li>
                <li><a style="color: white" href="/getallappeal">申诉</a></li>
                <li><a style="color: white" href="/getalluser">用户</a></li>
                <li><a style="color: white" href="/getallfedback">反馈</a></li>
            </ul>
            <c:if test="${isLogin}">
                <ul class="top-sell-book">
                    <li><a style="color: white" href="/managerinfo">${admin.username}</a></li>
                    <li><a style="color: white" href="/quitadmin">注销</a></li>
                </ul>
            </c:if>
            <c:if test="${!isLogin}">
                <ul class="top-sell-book">
                    <li>管理员请登陆</li>
                </ul>
            </c:if>

        </div>
    </div>


    <div id="top-search">
        <div>
            <a href="/AdminIndex"><h1>二手书<span>郑大</span></h1></a>
            <input id="search-input" type="text" name="search">
            <input id="submit-button" type="submit">
        </div>
    </div>

</div>

