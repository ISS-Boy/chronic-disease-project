<%--
  Created by IntelliJ IDEA.
  User: LH
  Date: 2017/11/4
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
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

    <title>Data - KafkaEagle</title>
    <jsp:include page="../public/css.jsp"></jsp:include>
    <jsp:include page="../public/tcss.jsp"></jsp:include>
   <%-- <script type="text/javascript">
        function delete(id,name){
            if(confirm("Are you sure to delete"+name+"?")){
                location.href=""+id;
            }
        }
    </script>--%>
</head>

<body>
<jsp:include page="../public/navbar.jsp"></jsp:include>
<div id="wrapper">
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">
                   DATATEST <small>overview</small>
                </h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="alert alert-info alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert"
                            aria-hidden="true">×</button>
                    <i class="fa fa-info-circle"></i> <strong>DATATEST CURD</strong>
                </div>
            </div>
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-cogs fa-fw"></i> DATATEST
                        <div class="pull-right">
                            <button id="ke-add-user-btn" type="button"
                                    class="btn btn-primary btn-xs">Add</button>
                        </div>
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <table id="result" class="table table-bordered table-condensed"
                               width="100%">
                            <thead>
                            <tr>
                                <th>dNumb</th>
                                <th>name</th>
                                <th>description</th>
                                <th>value</th>
                                <th>Operate</th>
                            </tr>
                            </thead>
                            <tbody>
                           <c:forEach items="${dataT}" var="d">
                            <tr>
                                <td>${d.dNumb }</td>
                                <td>${d.name }</td>
                                <td>${d.description }</td>
                                <td>${d.value }</td>
                                <td>
                                    <%--<input type="hidden" name="dt-id" class="dt-id" value="${d.id}">--%>
                                    <button type="button" id="dt-modify-btn"
                                            class="btn btn-sm btn-success modify-dt" value="${d.id}" onclick="modifyReview(this)">
                                        <span class="fa fa-edit fa-fw"></span> modify
                                    </button>
                                    <button type="button" id="dt-delete-btn"
                                            class="btn btn-sm btn-danger delete-dt" value="${d.id}" onclick="delData(this)">
                                        <span class="fa fa-trash-o fa-fw"></span> Delete
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                            </tbody>
                        </table>
                        <tr>
                            <td id="page">
                                <ul class="pagination">
                                    <li><a href="javascript:;" class="previous">Previous</a></li>
                                    <li class="active pageNum"><a href="javaScript:;">1</a></li>
                                    <li class="pageNum"><a href="javascript:;">2</a></li>
                                    <li class="pageNum"><a href="javascript:;">3</a></li>
                                    <li class="pageNum"><a href="javascript:;">4</a></li>
                                    <li class="pageNum"><a href="javascript:;">5</a></li>
                                    <li><a href="javascript:;" class="next">Next</a></li>
                                </ul>
                            </td>
                        </tr>
                    </div>
                </div>
                <!-- /.col-lg-4 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- add modal -->
        <div class="modal fade" aria-labelledby="keModalLabel"
             aria-hidden="true" id="ke_user_add_dialog" tabindex="-1"
             role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal">×</button>
                        <h4 class="modal-title" id="datatestLabel">
                            Add DATA
                        </h4>
                    </div>
                    <!-- /.row -->
                    <form role="form" action="/ke/system/datatest/add/" method="post"
                          onsubmit="return contextFormValid();return false;">
                        <fieldset class="form-horizontal">
                            <div class="form-group">
                                <label for="path" class="col-sm-2 control-label">dNumb</label>
                                <div class="col-sm-9">
                                    <input id="ke_rtxno_name" name="ke_rtxno_name" type="text"
                                           class="form-control" placeholder="输入编号">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="path" class="col-sm-2 control-label">name</label>
                                <div class="col-sm-9">
                                    <input id="ke_real_name" name="ke_real_name" type="text"
                                           class="form-control" placeholder="输入数据名称">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="path" class="col-sm-2 control-label">description</label>
                                <div class="col-sm-9">
                                    <input id="ke_user_name" name="ke_user_name" type="text"
                                           class="form-control" placeholder="输入数据描述">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="path" class="col-sm-2 control-label">value</label>
                                <div class="col-sm-9">
                                    <input id="ke_user_email" name="ke_user_email" type="text"
                                           class="form-control" placeholder="输入数据值">
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
                            <button type="submit" class="btn btn-primary" id="create-btn">Submit
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- modal modify -->
        <div class="modal fade" aria-labelledby="keModalLabel"
             aria-hidden="true" id="ke_user_modify_dialog" tabindex="-1"
             role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close" type="button" data-dismiss="modal">×</button>
                        <h4 class="modal-title" id="keModalLabel">
                            Modify Data
                        </h4>
                    </div>
                    <!-- /.row -->
                    <form role="form" action="/ke/system/datatest/modify/" method="post"
                          onsubmit="return contextModifyFormValid();return false;">
                        <fieldset class="form-horizontal">
                            <div class="form-group">
                                <label for="path" class="col-sm-2 control-label">dNumb</label>
                                <div class="col-sm-9">
                                    <input id="ke_user_id_modify" name="ke_user_id_modify"
                                           type="hidden" class="form-control" placeholder="1000">
                                    <input id="ke_rtxno_name_modify" name="ke_rtxno_name_modify" type="text"
                                           class="form-control" placeholder="1000">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="path" class="col-sm-2 control-label">name</label>
                                <div class="col-sm-9">
                                    <input id="ke_real_name_modify" name="ke_real_name_modify" type="text"
                                           class="form-control" placeholder="萝莉">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="path" class="col-sm-2 control-label">description</label>
                                <div class="col-sm-9">
                                    <input id="ke_user_name_modify" name="ke_user_name_modify" type="text"
                                           class="form-control" placeholder="smartloli">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="path" class="col-sm-2 control-label">value</label>
                                <div class="col-sm-9">
                                    <input id="ke_user_email_modify" name="ke_user_email_modify" type="text"
                                           class="form-control" placeholder="smartloli@email.com">
                                </div>
                            </div>
                            <div id="alert_mssage_modify" style="display: none"
                                 class="alert alert-danger">
                                <label> Oops! Please make some changes .</label>
                            </div>
                        </fieldset>

                        <div id="removeDt_div" class="modal-footer">
                            <button type="button" class="btn btn-default"
                                    data-dismiss="modal">Cancle</button>
                            <button type="submit" class="btn btn-primary" id="create-btn">Submit
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
    <jsp:param value="main/system/datatest.js" name="loader" />
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>
<script type="text/javascript">
    function contextFormValid() {
        var ke_rtxno_name = $("#ke_rtxno_name").val();
        var ke_real_name = $("#ke_real_name").val();
        var ke_user_name = $("#ke_user_name").val();
        var ke_user_email = $("#ke_user_email").val();
        if (ke_rtxno_name.length == 0 || ke_real_name.length == 0 || ke_user_name.length == 0 || ke_user_email.length == 0) {
            $("#alert_mssage").show();
            setTimeout(function() {
                $("#alert_mssage").hide()
            }, 3000);
            return false;
        }

        return true;
    }

    function contextModifyFormValid() {
        var ke_rtxno_name_modify = $("#ke_rtxno_name_modify").val();
        var ke_real_name_modify = $("#ke_real_name_modify").val();
        var ke_user_name_modify = $("#ke_user_name_modify").val();
        var ke_user_email_modify = $("#ke_user_email_modify").val();
        if (ke_rtxno_name_modify.length == 0 || ke_real_name_modify.length == 0 || ke_user_name_modify.length == 0 || ke_user_email_modify.length == 0) {
            $("#alert_mssage_modify").show();
            setTimeout(function() {
                $("#alert_mssage_modify").hide()
            }, 3000);
            return false;
        }

        return true;
    }

function delData(btn) {
    var id = $(btn).val();
    var url = "/ke/system/datatest/delete";
    if (confirm("Are you sure to delete it?")) {
        $.post(url, {"id": id}, function () {
            tips("删除成功");
        })
        $(btn).parent().parent().hide();
    }
}

</script>
</html>
