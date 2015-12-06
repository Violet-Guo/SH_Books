<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: chao
  Date: 2015/11/12
  Time: 14:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>二手书网</title>
    <meta content="text/html">
    <base target="_blank">
    <link type="text/css" rel="stylesheet" href="../../css/all.css">
    <link type="text/css" rel="stylesheet" href="../../css/index.css">
    <script type="text/javascript" src="../../js/jquery.min.js"></script>
    <script type="text/javascript" language="JavaScript" src="../../js/index.js"></script>
</head>
<body>
<jsp:include page="head.jsp"/>

<!--主要内容部分-->
<div id="main">
    <!--分类弹出框，左边部分-->
    <div id="top-left-pop">
        <h3>专业</h3>
        <ul id="major-all">
            <!--这里循环生成专业大分类-->
            <c:forEach items="${majorVosDep}" var="majorVoDep">
                <li>${majorVoDep.department}
                    <div class="top-pop">
                        <!--这里是专业小分类-->
                        <c:forEach items="${majorVosName}" var="majorVoName">
                            <c:if test="${majorVoName.getDepartment().equals(majorVoDep.getDepartment())}">
                                <a href="/SearchBook?majorName=${majorVoName.name}">${majorVoName.name}</a>
                            </c:if>
                        </c:forEach>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
    <!--热销书籍部分-->
    <div class="hotBookDiv">
        <h2>最新上架</h2>
        <ul>
            <c:forEach items="${bookLastVos}" var="bookVo" begin="0" end="4">
                <li>
                    <a href="/ShowBookDetail?bookID=${bookVo.id}"><img src="${bookVo.imagePath}"/></a>

                    <div class="hotBook-title">
                        <span>
                            <p><a href="/ShowBookDetail?bookID=${bookVo.id}">${bookVo.name}</a></p>
                            <p><a href="/ShowBookDetail?bookID=${bookVo.id}">作者:${bookVo.author}</a></p>
                            <p><a href="/ShowBookDetail?bookID=${bookVo.id}">价格:${bookVo.price}</a></p>
                        </span>
                    </div>

                </li>
            </c:forEach>
        </ul>
    </div>

    <%--<div id="newBookDiv" class="hotBookDiv">
        <h2>最新上架</h2>
        <ul>
            <li><img src="./images/test.jpg"/></li>
            <li><img src="./images/test.jpg"/></li>
            <li><img src="./images/test.jpg"/></li>
            <li><img src="./images/test.jpg"/></li>
            <li><img src="./images/test.jpg"/></li>
        </ul>
    </div>--%>
</div>
<jsp:include page="foot.jsp"/>
</body>
</html>