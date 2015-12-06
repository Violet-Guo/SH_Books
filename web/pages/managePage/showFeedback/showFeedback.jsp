<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
 <%@ page import="java.util.List,com.book.buy.vo.*,com.book.buy.dao.*,com.book.buy.daoImp.*,java.io.*,javax.servlet.*,java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<title>反馈信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=basePath %>css/all.css">
</head>
  
 <body>
<jsp:include page="/pages/mainPage/head.jsp"></jsp:include>
<title>反馈信息</title><br><br>
 <table border=2 align="center">
 <tr><td>userID</td><td>description</td><td>time</td>
 </tr>		
 
  <%
  	List feedbacks=(List<FeedBackVo>)session.getAttribute("feedbacklist");
	//out.println(informs);
 	FeedBackVo feedbackvo=new FeedBackVo();	
%> 
 
	<%int everyPageNum = 5;%><%--读取文章总数和计算分页数--%>
	<%String strPage=(String) session.getAttribute("thisPage");//String strPage = request.getParameter("thisPage");%>
	<%Integer thisPage;%>
	<%if(strPage==null||strPage.equals("")){thisPage = 1;}else{%>
	<%thisPage = Integer.valueOf(strPage);}%><%--得到当前页数--%>
	<%int allNum= feedbacks.size();%>
	<%//int allNum = getArticle.getArticleConunt((String) request.getAttribute("leftNav"));%>
	<%int pageNum = allNum%everyPageNum==0?allNum/everyPageNum:allNum/everyPageNum+1;//计算总共多少页数%>
	<%if(thisPage>pageNum){thisPage=pageNum;}%>
	<%if(thisPage<=0){thisPage=1;}%>
	<%request.setAttribute("thisPage",thisPage);int num=(thisPage-1)*5;System.out.println(thisPage);%>
	<% 
	
	for (int i = (thisPage-1)*5; i<(thisPage)*5; i++) {        //显示list
 	  	%>
<tr>
 	    <% 	 
		num++;
		if(num>allNum)break;
 		feedbackvo = (FeedBackVo)feedbacks.get(i);	 
 		%>
 		
<td><%=feedbackvo.getUserId()%></td>
<td><%=feedbackvo.getDescription()%></td>
<td><%=feedbackvo.getTime()%></td>
 		 
</tr>
 		 <%} %>
 		
</table>


<ul id="page">
        <li><a href="./showFeedback?thisPage=${requestScope.thisPage-1}">上一页</a></li>
        <%int firstOne = thisPage%10==0?(((thisPage-1)/10)*10+1):((thisPage/10)*10+1);%>
        <%int lastOne = thisPage%10==0?(((thisPage-1)/10+1)*10):((thisPage/10+1)*10);%>
        <%for(int i=firstOne;i<=(lastOne>pageNum?pageNum:lastOne);i++){%>
        <li><a <%if(thisPage==i){out.print("id='thisPage'");}%> href="./showFeedback?thisPage=<%out.print(i);%>"><%out.print(i);%></a></li>
        <%}%>
        <%if(pageNum%10>0&&pageNum/10>(thisPage-1)/10){out.print("<a href='./showFeedback?thisPage="+((((thisPage-1)/10)+1)*10+1)+"'>&gt;&gt;</a>");}%>
        <li><a href="./showFeedback?thisPage=${requestScope.thisPage+1}">下一页</a></li>
</ul>
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>

</html>
