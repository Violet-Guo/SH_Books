<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=basePath %>css/all.css">
<link rel="stylesheet" href="<%=basePath %>css/lmlogin.css">
<title>用户登录</title>
</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"></jsp:include>
<div id = "login">
	<h1>用户登录</h1>
	<form action="/LoginServlet" method="post">
		<div id = "logincenter">
			账号：<input type="text" name = "username"/><br/>
			密码：<input type="password" name = "password"/>
			<p id = "zhuce"><a href="/register">新用户注册</a></p>
			<input id = "pbutton" type="submit" value="登录" />
			<input id = "pbutton" type="reset" value="重置"/>
		</div>
	</form>
</div>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>