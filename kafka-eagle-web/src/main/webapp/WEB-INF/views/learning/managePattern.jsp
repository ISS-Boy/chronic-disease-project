<%--
  Created by IntelliJ IDEA.
  User: lee
  Date: 2018/11/1
  Time: 3:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
                    在线模式匹配管理
                </h1>
            </div>
        </div>

        <div class="row">
            <div class="panel-body">
                <table id="table_report" class="table table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th class="center">序号</th>
                        <th class='center'>名称</th>
                        <th class='center'>状态</th>
                        <th class='center'>操作</th>
                    </tr>
                    </thead>
                    <c:choose>
                        <c:when test="${not empty keonlines}">
                            <c:forEach items="${keonlines}" var="keonlines" varStatus="vs">

                                <tr id="tr${keonlines.configureId }">
                                    <td class="center">${keonlines.id}</td>
                                    <td class='center'>${keonlines.configureName}</td>
                                    <td class='center'>${keonlines.status}</td>
                                    <td style="width: 10%">
                                        <c:if test="${keonlines.status == '正在匹配'}">
                                            <a class='btn btn-primary' title="停止" onclick="stopMatching('${keonlines.id }','${keonlines.configureId }')" ><i class='fa fa-stop'></i></a>
                                        </c:if>

                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="100">没有相关数据</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </table>
            </div>
        </div>
    </div>
</div>


</body>
<jsp:include page="../public/script.jsp">
    <jsp:param value="ace/js/jquery.tips.js" name="loader" />
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>
<script>
    function addConfigure() {
        window.location.href="/ke/offlineLearning/toAdd?type=0";
    }

    function stopMatching(id,configureId) {
        var result = confirm("确定要停止吗?");
        if(result) {
            $.ajax({
                type: "POST",
                url: '/ke/onlineLearning/stopMatching',
                data: {id:id,configureId:configureId},
                dataType:'json',
                cache: false,
                success: function(msg){
                    if (msg.msg == "success") {
                        document.location.reload();
                    }
                }
            });
        }
    }

</script>
</html>
