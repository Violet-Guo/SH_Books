<%@ page import="java.util.List" %>
<%@ page import="com.book.buy.vo.ComplainVo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.book.buy.utils.Paging" %>
<%--
  Created by IntelliJ IDEA.
  User: violet
  Date: 2015/11/6
  Time: 13:40
  To change this template use File | Settings | File Templates.
  页面中显示所有的投诉列表
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="<%=basePath %>css/all.css">
    <link rel="stylesheet" href="<%=basePath %>css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath %>css/information.css">
    <link rel="stylesheet" href="<%=basePath %>css/sellerPublishedBook.css">
    <title>投诉列表</title>
</head>
<body>
<jsp:include page="/pages/mainPage/managerhead.jsp"></jsp:include>
<%
    request.setCharacterEncoding("utf-8");
    String state = (String)request.getParameter("state");
    if (null == state){
        state = "";
    }

    List<ComplainVo> lis = (List) request.getSession().getAttribute("allcomp");
    Paging paging = (Paging) request.getSession().getAttribute("paging");
%>

<br>

<div class="panel panel-primary" style="width: 1100px; margin: auto">
    <div class="panel-heading">
        <h3 class="panel-title">投诉</h3>
    </div>
    <div class="panel-body">
        <ul class="allcomp-top">
            <li><h2><a <%=state.equals("all") ? "class='on'" : ""%> href="/getallcomp">全部投诉</a></h2></li>
            <li><h2><a <%=state.equals("ye") ? "class='on'" : ""%> href="/getallcomp?state=yes">已处理</a></h2></li>
            <li><h2><a <%=state.equals("no") ? "class='on'" : ""%> href="/getallcomp?state=no">未处理</a></h2></li>
        </ul>
        <br><br><hr><br>
        <div id="comphead">
            <span id="xuhao">序号</span>
            <span id="content">投诉内容</span>
            <span id="feedtime">投诉状态</span>
        </div>
        <br><br>
        <%
            for (int i = 0; i < lis.size(); i++) {
                ComplainVo comp = (ComplainVo) lis.get(i);
                String des = comp.getDescription();
                if (des.length() > 20)
                    des = des.substring(0, 20);
        %>
        <div id="compbody">
            <span id="xuhao"><%=i + 1%></span>
            <span id="content"><a href="/getcompdetil?compid=<%=comp.getId()%>"><%=des%>
            </a></span>
            <%
                if (0 == comp.getState()) {
            %>
            <span id="feedtime">未处理</span>
            <%
            } else {
            %>
            <span id="feedtime">已处理</span>
            <%
                }
            %>
        </div>

        <br>
        <%
            }
            paging.printPage(out);
        %>
    </div>
</div>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>
