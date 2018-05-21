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
                    病患地区定位
                </h1>
                <h3 class="current-time">

                </h3>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div>
                    <select  id="year"  name="val1" >
                        <option >2010</option>
                        <option >2011</option>
                        <option >2012</option>
                        <option >2013</option>
                        <option >2014</option>
                        <option >2015</option>
                        <option >2016</option>
                        <option >2017</option>
                        <option >2018</option>
                    </select>
                    <select  id="disease"  name="val2" >
                        <option >Hypertension</option>
                        <option >Chronic sinusitis (disorder)</option>
                        <option >History of appendectomy</option>
                        <option >Malignant tumor of colon</option>
                        <option >Asthma</option>
                        <option >Coronary Heart Disease</option>
                        <option >Prediabetes</option>
                        <option >Non-small cell lung cancer (disorder)</option>
                    </select>
                    <button id="countrysub" type="button">确定</button>
                </div>
            </div>
        </div>

        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-cogs fa-fw"></i> 总体指标
                        <div id="main" style="height:600px;border:1px solid #ccc;padding:10px">
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
<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="/ke/media/js/monitor/echarts.js"></script>
<script type="text/javascript" src="/ke/media/js/monitor/bmap.js"></script>
<script type="text/javascript" src="/ke/media/js/monitor/china.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ogQzr2weGLe00PmSAZWf1eZ912ldWp1s"></script>
<script type="text/javascript" src="/ke/media/js/monitor/whole_location.js"></script>
<script type="text/javascript">
    $(function (){
        $('#countrysub').click(function(){
            var value1 = $('#year').val();
            var value2 = $('#disease').val();
            $.ajax({
                url:"/ke/monitor/whole_country/selected?value1="+value1+"&value2="+value2,
                type:"get",
                dataType:'json',
                success:function(){
                    myChart.showLoading();
                    setData()
                },
                error : function() {
                }

            })
        })
    })
</script>


<jsp:include page="../public/script.jsp">
    <jsp:param value="main/patient/currentTime.js" name="loader" />
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>

</html>

