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

<title>慢病大数据 - 伪生产者</title>
<jsp:include page="../public/css.jsp">
	<jsp:param value="plugins/select2/select2.min.css" name="css" />
</jsp:include>
</head>

<body>
	<jsp:include page="../public/navbar.jsp"></jsp:include>
	<div id="wrapper">
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">
						伪生产者
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
						<i class="fa fa-info-circle"></i> <strong>选择一个主题，然后向他发送一些信息.</strong>
					</div>
				</div>
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-cogs fa-fw"></i> 内容
							<div class="pull-right"></div>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="form-group">
								<label>主题名 (*)</label> <select id="select2val"
									name="select2val" tabindex="-1"
									style="width: 100%; font-family: 'Microsoft Yahei', 'HelveticaNeue', Helvetica, Arial, sans-serif; font-size: 1px;"></select>
								<input id="ke_topic_mock" name="ke_topic_mock" type="hidden" />
								<label for="inputError" class="control-label text-danger"><i
									class="fa fa-info-circle"></i> 选择你想要发送信息的.</label>
							</div>
							<div class="form-group">
								<label>消息 (*)</label>
								<textarea id="ke_mock_content" name="ke_mock_content"
									class="form-control" placeholder="限制120词." rows="3"
									maxlength="120"></textarea>
								<label for="inputError" class="control-label text-danger"><i
									class="fa fa-info-circle"></i> 写入一些消息发送到主题中.</label>
							</div>
							<button type="button" class="btn btn-primary" id="btn_send">Send
							</button>
							<div id="alert_mssage_mock" style="display: none"
								class="alert alert-danger">
								<label>错误！需要 (*).</label>
							</div>
							<div id="success_mssage_mock" style="display: none"
								class="alert alert-success">
								<label>消息发送成功.</label>
							</div>
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
	<jsp:param value="plugins/select2/select2.min.js" name="loader" />
	<jsp:param value="main/topic/mock.js" name="loader" />
</jsp:include>
</html>
