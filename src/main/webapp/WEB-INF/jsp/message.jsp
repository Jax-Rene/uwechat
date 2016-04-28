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
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <link href="${pageContext.request.contextPath}/css/weui.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/jquery.raty.css" rel="stylesheet"/>
</head>
<body>

<div class="container">
    <div class="row text-center" style="margin:40px">
        <h1 class="main-color">留/言/板</h1>
        <small class="small-color">您的意见是我们最大的前进动力</small>
    </div>

    <div class="row">
        <div class="text-center">
            <div id="raty" style="display: inline;"></div>
            <input type="hidden" id="raty-value"/>
        </div>
    </div>

    <div class="row">
        <div class="divider"></div>
    </div>


    <div class="row">
        <div class="text-center">
            <textarea class="input-sm" id="content" style="width: 65%;height: 45%;margin: 10px;"
                      placeholder="您可以在这里发表您的意见,我们将在第一时间里回复您!"></textarea>
        </div>
    </div>

    <div class="row">
        <div class="divider"></div>
    </div>

    <div class="row">
        <div class="text-center">
            <a id="submit" class="weui_btn weui_btn_primary" style="width: 65%;color:white">提交</a>
        </div>
    </div>
</div>


<div class="weui_dialog_confirm" style="display: none;">
    <div class="weui_mask"></div>
    <div class="weui_dialog">
        <div class="weui_dialog_hd"><strong class="weui_dialog_title">提示</strong></div>
        <div class="weui_dialog_bd">如果您对我们的服务不满意,请留下您宝贵的意见.</div>
        <div class="weui_dialog_ft">
            <a href="#" class="weui_btn_dialog primary">确定</a>
        </div>
    </div>
</div>


<input type="hidden" id="code" value="${code}"/>

<div id="toast" style="display: none;">
    <div class="weui_mask_transparent"></div>
    <div class="weui_toast">
        <i class="weui_icon_toast"></i>
        <p class="weui_toast_content">提交成功</p>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.raty.js"></script>
<script>
    $(function () {
        $('#raty').raty({
            click: function (score, evt) {
                $('#raty').raty('score', score);
                $('#raty-value').val(score);
            },
            starType: 'i'
        });

        $('#submit').click(function () {
            if ($('#raty-value').val() == 0 && $('#content').val() == '') {
                $('.weui_dialog_bd').html('如果您对我们的服务不满意,请留下您宝贵的意见!');
                $('.weui_dialog_confirm').fadeIn(300);
            }
            else {
                $.post('${pageContext.request.contextPath}/message/submit', {
                    score: $('#raty-value').val(),
                    content: $('#content').val(),
                    code: $('#code').val()
                }, function (data, status) {
                    if (status) {
                        if (data) {
                            $('#toast').fadeIn(500);
                            window.setTimeout(function () {
                                $('#toast').fadeOut(500);
                            }, 2000);
                        } else {
                            $('.weui_dialog_bd').html('连接超时,请稍后重试!');
                            $('.weui_dialog_confirm').fadeIn(300);
                        }
                    } else {
                        $('.weui_dialog_bd').html('未知错误,请稍后重试!');
                        $('.weui_dialog_confirm').fadeIn(300);
                    }
                });
            }
        });
    });

    $('.weui_btn_dialog').click(function () {
        $('.weui_dialog_confirm').fadeOut(300);
    });
</script>


</body>
</html>
