<%--
  Created by IntelliJ IDEA.
  User: violet
  Date: 2015/11/4
  Time: 23:50
  To change this template use File | Settings | File Templates.
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="<%=basePath %>css/all.css">
    <link rel="stylesheet" href="<%=basePath %>css/managerloginstate.css">
    <link rel="stylesheet" href="<%=basePath %>css/managerloginstate.css">
    <title>登陆失败</title>
</head>
<body>
    <jsp:include page="/pages/mainPage/managerhead.jsp"></jsp:include>
    <div id="loginstate">
        登陆失败，<a href="loginManager.jsp">点击跳转到管理员登陆界面</a>
    </div>
    <jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>
