<%--
  Created by IntelliJ IDEA.
  User: lee
  Date: 2018/10/30
  Time: 3:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
                    在线模式匹配
                </h1>
            </div>
        </div>
        <div class="row">

        </div>
        <div class="row">
            <div class="panel-body">
                <div class="col-lg-12" id="p_bloodPressure">
                    <iframe id="panel1" src="http://192.168.222.232:3000/dashboard/db/matchpatterntest?orgId=1&theme=light" width="100%" height="700px" frameborder="0"></iframe>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
<jsp:include page="../public/script.jsp">
    <jsp:param value="ace/js/jquery.tips.js" name="loader" />
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>
<script language=javascript>

</script>
</html>