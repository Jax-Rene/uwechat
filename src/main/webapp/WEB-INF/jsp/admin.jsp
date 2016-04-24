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
    <link href="http://g.alicdn.com/bui/bui/1.1.21/css/bs3/dpl.css" rel="stylesheet">
    <link href="http://g.alicdn.com/bui/bui/1.1.21/css/bs3/bui.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<div class="text-center" style="padding: 10px;">
    <h1 id="time"></h1>
    <h2>欢迎来到<span class="main-color">又一村酒店</span>后台管理系统</h2>
</div>
<div>
    <div id="tab"></div>
    <div class="row">
        <div>
            <div id="avg" class="text-center" style="padding: 10px;"></div>
            <div id="raty"></div>
            <div id="grid" style="padding-left: 5%;padding-right:5%;">
            </div>
        </div>
    </div>
</div>

<script src="http://g.tbcdn.cn/fi/bui/jquery-1.8.1.min.js"></script>
<script src="http://g.alicdn.com/bui/seajs/2.3.0/sea.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.raty.js"></script>
<script src="http://g.alicdn.com/bui/bui/1.1.10/config.js"></script>
<script src="http://g.alicdn.com/bui/bui/1.1.21/bui.js"></script>

<script>
    $(function () {
        var d = new Date();
        if (d.getHours() < 12)
            $('#time').text("早上好");
        else if (d.getHours() < 18)
            $('#time').text("下午好");
        else
            $('#time').text("晚上好");


        var Grid = BUI.Grid,
                Data = BUI.Data;
        var Store = Data.Store,
                columns = [];


        BUI.use('bui/tab', function (Tab) {

            var tab = new Tab.Tab({
                render: '#tab',
                elCls: 'nav-tabs',
                autoRender: true,
                children: [
                    {text: '留言板', value: '1'},
                    {text: '预订订单', value: '2'}
                ]
            });
            tab.on('selectedchange', function (ev) {
                $('#grid').html('');
                $.post('${pageContext.request.contextPath}/message/avg',function (data,status) {
                    if(data){
                        $('#avg').html('<h3>总平均分: ' + data + '</h3>');
//                        $('#raty').raty(data);
                    }
                });
                var item = ev.item;
                var index = item.get('value');
                switch (index) {
                    case "1":
                        columns = [
                            {title: 'id', dataIndex: 'id', width: '10%'},
                            {title: '评价星级', dataIndex: 'score', width: '20%'},
                            {title: '留言内容', dataIndex: 'content', width: '40%'},
                            {title: '用户', dataIndex: 'openId', width: '15%'},
                            {title: '留言时间', dataIndex: 'time', width: '15%'}
                        ];

                        var store = new Store({
                                    url: '${pageContext.request.contextPath}/message/load',
                                    autoLoad: true,
                                    pageSize: 10,
                                    proxy: {
                                        ajaxOptions: { //ajax的配置项，不要覆盖success,和error方法
                                            traditional: true,
                                            type: 'post'
                                        }
                                    },
                                    root: 'records',
                                    totalProperty: 'totalCount'
                                }),
                                grid = new Grid.Grid({
                                    render: '#grid',
                                    columns: columns,
                                    loadMask: true,
                                    store: store,
                                    // 底部工具栏
                                    bbar: {
                                        // pagingBar:表明包含分页栏
                                        pagingBar: true
                                    }
                                });
                        grid.render();
                        break;
                    case "2":
                        columns = [
                            {title: 'id', dataIndex: 'id', width: '10%'},
                            {title: '评价星级', dataIndex: 'score', width: '20%'},
                            {title: '留言内容', dataIndex: 'content', width: '40%'},
                            {title: '用户', dataIndex: 'openId', width: '15%'},
                            {title: '留言时间', dataIndex: 'time', width: '15%'}
                        ];

                        var store = new Store({
                                    url: '${pageContext.request.contextPath}/message/load',
                                    autoLoad: true,
                                    pageSize: 10,
                                    proxy: {
                                        ajaxOptions: { //ajax的配置项，不要覆盖success,和error方法
                                            traditional: true,
                                            type: 'post'
                                        }
                                    },
                                    root: 'records',
                                    totalProperty: 'totalCount'
                                }),
                                grid = new Grid.Grid({
                                    render: '#grid',
                                    columns: columns,
                                    loadMask: true,
                                    store: store,
                                    // 底部工具栏
                                    bbar: {
                                        // pagingBar:表明包含分页栏
                                        pagingBar: true
                                    }
                                });
                        grid.render();
                        break;
                }
            });
            tab.setSelected(tab.getItemAt(0));

        });
    });

</script>
</body>
</html>
