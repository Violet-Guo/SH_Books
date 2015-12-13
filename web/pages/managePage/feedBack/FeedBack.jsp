<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<title>反馈</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=basePath %>css/all.css">
</head>

<body>
		<jsp:include page="/pages/mainPage/head.jsp"></jsp:include>
		<form action="/FeedBackServlet" method="post" align="center"><br><br>
		<input type="hidden" name="action" value="post" />
		<h2 >请输入反馈内容：<br>
		<textarea name="description" rows="15" cols="80" ></textarea></h2>
		<input  type="submit" value="发布" >
		<input type="reset" value="重置" >	
		</form><br><br><br><br><br><br>
		<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>