<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.book.buy.vo.ComplainVo" %>
<%@ page import="com.book.buy.vo.BookVo" %>
<%@ page import="com.book.buy.vo.UserVo" %>
<%--
  Created by IntelliJ IDEA.
  User: violet
  Date: 2015/11/21
  Time: 14:16
  To change this template use File | Settings | File Templates.
  显示投诉的详情
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="<%=basePath %>css/all.css">
    <link rel="stylesheet" href="<%=basePath %>css/panel.css">
    <link rel="stylesheet" href="<%=basePath %>css/compdetil.css">
    <title>投诉详情</title>
</head>
<body>
<jsp:include page="/pages/mainPage/managerhead.jsp"></jsp:include>
<%
    request.setCharacterEncoding("utf-8");
    ComplainVo compvo = (ComplainVo) request.getSession().getAttribute("compdetil");
    BookVo bookvo = (BookVo) request.getSession().getAttribute("compbook");
    UserVo uservo = (UserVo) request.getSession().getAttribute("compuser");

    boolean isbook, iscomp;
    isbook = true;
    iscomp = true;
    if (0 == compvo.getState()) {
        iscomp = false;
    } else if (1 == compvo.getState()) {
        iscomp = true;
    }
    if (3 == bookvo.getState()) {
        isbook = false;
    } else if (1 == bookvo.getState()) {
        isbook = true;
    } else if (2 == bookvo.getState()){
        isbook = false;
    }
    request.setAttribute("iscomp", iscomp);
    request.setAttribute("isbook", isbook);
%>
<div class="panel panel-primary" style="width: 1100px; margin: auto">
    <div class="panel-heading">
        <h3 class="panel-title">投诉详情</h3>
    </div>
    <div class="panel-body">
        <div id="compdetil">
            投诉人：<%=uservo.getName()%><br>
            被投诉书籍：<%=bookvo.getName()%><br>
            投诉表述：<%=compvo.getDescription()%><br>
            投诉处理状态：
            <c:if test="${iscomp}">已处理</c:if>
            <c:if test="${!iscomp}">未处理</c:if><br>
            书籍状态：
            <c:if test="${!isbook}">已下架</c:if>
            <c:if test="${isbook}">上架</c:if><br>
            <input id="sbutton" type="submit" value="查看书籍详情" onclick="window.location='/ShowBookDetail?bookID=<%=bookvo.getId()%>'">
            &nbsp;&nbsp;
            <input id="sbutton" type="submit" value="下架书籍" onclick="window.location='/downbookbyadmin?bookid=<%=bookvo.getId()%>&&compid=<%=compvo.getId()%>'">
            &nbsp;&nbsp;
            <input id="sbutton" type="submit" value="投诉失败" onclick="window.location='/complainfail?bookid=<%=bookvo.getId()%>&&compid=<%=compvo.getId()%>'">
        </div>
    </div>
</div>
<br>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>
