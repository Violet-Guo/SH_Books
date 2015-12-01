<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>	
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List,com.book.buy.vo.*,javax.servlet.*,com.book.buy.dao.*,com.book.buy.daoImp.*,com.book.buy.factory.*,java.io.*,java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=basePath %>css/all.css">

<title>管理中心</title>
</head>

<body>
<jsp:include page="/pages/mainPage/head.jsp"></jsp:include>
<% 
Integer userID = new Integer(-1);
 // UserVo userVo = new UserVo(1, "nihao", "nihao", "/SH_Books/images/touxiang.png", "nihao", 1, "2015-01-01", "nihao", "nihao", 0);
UserVo userVo=new UserVo();
userVo=(UserVo)session.getAttribute("user");
//userID=userVo.getId();
//session.setAttribute("userID", userID);
session.setAttribute("user",userVo);
InformDao InformDaoImpl = InformDaoImplFactory.getInformDaoImpl();
List<InformVo> informs = null;
InformVo informvo=new InformVo();
try {
	
	informs=(List<InformVo>)InformDaoImpl.count(userID);
	//System.out.println(informs);
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

int num=informs.size();
%>
<br><br><br><br><br>
                     <H1 align="center"><a href="../feedBack/FeedBack.jsp">反馈</a>
                     <a href="../../../Inform">通知(<%=num %>)</a>
                    <a href="../help/help.jsp">帮助</a></H1>

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>


</html>