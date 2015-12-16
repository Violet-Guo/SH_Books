<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>	
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.book.buy.vo.UserVo" %>
<%@ page import="com.book.buy.dao.InformDao" %>
<%@ page import="com.book.buy.factory.InformDaoImplFactory" %>
<%@ page import="com.book.buy.vo.InformVo" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=basePath %>css/all.css">
<title>服务中心</title>
</head>
<body>
<%UserVo userVo = (UserVo) session.getAttribute("user");%>
<%Boolean isLogin;%>
<%	int num=0;
    List<InformVo> informVos = null;
    if (userVo != null) {
        isLogin = true;
        InformDao informDao = InformDaoImplFactory.getInformDaoImpl();
        informVos = informDao.count(userVo.getId());
    } else {
        isLogin = false;
    }
%>
<%request.setAttribute("isLogin", isLogin);%>
<div id="head">
    <div id="top-navigat">
        <div id="menu">
            <c:if test="${isLogin}">
                <ul class="navigat">
                    <li>
                        <a href="/PersonInfoServlet">${user.username}</a>
                        <ul>
                            <li><a href="/cancel">注销</a></li>
                        </ul>
                    </li>
                    <li><a href="./InformServlet">消息(<%=informVos.size()%>)</a></li>
                </ul>
            </c:if>
            <c:if test="${!isLogin}">
                <ul class="navigat">
                    <li><a href="/login">请登录!</a></li>
                    <li><a href="/register">注册</a></li>
                </ul>
            </c:if>
            <ul class="navigat top-sell-book">
                
                <li>
                    <a href="/controlCenter">服务中心</a>
                    <ul>
                        <li><a href="/feedback">反馈</a></li>
                        <li><a href="/InformServlet">通知</a></li>
                        <li><a href="/help">帮助</a></li>
                    </ul>
                </li>
          
                <li>
                    <a href="/controlCenter">通知</a>
                    <ul>
                        <li><a href="/unreadServlet">未读通知</a></li>
                        <li><a href="/wantServlet">心愿单通知</a></li>
                        <li><a href="/listServlet">订单</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
        </div>
 
   
<%if (informVos!=null)num=informVos.size(); %>
<H1 align="center"><a href="/feedback">反馈</a>
<a href="/InformServlet">通知(<%=num %>)</a>
<a href="/help">帮助</a></H1>
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>