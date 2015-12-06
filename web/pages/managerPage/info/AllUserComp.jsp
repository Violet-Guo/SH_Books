<%@ page import="java.util.List" %>
<%@ page import="com.book.buy.vo.ComplainVo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.book.buy.vo.UserVo" %>
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
    <title></title>
</head>
<body>
    <jsp:include page="/pages/mainPage/managerhead.jsp"></jsp:include>
    <%
        request.setCharacterEncoding("utf-8");
        List<UserVo> lis = new ArrayList<>();
        lis = (ArrayList)request.getSession().getAttribute("allcompuser");
    %>

    <br>
    <div class="panel panel-primary" style="width: 1100px; margin: auto">
        <div class="panel-heading">
            <h3 class="panel-title">用户</h3>
        </div>
        <div class="panel-body">
            <div>
                <span style="position: absolute; left: 200px;">用户名</span>
                <span style="position: absolute; left: 400px">被投诉次数</span>
                <span style="position: absolute; left: 700px">用户状态</span>
            </div>
            <%
                for (int i = 0; i < lis.size(); i++){
                    UserVo uservo = (UserVo)lis.get(i);
            %>
            <%=i+1%>.
            <p style="position: absolute; left: 200px"><%=uservo.getName()%></p>
            <p style="position: absolute; left: 400px"><%=uservo.getComplainNum()%></p>
            <%
                if (3 < uservo.getComplainNum()){
            %>
            <p style="position: absolute; left: 700px">已冻结</p><br>
            <%
            }
            else{
            %>
            <p style="position: absolute; left: 700px;">正常</p><br>
            <%
                }
            %>
            <%
                }
            %>
            <br>
        </div>
    </div>
    <jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>
