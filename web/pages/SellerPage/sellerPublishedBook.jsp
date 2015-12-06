<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.book.buy.vo.BookVo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: violet
  Date: 2015/12/1
  Time: 23:37
  To change this template use File | Settings | File Templates.
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="<%=basePath %>css/all.css">
    <link rel="stylesheet" href="<%=basePath %>css/sellerPublishedBook.css">
    <title>已发布的书籍</title>
</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"></jsp:include>
    <%
        request.setCharacterEncoding("utf-8");
        List<BookVo> booklis = (ArrayList)request.getSession().getAttribute("publishedbook");
    %>
    <div id="publishedbooklist">
        <div id="toph"><span id="num">序号</span><span id="bname">书名</span><span id="bauther">作者</span><span id="oldgrade">新旧程度</span>
            <span id="ptime">发布时间</span><span id="udstate">上下架状态</span><span id="bstate">书籍状态</span><span id="appeal">申诉</span></div>
        <%
            for (int i = 0; i < booklis.size(); i++){
                BookVo book = (BookVo)booklis.get(i);
                boolean udstate, bstate;
                bstate = false;
                if (0 == book.getState())
                    udstate = false;
                else if (1 == book.getState())
                    udstate = true;
                else if (4 == book.getState())
                    bstate = true;
        %>
        <div id="body">
            <span id="num"><%=i+1%></span>
            <span id="bname"><a href="#"><%=book.getName()%></a></span>
            <span id="bauther"><%=book.getAuthor()%></span>
            <span id="oldgrade"><%=book.getOldGrade()%></span>
            <span id="ptime"><%=book.getTime()%></span>
            <span id="udstate">
                <c:if test="${!udstate}">已下架</c:if>
                <c:if test="${udstate}">已上架</c:if>
                <c:if test="${bstate}">被管理员下架</c:if><br>
            </span>
            <span id="bstate">
                <c:if test="${!udstate}"><a href="#">上架</a></c:if>
                <c:if test="${udstate}"><a href="#">下架</a></c:if><br>
            </span>
            <span id="appeal">
                <c:if test="${bstate}"><a href="#">申诉</a></c:if>
                <c:if test="${!bstate}">申诉</c:if><br>
            </span>
        </div>
        <%
            }
        %>
    </div>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>
