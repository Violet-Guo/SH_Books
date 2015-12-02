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
    <title>已发布的书籍</title>
</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"></jsp:include>
    <div id="publishedbooklist">

    </div>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>
