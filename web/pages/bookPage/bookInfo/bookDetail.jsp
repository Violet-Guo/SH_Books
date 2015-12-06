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
<link rel="stylesheet" href="<%=basePath %>css/lmbookdetails.css">
<title>图书详情</title>
</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"></jsp:include>
<div id = "bdtop">
	<div id = "bdhead">
		<a href="#">查看买家评价</a>
	</div>
	<div id = "bdleft">
		<img id = "dbleftimage" alt="暂时没有图片" src="${sessionScope.bookDetils.imagePath}">
		<br/><br/><br/>
			<input id = "pbutton1" type="button" value="投诉" onclick="window.location='/addComp?bookid=${sessionScope.bookDetils.id}'"/>
	 	</div>
	<div id = "bdrighttop">
		书名：<span id = "booktitle">${sessionScope.bookDetils.name}</span>
		作者：<span id = "bookauthor">${sessionScope.bookDetils.author}</span>
		新旧程度：<span id = "bookoldgrade">${sessionScope.newOld}</span>
		价格：<span id = "bookprice">${sessionScope.bookDetils.price} 元</span>
		联系人：<span id = "user">${sessionScope.userVo.name}</span>
		联系电话：<span id = "tel">${sessionScope.userVo.phoneNumber}</span>
	</div>
	<br/><br/>
	<div id = "bdrightdown">
		<input id = "pbutton" type="button" value="添加到购物车" onclick="window.location='#'"/>
		<input id = "pbutton" type="button" value="一键下单"  onclick="window.location='#'"/>		
	</div>
<br/>
<hr/>
<br/>
<div id = "bdbutton">
	<h1>卖家描述：</h1>
	<p id = "bookdescription">&nbsp;&nbsp;&nbsp;${sessionScope.bookDetils.description}</p>
</div>
</div>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>