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
<title>添加心愿单</title>
</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"></jsp:include><br/><br/><br/>
<div id="msg">
<h1>抱歉,目前没有卖家发布"${param.bookname}"这本书，是否添加到心愿单？</h1></div><br/>
<div id="button">
<input type="button" style="font-size:15px" id="add" value="添加到心愿单" onclick="window.location.href='../WantPage/AddWant.jsp'">
<input type="button" style="font-size:15px" id="another" value="查找其他书籍" onclick="window.location.href='alter.jsp?bookid=${book.id}'">
</div>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>