<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>	
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="<%=basePath %>css/all.css">
	<link rel="stylesheet" href="<%=basePath %>css/model.css">
<title>到货提示</title>
</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"></jsp:include>
<div id="container">
<div id ="msg">
<h1>系统消息：</h1>
<h2>&nbsp;&nbsp;&nbsp;&nbsp;	您心愿单中的书籍“${param.bookname}”有卖家发布了，是否前去查看书籍详情。</h2>
<div id="checkbutton">
<input type="button" id="check" style="font-size:15px" value="查看" onclick="window.location.href='alter.jsp?bookid=${book.id}'">
<input type="button" id="back" style="font-size:15px" value="返回" onclick="window.location.href='alter.jsp?bookid=${book.id}'">
</div>
</div>
</div>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>