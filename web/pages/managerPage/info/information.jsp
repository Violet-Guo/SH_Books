<%@ page import="com.book.buy.vo.ComplainVo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.book.buy.vo.UserVo" %>
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
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="<%=basePath %>css/all.css">
    <link rel="stylesheet" href="<%=basePath %>css/bootstrap.min.css">
    <title>管理员主页</title>
</head>
<body>
    <jsp:include page="/pages/mainPage/managerhead.jsp"></jsp:include>
    <%
        request.setCharacterEncoding("utf-8");
        List<ComplainVo> lis1 = (ArrayList)request.getSession().getAttribute("allcomp");   //投诉

        List<ComplainVo> lis2 = (ArrayList)request.getSession().getAttribute("allappeal");    //申诉

        List<UserVo> lis3 = (ArrayList)request.getSession().getAttribute("allcompuser");

    %>
    <br>
    <div class="panel panel-primary" style="width: 1100px; margin: auto">
        <div class="panel-heading">
            <h3 class="panel-title">投诉<span style="float: right"><a style="color: white" href="AllComp.jsp">更多</a></span></h3>
        </div>
        <div class="panel-body">
            <%
                int len1 = 10;
                if(lis1.size() < len1)
                    len1 = lis1.size();
                for (int i = 0; i < len1; i++){
                    ComplainVo comp = (ComplainVo)lis1.get(i);
                    String des = comp.getDescription();
                    if (des.length() > 20)
                        des.substring(0, 20);
            %>
            <%=i+1%>.&nbsp;&nbsp;<a style="color: black" href="/getcompdetil?compid=<%=comp.getId()%>"><%=des%></a> &nbsp;&nbsp;
            <%
                if (0 == comp.getState()){
            %>
            <p style="float: right">未处理</p><br>
            <%
            }
            else{
            %>
            <p style="float: right">已处理</p><br>
            <%
                }
            %>
            <%
                }
            %>
        </div>
    </div>
    <br>
    <div class="panel panel-primary" style="width: 1100px; margin: auto">
        <div class="panel-heading">
            <h3 class="panel-title">申诉<span style="float: right"><a style="color: white" href="AllAppeal.jsp">更多</a></span></h3>
        </div>
        <div class="panel-body">
            <%
                int len2 = 10;
                if(len2 > lis2.size())
                    len2 = lis2.size();
                for (int i = 0; i < len2; i++){
                    ComplainVo comp = (ComplainVo)lis2.get(i);
                    String des = comp.getDescription();
                    if (des.length() > 20)
                        des.substring(0, 20);
            %>
            <%=i+1%>.&nbsp;&nbsp;<a style="color: black" href="/getappealdetil?appid=<%=comp.getId()%>"><%=des%></a>&nbsp;&nbsp;
            <%
                if (0 == comp.getState()){
            %>
            <p style="float: right">未处理</p><br>
            <%
            }
            else{
            %>
            <p style="float: right">已处理</p><br>
            <%
                }
            %>
            <%
                }
            %>
        </div>
    </div>
    <br>
    <div class="panel panel-primary" style="width: 1100px; margin: auto">
        <div class="panel-heading">
            <h3 class="panel-title">用户<span style="float: right;"><a style="color: white" href="AllUserComp.jsp">更多</a></span></h3>
        </div>
        <span class="panel-body">
            <div>
                <span style="position: absolute; left: 200px;">用户名</span>
                <span style="position: absolute; left: 400px">被投诉次数</span>
                <span style="position: absolute; left: 700px">用户状态</span>
            </div>
            <%
                int len3 = 10;
                if(lis3.size() < len3)
                    len3 = lis3.size();
                for (int i = 0; i < len3; i++){
                    UserVo uservo = (UserVo)lis3.get(i);
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
    <br>
    <jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>
