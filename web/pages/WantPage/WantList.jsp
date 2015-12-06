<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="<%=basePath %>css/all.css">
    <link rel="stylesheet" href="<%=basePath %>css/model.css">
    <script type="text/javascript">
        function createXHR() {
            if (window.XMLHttpRequest) {
                return new XMLHttpRequest();
            }
            else if (window.ActiveXObject) {
                return new ActiveXObject("Microsoft.XMLHTTP");
            }
        }


        //将已经删除的行从列表里清除，依据删除的行的id
        function deleteCurrentRow(obj) {
            var id = obj.id;
            var xhr = createXHR();
            xhr.onreadystatechange = function () {
                if (xhr.readyState == 4) {
                    if (xhr.status >= 200 && xhr.status < 300 || xhr.status == 304) {
                        alert(xhr.responseText);
                    }
                }
                xhr.open("get", "DeleteWant.do?id=" + id, true);
                xhr.send(null);
            }
            var tr = obj.parentNode.parentNode;
            var tbody = tr.parentNode;
            tbody.removeChild(tr);
            //只剩行首时删除表格
            if (tbody.rows.length == 1) {
                tbody.parentNode.removeChild(tbody);
            }
            alert("删除成功！");
        }

    </script>
    <title>心愿单列表</title>
</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"></jsp:include>
<div id="wanttable">
    <table id="wantlist" width="500" border="1" height="40">
        <tbody>
        <tr align="center">
            <td>图书名称</td>
            <td>图书作者</td>
            <td>出版年月</td>
            <td>图书ISBN号</td>
            <td>修改</td>
            <td>删除</td>
        </tr>
        <c:forEach var="book" items="${sessionScope.books}">
            <tr align="center">
                <td><c:out value="${book.name}"></c:out></td>
                <td><c:out value="${book.author}"></c:out></td>
                <td><c:out value="${book.publicYear}"></c:out></td>
                <td><c:out value="${book.pubNumber}"></c:out></td>
                <td>
                    <button onclick="window.location.href='AlterWant.jsp?bookname=${book.name}&writer=${book.author}&year=${book.publicYear}&ISBN=${book.pubNumber}&id=${book.id}'"
                            id="alter" value="<c:out value="${book.id}" />">修改
                    </button>
                </td>
                <td>
                    <button onclick="deleteCurrentRow(this)" id="<c:out value="${book.id}" />"
                            value="<c:out value="${book.id}" />">删除
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<br/>

<div id="page">
    <a href="WantList.jsp?curPage=1">首页</a>
    <a href="WantList.jsp?curPage=${curpage-1}">上一页</a>
    <a href="WantList.jsp?curPage=${curpage+1}">下一页</a>
    <a href="WantList.jsp?curPage=${sessionScope.maxpage}">尾页</a><br/>

    <div id="count">第<c:out value="${curpage}"></c:out>页/共<c:out value="${sessionScope.maxpage}"></c:out>页</div>
</div>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>