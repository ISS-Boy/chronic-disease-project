
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
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-cogs fa-fw"></i>
                        <div id="main" style="height:600px;border:1px solid #ccc;padding:10px">
                        </div>
                    </div>
                    <!-- /.panel-heading -->

                </div>
                <!-- /.col-lg-4 -->
            </div>
            <!-- /.row -->
        </div>

    </div>
</div>
</body>

<script type="text/javascript" src="/ke/media/js/monitor/jquery.js"></script>
<script type="text/javascript" src="/ke/media/js/monitor/echarts.js"></script>
<script type="text/javascript" src="/ke/media/js/monitor/bmap.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ogQzr2weGLe00PmSAZWf1eZ912ldWp1s"></script>
<script type="text/javascript" src="/ke/media/js/monitor/diease_history_timeline.js"></script>
<jsp:include page="../public/script.jsp">
    <jsp:param value="main/patient/currentTime.js" name="loader" />
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>

</html>
