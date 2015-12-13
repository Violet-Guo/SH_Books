<%--
  Created by eclipse.
  User: 张黎明
  Date: 2015/11/11
  Time: 22:45
  通知详情
--%><%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List,com.book.buy.vo.*,com.book.buy.dao.*,com.book.buy.daoImp.*,java.io.*,com.book.buy.factory.*,javax.servlet.*,java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>订单通知</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=basePath %>css/all.css">
</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"></jsp:include>
<br> <br>  
 <table border=2 align="center">
 <tr><td>类别</td><td>详情</td><td>时间</td></tr>
      <%		
		Integer userID = new Integer(-1);
      	// userID=1;    
		userID = (Integer) session.getAttribute("userID"); 
		String href = "";// 跳转的界面
		if (userID == null ) 
		{
			href = "/login";
			out.print("<script language='javascript'>alert('你的登录状态有问题,请重新登录~~');window.location.href='"
					+ href + "';</script>"); // 页面重定向
		}
		%>		
		<%
		List<InformVo> informs=null;
		informs=(List<InformVo>)session.getAttribute("informs");
  		//out.println(informs);
 	 	InformVo informvo=new InformVo();	
	%>
 	<% int everyPageNum = 10;%>
 	<%String strPage=(String) session.getAttribute("thisPage");%>

 	<%Integer thisPage;%>
 	<%if(strPage==null||strPage.equals("")){thisPage = 1;}else{%>
 	<%thisPage = Integer.valueOf(strPage);}%><%--得到当前页数--%>
 	<%int allNum= informs.size();%>
 	<%//int allNum = getArticle.getArticleConunt((String) request.getAttribute("leftNav"));%>
 	<%int pageNum = allNum%everyPageNum==0?allNum/everyPageNum:allNum/everyPageNum+1;//计算总共多少页数%>
 
 	<%if(thisPage>pageNum){thisPage=pageNum;}%>
 	<%if(thisPage<=0){thisPage=1;}int num=(thisPage-1)*10;%>
 	<%request.setAttribute("thisPage",thisPage);%>
 	<% 
 		for (int i = (thisPage-1)*10; i<(thisPage)*10; i++) {        //显示list
 	 	  	%>
 	 	    <tr>
 	 	    <% 	 
 	 	   num++;
 	 	 	 if(num>allNum)break;
 			informvo = (InformVo)informs.get(i);
 	 		BookDao BookDaoImpl = BookDaoImpFactory.getBookDaoImpl();
 	  		BookVo bookvo=new BookVo();	
 	  		try 
 	  		{	
 	  		bookvo=BookDaoImpl.findById(informvo.getNum());	
 	  		} catch (SQLException e) 
 	  		{
 	  		e.printStackTrace();
 	  		}
 	  		String bookname=bookvo.getName();
 		%>
 		
 		<%String a; if (informvo.getType()==1) a="心愿单到货"; else a="订单通知";%> <td><%= a %></td>
         <%if(informvo.getType()==1) {%>
         
 		 <td class="jive-thread-name" width="20%"><a id="jive-thread-1" href="/ShowBookDetail?bookID=<%=informvo.getNum() %>"><%=bookname%></a></td>
 		<% }%>
 		   <% if(informvo.getType()==2) {%>
 		    <td class="jive-thread-name" width="20%"><a id="jive-thread-1" href="/order?isUser=seller">查看详情</a></td>
 		<% }
 		String str=informvo.getTime().substring(0,19);%>
 		<td><%=str%></td>		 
 		 </tr>
 		 <%} %>
        </table>
        <ul id="page">
        <li><a href="/listServlet?thisPage=${requestScope.thisPage-1}">上一页</a></li>
        <%int firstOne = thisPage%10==0?(((thisPage-1)/10)*10+1):((thisPage/10)*10+1);%>
        <%int lastOne = thisPage%10==0?(((thisPage-1)/10+1)*10):((thisPage/10+1)*10);%>
        <%for(int i=firstOne;i<=(lastOne>pageNum?pageNum:lastOne);i++){%>
        <li><a <%if(thisPage==i){out.print("id='thisPage'");}%> href="/listServlet?thisPage=<%out.print(i);%>"><%out.print(i);%></a></li>
        <%}%>
        <%if(pageNum%10>0&&pageNum/10>(thisPage-1)/10){out.print("<a href='/listServlet?thisPage="+((((thisPage-1)/10)+1)*10+1)+"'>&gt;&gt;</a>");}%>
        <li><a href="/listServlet?thisPage=${requestScope.thisPage+1}">下一页</a></li>
</ul>
<br><br><br>
<p align="center"><a href="/controlCenter" >返回服务中心</a></p>
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
  </body>
</html>
