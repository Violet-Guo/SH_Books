<%@ page import="com.book.buy.vo.ComplainVo" %>
<%@ page import="com.book.buy.vo.BookVo" %>
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
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="<%=basePath %>css/all.css">
    <link rel="stylesheet" href="<%=basePath %>css/bootstrap.min.css">
    <title>投诉详情</title>
</head>
<body>
    <jsp:include page="/pages/mainPage/managerhead.jsp"></jsp:include>
    <%
        request.setCharacterEncoding("utf-8");
        ComplainVo compvo = (ComplainVo)request.getSession().getAttribute("compdetil");
        BookVo bookvo = (BookVo)request.getSession().getAttribute("bookvo");
    %>
    <div class="panel panel-primary" style="width: 1100px; margin: auto">
        <div class="panel-heading">
            <h3 class="panel-title">投诉详情</h3>
        </div>
        <div class="panel-body">
            <a href="">商品详情</a><br>
            <a href="">商品下架</a>
            投诉人：
            被投诉书籍：
            投诉表述：
            状态：
        </div>
    </div>
    <jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>
