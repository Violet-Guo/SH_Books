<%@ page import="com.book.buy.vo.FeedBackVo" %>
<%@ page import="com.book.buy.vo.UserVo" %>
<%--
  Created by IntelliJ IDEA.
  User: violet
  Date: 2015/12/7
  Time: 0:50
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
    <link rel="stylesheet" href="<%=basePath %>css/panel.css">
    <link rel="stylesheet" href="<%=basePath %>css/compdetil.css">
    <title>反馈详情</title>
</head>
<body>
<jsp:include page="/pages/mainPage/managerhead.jsp"></jsp:include>
<%
    request.setCharacterEncoding("utf-8");
    UserVo user = (UserVo) request.getSession().getAttribute("feduser");
    FeedBackVo fed = (FeedBackVo) request.getSession().getAttribute("feddetil");
%>

<div class="panel panel-primary" style="width: 1100px; margin: auto">
    <div class="panel-heading">
        <h3 class="panel-title">反馈</h3>
    </div>
    <div class="panel-body">
        <div id="fedbody">
            <p id="username">反馈人：<%=user.getName()%>
            </p>

            <p id="fedtime">反馈时间：<%=fed.getTime()%>
            </p>

            <p id="content">反馈内容：<%=fed.getDescription()%>
            </p>
        </div>
    </div>
</div>
<br>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>
