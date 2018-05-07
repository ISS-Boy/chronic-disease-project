<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<jsp:include page="../public/mycss.jsp"></jsp:include>
		<jsp:include page="../public/tcss.jsp"></jsp:include>
		<meta charset="utf-8" />
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

	</head>
	
	<body>
	<form id="addMenuDialog" action="/ke/menu/${type}" method="post">
		<div class="">
			<div>
				<input type="hidden" name="id" id="id" value="${menu.id}"/>
					<label class="col-sm-2 control-label">菜单名称</label>
					<div class="col-sm-9">
						<input id="menuName" name="menuName" class="form-control" value="${menu.menuName }" type="text" placeholder="菜单管理"/>
					</div>
					<label class="col-sm-2 control-label">资源路径</label>
					<div class="col-sm-9">
						<input id="menuUrl" name="menuUrl" class="form-control" value="${menu.menuUrl }" type="text" placeholder="menu/menu"/>
					</div>
					<label class="col-sm-2 control-label">父菜单</label>
					<div class="col-sm-9">
						<select class="form-control" name="parentId" id="parentId" title="菜单">
							<option value="0">顶级菜单</option>
							<c:forEach items="${menuList}" var="pmenu">
								<option value="${pmenu.id }">${pmenu.menuName }</option>
							</c:forEach>
						</select>
					</div>
					<label class="col-sm-2 control-label">顺序</label>
					<div class="col-sm-9">
						<input id="menuOrder" name="menuOrder" class="form-control" value="${menu.menuOrder }" type="number" placeholder="0"/>
					</div>
			</div>
		</div>
		<div id="remove_div" class="modal-footer">
			<button type="button" class="btn btn-primary"
					onclick="saveMenu();">确定</button>
			<button type="button" class="btn btn-default"
					onclick="top.Dialog.close();">取消</button>
		</div>
	</form>
	</body>
	<jsp:include page="../public/script.jsp">
		<jsp:param value="main/menu/menu.js" name="loader" />
		<jsp:param value="ace/js/jquery.tips.js" name="loader" />
	</jsp:include>

<script>
    $(document).ready(function() {
        var select = document.getElementById("parentId");
        var parentId = ${menu.parentId};
        for (var i = 0; i < select.options.length; i++) {
            if (select.options[i].value == parentId) {
                select.options[i].selected = true;
                break;
            }
        }
    });
</script>
</html>