<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <script src="/ke/media/js/public/jquery.js" type="text/javascript"></script>
    <script src="/ke/media/js/public/bootstrap.min.js" type="text/javascript"></script>
    <script src="/ke/media/js/public/morris.min.js" type="text/javascript"></script>
    <script src="/ke/media/js/public/navbar.js" type="text/javascript"></script>
    <script src="/ke/media/js/public/bootstrap-treeview.min.js" type="text/javascript"></script>
    <script src="/ke/media/js/plugins/handlebars/handlebars-v4.0.10.js" type="text/javascript"></script>

    <jsp:include page="../public/css.jsp"></jsp:include>
    <title>搜索回顾</title>
</head>
<body>
<jsp:include page="../public/navbar.jsp"></jsp:include>
<div id="wrapper">
    <div class="page-wrapper" style="background-color: #FFFFFF">
        <div class="container block">
            <c:forEach items="${urls}" varStatus="i" var="url">
                <div class="row">
                    <h2>第${i.count}个查询</h2>
                </div>
                <div class="row">
                    <img name="img-${i.count}" src="${url}" frameborder="0" />
                </div>
                <hr>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>