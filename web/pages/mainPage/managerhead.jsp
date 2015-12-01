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
    String username = (String)request.getSession().getAttribute("username");
    Boolean isLogin;
    if (username != null){
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
                <li><a style="color: white" href="AllComp.jsp">投诉</a></li>
                <li><a style="color: white" href="AllAppeal.jsp">申诉</a></li>
                <li><a style="color: white" href="AllUserComp.jsp">用户</a></li>
            </ul>
            <c:if test="${isLogin}">
                <ul class="top-sell-book">
                    <li><a style="color: white" href="#">${username}</a></li>
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
            <h1>二手书<span>郑大</span></h1>
            <input id="search-input" type="text" name="search">
            <input id="submit-button" type="submit">
        </div>
    </div>

</div>

