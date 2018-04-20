<%--
  Created by IntelliJ IDEA.
  User: lee
  Date: 2018/3/26
  Time: 下午4:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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

    <link rel="stylesheet" href="https://cdn.datatables.net/select/1.2.5/css/select.dataTables.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.5.1/css/buttons.dataTables.min.css">
</head>
<body>
<jsp:include page="../public/navbar.jsp"></jsp:include>
<div id="wrapper">
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">
                    Rxnorm标准对照表
                    <small>overview</small>
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
                                    class="btn btn-primary btn-xs" data-toggle="modal"
                                    data-target="#ke_snomed_add_dialog">新增
                            </button>
                            <button id="deleteSnomed" class="btn btn-danger btn-xs">删除</button>
                            <button id="modifySnomed" class="btn btn-success btn-xs" data-toggle="modal"
                            >编辑
                            </button>
                        </div>
                    </div>
                    <div class="panel-body">

                        <table id="RxnormTable" class="table table-bordered table-condensed" width="100%">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Rxnorm编码</th>
                                <th>编码名称</th>
                                <th>助记码</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!-- add modal -->
        <div class="modal fade" aria-labelledby="myModalLabel"
             id="ke_snomed_add_dialog" tabindex="-1"
             role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal">×</button>
                        <h4 class="modal-title" id="snomedLabel">
                            Add snomed
                        </h4>
                    </div>
                    <!-- /.row -->
                    <form role="form" action="/ke/patient_analysis/rxnorm_add" method="post"
                          onsubmit="return contextFormValid();return false;">
                        <fieldset class="form-horizontal">
                            <div class="form-group">
                                <label for="path" class="col-sm-2 control-label">SnomedCode</label>
                                <div class="col-sm-9">
                                    <input id="ke_snomed_code" name="ke_snomed_code" type="text"
                                           class="form-control" placeholder="输入编码">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="path" class="col-sm-2 control-label">SnomedCnomen</label>
                                <div class="col-sm-9">
                                    <input id="ke_snomedCnomen" name="ke_snomedCnomen" type="text"
                                           class="form-control" placeholder="输入名称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="path" class="col-sm-2 control-label">helpCode</label>
                                <div class="col-sm-9">
                                    <input id="ke_snomed_helpCode" name="ke_helpCode" type="text"
                                           class="form-control" placeholder="助记码">
                                </div>
                            </div>

                            <div id="alert_snomed_add_mssage" style="display: none"
                                 class="alert alert-danger">
                                <label> Oops! Please make some changes .</label>
                            </div>
                        </fieldset>

                        <div id="remove_div" class="modal-footer">
                            <button type="button" class="btn btn-default"
                                    data-dismiss="modal">Cancle
                            </button>
                            <button type="submit" class="btn btn-primary" id="snomed_create-btn">Submit
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- add modal -->
        <!-- modify modal -->
        <div class="modal fade" aria-labelledby="myModalLabel"
             id="ke_snomed_modify_dialog" tabindex="-1"
             role="dialog" data-toggle="modal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal">×</button>
                        <h4 class="modal-title" id="snomed1Label">
                            修改 snomed
                        </h4>
                    </div>
                    <!-- /.row -->
                    <form role="form">
                        <fieldset class="form-horizontal">
                            <div class="form-group">
                                <input id="ke_modify_snomed_id" name="id" type="hidden"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="path" class="col-sm-2 control-label">SnomedCode</label>
                                <div class="col-sm-9">
                                    <input id="ke_modify_snomed_code" name="snomedCode" type="text"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="path" class="col-sm-2 control-label">SnomedCnomen</label>
                                <div class="col-sm-9">
                                    <input id="ke_modify_snomedCnomen" name="snomedCnomen" type="text"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="path" class="col-sm-2 control-label">helpCode</label>
                                <div class="col-sm-9">
                                    <input id="ke_modify_snomed_helpCode" name="helpCode" type="text"
                                           class="form-control">
                                </div>
                            </div>

                            <div id="alert_snomed_edit_mssage" style="display: none"
                                 class="alert alert-danger">
                                <label> Oops! Please make some changes .</label>
                            </div>
                        </fieldset>

                        <div id="removeSnomed_div" class="modal-footer">
                            <button type="button" class="btn btn-default"
                                    data-dismiss="modal">Cancle
                            </button>
                            <button type="button" class="btn btn-primary" id="snomed_modify-btn">Submit
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- modify modal -->
    </div>
</div>
</body>
<jsp:include page="../public/script.jsp">
    <jsp:param value="main/patient/rxnorm.js" name="loader"/>
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>
<script src="https://cdn.datatables.net/select/1.2.5/js/dataTables.select.min.js"></script>
<script src=".https://cdn.datatables.net/buttons/1.5.1/js/dataTables.buttons.min.js"></script>
</html>


