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
<title>服务中心</title>
</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"></jsp:include>
<% 
	Integer userID = new Integer(-1);
 	//UserVo userVo = new UserVo(1, "nihao", "nihao", "/SH_Books/images/touxiang.png", "nihao", 1, "2015-01-01", "nihao", "nihao", 0);
	UserVo userVo=new UserVo();
	int num=0;					//初始化未读消息数为0
	userVo=(UserVo)session.getAttribute("user");
	if(userVo!=null)
{
	userID=userVo.getId();		//从session中拿id
	session.setAttribute("user",userVo);
	InformDao InformDaoImpl = InformDaoImplFactory.getInformDaoImpl();
	List<InformVo> informs = null;
	InformVo informvo=new InformVo();	
	try 
	{	
		informs=(List<InformVo>)InformDaoImpl.count(userID);//调用count拿到所有未读消息
		//System.out.println(informs);
	} catch (SQLException e) 
	{
	e.printStackTrace();
	}
	num=informs.size();//num为未读消息数
}

%>
<br><br><br><br><br>
<H1 align="center"><a href="/feedback">反馈</a>
<a href="/InformServlet">通知(<%=num %>)</a>
<a href="/help">帮助</a></H1>
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>