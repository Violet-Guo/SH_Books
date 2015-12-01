<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>

<title>反馈</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=basePath %>css/all.css">

<title>服务中心</title>


</head>

<body>
<jsp:include page="/pages/mainPage/head.jsp"></jsp:include>
		<form action="../../../FeedBack" method="post">
		<br><br>
		<input type="hidden" name="action" value="post" />
				<h2 align="center">账号:					
		<textarea  align="center" name="userId" rows="2" cols="80" ></textarea></h2>
		<br>
		<h2 align="center">内容:
		<textarea  align="center" name="description" rows="15" cols="80" ></textarea></h2>
		<br>	<br>

		<input  style="margin-left:650px;" type="submit" value="发布" >
		<input align="center" type="reset" value="重置" >
		</form>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>



</html>