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
<title>用户注册</title>
</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"></jsp:include>
<div id = "login">
	<h1>用户注册</h1>
	<form action="/SH_Books/RegisterServlet" method="post">
		<div id = "logincenter">
			账&nbsp;号：<input type="text" name = "xuehao"/><br/>
			用户名：<input type="text" name = "username"/><br/>
			密&nbsp;码：<input type="password" name = "mima"/><br/>
			手&nbsp;机：<input type="text" name = "tel"/><br/>
			Q&nbsp;Q：&nbsp;<input type="text" name = "QQ"/><br/>
			<input id = "pbutton" type="submit" value="注册"/>
			<input id = "pbutton" type="reset" value="重置"/>
		</div>
	</form>
</div>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>