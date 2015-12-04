<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.book.buy.vo.ComplainVo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.book.buy.vo.UserVo" %>
<%@ page import="com.book.buy.vo.BookVo" %>
<%--
  Created by IntelliJ IDEA.
  User: violet
  Date: 2015/11/21
  Time: 14:16
  To change this template use File | Settings | File Templates.
  显示申诉的详情
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="<%=basePath %>css/all.css">
    <link rel="stylesheet" href="<%=basePath %>css/bootstrap.min.css">
    <title>申诉详情</title>
</head>
<body>
<jsp:include page="/pages/mainPage/managerhead.jsp"></jsp:include>
<%
    request.setCharacterEncoding("utf-8");
    ComplainVo compvo = (ComplainVo) request.getSession().getAttribute("appealdetil");
    UserVo uservo = (UserVo) request.getSession().getAttribute("appealuser");
    BookVo bookvo = (BookVo) request.getSession().getAttribute("appealbook");

    boolean isappeal, isbook;
    if (0 == compvo.getState()) {
        isappeal = false;
    } else if (1 == compvo.getState()) {
        isappeal = true;
    }
    if (4 == bookvo.getState()) {
        isbook = false;
    } else if (1 == bookvo.getState()) {
        isbook = true;
    }
%>
<div class="panel panel-primary" style="width: 1100px; margin: auto">
    <div class="panel-heading">
        <h3 class="panel-title">投诉详情</h3>
    </div>
    <div class="panel-body">
        <div id="appealdetil">
            申诉人：<%=uservo.getName()%><br>
            被申诉书籍：<%=bookvo.getName()%><br>
            申诉描述：<%=compvo.getDescription()%><br>
            申诉处理状态：
            <c:if test="${isappeal}">已处理</c:if>
            <c:if test="${!isappeal}">未处理</c:if><br>
            书籍状态：
            <c:if test="${isbook}">已上架</c:if>
            <c:if test="${!isbook}">已下架</c:if><br>
            <br>
            <input id="sbutton" type="submit" value="查看书籍详情" onclick="window.location='#'">
            &nbsp;&nbsp;
            <input id="sbutton" type="submit" value="上架书籍" onclick="window.location='#'">
        </div>
    </div>
</div>
</div>
<br>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>
