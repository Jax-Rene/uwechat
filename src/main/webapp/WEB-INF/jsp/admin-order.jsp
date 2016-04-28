<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 16/4/25
  Time: 下午1:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>订单管理</title>
    <link href="http://g.alicdn.com/bui/bui/1.1.21/css/bs3/dpl.css" rel="stylesheet">
    <link href="http://g.alicdn.com/bui/bui/1.1.21/css/bs3/bui.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/header.css" rel="stylesheet">
</head>
<body>
<jsp:include page="head.jsp"></jsp:include>
<div>
    <div class="row">
        <div style="height: 60px;"></div>
    </div>

    <div class="row text-center" style="padding:20px;">
        <h1 class="main-color" style="font-size: 55px;">订/单/管/理</h1>
        <span>Order Manage</span>
    </div>


    <div class="row text-center">
        <div class="row">
            <div class="doc-content">
                <form class="form-panel" action="post" style="background-color: #FFFFFF">
                    <ul class="panel-content">
                        <li>
                            <span>
                <label>预订日期：</label><input type="text" class="calendar" id="start-time"
                                           style="width: 130px;"> <label>至</label>
                                           <input type="text" class="calendar" id="end-time"
                                                  style="width: 130px;"></span>
                            <input type="text" placeholder="手机号码" id="phone">&nbsp;&nbsp;
                            <button type="button" id="search" class="button button-primary">查询</button>
                            <button type="button" id="today" class="button button-success">今日预订</button>
                            <button type="button" id="add" class="button button-danger">添加订单</button>
                        </li>
                    </ul>
                </form>
            </div>
        </div>
    </div>

    <div class="row">
        <div>
            <div id="grid" style="padding-left: 5%;padding-right:5%;" class="message"></div>
        </div>
    </div>

    <div id="content" style="display: none">
        <form id="form" class="form-horizontal">
            <input type="hidden" id="id"/>
            <div class="row">
                <div class="control-group span8">
                    <label class="control-label">姓氏：</label>
                    <div class="controls">
                        <input type="text" class="input-normal control-text" data-rules="{required : true}" id="name">
                    </div>
                </div>
                <div class="control-group span8">
                    <label class="control-label">性别：</label>
                    <div class="controls">
                        <select class="input-normal" id="sex">
                            <option value="0">男</option>
                            <option value="1">女</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="control-group span15">
                    <label class="control-label">用餐人数：</label>
                    <div class="controls">
                        <input class="input-normal control-text" type="text" data-rules="{required:true}" id="people"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="control-group span15">
                    <label class="control-label">电话号码：</label>
                    <div class="controls">
                        <input class="input-normal control-text" type="text" data-rules="{required:true}"
                               id="phone-form"/>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="control-group span15">
                    <label class="control-label">用餐时间：</label>
                    <div class="controls">
                        <input type="text" class="calendar calendar-time" style="width: 130px;" id="order-time">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="control-group span15">
                    <label class="control-label">备注：</label>
                    <div class="controls control-row5">
                        <textarea class="input-normal" type="text" style="width: 150px;height: 100px;"
                                  id="remark"></textarea>
                    </div>
                </div>
            </div>
        </form>
    </div>

</div>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/base.js"></script>
<script src="http://g.alicdn.com/bui/seajs/2.3.0/sea.js"></script>
<script src="http://g.alicdn.com/bui/bui/1.1.10/config.js"></script>
<script src="http://g.alicdn.com/bui/bui/1.1.21/bui.js"></script>

<script>
    $(function () {
        var Calendar = BUI.Calendar
        var datepicker = new Calendar.DatePicker({
            trigger: '.calendar',
            showTime: true,
            autoRender: true
        });

        var Overlay = BUI.Overlay,
                Form = BUI.Form;

        var form = new Form.HForm({
            srcNode: '#form'
        }).render();

        var dialog = new Overlay.Dialog({
            title: '添加订单',
            width: 400,
            height: 500,
            contentId: 'content',
            success: function () {
                var name = $('#name').val();
                var people = $('#people').val();
                var phone = $('#phone-form').val();
                var orderTime = translateToDateTimeLocal($('#order-time').val());
                var patrn = /^((13[0-9])|(15[^4,\D])|(18[0,5-9]))\d{8}$/;
                if (name.length > 7 || name == '') {
                    alert('您输入的姓名不合法,请重新输入!');
                } else if (people == '') {
                    alert('您输入的用餐人数不合法,请重新输入!');
                } else if (!patrn.exec(phone)) {
                    alert('您输入的手机号码有误,请重新输入!');
                } else if (orderTime == '') {
                    alert('您输入的用餐时间有误,请重新输入!');
                } else {
                    $.post('${pageContext.request.contextPath}/admin/order/add', {
                        lastName: name,
                        sex: $('#sex').val(),
                        phone: phone,
                        people: people,
                        orderTime: orderTime,
                        remark: $('#remark').val(),
                        id:$('#id').val()
                    }, function (data, status) {
                        if (data == "success") {
                            dialog.close();
                            alert('操作成功,请刷新页面查看结果');
                        } else {
                            alert(data);
                        }
                    });
                }
            }
        });


        //添加记录
        $('#add').click(function () {
            dialog.show();
        });

        $('#search').click(function () {
            $('#grid').html('');
            var Grid = BUI.Grid,
                    Data = BUI.Data;
            var Store = Data.Store,
                    columns = [
                        {title: 'id', dataIndex: 'id', width: 100},
                        {title: '称呼', dataIndex: 'call', width: 200},
                        {title: '手机号码', dataIndex: 'phone', width: 200},
                        {title: '人数', dataIndex: 'people', width: 100},
                        {title: '预订时间', dataIndex: 'orderTime', width: 200},
                        {title: '备注', dataIndex: 'remark', width: 500}
                    ];
            var store = new Store({
                        url: '${pageContext.request.contextPath}/admin/order/load',
                        autoLoad: true,
                        pageSize: 100,
                        proxy: {
                            ajaxOptions: { //ajax的配置项，不要覆盖success,和error方法
                                traditional: true,
                                type: 'post'
                            }
                        },
                        params: {
                            startTime: $('#start-time').val(),
                            endTime: $('#end-time').val(),
                            phone: $('#phone').val()
                        },
                        root: 'records',
                        totalProperty: 'totalCount'
                    }),
                    grid = new Grid.Grid({
                        render: '#grid',
                        columns: columns,
                        loadMask: true,
                        store: store,
                        tbar: { //添加、删除
                            items: [
                                {
                                    btnCls: 'button button-small',
                                    text: '<i class="icon-edit"></i>编辑',
                                    listeners: {
                                        'click': editFunction
                                    }
                                },
                                {
                                    btnCls: 'button button-small',
                                    text: '<i class="icon-remove"></i>删除',
                                    listeners: {
                                        'click': delFunction
                                    }
                                }]
                        },
                        // 底部工具栏
                        bbar: {
                            // pagingBar:表明包含分页栏
                            pagingBar: true
                        },
                        plugins: [Grid.Plugins.CheckSelection, Grid.Plugins.ColumnResize]
                    });
            grid.render();


            //删除选中的记录
            function delFunction() {
                var selections = grid.getSelection();
                var Overlay = BUI.Overlay
                var dialog = new Overlay.Dialog({
                    title: '提示',
                    width: 200,
                    height: 130,
                    bodyContent: '<p>您确定要删除这<span style="color: red;">' + selections.length + '</span>条记录吗?</p>',
                    success: function () {
                        var idList = [];
                        for (var i = 0; i < selections.length; i++)
                            idList.push(selections[i].id);
                        $.post('${pageContext.request.contextPath}/admin/order/del', {
                            ids: idList
                        }, function (data, status) {
                            if (status) {
                                if (data) {
                                    store.remove(selections);
                                } else {
                                    alert('删除失败,请稍后重试!');
                                }
                            } else {
                                alert('网络传输失败,请稍后重试!');
                            }
                        });
                        this.close();
                    }
                });
                dialog.show();
            }

            //删除选中的记录
            function editFunction() {
                var selections = grid.getSelection();
                if (selections.length != 1) {
                    alert('只能一次性编辑一条信息');
                } else {
                    $('#name').val(selections[0].lastName);
                    $('#sex').val(selections[0].sex);
                    $('#phone-form').val(selections[0].phone);
                    $('#remark').val(selections[0].remark);
                    $('#people').val(selections[0].people);
                    $('#order-time').val(selections[0].orderTime);
                    $('#id').val(selections[0].id);
                    dialog.show();
                }
            }
        });
        $('#today').click(function () {
            var d = new Date();
            $('#start-time').val(d.getFullYear() + "-" + d.getMonth() + "-" + d.getDate() + " 00:00:00");
            $('#end-time').val(d.getFullYear() + "-" + d.getMonth() + "-" + (d.getDate() + 1) + " 00:00:00");
        });

    });
</script>
</body>
</html>
