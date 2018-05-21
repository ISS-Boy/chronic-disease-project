<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">

	<title>chronic - disease</title>
	<jsp:include page="../public/css.jsp"></jsp:include>
	<jsp:include page="../public/tcss.jsp"></jsp:include>
</head>
<body>
<jsp:include page="../public/navbar.jsp"></jsp:include>
<div id="wrapper">
	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">
					离线学习管理
				</h1>
			</div>
		</div>
		<div class="row">
			<div class="page_and_btn">
				<div>
					&nbsp;&nbsp;<a class="btn btn-small btn-success" onclick="addConfigure();">新增</a>
				</div>
			</div>
		</div>
	<div class="row">
		<div class="panel-body">
			<table id="table_report" class="table table-bordered table-condensed">
				<thead>
				<tr>
					<th class="center">序号</th>
					<th class='center'>名称</th>
					<th class='center'>年龄</th>
					<th class='center'>性别</th>
					<th class='center'>病史</th>
					<th class='center'>度量值</th>
					<th class='center'>起始日期</th>
					<th class='center'>截止时间</th>
					<th class='center'>窗口长度</th>
					<th class='center'>窗口段数</th>
					<th class='center'>字母表个数</th>
					<th class='center'>起始窗口大小</th>
					<th class='center'>频率阈值</th>
					<th class='center'>距离阈值</th>
					<th class='center'>模式个数</th>
					<th class='center'>状态</th>
					<th class='center'>操作</th>
				</tr>
				</thead>
				<c:choose>
					<c:when test="${not empty learningConfigures}">
						<c:forEach items="${learningConfigures}" var="learningConfigure" varStatus="vs">
						<tr id="tr${learningConfigure.configureId }">
							<td class="center">${vs.index+1}</td>
							<td class='center'>${learningConfigure.configureName }</td>
							<td>${learningConfigure.age}</td>
							<td class='center'>
								<c:if test="${learningConfigure.gender == 'F'}">女</c:if>
								<c:if test="${learningConfigure.gender == 'M'}">男</c:if>
								<c:if test="${learningConfigure.gender == 'all'}">全部</c:if>
							</td>
							<td class='center'>${learningConfigure.disease }</td>
							<td class="center" style="word-wrap:break-word;word-break:break-all;width: 12%;">${learningConfigure.metric}</td>
							<td class='center'>${learningConfigure.dateBegin }</td>
							<td>${learningConfigure.dateEnd}</td>
							<td class='center'>${learningConfigure.slidingWindowSize }</td>
							<td class='center'>${learningConfigure.paaSize }</td>
							<td class="center">${learningConfigure.alphabetSize}</td>
							<td class='center'>${learningConfigure.analysisWindowStartSize }</td>
							<td>${learningConfigure.frequencyThreshold}</td>
							<td class='center'>${learningConfigure.rThreshold }</td>
							<td class='center'>${learningConfigure.k }</td>
							<td class='center'>${learningConfigure.isDone }</td>
							<td style="width: 10%">
								<c:if test="${learningConfigure.isDone == '未完成'}">
									<a class='btn btn-primary' title="停止" onclick="stopLearning('${learningConfigure.configureId }')" ><i class='fa fa-stop'></i></a>
								</c:if>
								<c:if test="${learningConfigure.isDone == '已完成' || learningConfigure.isDone == '已停止'}">
									<a class='btn btn-danger' title="删除"  onclick="deleteConfigure('${learningConfigure.configureId }')"><i class='fa fa-trash'></i></a>
								</c:if>
								<c:if test="${learningConfigure.isDone == '已完成'}">
									<a class='btn btn-success' title="查看结果"  onclick="showResult('${learningConfigure.configureId }')"><i class='fa fa-eye'></i></a>
								</c:if>
							</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
						<td colspan="100">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
	</div>
	</div>
</div>

	
</body>
<jsp:include page="../public/script.jsp">
	<jsp:param value="ace/js/jquery.tips.js" name="loader" />
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>
<script>
	function addConfigure() {
        window.location.href="/ke/offlineLearning/toAdd?type=0";
    }

    function stopLearning(configureId) {
        var result = confirm("确定要停止吗?");
            if(result) {
                $.ajax({
                    type: "POST",
                    url: '/ke/offlineLearning/stopLearning',
                    data: {configureId:configureId},
                    dataType:'json',
                    cache: false,
                    success: function(msg){
                        if (msg.msg == "success") {
                        	document.location.reload();
                        }
                    }
                });
            }
    }


    function deleteConfigure(configureId) {
        var result = confirm("确定要删除吗?");
            if(result) {
                $.ajax({
                    type: "POST",
                    url: '/ke/offlineLearning/deleteConfigure',
                    data: {configureId:configureId},
                    dataType:'json',
                    cache: false,
                    success: function(msg){
                        if (msg.msg == "success") {
                        	document.location.reload();
                        }
                    }
                });
            }
    }


    function showResult(configureId) {
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="结果";
        diag.URL = '/ke/offlineLearning/showResult?configureId=' + configureId;
        diag.Width = 888;
        diag.Height = 555;
        diag.CancelEvent = function(){ //关闭事件
            window.location.href="/ke/offlineLearning/getAllConfigure";
            diag.close();
        };
        diag.show();
    }

</script>
</html>