<%--
  Created by IntelliJ IDEA.
  User: lee
  Date: 2018/3/11
  Time: 下午3:29
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
    <script language=javascript>
        <!--
        // 拼接url
        function mbar(sobj2) {
            var sobj1 = document.getElementById("select");
            var docurl ="http://192.168.222.232:3000/dashboard-solo/db/bodymetric?orgId=1&panelId=1&from="+sobj1.options[sobj1.selectedIndex].value;
            var sobj2 = document.getElementById("refresh");
            var docur2 =docurl+"&to=now&refresh="+sobj2.options[sobj2.selectedIndex].value+"&theme=light";
            var frame = document.getElementById("panel1");
            frame.src=docur2;//替换url
            if (docur2 != "") {
                // open(docur2,'_blank');
                sobj.selectedIndex=1;
                sobj2.blur();
            }
        }
        //-->
    </script>
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
                    <div  >
                        选择区间&nbsp;&nbsp;
                        <select id="select" style="width:160px;height:25px" onchange=mbar(this) name="select">
                            <option value="now-0.5h">最近30分钟</option>
                            <option value ="now-1h">最近1小时</option>
                            <option value ="now-2h">最近2小时</option>
                            <option value="now-3h">最近3小时</option>
                            <option value="now-4h">最近4小时</option>
                        </select>

                    </div>
                    <div >
                        刷新频率&nbsp;&nbsp;
                        <select id="refresh" style="width:160px;height:25px" onchange=mbar(this) name="refresh">
                            <option value="10s">10秒</option>
                            <option value ="20s">20秒</option>
                            <option value ="30s">30秒</option>
                        </select>

                    </div>
                    <div class="panel-body " >
                        <div class="col-lg-6" id="p_bloodPressure">
                            <iframe id="panel1" src="http://192.168.222.232:3000/dashboard-solo/db/bodymetric?refresh=30s&orgId=1&panelId=1&from=now-3h&to=now&theme=light" width="100%" height="300" frameborder="0"></iframe>
                        </div>
                        <div class="col-lg-6" id="p_heartRate">
                            <iframe src="http://192.168.222.232:3000/dashboard-solo/db/bodymetric?refresh=30s&orgId=1&panelId=2&from=now-3h&to=now&theme=light" width="100%" height="300" frameborder="0"></iframe>
                        </div>
                        <div class="col-lg-6" id="p_stepCount">
                            <iframe src="http://192.168.222.232:3000/dashboard-solo/db/bodymetric?orgId=1&from=1484325665000&to=1484412065000&panelId=3&theme=light" width="100%" height="300" frameborder="0"></iframe>
                        </div>
                        <div class="col-lg-6" id="p_bodyTemperature">
                            <iframe src="http://192.168.222.232:3000/dashboard-solo/db/bodymetric?orgId=1&refresh=1m&from=1521673396153&to=1521684196153&panelId=5&theme=light" width="100%" height="200" frameborder="0"></iframe>
                        </div>

                    </div>
                </div>


                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-cogs fa-fw"></i> 个人轨迹信息
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">

                        <div id="main" style="height:800px;"></div>
                        <%--<img style="margin:20px" width="280" height="140"
                             src="http://api.map.baidu.com/staticimage/v2?ak=etLBbgQqfQQE3Cb25G29FjDKZtQnzVCp&width=680&height=640&zoom=11"/>--%>
                        <script src="/ke/media/js/track/echarts/echarts.js"></script>
                        <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ogQzr2weGLe00PmSAZWf1eZ912ldWp1s"></script>
                        <script src="/ke/media/js/track/t_js/jquery.min.js"></script>
                        <script src="/ke/media/js/track/t_js/require.js"></script>
                        <script src="/ke/media/js/track/t_js/example3.js"></script>

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
