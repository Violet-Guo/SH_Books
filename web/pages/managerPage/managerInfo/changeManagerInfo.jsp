<%--
  Created by IntelliJ IDEA.
  User: violet
  Date: 2015/12/2
  Time: 21:52
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
    <link rel="stylesheet" href="<%=basePath %>css/changeManagerInfo.css">
    <title>修改管理员密码</title>
</head>
<body>
    <jsp:include page="/pages/mainPage/managerhead.jsp"></jsp:include>
    <div id="managerInfo">
        <form action="/changemanagerinfo" method="post">
            原密码：<input type="password" name="oldpass"><br>
            新密码：<input type="password" name="newpass1"><br>
            新密码：<input type="password" name="newpass2"><br>
            <input id="sbutton" type="submit" value="确定修改">
        </form>
    </div>
    <br>
    <jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>
