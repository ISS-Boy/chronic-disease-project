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

<title>慢病大数据 - 集群资源总览</title>
<jsp:include page="../public/css.jsp"></jsp:include>
</head>
<style type="text/css">
.node circle {
	cursor: pointer;
	fill: #fff;
	stroke: steelblue;
	stroke-width: 1.5px;
}

.node text {
	font-size: 14px;
}

path.link {
	fill: none;
	stroke: #ccc;
	stroke-width: 1.5px;
}
</style>
<body>
	<jsp:include page="../public/navbar.jsp"></jsp:include>
	<div id="wrapper">
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">
						Kafka资源状况 <small>总览</small>
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
						<i class="fa fa-info-circle"></i>
						<strong>
						集群状态管理部分展示了Kafka和Zookeeper的一些相关集群信息，
						并提供相应的管理功能
						</strong>
						如果不了解这些信息，请通过访问<a href="http://kafka.apache.org/" target="_blank"
							class="alert-link">Kafka</a> 和 <a
							href="http://zookeeper.apache.org/" target="_blank"
							class="alert-link">Zookeeper</a> 去了解相关概念.
					</div>
				</div>
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-3 col-md-6">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-tasks fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div id="brokers_count" class="huge">0</div>
									<div>Kafka服务器</div>
								</div>
							</div>
						</div>
						<a href="/ke/cluster/info">
							<div class="panel-footer">
								<span class="pull-left">查看详情</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
				<div class="col-lg-3 col-md-6">
					<div class="panel panel-green">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-comment-o fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div id="topics_count" class="huge">0</div>
									<div>Kafka主题</div>
								</div>
							</div>
						</div>
						<a href="/ke/topic/list">
							<div class="panel-footer">
								<span class="pull-left">查看详情</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
				<div class="col-lg-3 col-md-6">
					<div class="panel panel-info">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-sitemap fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div id="zks_count" class="huge">0</div>
									<div>Zookeepers</div>
								</div>
							</div>
						</div>
						<a href="/ke/cluster/info">
							<div class="panel-footer">
								<span class="pull-left">查看详情</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
				<div class="col-lg-3 col-md-6">
					<div class="panel panel-default">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i class="fa fa-users fa-5x"></i>
								</div>
								<div class="col-xs-9 text-right">
									<div id="consumers_count" class="huge">0</div>
									<div>Kafka消费者组</div>
								</div>
							</div>
						</div>
						<a href="/ke/consumers">
							<div class="panel-footer">
								<span class="pull-left">查看详情</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-tasks fa-fw"></i> Kafka 服务器概览
							<div class="pull-right"></div>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div id="kafka_brokers"></div>
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
</body>
<jsp:include page="../public/script.jsp">
	<jsp:param value="plugins/d3/d3.js" name="loader" />
	<jsp:param value="plugins/d3/d3.layout.js" name="loader" />
	<jsp:param value="main/index.js" name="loader" />
</jsp:include>
</html>
