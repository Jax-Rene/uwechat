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
</head>
<body>
<div>
    <div class="row text-center">
        <div class="row">
            <div class="doc-content">
                <form class="form-panel" action="post">
                    <div class="panel-title">
                    </div>
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
</div>

<script src="${pageContext.request.contextPath}/js/jquery-1.8.1.min.js"></script>
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
                        {title: '预订时间', dataIndex: 'orderTime', width: 400}
                    ];
            var store = new Store({
                        url: '${pageContext.request.contextPath}/order/load',
                        autoLoad: true,
                        pageSize: 10,
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
                            items: [{
                                btnCls: 'button button-small',
                                text: '<i class="icon-plus"></i>添加',
                                listeners: {
                                    'click': addFunction
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


            //添加记录
            function addFunction() {
                var newData = {};
                store.addAt(newData,0);
                editing.edit(newData,'id');
            }

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
                        $.post('${pageContext.request.contextPath}/order/del', {
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
        });
        $('#today').click(function () {
            var d = new Date();
            $('#start-time').val(d.getFullYear() + "-" + d.getMonth() + "-" + d.getDate() + " 00:00:00");
            $('#end-time').val(d.getFullYear() + "-" + d.getMonth() + "-" + d.getDate() + " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds());
        });
    });
</script>
</body>
</html>
