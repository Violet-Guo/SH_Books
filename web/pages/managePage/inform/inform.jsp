<%--
  Created by eclipse.
  User: 张黎明
  Date: 2015/11/11
  Time: 22:45
  4类通知
--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
 <%@ page import="java.util.List,com.book.buy.vo.*,com.book.buy.dao.*,com.book.buy.daoImp.*,com.book.buy.factory.*,java.io.*,javax.servlet.*,java.sql.*" %>
<%@ page import="java.util.ArrayList" %>
<html>
<head>
    <link rel="stylesheet" href="<%=basePath %>css/all.css">
    <link rel="stylesheet" href="<%=basePath %>css/panel.css">
    <link rel="stylesheet" href="<%=basePath %>css/informbody.css">
    <title>消息通知</title>
</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"></jsp:include>

<%
    request.setCharacterEncoding("utf-8");

    List<InformVo> lis1 = (List<InformVo>)session.getAttribute("informs");   //所有消息

    List<InformVo> lis2 = (ArrayList) request.getSession().getAttribute("wants");    //心愿单通知

    List<InformVo> lis3 = (ArrayList) request.getSession().getAttribute("list");	//订单通知

    List<InformVo> lis4 = (ArrayList) request.getSession().getAttribute("unread");	//未读通知
     
%>
<br>

<div class="panel panel-primary" style="width: 1100px; margin: auto">
    <div class="panel-heading">
        <h3 class="panel-title">所有消息<span id="more"><a id="wm" href="/Informs">更多</a></span></h3>
    </div>
    <div class="panel-body">
        <div id="infhead">
        
            <span id="type">通知类型</span>
            <span id="des">详细</span>
            <span id="time">时间</span>
        </div>
        <br>
        <hr>
        <%
            int len1 = 10;
            if (lis1.size() < len1)
                len1 = lis1.size();
            for (int i = 0; i < len1; i++) 
            {
                InformVo inf = (InformVo) lis1.get(i);
        %>
              
 		<%String a; 
 		if (inf.getType()==1)
 		{
 			a="心愿单到货"; 
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
      		  <span id="type"><%=a%></span>
            <span id="des"><a href="/ShowBookDetail?bookID=<%=inf.getNum()%>"> <%=bookname %></a></span>
 		<%}
 		else
 		{
 			a="订单通知";
 			%>
 			  <span id="type"><%=a%></span>
 			  <span id="des"><a href="/order"> 查看订单详情</a></span>
 		<% }
 		String str=inf.getTime().substring(0,19);
 		%>
          <span id="time"><%= str %></span>
          <br>
          <%
         }
          %>
        </div>
        <br>
       
    </div>
<br>

<br>

<div class="panel panel-primary" style="width: 1100px; margin: auto">
    <div class="panel-heading">
        <h3 class="panel-title">未读消息<span id="more"><a id="wm" href="/unreadServlet">更多</a></span></h3>
    </div>
    <div class="panel-body">
        <div id="infhead">
        
            <span id="type">通知类型</span>
            <span id="des">详细</span>
            <span id="time">时间</span>
        </div>
        <br>
        <hr>
        <%
        len1 = 10;
            if (lis4.size() < len1)
                len1 = lis4.size();
            for (int i = 0; i < len1; i++) 
            {
                InformVo inf = (InformVo) lis4.get(i);
        %>
      
 		<%String a; 
 		if (inf.getType()==1)
 		{
 			a="心愿单到货"; 
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
      		  <span id="type"><%=a%></span>
            <span id="des"><a href="/ShowBookDetail?bookID=<%=inf.getNum()%>"> <%=bookname %></a></span>
 		<%}
 		else
 		{
 			a="订单通知";
 			%>
 			  <span id="type"><%=a%></span>
 			  <span id="des"><a href="/order"> 查看订单详情</a></span>
 		<% }
 		String str=inf.getTime().substring(0,19);
 		%>
          <span id="time"><%= str %></span>
          <br>
          <%
         }
          %>
        </div>
        <br>
       
    </div>
<br>
<br>

<div class="panel panel-primary" style="width: 1100px; margin: auto">
    <div class="panel-heading">
        <h3 class="panel-title">心愿单通知<span id="more"><a id="wm" href="/wantServlet">更多</a></span></h3>
    </div>
    <div class="panel-body">
        <div id="infhead">
        
            <span id="type">通知类型</span>
            <span id="des">详细</span>
            <span id="time">时间</span>
        </div>
        <br>
        <hr>
        <%
           len1 = 10;
            if (lis2.size() < len1)
                len1 = lis2.size();
            for (int i = 0; i < len1; i++) 
            {
                InformVo inf = (InformVo) lis2.get(i);
        %>
   
 	<%String a; 
 		if (inf.getType()==1)
 		{
 			a="心愿单到货"; 
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
      		  <span id="type"><%=a%></span>
            <span id="des"><a href="/ShowBookDetail?bookID=<%=inf.getNum()%>"> <%=bookname %></a></span>
 		<%}
 		else
 		{
 			a="订单通知";
 			%>
 			  <span id="type"><%=a%></span>
 			  <span id="des"><a href="/order"> 查看订单详情</a></span>
 		<% }
 		String str=inf.getTime().substring(0,19);
 		%>
          <span id="time"><%= str %></span>
          <br>
          <%
         }
          %>
        </div>
        <br>
       
    </div>
<br>
<br>

<div class="panel panel-primary" style="width: 1100px; margin: auto">
    <div class="panel-heading">
        <h3 class="panel-title">订单通知<span id="more"><a id="wm" href="/listServlet">更多</a></span></h3>
    </div>
    <div class="panel-body">
        <div id="infhead">
     
            <span id="type">通知类型</span>
            <span id="des">详细</span>
            <span id="time">时间</span>
        </div>
        <br>
        <hr>
        <%
            len1 = 10;
            if (lis3.size() < len1)
                len1 = lis3.size();
            for (int i = 0; i < len1; i++) 
            {
                InformVo inf = (InformVo) lis3.get(i);
        %>

 		<%String a; 
 		if (inf.getType()==1)
 		{
 			a="心愿单到货"; 
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
      		  <span id="type"><%=a%></span>
            <span id="des"><a href="/ShowBookDetail?bookID=<%=inf.getNum()%>"> <%=bookname %></a></span>
 		<%}
 		else
 		{
 			a="订单通知";
 			%>
 			  <span id="type"><%=a%></span>
 			  <span id="des"><a href="/order"> 查看订单详情</a></span>
 		<% }
 		String str=inf.getTime().substring(0,19);
 		%>
          <span id="time"><%= str %></span>
          <br>
          <%
         }
          %>
        </div>
        <br>
       
    </div>
<br>
<p align="center"><a href="/controlCenter" >返回服务中心</a></p>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>
          