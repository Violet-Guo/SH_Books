<%@ page import="java.util.List" %>
<%@ page import="com.book.buy.vo.ComplainVo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.book.buy.vo.UserVo" %>
<%@ page import="com.book.buy.utils.Paging" %>
<%--
  Created by IntelliJ IDEA.
  User: violet
  Date: 2015/11/11
  Time: 16:07
  To change this template use File | Settings | File Templates.
  显示所有用户的被投诉的次数，管理员进行相应的操作
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
    <link rel="stylesheet" href="<%=basePath %>css/information.css">
    <title></title>
</head>
<body>
    <jsp:include page="/pages/mainPage/managerhead.jsp"></jsp:include>
    <%
        request.setCharacterEncoding("utf-8");
        List<UserVo> lis = (List)request.getSession().getAttribute("allcompuser");
        Paging paging = (Paging)request.getSession().getAttribute("paging");
    %>

    <br>
    <div class="panel panel-primary" style="width: 1100px; margin: auto">
        <div class="panel-heading">
            <h3 class="panel-title">用户</h3>
        </div>
        <div class="panel-body">
            <div id="userhead">
                <span id="xuhao">序号</span>
                <span id="username">用户名</span>
                <span id="compnum">被投诉次数</span>
                <span id="userstate">用户状态</span>
            </div>
            <br>
            <hr>
            <%
                for (int i = 0; i < lis.size(); i++){
                    UserVo uservo = (UserVo)lis.get(i);
            %>
            <div id="userbody">
                <span id="xuhao"><%=i + 1%>.</span>
                <span id="username"><%=uservo.getName()%></span>
                <span id="compnum"><%=uservo.getComplainNum()%></span>
                <%
                    if (3 < uservo.getComplainNum()) {
                %>
                <span id="userstate">已冻结</span>
                <%
                } else {
                %>
                <span id="userstate">正常</span>
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
