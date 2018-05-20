<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="zh">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>慢病大数据 - 主题新建</title>
<jsp:include page="../public/css.jsp"></jsp:include>
</head>

<body>
	<jsp:include page="../public/navbar.jsp"></jsp:include>
	<div id="wrapper">
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">
						主题 <small>创建</small>
					</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="alert alert-info alert-dismissable">
						<button type="button" class="close" data-dismiss="alert"
							aria-hidden="true">×</button>
						<i class="fa fa-info-circle"></i> <strong>创建一个新的Kafka主题.</strong>
					</div>
				</div>
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-tasks fa-fw"></i> 主题创建信息
							<div class="pull-right"></div>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-12">
									<form role="form" action="/ke/topic/create/form" method="post"
										onsubmit="return contextFormValid();return false;">
										<div class="form-group">
											<label>主题名称 (*)</label> <input id="ke_topic_name"
												name="ke_topic_name" class="form-control" maxlength=50>
											<label for="inputError" class="control-label text-danger"><i
												class="fa fa-info-circle"></i> 由字母、数字和下划线组成.</label>
										</div>
										<div class="form-group">
											<label>分区 (*)</label> <input id="ke_topic_partition"
												name="ke_topic_partition" class="form-control" maxlength=50
												value="1"> <label for="inputError"
												class="control-label text-danger"><i
												class="fa fa-info-circle"></i> 分区数必须是数字.</label>
										</div>
										<div class="form-group">
											<label>副本因子 (*)</label> <input
												id="ke_topic_repli" name="ke_topic_repli"
												class="form-control" maxlength=50 value="1"><label
												for="inputError" class="control-label text-danger"><i
												class="fa fa-info-circle"></i> 副本因子必须是数字，并且最好要大于Brokers的服务器数量</label>
										</div>
										<button type="submit" class="btn btn-success">新建</button>
										<div id="alert_mssage" style="display: none"
											class="alert alert-danger">
											<label>错误! 请修改(*).</label>
										</div>
									</form>
								</div>
							</div>
							<!-- /.panel-body -->
						</div>
					</div>
					<!-- /.col-lg-4 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /#page-wrapper -->
		</div>
	</div>
</body>

<jsp:include page="../public/script.jsp">
	<jsp:param value="main/topic/create.js" name="loader" />
</jsp:include>
<script type="text/javascript">
	//验证提交表单内容
	function contextFormValid() {
		var ke_topic_name = $("#ke_topic_name").val();
		var ke_topic_partition = $("#ke_topic_partition").val();
		var ke_topic_repli = $("#ke_topic_repli").val();
		var reg = /^[A-Za-z0-9_]+$/;
		var digit = /^[0-9]+$/;
		if (ke_topic_name.length == 0 || !reg.test(ke_topic_name)) {
			$("#alert_mssage").show();
			setTimeout(function() {
				$("#alert_mssage").hide()
			}, 3000);
			return false;
		}
		if (isNaN(ke_topic_partition) || isNaN(ke_topic_repli)) {
			$("#alert_mssage").show();
			setTimeout(function() {
				$("#alert_mssage").hide()
			}, 3000);
			return false;
		}
		return true;
	}
</script>
</html>
