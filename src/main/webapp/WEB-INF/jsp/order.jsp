<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 16/4/21
  Time: 下午2:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>预订酒桌</title>
    <link href="${pageContext.request.contextPath}/css/weui.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet"/>
</head>
<body>


<div class="bd">

    <div class="weui_cells weui_cells_form">
        <div class="hd text-center">
            <h1 class="page_title main-color">预订</h1>
            <span class="text-center small-color">booking</span>
        </div>

        <div style="height: 2%;"></div>

        <div class="weui_cells_title main-color">称呼</div>
        <div class="weui_cells">
            <div class="weui_cell weui_cell_select weui_select_after">
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" id="name" type="text" placeholder="请输入您的贵姓"/>
                </div>
                <div class="weui_cell_hd">
                    <select class="weui_select" name="select2" id="sex">
                        <option value="1">先生</option>
                        <option value="2">小姐</option>
                    </select>
                </div>
            </div>

            <div class="weui_cells_title main-color">联系电话</div>
            <div class="weui_cell weui_cell_select weui_select_before">
                <div class="weui_cell_hd">
                    <select class="weui_select" name="select2">
                        <option value="1">+86</option>
                    </select>
                </div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" id="phone" type="number" pattern="[0-9]*" placeholder="请输入号码"/>
                </div>
            </div>

            <div class="weui_cells_title main-color">用餐人数</div>
            <div class="weui_cell">
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" id="people" type="number" pattern="[0-9]*" placeholder="请输入用餐人数"/>
                </div>
            </div>

            <div class="weui_cells_title main-color">用餐时间</div>
            <div class="weui_cell">
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" id="order-time" type="datetime-local" value="" placeholder="" min="${now}"/>
                </div>
            </div>

            <div class="weui_cells_tips main-color text-center" style="margin:20px;"><a href="#">如果有任何疑问请点击此处联系我们</a>
            </div>
            <div class="weui_btn_area">
                <a class="weui_btn weui_btn_primary" href="javascript:" id="submit">提交</a>
            </div>

            <input id="code" type="hidden" value="${code}"/>
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

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/base.js"></script>
<script>
    $(function () {
        $('#order-time').val(generateNowDateTime());
        $('#order-time').attr('min',generateNowDateTime());
        $('#submit').click(function () {
            var name = $('#name').val();
            var people = $('#people').val();
            var phone = $('#phone').val();
            var orderTime = $('#order-time').val();
            var patrn = /^((13[0-9])|(15[^4,\D])|(18[0,5-9]))\d{8}$/;
            if (name.length > 7 || name == '') {
                $('.weui_dialog_bd').html('您输入的姓名不合法,请重新输入!');
                $('.weui_dialog_confirm').fadeIn(200);
            } else if (people == '') {
                $('.weui_dialog_bd').html('您输入的用餐人数不合法,请重新输入!');
                $('.weui_dialog_confirm').fadeIn(200);
            } else if (!patrn.exec(phone)) {
                $('.weui_dialog_bd').html('您输入的手机号码有误,请重新输入!');
                $('.weui_dialog_confirm').fadeIn(200);
            } else if (orderTime == '') {
                $('.weui_dialog_bd').html('您输入的用餐时间有误,请重新输入!');
                $('.weui_dialog_confirm').fadeIn(200);
            } else {
                $.post('${pageContext.request.contextPath}/order/submit', {
                    lastName: name,
                    sex: $('#sex').val(),
                    phone: phone,
                    people: people,
                    orderTime: orderTime,
                    code: $('#code').val()
                }, function (data, status) {
                    if (status) {
                        if (data) {
                            $('.weui_dialog_bd').html('提交预订请求成功,您还需要支付100元定金才能完成预订,点击确定将跳转到支付页面!');
                            $('.weui_dialog_confirm').fadeIn(200);
                        } else {
                            $('.weui_dialog_bd').html('提交失败,请检查表单信息后重试!');
                            $('.weui_dialog_confirm').fadeIn(200);
                        }
                    } else {
                        $('.weui_dialog_bd').html('未知错误,请稍后重试!');
                        $('.weui_dialog_confirm').fadeIn(200);
                    }
                });
            }
        });
        $('.weui_btn_dialog').click(function () {
            $('.weui_dialog_confirm').fadeOut(200);
        });
    });
</script>

</body>
</html>
