<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: chao
  Date: 2015/12/9
  Time: 23:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>评价列表</title>
    <link rel="stylesheet" href="../../css/bootstrap.min.css">
    <script src="../../js/jquery.min.js"></script>
    <script src="../../js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="../../css/all.css">
</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"/>
<div id="main" class="panel">
    <ul class="list-group">
        <li class="list-group-item active">给"${sellVo.username}"的评价</li>
        <c:forEach items="${evaluationVos}" var="evaluationVo" varStatus="status">
        <li class="list-group-item">
            <span class="badge">${userVos[status.count-1].username}</span>
            ${evaluationVo.content}
        </li>
        </c:forEach>
    </ul>
</div>
<jsp:include page="/pages/mainPage/foot.jsp"/>
</body>
</html>
