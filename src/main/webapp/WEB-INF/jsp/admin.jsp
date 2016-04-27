<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 16/4/24
  Time: 下午4:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>后台管理系统</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="http://g.alicdn.com/bui/bui/1.1.21/css/bs3/bui.css" rel="stylesheet">
    <link href="http://g.alicdn.com/bui/bui/1.1.21/css/bs3/dpl.css" rel="stylesheet">

    <style>
        h1{
            color: #FFFFFF;
            text-align: center;
            margin-top: 5%;
            font-size: 50px;
            font-family:fantasy,Menlo, Monaco, Consolas, "Courier New", monospace;
        }

        a:hover{ text-decoration:none;}
    </style>
</head>
<body>
<h1 id="time"></h1>

<div class="row">
    <div style="height: 200px;"></div>
</div>

<div class="row offset7">
    <a href="${pageContext.request.contextPath}/admin/message"><div class="admin-block">留言板</div></a>
    <a href="${pageContext.request.contextPath}/admin/order"><div class="admin-block" style="background-color: #9AB86C">订单管理</div></a>
</div>

<a href="${pageContext.request.contextPath}/j_spring_security_logout_">登出</a>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.backstretch.js"></script>
<script>
    $(function () {
        $.backstretch("${pageContext.request.contextPath}/img/admin.jpg");
        var d = new Date();
        if (d.getHours() < 12)
            $('#time').text('早上好,欢迎光临!');
        else if (d.getHours() < 18)
            $('#time').text('下午好,欢迎光临!');
        else
            $('#time').text('晚上好,欢迎光临');
    });
</script>
</body>
</html>
