<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: johnny
  Date: 16/4/25
  Time: 下午1:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>留言板</title>
    <link href="http://g.alicdn.com/bui/bui/1.1.21/css/bs3/dpl.css" rel="stylesheet">
    <link href="http://g.alicdn.com/bui/bui/1.1.21/css/bs3/bui.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/header.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/jquery.raty.css" rel="stylesheet">
</head>
<body>

<c:import url="head.jsp"></c:import>
<div>
    <div class="row">
        <div style="height: 60px;"></div>
    </div>

    <div class="row text-center">
        <h1 class="main-color" style="font-size: 55px;">留/言/板</h1>
        <span>Message Board</span>
    </div>

    <div class="row">
        <div>
            <div class="text-center" style="padding: 20px;">
                <div id="avg" style="display: inline;" class="message"></div>
                <div id="raty" style="display: inline;" class="message"></div>
            </div>
            <div id="grid" style="padding-left: 5%;padding-right:5%;" class="message">
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="http://g.alicdn.com/bui/seajs/2.3.0/sea.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.raty.js"></script>
<script src="http://g.alicdn.com/bui/bui/1.1.10/config.js"></script>
<script src="http://g.alicdn.com/bui/bui/1.1.21/bui.js"></script>

<script>
    $(function () {
        var Grid = BUI.Grid,
                Data = BUI.Data;
        var Store = Data.Store,
                columns = [
                    {title: 'id', dataIndex: 'id', width: '10%'},
                    {title: '评价星级', dataIndex: 'score', width: '10%'},
                    {title: '留言内容', dataIndex: 'content', width: '45%'},
                    {title: '用户', dataIndex: 'nickName', width: '15%'},
                    {title: '留言时间', dataIndex: 'time', width: '20%'}
                ];

        $.post('${pageContext.request.contextPath}/admin/message/avg', function (data, status) {
            if (data) {
                $('#avg').html('<h3>总平均分: ' + data + '</h3>');
                $('#raty').raty({
                    starType: 'i',
                });
                $('#raty').raty('score',data);
            }
        });

        var store = new Store({
                    url: '${pageContext.request.contextPath}/admin/message/load',
                    autoLoad: true,
                    pageSize: 100,
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
    });
</script>
</body>
</html>
