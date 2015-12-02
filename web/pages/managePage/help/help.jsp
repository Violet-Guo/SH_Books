<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>用户帮助</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=basePath %>css/all.css">




</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"></jsp:include>

<h1 align="center">使用帮助</h1><br><br>
<h2 align="center">买家使用说明          </h2><br><br>
<h3 align="center">   1.用户需登录方可进行交易         </h3><br>
<h3 align="center">   2.若系统无您所需书籍，可将该书籍添加至心愿单，该书籍上架时我们将第一时间通知您         </h3><br>
<h3 align="center">   3.书籍信息内容涉及敏感词汇、图文物不符者可对其进行投诉         </h3><br><br>
<h2 align="center">卖家使用说明</h2><br><br>
<h3 align="center">   1.用户需登录方可进行交易         </h3><br>
<h3 align="center">   2.发布图书应内容健康，图文物相符         </h3><br>
<h3 align="center">   3.书籍违规被强制下架后应修改内容并提出申诉         </h3><br>
<h3 align="center">   4.违规多次将被封号         </h3><br>
<img  src="../../../images/买卖时序图.jpg" style="margin-left:450px" width="500" height="800"/>
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>

</html>

