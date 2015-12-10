<%--
  Created by IntelliJ IDEA.
  User: chao
  Date: 2015/12/8
  Time: 13:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订单生成成功</title>
    <link type="text/css" rel="stylesheet" href="../../css/all.css">
    <style>
        #main{
            width: 1100px;
            margin: auto;
        }
        #main .success{
            display: table;
            margin: auto;
            padding-top: 100px;
            font-size: 16px;
        }
        #main .success li{
            float: left;
            padding-left: 15px;
            padding-right: 15px;
        }
    </style>
</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"/>
<div id="main">
    <ul class="success">
        <li>订单生成成功!</li>
        <li><a href="/order">查看订单</a></li>
        <li><a href="/">返回首页</a></li>
    </ul>
</div>

<jsp:include page="/pages/mainPage/foot.jsp"/>
</body>
</html>
