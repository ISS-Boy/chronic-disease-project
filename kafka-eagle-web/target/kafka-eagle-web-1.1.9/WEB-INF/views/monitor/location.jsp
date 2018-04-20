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
    <link rel="stylesheet" type="text/css" href="/ke/media/css/chronicD/location.css">
</head>
<body>
<jsp:include page="../public/navbar.jsp"></jsp:include>
<div id="wrapper">
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">
                    monitor<small>overview</small>
                </h1>
                <h3 class="current-time">

                </h3>
            </div>
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-cogs fa-fw"></i> 实时状态信息
                    </div>
                    <!-- /.panel-heading -->
                    <%--<div class="panel-body" style="position: relative;">--%>
                        <%--<div id="status_info" >--%>
                            <%--<div class="status_detail"  id="userid">--%>
                                <%--<i class="fa fa-user"></i>&nbsp;ID：XXX--%>
                            <%--</div>--%>
                            <%--<div class="status_detail" id="steps">--%>
                                <%--<i class="fa fa-angellist"></i>&nbsp;步数：XXX--%>
                            <%--</div>--%>
                            <%--<div class="status_detail" id="blood_pressure">--%>
                                <%--<i class="fa fa-stethoscope"></i>&nbsp;血压：XXX--%>
                            <%--</div>--%>
                            <%--<div class="status_detail" id="heart_rate">--%>
                                <%--<i class="fa fa-heartbeat"></i>&nbsp;心率:XXX--%>
                            <%--</div>--%>
                            <%--<div class="status_detail" id="mood_">--%>
                                <%--<i class="fa fa-smile-o"></i>&nbsp;情绪：XXX--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<div id="main" style="height:800px;"></div>--%>
                        <%--&lt;%&ndash;<img style="margin:20px" width="280" height="140"--%>
                             <%--src="http://api.map.baidu.com/staticimage/v2?ak=etLBbgQqfQQE3Cb25G29FjDKZtQnzVCp&width=680&height=640&zoom=11"/>&ndash;%&gt;--%>
                            <%--<script src="/ke/media/js/track/echarts/echarts.js"></script>--%>
                            <%--<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ogQzr2weGLe00PmSAZWf1eZ912ldWp1s"></script>--%>
                            <%--<script src="/ke/media/js/track/t_js/jquery.min.js"></script>--%>
                            <%--<script src="/ke/media/js/track/t_js/require.js"></script>--%>
                            <%--<script src="/ke/media/js/track/t_js/example3.js"></script>--%>

                    <%--</div>--%>
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
