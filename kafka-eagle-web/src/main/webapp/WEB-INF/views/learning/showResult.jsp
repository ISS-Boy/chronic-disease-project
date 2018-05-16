<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="zh">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

	<jsp:include page="../public/mycss.jsp"></jsp:include>
	<jsp:include page="../public/tcss.jsp"></jsp:include>
	<title>菜单</title>
	<meta name="description" content="overview & stats" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link href="static/css/bootstrap.min.css" rel="stylesheet" />
	<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="static/css/font-awesome.min.css" />
	<link rel="stylesheet" href="static/css/ace.min.css" />
	<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
	<link rel="stylesheet" href="static/css/ace-skins.min.css" />
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<style>
		table,table tr th, table tr td { border:1px solid #0094ff; }
	</style>
</head>

<body>
<table id="table_report" class="table table-bordered table-condensed">
	<thead>
	<tr>
		<th class="center">序号</th>
		<th class='center'>名称</th>
		<th class='center'>长度</th>
		<th class='center'>排序</th>
		<th class='center'>详细</th>
	</tr>
	</thead>
	<c:choose>
		<c:when test="${not empty patterns}">
			<c:forEach items="${patterns}" var="pattern" varStatus="vs">
				<tr id="tr${pattern.id }">
					<td  class="center">${vs.index+1}</td>
					<td  class='center'>${pattern.alias }&nbsp;</td>
					<td class="center">${pattern.lengths}</td>
					<td class='center'>${pattern.patternOrder }</td>
					<td>
				<table>
					<thead>
					<tr>
						<th class='center'>度量值</th>
						<th class='center'>模式</th>
						<th class='center'>操作</th>
					</tr>
					</thead>
					<c:forEach items="${pattern.patternDetail}" var="detail" varStatus="vs">
						<tr>
							<td class='center'>${detail.measureName}</td>
							<td style="word-wrap:break-word;word-break:break-all;width: 72%;"class='center'>${detail.measureValue }</td>
							<td class="center" style="width: 10%;">
								<a class='btn btn-success' title="图形显示" onclick="showGraph1('${detail.id }');" ><i class='fa fa-eye'></i></a>
							</td>
						</tr>
					</c:forEach>
				</table>

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
</body>
<jsp:include page="../public/script.jsp">
	<jsp:param value="ace/js/jquery.tips.js" name="loader" />
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>
<script>
    function showGraph1(id) {
        var diag = new Dialog();
        diag.Drag=true;
        diag.Title ="图形展示";
        diag.URL = '/ke/offlineLearning/showGraph1?id=' + id;
        diag.Width = 888;
        diag.Height = 555;
        diag.CancelEvent = function(){ //关闭事件
            diag.close();
        };
        diag.show();
    }
</script>
</html>
