<%--
  Created by IntelliJ IDEA.
  User: violet
  Date: 2015/11/8
  Time: 21:19
  To change this template use File | Settings | File Templates.
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

    String bookid = request.getParameter("bookid");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="<%=basePath %>css/all.css">
    <link rel="stylesheet" href="<%=basePath %>css/addcomp.css">
    <title>添加投诉</title>
</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"></jsp:include>
<div id="addcomp">
    <h1>投诉内容：</h1><br>

    <form action="/addcomp?bookid=<%=bookid%>" method="post">
        <textarea id="comp" name="comp"></textarea>
        <br><br>
        <input id="cbutton" type="submit">&nbsp;&nbsp;
        <input id="cbutton" type="reset"><br>
    </form>
</div>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>
