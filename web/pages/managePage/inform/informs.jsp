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
<title>消息通知</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=basePath %>css/all.css">
</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"></jsp:include>
<br> <br>  
<p style="margin-left:350px;"><a href="/controlCenter" >返回服务中心</a></p>
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
 	 	InformVo inf=new InformVo();	
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
 			inf = (InformVo)informs.get(i);
 	 		BookDao BookDaoImpl = BookDaoImpFactory.getBookDaoImpl();
 	  		BookVo bookvo=new BookVo();	
 	  		try 
 	  		{	
 	  		bookvo=BookDaoImpl.findById(inf.getNum());	
 	  		} catch (SQLException e) 
 	  		{
 	  		e.printStackTrace();
 	  		}
 	  		String bookname=bookvo.getName();
 		%>
 		
 		<%String a; 
 		if (inf.getType()==1)
 		{
 			a="心愿单到货"; 
 			%>
 			<td><%=a %></td>
 			<% 
      		BookDao book = BookDaoImpFactory.getBookDaoImpl();
      		BookVo bok=new BookVo();	
      		try 
      		{	
      		bok=book.findById(inf.getNum());	
      		} catch (SQLException e) 
      		{
      		e.printStackTrace();
      		}
      		String bookName=bok.getName();
      		%>
      	
      		  <td class="jive-thread-name" width="20%"><a id="jive-thread-1" href="/ShowBookDetail?bookID=<%=inf.getNum() %>"><%=bookName%></a></td>
 		<%}
 		else if(inf.getType()==2)
 		{
 			a="订单通知";
 			%>
 			  <td><%=a%></td>
 			 <td><a href="/order"> 查看订单详情</a></td>
 		<% }
 		else if(inf.getType()==3)
 		{
 			a="被投诉通知";
 			//根据num中的id拿到bookid 显示bookname
 			BookDao book = BookDaoImpFactory.getBookDaoImpl();
 			ComplainDao com=ComplainDaoImpFactory.getCompDaoImp();
 			ComplainVo comvo=new ComplainVo();
      		BookVo bok=new BookVo();	
      		try 
      		{	
      		comvo=com.getCompById(inf.getNum());//从inform表里面num中的comid得到comvo
      		} catch (SQLException e) 
      		{
      		e.printStackTrace();
      		}
      		int id=comvo.getBookid();//由comvo得到bookid
      		String bookName=book.findById(id).getName();//由bookid得到bookname去显示
      		if(bookName.length()>10) bookName=bookName.substring(0,10);
 		%>
 				<td><%=a %></td>
      		  <td class="jive-thread-name" width="20%"><a id="jive-thread-1" href="/ShowBookDetail?bookID=<%=id %>"><%=bookName%></a></td>
 			 
 		<%
 			
 		}
 		else if(inf.getType()==4)
 		{
 			a="申诉成功";
 			BookDao book = BookDaoImpFactory.getBookDaoImpl();
 			ComplainDao com=ComplainDaoImpFactory.getCompDaoImp();
 			ComplainVo comvo=new ComplainVo();
      		BookVo bok=new BookVo();	
      		try 
      		{	
      		comvo=com.getCompById(inf.getNum());
      		} catch (SQLException e) 
      		{
      		e.printStackTrace();
      		}
      		int id=comvo.getBookid();
      		String bookName=book.findById(id).getName();
      		if(bookName.length()>10) bookName=bookName.substring(0,10);
 		%>
 		<td><%=a %></td>
      		  <td class="jive-thread-name" width="20%"><a id="jive-thread-1" href="/ShowBookDetail?bookID=<%=id %>"><%=bookName%></a></td>
 			 
 		<%
 		}
 		else if(inf.getType()==5)
 		{
 			a="冻结通知";
 			%>
 			<td><%=a %></td>
      		<td>您的账户已被冻结</td>
		<%

 		}
 		else if(inf.getType()==6)
 		{
 			a="解冻通知";
 			%>
			 <td><%=a %></td>
      		<td>您的账户已被解冻</td>
		<%
 		}
 		else if(inf.getType()==7)
 		{
 			a="申诉失败";
 			BookDao book = BookDaoImpFactory.getBookDaoImpl();
 			ComplainDao com=ComplainDaoImpFactory.getCompDaoImp();
 			ComplainVo comvo=new ComplainVo();
      		BookVo bok=new BookVo();	
      		try 
      		{	
      		comvo=com.getCompById(inf.getNum());
      		} catch (SQLException e) 
      		{
      		e.printStackTrace();
      		}
      	
      		int id=comvo.getBookid();
      		String bookName=book.findById(id).getName();
      		if(bookName.length()>10) bookName=bookName.substring(0,10);
 		%>
 			<td><%=a %></td>
      		  <td class="jive-thread-name" width="20%"><a id="jive-thread-1" href="/ShowBookDetail?bookID=<%=id %>"><%=bookName%></a></td>

 		<%
 		}
 		String str=inf.getTime().substring(0,19);
 		%>
          <td><%=str %></td>
       
          <%
         }%>
        </table>
        <ul id="page">
        <li><a href="/Informs?thisPage=${requestScope.thisPage-1}">上一页</a></li>
        <%int firstOne = thisPage%10==0?(((thisPage-1)/10)*10+1):((thisPage/10)*10+1);%>
        <%int lastOne = thisPage%10==0?(((thisPage-1)/10+1)*10):((thisPage/10+1)*10);%>
        <%for(int i=firstOne;i<=(lastOne>pageNum?pageNum:lastOne);i++){%>
        <li><a <%if(thisPage==i){out.print("id='thisPage'");}%> href="/Informs?thisPage=<%out.print(i);%>"><%out.print(i);%></a></li>
        <%}%>
        <%if(pageNum%10>0&&pageNum/10>(thisPage-1)/10){out.print("<a href='/Informs?thisPage="+((((thisPage-1)/10)+1)*10+1)+"'>&gt;&gt;</a>");}%>
        <li><a href="/Informs?thisPage=${requestScope.thisPage+1}">下一页</a></li>
</ul>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
  </body>
</html>
