<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.book.buy.vo.BookVo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.book.buy.utils.Paging" %>
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

    String state = (String) request.getAttribute("state");

    if (state == null) {
        state = "";
    }

    List<BookVo> booklis = (List) request.getSession().getAttribute("publishedbook");
    Paging paging = (Paging) request.getSession().getAttribute("paging");
%>
<div id="publishedbooklist">
    <ul class="publishedbook-top">
        <li>
            <h2><a <%=state.equals("all") ? "class='on'" : ""%> href="/publishedbooks">全部书籍</a></h2>
        </li>
        <li>
            <h2><a <%=state.equals("up") ? "class='on'" : ""%> href="/publishedbooks?state=up">已上架的书籍</a></h2>
        </li>
        <li>
            <h2><a <%=state.equals("down") ? "class='on'" : ""%> href="/publishedbooks?state=down">已下架的书籍</a></h2>
        </li>
        <li>
            <h2><a <%=state.equals("managerdown") ? "class='on'" : ""%> href="/publishedbooks?state=managerdown">被管理员下架的书</a>
            </h2>
        </li>
        <li>
            <h2><a <%=state.equals("selled") ? "class='on'" : ""%> href="/publishedbooks?state=selled">已售出的书籍</a></h2>
        </li>
    </ul>
    <br>
    <hr>
    <br>

    <div id="toph">
        <span id="num"><strong>序号</strong></span>
        <span id="bname"><strong>书名</strong></span>
        <span id="bauther"><strong>作者</strong></span>
        <span id="oldgrade"><strong>新旧程度</strong></span>
        <span id="ptime"><strong>发布时间</strong></span>
        <span id="udstate"><strong>上下架状态</strong></span>
        <span id="bstate"><strong>书籍状态</strong></span>
        <span id="appeal"><strong>申诉</strong></span>
        <span id="edit"><strong>编辑书籍</strong></span>
        <span id="del"><strong>删除书籍</strong></span>
    </div>
    <br>
    <%
        for (int i = 0; i < booklis.size(); i++) {
            BookVo book = (BookVo) booklis.get(i);
            boolean udstate, bstate;
            udstate = false;
            bstate = false;
            if (1 == book.getState())
                udstate = true;
            else if (2 == book.getState())
                udstate = false;
            else if (3 == book.getState())
                bstate = true;

            request.setAttribute("udstate", udstate);
            request.setAttribute("bstate", bstate);
    %>
    <div id="body">
        <span id="num"><%=i + 1%></span>
        <span id="bname"><a href="/bookDetail?bookID=<%=book.getId()%>"><%=book.getName()%>
        </a></span>
        <span id="bauther"><%=book.getAuthor()%></span>
        <span id="oldgrade"><%=book.getOldGrade()%></span>
        <span id="ptime"><%=book.getTime()%></span>
            <span id="udstate">
                <c:if test="${!udstate}">已下架</c:if>
                <c:if test="${udstate}">已上架</c:if>
                <c:if test="${bstate}">被管理员下架</c:if>
            </span>
            <span id="bstate">
                <c:if test="${!udstate}"><a href="/OnSaleServlet?bookid=<%=book.getId()%>">上架</a></c:if>
                <c:if test="${udstate}"><a href="/OffShelvesServlet?bookid=<%=book.getId()%>">下架</a></c:if>
            </span>
            <span id="appeal">
                <c:if test="${bstate}"><a href="/addAppeal?bookid=<%=book.getId()%>">申诉</a></c:if>
                <c:if test="${!bstate}">申诉</c:if>
            </span>
        <span id="edit"><a href="/ModifyBookInfoServlet?bookID=<%=book.getId()%>">编辑</a></span>
        <span id="del"><a href="/DeleteBookServlet?bookid=<%=book.getId()%>">删除书籍</a></span>
    </div>
    <br>
    <%
        }
        paging.printPage(out);
    %>
</div>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>
