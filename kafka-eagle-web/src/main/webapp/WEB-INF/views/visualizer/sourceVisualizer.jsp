<%--
  Created by IntelliJ IDEA.
  User: lee
  Date: 2018/3/11
  Time: 下午3:55
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

        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <iframe id="visualIframe" src="http://172.16.17.131:8080/?filter=${monitorGroupId}" scrolling="yes" onload="changeFrameHeight()" frameborder="0" width="100%"></iframe>
                </div>
                <!-- /.col-lg-4 -->
            </div>
            <!-- /.row -->
        </div>

    </div>
</div>
</body>
<script type="text/javascript">
    function changeFrameHeight(){
        var ifm= document.getElementById("visualIframe");
        ifm.height=document.documentElement.clientHeight;
    }
    window.onresize=function(){
        changeFrameHeight();
    }
</script>
<jsp:include page="../public/script.jsp">
    <jsp:param value="main/patient/currentTime.js" name="loader" />
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>
</html>

