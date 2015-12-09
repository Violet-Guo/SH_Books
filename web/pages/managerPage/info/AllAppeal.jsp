<%@ page import="java.util.List" %>
<%@ page import="com.book.buy.vo.ComplainVo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.book.buy.utils.Paging" %>
<%--
  Created by IntelliJ IDEA.
  User: violet
  Date: 2015/11/11
  Time: 16:05
  To change this template use File | Settings | File Templates.
  页面显示所有的申诉列表
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
    <title>申诉列表</title>
</head>
<body>
    <jsp:include page="/pages/mainPage/managerhead.jsp"></jsp:include>
    <%
        request.setCharacterEncoding("utf-8");
        List<ComplainVo> lis = new ArrayList<>();
        lis = (List)request.getSession().getAttribute("allappeal");
        Paging paging = (Paging)request.getSession().getAttribute("paging");
    %>

    <br>
    <div class="panel panel-primary" style="width: 1100px; margin: auto">
        <div class="panel-heading">
            <h3 class="panel-title">申诉</h3>
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
                for (int i = 0; i < lis.size(); i++) {
                    ComplainVo comp = (ComplainVo) lis.get(i);
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
                paging.printPage(out);
            %>
        </div>
    </div>
    <jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>
