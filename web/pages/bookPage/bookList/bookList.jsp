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
<link rel="stylesheet" href="<%=basePath %>css/bookList.css">
<title>图书列表</title>
</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"></jsp:include>
<div id = "bl">
	<div id = "bltop">
		<p id = "bltopnomove">已选中：</p>&nbsp;&nbsp;&nbsp;&nbsp;${sessionScope.majorNamet}&nbsp;&nbsp;${sessionScope.tnianji}&nbsp;&nbsp;${sessionScope.newOld}
		<p id = "bltopnomove">专业：</p>
		<div id = "zhuanye">&nbsp;&nbsp;&nbsp;&nbsp;
			<c:set var="t" value="1"></c:set>
			<c:forEach items="${sessionScope.mList}" var="maj">
				<a href="/SearchBook?method=1&majorNamet=${maj.name}">${maj.name}</a>&nbsp;&nbsp;
				<c:set var="t" value="${t+1}"></c:set>
				<c:if test="${t%10==0}">
					<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;
				</c:if>
			</c:forEach>
		</div>
		<br/>
		<p id = "bltopnomove">年级：</p>
		<div id = "nianji">&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="/SearchBook?method=1&nianji=1">大一</a>&nbsp;&nbsp;
			<a href="/SearchBook?method=1&nianji=2">大二</a>&nbsp;&nbsp;
			<a href="/SearchBook?method=1&nianji=3">大三</a>&nbsp;&nbsp;
			<a href="/SearchBook?method=1&nianji=4">大四</a>
		</div>
		<br/>
		<p id = "bltopnomove">新旧：</p>
		<div id = "oldAndNew">&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="/SearchBook?method=1&oldAndNew=10">全新</a>&nbsp;&nbsp;
			<a href="/SearchBook?method=1&oldAndNew=9">九成新</a>&nbsp;&nbsp;
			<a href="/SearchBook?method=1&oldAndNew=8">八成新</a>&nbsp;&nbsp;
			<a href="/SearchBook?method=1&oldAndNew=5">五成新</a>	&nbsp;&nbsp;		
			<a href="/SearchBook?method=1&oldAndNew=4">五成以下</a>
		</div>
		<br/>
	</div>
	<div id = "blbuttom">
		<c:if test="${sessionScope.bList.size() != 0}">
		<c:forEach items="${sessionScope.bList }" var="bok">
			<div id = "blbook">
				<a href="/ShowBookDetail?bookID=${bok.id}"><img  id = "blimage" alt="暂无图片" src="${bok.imagePath}"/></a>
				<br/><p id = "blname">${bok.name}</p>
			</div>
		</c:forEach>
		</c:if>
		<c:if test="${sessionScope.bList.size() == 0}">
			<div id = "blnobook">
				<h2>您要查找的图书不存在</h2><br/>
				<a href="#"><input id = "pbutton" type="button" value="是否加入心愿单？"></a>
			</div>
		</c:if>
		<br/>
	</div>
	<br/>
	<div>
		<ul id="page">
	        <li><a href="/SearchBook?fenye=${sessionScope.fenye}&thisPage=${sessionScope.thisPage-1}">上一页</a></li>
	        <%int thisPage = (Integer)session.getAttribute("thisPage"); %>
	        <%int pageNum = (Integer)session.getAttribute("pageNum"); %>
	        <%int firstOne = thisPage%10==0?(((thisPage-1)/10)*10+1):((thisPage/10)*10+1);%>
	        <%int lastOne = thisPage%10==0?(((thisPage-1)/10+1)*10):((thisPage/10+1)*10);%>
	        <%for(int i=firstOne;i<=(lastOne>pageNum?pageNum:lastOne);i++){%>
	        <li><a <%if(thisPage==i){out.print("id='thisPage'");}%> href="/SearchBook?fenye=${sessionScope.fenye}&thisPage=<%out.print(i);%>"><%out.print(i);%></a></li>
	        <%}%>
	        <%if(pageNum%10>0&&pageNum/10>(thisPage-1)/10){out.print("<a href='/SearchBook?fenye=${sessionScope.fenye}&thisPage="+((((thisPage-1)/10)+1)*10+1)+"'>&gt;&gt;</a>");}%>
	        <li><a href="/SearchBook?fenye=${sessionScope.fenye}&thisPage=${sessionScope.thisPage+1}">下一页</a></li>
		</ul>
	</div>
</div>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>