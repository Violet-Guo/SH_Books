<%@ page import="java.util.List" %>
<%@ page import="com.book.buy.vo.FeedBackVo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.book.buy.utils.Paging" %>
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
    <link rel="stylesheet" href="<%=basePath %>css/information.css">
    <title>反馈列表</title>
</head>
<body>
<%
    request.setCharacterEncoding("utf-8");

    List<FeedBackVo> lis = (List)request.getSession().getAttribute("allfeedback");
    Paging paing = (Paging)request.getSession().getAttribute("paging");
%>
<jsp:include page="/pages/mainPage/managerhead.jsp"></jsp:include>
<div class="panel panel-primary" style="width: 1100px; margin: auto">
    <div class="panel-heading">
        <h3 class="panel-title">反馈<span id="more"><a id="wm" href="#">更多</a></span></h3>
    </div>
    <div class="panel-body">
        <div id="feedhead">
            <span id="xuhao">序号</span>
            <span id="content">反馈内容</span>
            <span id="feedtime">反馈时间</span>
        </div>
        <br>
        <hr>
        <%
            for (int i = 0; i < lis.size(); i++) {
                FeedBackVo feed = (FeedBackVo) lis.get(i);
                String des = feed.getDescription();
                if (20 < des.length())
                    des = des.substring(0, 20);
        %>
        <div id="feedbody">
            <span id="xuhao"><%=i + 1%>.</span>
            <span id="content"><a href="/getfedbackdetil?userid=<%=feed.getUserId()%>&&time=<%=feed.getTime()%>"><%=des%>
            </a></span>
            <span id="feedtime"><%=feed.getTime()%></span>
        </div>
        <br>
        <%
            }
            paing.printPage(out);
        %>
    </div>
</div>
<br>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>
