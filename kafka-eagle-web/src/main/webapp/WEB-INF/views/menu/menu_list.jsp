<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

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
<div id="page-wrapper">
	<div class="row">
	<table id="table_report" class="table table-bordered table-condensed">
		<thead>
		<tr>
			<th class="center">序号</th>
			<th class='center'>名称</th>
			<th class='center'>资源路径</th>
			<th class='center'>排序</th>
			<th class='center'>父菜单</th>
			<th class='center'>操作</th>
		</tr>
		</thead>
		<c:choose>
			<c:when test="${not empty menuList}">
				<c:forEach items="${menuList}" var="menu" varStatus="vs">
				<tr id="tr${menu.id }">
				<td class="center">${vs.index+1}</td>
				<td class='center'><i class="${menu.icon }">&nbsp;</i>${menu.menuName }&nbsp;
				</td>
				<td>${menu.menuUrl == '#'? '': menu.menuUrl}</td>
				<td class='center'>${menu.menuOrder }</td>
					<td class='center'>${menu.parentId }</td>
				<td style="width: 25%;">
				<a class='btn btn-primary btn-xs' title="编辑" onclick="editmenu('${menu.id }')" ><i class='icon-edit'></i></a>
				<a class='btn btn-primary btn-xs' title="删除"  onclick="delmenu('${menu.id }',true)"><i class='icon-trash'></i></a>
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
	<div class="page_and_btn">
		<div>
			&nbsp;&nbsp;<a class="btn btn-small btn-success" onclick="addmenu();">新增</a>
		</div>
	</div>
	
</body>
<jsp:include page="../public/script.jsp">
	<jsp:param value="main/patient/currentTime.js" name="loader" />
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>
</html>