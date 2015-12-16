<%@page import="com.book.buy.vo.LocationVo"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
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
			<li><a href="/InformServlet">消息（${sessionScope.num }）</a></li>
			<li><a href="/WantListServlet">心愿书籍</a></li>
			<li><a href="/publishedbooks">已发布图书</a></li>
		</ul>
	</div>
	<div id = "irighttop">
		<ul id = "ilist">
			<li id = "ilistitem"><a href="/order?state=waitmoney">待付款（）</a></li>
			<li id = "ilistitem"><a href="/order?state=waitsure">待确认（）</a></li>
			<li id = "ilistitem"><a href="/order?state=waiteva">待评价（）</a></li>
		</ul>
	</div>
	<form action="/ChangePersonInfoServlet" method="post" enctype="multipart/form-data">
		<div id = "irightbutton">
			<div>
				<p id = "icurword">当前头像：</p>
				<img id = "icurimage" alt="暂无头像" src="${sessionScope.user.headPhoto}"><br/><br/>
				请选择头像：<input type="file" name = "ifile"/>
			</div>
			学号：&nbsp;&nbsp;${sessionScope.user.name}<br/>
			用户名：&nbsp;<input type="text" name="username" value="${sessionScope.user.username}" /><br/>
			密码：&nbsp;&nbsp;<input type="password" name="mima"/><br/>
			电话：&nbsp;&nbsp;<input type="text" name="tel" value="${sessionScope.user.phoneNumber}"/><br/>
			QQ：&nbsp;&nbsp;&nbsp;<input type="text" name="qq" value="${sessionScope.user.qq}"/><br/>
			常用地址：
			<%
				LocationVo loc = (LocationVo)session.getAttribute("location");
				if(loc == null)
				    request.setAttribute("flag", 0);
				else
				    request.setAttribute("flag", 1);
			%>
			<select name="yuanqu">
				<option value="松园" <% if (request.getAttribute("flag").equals("1") && ((LocationVo) session.getAttribute("location")).getDorName()
					.equals("松园")) out.print("selected='selected'"); %>>松园</option>
				<option value="菊园" <% if (request.getAttribute("flag").equals("1") && ((LocationVo) session.getAttribute("location")).getDorName()
					.equals("菊园")) out.print("selected='selected'"); %>>菊园</option>
				<option value="柳园" <% if (request.getAttribute("flag").equals("1") && ((LocationVo) session.getAttribute("location")).getDorName()
					.equals("柳园")) out.print("selected='selected'"); %>>柳园</option>
				<option value="荷园" <% if (request.getAttribute("flag").equals("1") && ((LocationVo) session.getAttribute("location")).getDorName()
					.equals("荷园")) out.print("selected='selected'"); %>>荷园</option>
			</select>
			<input type="text" name="jihaolou" value="${sessionScope.location.dorNum}"/> 楼
			<input type="text" name="jilingji" value=" ${sessionScope.location.floorNum}"/> 室
			</div>
			<input id = "pbutton1" type="submit" value="修改"/>
	</form>
</div>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>