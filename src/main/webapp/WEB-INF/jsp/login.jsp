<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 16/3/4
  Time: 上午9:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>又一村酒店后台管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form-elements.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login-style.css">
</head>
<body>

<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>管理员登录</strong></h1>
                    <div class="description">
                        <p>
                            欢迎登录又一村酒店后台管理系统
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <p>请输入您的帐号以及密码</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-lock"></i>
                        </div>
                    </div>
                    <div class="form-bottom">
                        <form role="form" action="${pageContext.request.contextPath}/admin" method="post"
                              class="login-form" id="form">
                            <div class="form-group">
                                <label class="sr-only" for="form-username">Username</label>
                                <input type="text" placeholder="请输入用户名"
                                       class="form-username form-control" id="form-username">
                                <input type="hidden" id="user-name" name="userName"/>
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">Password</label>
                                <input type="password" placeholder="请输入密码"
                                       class="form-password form-control" id="form-password">
                                <input type="hidden" id="pass-word" name="passWord"/>
                            </div>
                            <button type="submit" class="btn btn-primary">登录
                            </button>
                            <p class="text-center" style="color: red;">${loginError}</p>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.backstretch.js"></script>
<script src="${pageContext.request.contextPath}/js/md5.min.js"></script>

<script>
    $(function () {
        $.backstretch("${pageContext.request.contextPath}/img/login-back/1.jpeg");
        $('#form').submit(function () {
                    $('#user-name').val(md5($('#form-username').val()));
                    $('#pass-word').val(md5($('#form-password').val()));
                }
        );
    });

</script>


</body>
</html>
