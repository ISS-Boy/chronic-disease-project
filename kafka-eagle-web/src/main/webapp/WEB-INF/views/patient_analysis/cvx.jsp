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
                    CVX标准对照表
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
                            <button id="ke-add-cvx-btn" type="button"
                                    class="btn btn-primary btn-xs" data-toggle="modal"
                                    data-target="#ke_cvx_add_dialog">新增
                            </button>
                            <button id="deleteCvx" class="btn btn-danger btn-xs">删除</button>
                            <button id="modifyCvx" class="btn btn-success btn-xs" data-toggle="modal"
                            >编辑
                            </button>
                        </div>
                    </div>
                    <div class="panel-body">

                        <table id="cvxTable" class="table table-bordered table-condensed" width="100%">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Cvx编码</th>
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
             id="ke_cvx_add_dialog" tabindex="-1"
             role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal">×</button>
                        <h4 class="modal-title" id="cvxLabel">
                            Add cvx
                        </h4>
                    </div>
                    <!-- /.row -->
                    <form role="form">
                        <fieldset class="form-horizontal">
                            <div class="form-group">
                                <label for="path" class="col-sm-2 control-label">Cvxcode</label>
                                <div class="col-sm-9">
                                    <input id="ke_add_cvx_code" name="cvxcode" type="text"
                                           class="form-control" placeholder="输入编码">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="path" class="col-sm-2 control-label">CvxDescription</label>
                                <div class="col-sm-9">
                                    <input id="ke_add_cvxDescription" name="cvxDescription" type="text"
                                           class="form-control" placeholder="输入名称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="path" class="col-sm-2 control-label">helpCode</label>
                                <div class="col-sm-9">
                                    <input id="ke_add_helpCode" name="helpCode" type="text"
                                           class="form-control" placeholder="助记码">
                                </div>
                            </div>

                            <div id="alert_cvx_add_mssage" style="display: none"
                                 class="alert alert-danger">
                                <label> 输入不能为空</label>
                            </div>
                        </fieldset>

                        <div id="remove_div" class="modal-footer">
                            <button type="button" class="btn btn-default"
                                    data-dismiss="modal">Cancle
                            </button>
                            <button type="button" class="btn btn-primary" id="cvx_create-btn">Submit
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- add modal -->
        <!-- modify modal -->
        <div class="modal fade" aria-labelledby="myModalLabel"
             id="ke_cvx_modify_dialog" tabindex="-1"
             role="dialog" data-toggle="modal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal">×</button>
                        <h4 class="modal-title" id="cvx2Label">
                            修改 cvx
                        </h4>
                    </div>
                    <!-- /.row -->
                    <form role="form">
                        <fieldset class="form-horizontal">
                            <div class="form-group">
                                <input id="ke_modify_cvx_id" name="id" type="hidden"
                                       class="form-control">
                            </div>
                            <div class="form-group">
                                <label for="path" class="col-sm-2 control-label">cvxCode</label>
                                <div class="col-sm-9">
                                    <input id="ke_modify_cvx_code" name="cvxcode" type="text"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="path" class="col-sm-2 control-label">cvxCnomen</label>
                                <div class="col-sm-9">
                                    <input id="ke_modify_cvxDescription" name="cvxDescription" type="text"
                                           class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="path" class="col-sm-2 control-label">helpCode</label>
                                <div class="col-sm-9">
                                    <input id="ke_modify_cvx_helpCode" name="helpCode" type="text"
                                           class="form-control">
                                </div>
                            </div>

                            <div id="alert_cvx_edit_mssage" style="display: none"
                                 class="alert alert-danger">
                                <label> Oops! Please make some changes .</label>
                            </div>
                        </fieldset>

                        <div id="removeCvx_div" class="modal-footer">
                            <button type="button" class="btn btn-default"
                                    data-dismiss="modal">Cancle
                            </button>
                            <button type="button" class="btn btn-primary" id="cvx_modify-btn">Submit
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
    <jsp:param value="main/patient/cvx.js" name="loader"/>
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>
<script src="https://cdn.datatables.net/select/1.2.5/js/dataTables.select.min.js"></script>
<script src=".https://cdn.datatables.net/buttons/1.5.1/js/dataTables.buttons.min.js"></script>
</html>


