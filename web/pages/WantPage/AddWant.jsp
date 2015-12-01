<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript">
        var isNav4 = false, isNav5 = false, isIE4 = false
        var strSeperator = "-";
        var vDateType = 3;
        var vYearType = 4;
        var vYearLength = 2;
        var err = 0;
        if (navigator.appName == "Netscape") {
            if (navigator.appVersion < "5") {
                isNav4 = true;
                isNav5 = false;
            }
            else if (navigator.appVersion > "4") {
                isNav4 = false;
                isNav5 = true;
            }
        }
        else {
            isIE4 = true;
        }
        function DateFormat(vDateName, vDateValue, e, dateCheck, dateType) {
            vDateType = dateType;

            if (vDateValue == "~") {
                alert("AppVersion = " + navigator.appVersion + " \nNav. 4 Version = " + isNav4 + " \nNav. 5 Version = " + isNav5 + " \nIE Version = " + isIE4 + " \nYear Type = " + vYearType + " \nDate Type = " + vDateType + " \nSeparator = " + strSeperator);
                vDateName.value = "";
                vDateName.focus();
                return true;
            }
            var whichCode = (window.Event) ? e.which : e.keyCode;
            if (vDateValue.length > 8 && isNav4) {
                if ((vDateValue.indexOf("-") >= 1) || (vDateValue.indexOf("/") >= 1))
                    return true;
            }
            var alphaCheck = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ/-";
            if (alphaCheck.indexOf(vDateValue) >= 1) {
                if (isNav4) {
                    vDateName.value = "";
                    vDateName.focus();
                    vDateName.select();
                    return false;
                }
                else {
                    vDateName.value = vDateName.value.substr(0, (vDateValue.length - 1));
                    return false;
                }
            }
            if (whichCode == 8)
                return false;
            else {
                var strCheck = '47,48,49,50,51,52,53,54,55,56,57,58,59,95,96,97,98,99,100,101,102,103,104,105';
                if (strCheck.indexOf(whichCode) != -1) {
                    if (isNav4) {
                        if (((vDateValue.length < 6 && dateCheck) || (vDateValue.length == 7 && dateCheck)) && (vDateValue.length >= 1)) {
                            alert("错误的日期\n请重新输入");
                            vDateName.value = "";
                            vDateName.focus();
                            vDateName.select();
                            return false;
                        }
                        if (vDateValue.length == 6 && dateCheck) {
                            var mDay = vDateName.value.substr(2, 2);
                            var mMonth = vDateName.value.substr(0, 2);
                            var mYear = vDateName.value.substr(4, 4)
                            if (mYear.length == 2 && vYearType == 4) {
                                var mToday = new Date();
                                var checkYear = mToday.getFullYear() + 30;
                                var mCheckYear = '20' + mYear;
                                if (mCheckYear >= checkYear)
                                    mYear = '19' + mYear;
                                else
                                    mYear = '20' + mYear;
                            }
                            var vDateValueCheck = mMonth + strSeperator + mDay + strSeperator + mYear;
                            if (!dateValid(vDateValueCheck)) {
                                alert("错误的日期\n请重新输入");
                                vDateName.value = "";
                                vDateName.focus();
                                vDateName.select();
                                return false;
                            }
                            return true;
                        }
                        else {
                            if (vDateValue.length >= 8 && dateCheck) {
                                if (vDateType == 1) {
                                    var mDay = vDateName.value.substr(2, 2);
                                    var mMonth = vDateName.value.substr(0, 2);
                                    var mYear = vDateName.value.substr(4, 4)
                                    vDateName.value = mMonth + strSeperator + mDay + strSeperator + mYear;
                                }
                                if (vDateType == 2) {
                                    var mYear = vDateName.value.substr(0, 4)
                                    var mMonth = vDateName.value.substr(4, 2);
                                    var mDay = vDateName.value.substr(6, 2);
                                    vDateName.value = mYear + strSeperator + mMonth + strSeperator + mDay;
                                }
                                if (vDateType == 3) {
                                    var mMonth = vDateName.value.substr(2, 2);
                                    var mDay = vDateName.value.substr(0, 2);
                                    var mYear = vDateName.value.substr(4, 4)
                                    vDateName.value = mDay + strSeperator + mMonth + strSeperator + mYear;
                                }
                                var vDateTypeTemp = vDateType;
                                vDateType = 1;
                                var vDateValueCheck = mMonth + strSeperator + mDay + strSeperator + mYear;
                                if (!dateValid(vDateValueCheck)) {
                                    alert("错误的日期\n请重新输入");
                                    vDateType = vDateTypeTemp;
                                    vDateName.value = "";
                                    vDateName.focus();
                                    vDateName.select();
                                    return false;
                                }
                                vDateType = vDateTypeTemp;
                                return true;
                            }
                            else {
                                if (((vDateValue.length < 8 && dateCheck) || (vDateValue.length == 9 && dateCheck)) && (vDateValue.length >= 1)) {
                                    alert("错误的日期\n请重新输入");
                                    vDateName.value = "";
                                    vDateName.focus();
                                    vDateName.select();
                                    return false;
                                }
                            }
                        }
                    }
                    else {

                        if (((vDateValue.length < 8 && dateCheck) || (vDateValue.length == 9 && dateCheck)) && (vDateValue.length >= 1)) {
                            alert("错误的日期\n请重新输入");
                            vDateName.value = "";
                            vDateName.focus();
                            return true;
                        }
                        if (vDateValue.length >= 8 && dateCheck) {
                            if (vDateType == 1) {
                                var mMonth = vDateName.value.substr(0, 2);
                                var mDay = vDateName.value.substr(3, 2);
                                var mYear = vDateName.value.substr(6, 4)
                            }
                            if (vDateType == 2) {
                                var mYear = vDateName.value.substr(0, 4)
                                var mMonth = vDateName.value.substr(5, 2);
                                var mDay = vDateName.value.substr(8, 2);
                            }
                            if (vDateType == 3) {
                                var mDay = vDateName.value.substr(0, 2);
                                var mMonth = vDateName.value.substr(3, 2);
                                var mYear = vDateName.value.substr(6, 4)
                            }
                            if (vYearLength == 4) {
                                if (mYear.length < 4) {
                                    alert("错误的日期\n请重新输入");
                                    vDateName.value = "";
                                    vDateName.focus();
                                    return true;
                                }
                            }
                            var vDateTypeTemp = vDateType;
                            vDateType = 1;
                            var vDateValueCheck = mMonth + strSeperator + mDay + strSeperator + mYear;
                            if (mYear.length == 2 && vYearType == 4 && dateCheck) {
                                var mToday = new Date();
                                var checkYear = mToday.getFullYear() + 30;
                                var mCheckYear = '20' + mYear;
                                if (mCheckYear >= checkYear)
                                    mYear = '19' + mYear;
                                else
                                    mYear = '20' + mYear;
                                vDateValueCheck = mMonth + strSeperator + mDay + strSeperator + mYear;
                                if (vDateTypeTemp == 1)
                                    vDateName.value = mMonth + strSeperator + mDay + strSeperator + mYear;
                                if (vDateTypeTemp == 3)
                                    vDateName.value = mDay + strSeperator + mMonth + strSeperator + mYear;
                            }
                            if (!dateValid(vDateValueCheck)) {
                                alert("错误的日期\n请重新输入");
                                vDateType = vDateTypeTemp;
                                vDateName.value = "";
                                vDateName.focus();
                                return true;
                            }
                            vDateType = vDateTypeTemp;
                            return true;
                        }
                        else {
                            if (vDateType == 1) {
                                if (vDateValue.length == 2) {
                                    vDateName.value = vDateValue + strSeperator;
                                }
                                if (vDateValue.length == 5) {
                                    vDateName.value = vDateValue + strSeperator;
                                }
                            }
                            if (vDateType == 2) {
                                if (vDateValue.length == 4) {
                                    vDateName.value = vDateValue + strSeperator;
                                }
                                if (vDateValue.length == 7) {
                                    vDateName.value = vDateValue + strSeperator;
                                }
                            }
                            if (vDateType == 3) {
                                if (vDateValue.length == 2) {
                                    vDateName.value = vDateValue + strSeperator;
                                }
                                if (vDateValue.length == 5) {
                                    vDateName.value = vDateValue + strSeperator;
                                }
                            }
                            return true;
                        }
                    }
                    if (vDateValue.length == 10 && dateCheck) {
                        if (!dateValid(vDateName)) {
                            alert("错误的日期\n请重新输入");
                            vDateName.focus();
                            vDateName.select();
                        }
                    }
                    return false;
                }
                else {
                    if (isNav4) {
                        vDateName.value = "";
                        vDateName.focus();
                        vDateName.select();
                        return false;
                    }
                    else {
                        vDateName.value = vDateName.value.substr(0, (vDateValue.length - 1));
                        return false;
                    }
                }
            }
        }
        function dateValid(objName) {
            var strDate;
            var strDateArray;
            var strDay;
            var strMonth;
            var strYear;
            var intday;
            var intMonth;
            var intYear;
            var booFound = false;
            var datefield = objName;
            var strSeparatorArray = new Array("-", " ", "/", ".");
            var intElementNr;
            var strMonthArray = new Array(12);
            strMonthArray[0] = "Jan";
            strMonthArray[1] = "Feb";
            strMonthArray[2] = "Mar";
            strMonthArray[3] = "Apr";
            strMonthArray[4] = "May";
            strMonthArray[5] = "Jun";
            strMonthArray[6] = "Jul";
            strMonthArray[7] = "Aug";
            strMonthArray[8] = "Sep";
            strMonthArray[9] = "Oct";
            strMonthArray[10] = "Nov";
            strMonthArray[11] = "Dec";
            strDate = objName;
            if (strDate.length < 1) {
                return true;
            }
            for (intElementNr = 0; intElementNr < strSeparatorArray.length; intElementNr++) {
                if (strDate.indexOf(strSeparatorArray[intElementNr]) != -1) {
                    strDateArray = strDate.split(strSeparatorArray[intElementNr]);
                    if (strDateArray.length != 3) {
                        err = 1;
                        return false;
                    }
                    else {
                        strDay = strDateArray[0];
                        strMonth = strDateArray[1];
                        strYear = strDateArray[2];
                    }
                    booFound = true;
                }
            }
            if (booFound == false) {
                if (strDate.length > 5) {
                    strDay = strDate.substr(0, 2);
                    strMonth = strDate.substr(2, 2);
                    strYear = strDate.substr(4);
                }
            }
            if (strYear.length == 2) {
                strYear = '20' + strYear;
            }
            strTemp = strDay;
            strDay = strMonth;
            strMonth = strTemp;
            intday = parseInt(strDay, 10);
            if (isNaN(intday)) {
                err = 2;
                return false;
            }
            intMonth = parseInt(strMonth, 10);
            if (isNaN(intMonth)) {
                for (i = 0; i < 12; i++) {
                    if (strMonth.toUpperCase() == strMonthArray[i].toUpperCase()) {
                        intMonth = i + 1;
                        strMonth = strMonthArray[i];
                        i = 12;
                    }
                }
                if (isNaN(intMonth)) {
                    err = 3;
                    return false;
                }
            }
            intYear = parseInt(strYear, 10);
            if (isNaN(intYear)) {
                err = 4;
                return false;
            }
            if (intMonth > 12 || intMonth < 1) {
                err = 5;
                return false;
            }
            if ((intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7 || intMonth == 8 || intMonth == 10 || intMonth == 12) && (intday > 31 || intday < 1)) {
                err = 6;
                return false;
            }
            if ((intMonth == 4 || intMonth == 6 || intMonth == 9 || intMonth == 11) && (intday > 30 || intday < 1)) {
                err = 7;
                return false;
            }
            if (intMonth == 2) {
                if (intday < 1) {
                    err = 8;
                    return false;
                }
                if (LeapYear(intYear) == true) {
                    if (intday > 29) {
                        err = 9;
                        return false;
                    }
                }
                else {
                    if (intday > 28) {
                        err = 10;
                        return false;
                    }
                }
            }
            return true;
        }
        function LeapYear(intYear) {
            if (intYear % 100 == 0) {
                if (intYear % 400 == 0) {
                    return true;
                }
            }
            else {
                if ((intYear % 4) == 0) {
                    return true;
                }
            }
            return false;
        }
    </script>
    <link rel="stylesheet" href="<%=basePath %>css/all.css">
    <link rel="stylesheet" href="<%=basePath %>css/model.css">
    <title>添加心愿单</title>
</head>
<body>
<jsp:include page="/pages/mainPage/head.jsp"></jsp:include>
<div id="title"><h1>添加心愿单</h1></div>
<form action="/AddWantServlet" method="post">
    <table border="0" align="center" style="font-size:15px">
        <tr id="bookname" bgcolor="#FFFFCC">
            <td align="right">
                <div align="center">图书名称:</div>
            </td>
            <td align="left"><input type="text" name="name"></td>
        </tr>
        <tr id="pubyear" bgcolor="#FFFFCC">
            <td align="right">
                <div align="center">出版年月:</div>
            </td>
            <td align="left"><input type="text" name="year" onFocus="javascript:vDateType='2'"
                                    onKeyUp="DateFormat(this,this.value,event,false,'2')"
                                    onBlur="DateFormat(this,this.value,event,true,'2')"></td>
            <td>&nbsp;&nbsp;形如（YYYY-MM-DD）</td>
        </tr>
        <tr id="writer" bgcolor="#FFFFCC">
            <td align="right">
                <div align="center">图书作者:</div>
            </td>
            <td align="left"><input type="text" name="writer"></td>
        </tr>
        <tr id="ISBN" bgcolor="#FFFFCC">
            <td align="right">
                <div align="center">图书ISBN编号:</div>
            </td>
            <td align="left"><input type="text" name="ISBN"></td>
        </tr>
    </table>
    <div id="formbutton">
        <input type="submit" name="submit" value="提交">
        <input type="reset" name="reset" value="重置">
    </div>
</form>
<jsp:include page="/pages/mainPage/foot.jsp"></jsp:include>
</body>
</html>