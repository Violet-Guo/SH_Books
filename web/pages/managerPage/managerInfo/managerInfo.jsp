<%--
  Created by IntelliJ IDEA.
  User: violet
  Date: 2015/11/26
  Time: 19:45
  To change this template use File | Settings | File Templates.
  显示管理员的个人信息
--%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="<%=basePath %>css/all.css">
    <link rel="stylesheet" href="<%=basePath %>css/managerInfo.css">
    <title>管理员信息</title>
</head>
<body>
<jsp:include page="/pages/mainPage/managerhead.jsp"></jsp:include>
    <div id="managerInfo">
        <p>管理员帐号：${admin.username}</p><br>
        <input id="sbutton" type="submit" value="修改密码" onclick="window.location='/changepassadmin'"/>
    </div>
    <br>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>
