<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="zh">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="view端口" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>慢病大数据 - ZK & Kafka</title>
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
                    ZK & Kafka 信息
                    <small>总览</small>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="alert alert-info alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert"
                            aria-hidden="true">×
                    </button>
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
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-tasks fa-fw"></i> Kafka集群信息
                        <div class="pull-right"></div>
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div id="kafka_cluster_info">
                            <table id="kafka_tab" class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>IP</th>
                                    <th>端口</th>
                                    <th>创建时间</th>
                                    <th>修改时间</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                    <!-- /.panel-body -->
                </div>
            </div>
            <!-- /.col-lg-4 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-sitemap fa-fw"></i> Zookeeper集群信息
                        <div class="pull-right"></div>
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <div id="zookeeper_cluster_info">
                            <table id="zk_tab" class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>IP</th>
                                    <th>端口</th>
                                    <th>Mode</th>
                                </tr>
                                </thead>
                            </table>
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
</body>
<jsp:include page="../public/script.jsp">
    <jsp:param value="main/cluster/cluster.js" name="loader"/>
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>
</html>
