<%@ page import="com.book.buy.vo.ComplainVo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.book.buy.vo.UserVo" %>
<%@ page import="com.book.buy.vo.FeedBackVo" %>
<%--
  Created by IntelliJ IDEA.
  User: violet
  Date: 2015/11/4
  Time: 19:56
  To change this template use File | Settings | File Templates.
  管理员登陆进去后的主页
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
    <title>管理员主页</title>
</head>
<body>
<jsp:include page="/pages/mainPage/managerhead.jsp"></jsp:include>
<%
    request.setCharacterEncoding("utf-8");
    List<ComplainVo> lis1 = (ArrayList) request.getSession().getAttribute("allcomp");   //投诉

    List<ComplainVo> lis2 = (ArrayList) request.getSession().getAttribute("allappeal");    //申诉

    List<UserVo> lis3 = (ArrayList) request.getSession().getAttribute("allcompuser");

    List<FeedBackVo> lis4 = (ArrayList) request.getSession().getAttribute("allfeedback");

%>
<br>

<div class="panel panel-primary" style="width: 1100px; margin: auto">
    <div class="panel-heading">
        <h3 class="panel-title">投诉<span id="more"><a id="wm" href="/getallcomp">更多</a></span></h3>
    </div>
    <div class="panel-body">
        <div id="comphead">
            <span id="xuhao">序号</span>
            <span id="content">投诉内容</span>
            <span id="feedtime">投诉状态</span>
        </div>
        <br>
        <hr>
        <%
            int len1 = 10;
            if (lis1.size() < len1)
                len1 = lis1.size();
            for (int i = 0; i < len1; i++) {
                ComplainVo comp = (ComplainVo) lis1.get(i);
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
        %>
    </div>
</div>
<br>

<div class="panel panel-primary" style="width: 1100px; margin: auto">
    <div class="panel-heading">
        <h3 class="panel-title">申诉<span id="more"><a id="wm" href="/getallappeal">更多</a></span></h3>
    </div>
    <div class="panel-body">
        <div>
            <span id="xuhao">序号</span>
            <span id="content">申诉内容</span>
            <span id="feedtime">申诉状态</span>
        </div>
        <br>
        <hr>
        <%
            int len2 = 10;
            if (len2 > lis2.size())
                len2 = lis2.size();
            for (int i = 0; i < len2; i++) {
                ComplainVo comp = (ComplainVo) lis2.get(i);
                String des = comp.getDescription();
                if (des.length() > 20)
                    des = des.substring(0, 20);
        %>
        <div>
            <span id="xuhao"><%=i + 1%></span>
            <span id="content"><a href="/getappealdetil?appid=<%=comp.getId()%>"><%=des%>
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
        %>
    </div>
</div>
<br>

<div class="panel panel-primary" style="width: 1100px; margin: auto">
    <div class="panel-heading">
        <h3 class="panel-title">用户<span id="more"><a id="wm" href="/getalluser">更多</a></span></h3>
    </div>
        <span class="panel-body">
            <div id="userhead">
                <span id="xuhao">序号</span>
                <span id="username">用户名</span>
                <span id="compnum">被投诉次数</span>
                <span id="userstate">用户状态</span>
            </div>
            <br>
            <hr>
            <%
                int len3 = 10;
                if(lis3.size() < len3)
                    len3 = lis3.size();
                for (int i = 0; i < len3; i++){
                    UserVo uservo = (UserVo)lis3.get(i);
            %>
            <div id="userbody">
                <span id="xuhao"><%=i+1%>.</span>
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
            %>
</div>
</div>
<br>

<div class="panel panel-primary" style="width: 1100px; margin: auto">
    <div class="panel-heading">
        <h3 class="panel-title">反馈<span id="more"><a id="wm" href="/getallfedback">更多</a></span></h3>
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
            int len4 = 10;
            if (lis4.size() < len4)
                len4 = lis4.size();
            for (int i = 0; i < len4; i++) {
                FeedBackVo feed = (FeedBackVo) lis4.get(i);
                String des = feed.getDescription();
                if (20 < des.length())
                    des = des.substring(0, 20);
        %>
        <div id="feedbody">
            <span id="xuhao"><%=i + 1%>.</span>
            <span id="content"><a href="/getfedbackdetil?userid=<%=feed.getUserId()%>&&time=<%=feed.getTime()%>"><%=des%></a></span>
            <span id="feedtime"><%=feed.getTime()%></span>
        </div>
        <br>
        <%
            }
        %>
    </div>
</div>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>
