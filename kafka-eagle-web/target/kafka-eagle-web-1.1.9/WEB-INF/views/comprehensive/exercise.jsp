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
                    运动习惯分析<small>overview</small>
                </h1>
                选择日期&nbsp;&nbsp;
                <select style="width:160px;height:25px">
                    <option value ="volvo">2017年11月30</option>
                    <option value ="saab">2017年11月29</option>
                    <option value="opel">2017年11月28</option>
                    <option value="audi">2017年11月27</option>
                    <option value="audi">2017年11月26</option>
                    <option value="audi">2017年11月25</option>
                    <option value="audi">2017年11月24</option>
                    <option value="audi">2017年11月23</option>
                    <option value="audi">2017年11月22</option>
                    <option value="audi">2017年11月21</option>
                    <option value="audi">2017年11月20</option>
                </select>
            </div>
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-cogs fa-fw"></i> 运动状态
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">

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
    <jsp:param value="main/patient/currentTime.js" name="loader" />
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>
</html>
