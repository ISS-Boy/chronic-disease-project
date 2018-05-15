<%--
  Created by IntelliJ IDEA.
  User: lee
  Date: 2018/5/11
  Time: 下午3:09
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
    <style type="text/css">
        .sameRow{
            width: 100%;
            margin: 15px;
        }
        .outerchoose{
            margin-bottom: 10px;
        }
        .secondRow{
            margin: 15px;
        }
        .datetime{
            width: 40%;
        }

    </style>
</head>
<body>
<jsp:include page="../public/navbar.jsp"></jsp:include>
<div id="wrapper">
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h3 class="page-header">
                    添加信息<small>overview</small>
                </h3>
            </div>
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-cogs fa-fw"></i> 开始学习前，添加信息
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <form action="/ke/offlineLearning/run" method="post">
                            <div class="outerchoose col-md-5" style="border: 1px solid #eee;margin-right: 20px">
                                <input name="userIds" value="${userIds}" hidden />
                                <input name="ages" value="${firstInfo.ages}" hidden />
                                <input name="gender" value="${firstInfo.gender}" hidden />
                                <input name="diseases" value="${firstInfo.diseases}" hidden />
                                <div class="sameRow">
                                    <input type="checkbox" name="metric" value="heart_rate"/> 心率
                                </div>
                                <div class="sameRow">
                                    <input type="checkbox" name="metric" value="systolic_blood_pressure"/> 舒张压
                                </div>
                                <div class="sameRow">

                                    <input type="checkbox" name="metric" value="diastolic_blood_pressure"/> 收缩压
                                </div>
                                <div class="sameRow">
                                    <span>时间段</span>
                                    <input name="startTime" class="datetime" type="datetime-local" id="fromtime" value=""/>至
                                    <input name="endTime" class="datetime" type="datetime-local" id="totime" value=""/>
                                </div>
                            </div>
                            <div class="outerchoose col-md-6" style="border: 1px solid #eee;">
                                <div class="secondRow input-group col-xs-10">
                                    <span class="input-group-addon">名称：</span>
                                    <input name="taskName" type="text" min="0"  class="form-control" required/>
                                </div>
                                <div class="secondRow input-group col-xs-10">
                                    <span class="input-group-addon">窗口长度：</span>
                                    <input name="windowLength" type="number" min="0" class="form-control"/>
                                </div>
                                <div class="secondRow input-group col-xs-10">
                                    <span class="input-group-addon">段数：</span>
                                    <input name="segment" type="number" min="0"  class="form-control"/>
                                </div>
                                <div class="secondRow input-group col-xs-10">
                                    <span class="input-group-addon">字母表个数：</span>
                                    <input name="alphabetCount" type="number" min="0"  class="form-control"/>
                                </div>
                                <div class="secondRow input-group col-xs-10">
                                    <span class="input-group-addon">窗口大小：</span>
                                    <input name="windowSize" type="number" min="0" class="form-control"/>
                                </div>
                                <div class="secondRow input-group col-xs-10">
                                    <span class="input-group-addon">频率阈值：</span>
                                    <input name="frequencyThreshold" type="number" min="0" class="form-control"/>
                                </div>
                                <div class="secondRow input-group col-xs-10">
                                    <span class="input-group-addon">距离阈值：</span>
                                    <input name="distanceThreshold" type="number" min="0" class="form-control"/>
                                </div>
                                <hr>
                                <div class="secondRow input-group col-xs-10">
                                    <span class="input-group-addon">模式集个数：</span>
                                    <input name="patternCount" type="number" min="0"  class="form-control"/>
                                </div>

                            </div>
                            <div class="col-md-12">
                                <input class="startLearning btn btn-primary" type="submit" value="开始学习" style="float: right;right: 10px">
                            </div>
                        </form>

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
    <jsp:param value="ace/js/jquery.tips.js" name="loader" />
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>
<script>
    document.getElementById("fromtime").value=new Date().toLocaleTimeString();
</script>
</html>

