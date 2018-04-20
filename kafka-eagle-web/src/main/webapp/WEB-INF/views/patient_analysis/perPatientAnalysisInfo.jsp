<%--
  Created by IntelliJ IDEA.
  User: LH
  Date: 2017/11/29
  Time: 10:23
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
                    个人数据分析<small>overview</small>
                </h1>

            </div>
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-cogs fa-fw"></i> 个人关键指标

                    </div>
                    <!-- /.panel-heading -->
                    <ul class="nav nav-tabs" id="personalInfoTab">
                        <li class="active"><a href="#p_bloodPressure" data-toggle="tab">血压</a></li>
                        <li><a href="#p_heartRate" data-toggle="tab">心率</a></li>
                        <li><a href="#p_stepCount" data-toggle="tab">步数</a></li>
                        <li><a href="#p_bodyTemperature" data-toggle="tab">体温</a></li>
                    </ul>
                    <div class="panel-body tab-content">
                        <div class="tab-pane fade in active" id="p_bloodPressure">
                            <iframe name="perPatientIframe" src="http://172.16.17.180:8080/pentaho/api/repos/%3Apublic%3AKylin%3ABloodPressurePatient.wcdf/generatedContent?ts=1520502233924"  scrolling="true" height="800px" width="100%" frameborder="0"></iframe>
                        </div>
                        <div class="tab-pane fade" id="p_heartRate">
                            <iframe name="perPatientIframe" src="http://172.16.17.180:8080/pentaho/api/repos/%3Apublic%3AKylin%3AHeartRatePatient.wcdf/generatedContent" width="100%" height="800" scrolling="true" frameborder="0"></iframe>
                        </div>
                        <div class="tab-pane fade" id="p_stepCount">
                            <iframe name="perPatientIframe" src="http://172.16.17.180:8080/pentaho/api/repos/%3Apublic%3AKylin%3AStepCountPatient.wcdf/generatedContent" width="100%" height="800" scrolling="yes" frameborder="0"></iframe>
                        </div>
                        <div class="tab-pane fade" id="p_bodyTemperature">
                            <iframe name="perPatientIframe" src="http://172.16.17.180:8080/pentaho/api/repos/%3Apublic%3AKylin%3ABodyTemperaturePatient.wcdf/generatedContent" width="100%" height="800" scrolling="yes" frameborder="0"></iframe>
                        </div>


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
    <jsp:param value="main/patient/perPatientAnalysisInfo.js" name="loader" />
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>
</html>
