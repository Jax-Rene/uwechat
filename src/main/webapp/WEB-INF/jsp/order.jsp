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
                        <option value="0">先生</option>
                        <option value="1">女士</option>
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


            <div class="weui_cells_title main-color">备注信息</div>
            <div class="weui_cell">
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_textarea" id="remark" placeholder="请输入备注信息(选填)"/>
                </div>
            </div>

            <div class="weui_cells_tips main-color text-center" style="margin:20px;"><a href="http://mp.weixin.qq.com/s?__biz=MzI4OTIyMjIzNw==&mid=100000010&idx=1&sn=dc74d2a02d8823a2880bba59a33e1a50#rd"">如果有任何疑问请点击此处联系我们</a>
            </div>
            <div class="weui_btn_area">
                <a class="weui_btn weui_btn_primary" href="javascript:" id="submit">提交</a>
            </div>
            <input id="code" type="hidden" value="${code}"/>
        </div>
    </div>
    <div style="height: 50px;"></div>
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
                    code: $('#code').val(),
                    remark:$('#remark').val()
                }, function (data, status) {
                    if (status) {
                        if (data == 'success') {
                            $('.weui_dialog_bd').html('提交订单成功,请务必于预订时间到酒楼用餐!');
                            $('.weui_dialog_confirm').fadeIn(200);
                        } else {
                            $('.weui_dialog_bd').html(data);
                            $('.weui_dialog_confirm').fadeIn(200);
                        }
                    } else {
                        $('.weui_dialog_bd').html('连接超市,请稍后重试!');
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
