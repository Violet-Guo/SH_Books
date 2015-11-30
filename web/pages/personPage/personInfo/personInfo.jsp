<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=basePath %>css/all.css">
<link rel="stylesheet" href="<%=basePath %>css/lmpersoninfo.css">
<title>个人信息</title>
</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"></jsp:include>
<div id = "personinfo">
	<div id = "ileft">
		<img id = "iimage" alt="暂无头像" src="${sessionScope.user.headPhoto}">
		<h3><strong>账号管理</strong></h3>
		<ul>
			<li><a href="#">个人资料</a></li>
			<li><a href="#">消息（${sessionScope.num }）</a></li>
			<li><a href="#">心愿书籍</a></li>
			<li><a href="#">已发布图书</a></li>
		</ul>
	</div>
	<div id = "irighttop">
		<ul id = "ilist">
			<li id = "ilistitem"><a href="#">待付款（）</a></li>
			<li id = "ilistitem"><a href="#">待发货（）</a></li>
			<li id = "ilistitem"><a href="#">待收货（）</a></li>
			<li id = "ilistitem"><a href="#">待评价（）</a></li>
			<li id = "ilistitem"><a href="#">退货</a></li>
		</ul>
	</div>
	<div id = "irightcenter">
		<input id = "pbutton" type="button" value="修改个人信息" onclick="window.location='./changePersonInfo.jsp'"/>
	</div>
	<div id = "irightbutton">
		<div>
			<p id = "icurword">当前头像：</p>
			<img id = "icurimage" alt="暂无头像" src="${sessionScope.user.headPhoto}">
		</div>
		<br/><br/>
		学&nbsp;号：&nbsp;&nbsp;${sessionScope.user.name}<br/>
		用户名：&nbsp;&nbsp;${sessionScope.user.username}<br/>
		密&nbsp;码：&nbsp;&nbsp;**********<br/>
		电&nbsp;话：&nbsp;&nbsp;${sessionScope.user.phoneNumber}<br/>
		QQ：&nbsp;&nbsp;&nbsp;${sessionScope.user.qq}<br/>
		常用地址：
		<c:if test="${sessionScope.location == null }">
			&nbsp;&nbsp;暂无
		</c:if>
		<c:if test="${sessionScope.location != null }">
			&nbsp;&nbsp;${sessionScope.location.dorName} ${sessionScope.location.floorNum}楼  ${sessionScope.location.dorNum}室<br>
		</c:if>
	</div>
</div>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>