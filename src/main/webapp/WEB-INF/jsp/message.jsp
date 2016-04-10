<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 16/4/10
  Time: 下午1:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>留言板</title>
    <meta name="generator" content="Bootply"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet"/>
</head>
<body>

<div class="container">
    <div class="row text-center" style="margin:40px">
        <h1 class="main-color">留言反馈</h1>
        <small class="small-color">您的意见是我们最大的前进动力</small>
    </div>

    <hr/>

    <div class="row">
        <div class="text-center">
            <div id="raty"></div>
            <input type="hidden" id="raty-value"/>
        </div>
    </div>

    <div class="row">
        <div class="divider"></div>
    </div>


    <div class="row">
        <div class="text-center">
            <textarea class="input-sm" style="width: 60%;height: 35%;margin: 10px;" placeholder="您可以在这里发表您的意见,我们将在第一时间里回复您!"></textarea>
        </div>
    </div>

    <div class="row">
        <div class="divider"></div>
    </div>

    <div class="row">
        <div class="text-center">
            <input id="submit" type="button" class="btn btn-success" style="width: 60%;margin: 10px;" value="提交"/>
        </div>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.raty.js"></script>
<script>
    $(function () {
        $('#raty').raty({
            click: function (score, evt) {
                $('#raty-value').val(score);
            }
        });

        $('#submit').click(function () {
           alert($('#raty-value').val());
        });
    });
</script>
</body>
</html>
