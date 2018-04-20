<%--
  Created by IntelliJ IDEA.
  User: LH
  Date: 2018/1/13
  Time: 10:31
  To change this template use File | Settings | File Templates.
--%>
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
<div id="wrapper">
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">
                    用户历史数据详情<small>overview</small>
                </h1>
                <h3 class="current-time">

                </h3>
            </div>
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">


                    <!-- /.panel-heading -->
                    <div class="panel-body">

                        <div class="panel-heading">
                            <i class="fa fa-cogs fa-fw"></i>关键指标
                        </div>
                        <table id="result" class="table table-bordered table-condensed"
                               width="100%">
                            <thead>
                            <tr>
                                <th>数据时间</th>
                                <th>身高</th>
                                <th>体重</th>
                                <th>血常规</th>
                                <th>总胆固醇</th>
                                <th>甘油三酯</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
                <!-- /.col-lg-4 -->
            </div>
            <!-- /.row -->
        </div>

    </div>
</div>
</body>
<jsp:include page="../public/script.jsp">
    <jsp:param value="main/patient/currentTime.js" name="loader" />
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>
</html>
