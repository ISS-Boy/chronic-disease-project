<%--
  Created by IntelliJ IDEA.
  User: lee
  Date: 2018/3/8
  Time: 下午9:25
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
                    LOINC标准对照表<small>overview</small>
                </h1>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-cogs fa-fw"></i> 对照表
                        <div class="pull-right">
                            <button id="ke-add-icd-btn" type="button"
                                    class="btn btn-primary btn-xs" data-toggle="modal" data-target="#ke_icd_add_dialog">Add</button>
                        </div>
                    </div>
                    <div class="panel-body">
                        <table id="loincTable" class="table table-bordered table-condensed" width="100%">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>LOINC码</th>
                                <th>LOINC组成</th>
                                <th>LOINC性质</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!-- add modal -->
        <div class="modal fade" aria-labelledby="myModalLabel"
             id="ke_icd_add_dialog" tabindex="-1"
             role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal">×</button>
                        <h4 class="modal-title" id="datatestLabel">
                            Add snomed
                        </h4>
                    </div>
                    <!-- /.row -->
                    <form role="form" action="/ke/patient_analysis/loinc_add" method="post"
                          onsubmit="return contextFormValid();return false;">
                        <fieldset class="form-horizontal">
                            <div class="form-group">
                                <label for="path" class="col-sm-3 control-label">loincCode</label>
                                <div class="col-sm-9">
                                    <input id="ke_loincCode" name="loincCode" type="text"
                                           class="form-control" placeholder="输入编码">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="path" class="col-sm-3 control-label">loincComponent</label>
                                <div class="col-sm-9">
                                    <input id="ke_oincComponent" name="loincComponent" type="text"
                                           class="form-control" placeholder="输入名称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="path" class="col-sm-3 control-label">loincProperty</label>
                                <div class="col-sm-9">
                                    <input id="ke_loincProperty" name="loincProperty" type="text"
                                           class="form-control" placeholder="助记码">
                                </div>
                            </div>

                            <div id="alert_mssage" style="display: none"
                                 class="alert alert-danger">
                                <label> Oops! Please make some changes .</label>
                            </div>
                        </fieldset>

                        <div id="remove_div" class="modal-footer">
                            <button type="button" class="btn btn-default"
                                    data-dismiss="modal">Cancle</button>
                            <button type="submit" class="btn btn-primary" id="Icd_create-btn">Submit
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </div>
</div>
</body>
<jsp:include page="../public/script.jsp">
    <jsp:param value="main/patient/loinc.js" name="loader" />
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>

</html>

