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
    <script src="<%=basePath %>js/jquery.min.js"></script>
    <title>修改管理员密码</title>
</head>
<body>
    <jsp:include page="/pages/mainPage/managerhead.jsp"></jsp:include>
    <div id="managerInfo">
        <form action="/changemanagerinfo" method="post">
            原密码：<input type="password" name="oldpass"><br>
            新密码：<input type="password" name="newpass1"><br>
            新密码：<input type="password" name="newpass2"><br>
            <script>
                function judge(){
                    var oldpass = $("input[name='oldpass']").val();
                    var newpass1 = $("input[name='newpass1']").val();
                    var newpass2 = $("input[name='newwpass2']").val();
                    if(oldpass == ""){
                        alert("原密码不能为空");
                        return false;
                    }
                    if(newpass1 == ""){
                        alert("新密码不能为空");
                        return false;
                    }
                    if(newpass2 == ""){
                        alert("新密码不能为空");
                        return false;
                    }
                    return true;
                }
            </script>
            <input id="sbutton" type="submit" onclick="return judge()" value="确定修改">
        </form>
    </div>
    <br>
    <jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>
