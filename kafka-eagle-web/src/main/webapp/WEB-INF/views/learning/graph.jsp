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
	<link rel="icon" href="https://static.jianshukeji.com/highcharts/images/favicon.ico">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>
	<script src="https://img.hcharts.cn/highcharts/modules/exporting.js"></script>
	<script src="https://img.hcharts.cn/highcharts/modules/oldie.js"></script>
	<script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>

<title>Role - KafkaEagle</title>
<jsp:include page="../public/css.jsp"></jsp:include>
<jsp:include page="../public/tcss.jsp"></jsp:include>
</head>

<body>
	<div id="container" style="min-width:400px;height:400px" data-highcharts-chart="0"></div>
</body>
<jsp:include page="../public/script.jsp">
	<jsp:param value="main/offlineLearning/learning.js" name="loader" />
	<jsp:param value="ace/js/jquery.tips.js" name="loader" />
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>
<script>
    var result = '';
    var measure = '${patternDetail.measureName}';
    if (measure == 'systolic_blood_pressure' || measure == 'diastolic_blood_pressure') {
        result = 'mmHg';
    } else if (measure == 'heart_rate') {
        result = 'beats/min';
    } else if (measure == 'body_temperature') {
        result = '℃';
    }

    var chart = Highcharts.chart('container', {
        chart: {
            type: 'line'
        },
        title: {
            text: measure
        },
        yAxis: {
            title: {
                text: result
            }
        },
        //subtitle: {
        //text: '数据来源: WorldClimate.com'
        //},
        plotOptions: {
            line: {
                dataLabels: {
                    // 开启数据标签
                    enabled: false
                },
                // 开启鼠标跟踪，对应的提示框、点击事件会失效
                enableMouseTracking: true
            }
        },
        series: [{
            name: measure,
            data: ${patternDetail.datas}
        }]
    });
</script>
</html>
