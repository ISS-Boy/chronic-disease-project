<%--
  Created by IntelliJ IDEA.
  User: lee
  Date: 2018/4/1
  Time: 上午10:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
    <link rel="stylesheet" type="text/css" media="all" href="/ke/media/css/chronicD/timeline_styles.css">
    <link rel="stylesheet" type="text/css" media="all" href="/ke/media/css/public/bootstrap-glyphicons.css">
</head>
<body>
<jsp:include page="../public/navbar.jsp"></jsp:include>
<div id="wrapper">
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">
                    用户历史数据<small>overview</small>
                </h1>
                <h3 class="current-time">

                </h3>
            </div>
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div id="" class="">
                    <label>请输入用户id：</label>
                    <input id="userId" name="userId" class="" type="text" placeholder="例：the-user-1">
                    <input id="changeUserBtn" type="button" class="btn-primary" onclick="changeUser()" value="确定"／>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-cogs fa-fw"></i> 时间线
                    </div>

                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <!-- timeline -->
                        <div id="timelineshow">
                            <ul class="timeline">
                                <li><div class="tldate">TIMELINE</div></li>

                            </ul>

                        </div>
                        <!-- timeline -->

                        <!-- encounter详情模态框（Modal） -->
                        <div class="modal fade bs-example-modal-lg" id="myEncounterModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                        <h4 class="modal-title" id="myModalLabel">encounter详情</h4>
                                    </div>
                                    <div class="modal-body">
                                        <div class="panel panel-primary">
                                            <div class="panel-heading">
                                                <h3 class="panel-title" id="event_medication">Medication</h3>
                                            </div>
                                            <div class="panel-body">
                                                <table id="medicationDetail" class="table table-bordered table-condensed" width="100%">
                                                    <thead>
                                                    <tr>
                                                        <th>Codes</th>
                                                        <th>Description</th>
                                                        <th>Time</th>
                                                        <th>Status</th>
                                                        <th>Results</th>
                                                        <th>Fields</th>
                                                    </tr>
                                                    </thead>
                                                </table>
                                            </div>
                                        </div>

                                        <div class="panel panel-warning">
                                            <div class="panel-heading">
                                                <h3 class="panel-title" id="event_allergy">Allergy</h3>
                                            </div>
                                            <div class="panel-body">
                                                <table id="allergyDetail" class="table table-bordered table-condensed" width="100%">
                                                    <thead>
                                                    <tr>
                                                        <th>Description</th>
                                                    </tr>
                                                    </thead>
                                                </table>
                                            </div>
                                        </div>

                                        <div class="panel panel-info">
                                            <div class="panel-heading">
                                                <h3 class="panel-title" id="event_careplan">CarePlan</h3>
                                            </div>
                                            <div class="panel-body">
                                                <table id="carePlanDetail" class="table table-bordered table-condensed" width="100%">
                                                    <thead>
                                                    <tr>
                                                        <th>Codes</th>
                                                        <th>Description</th>
                                                        <th>Time</th>
                                                        <th>Reasondescription</th>
                                                    </tr>
                                                    </thead>
                                                </table>
                                            </div>
                                        </div>

                                        <div class="panel panel-success">
                                            <div class="panel-heading">
                                                <h3 class="panel-title" id="event_condition">Condition</h3>
                                            </div>
                                            <div class="panel-body">
                                                <table id="conditionDetail" class="table table-bordered table-condensed" width="100%">
                                                    <thead>
                                                    <tr>
                                                        <th>Codes</th>
                                                        <th>Description</th>
                                                        <th>Time</th>
                                                        <th>Reasondescription</th>
                                                    </tr>
                                                    </thead>
                                                </table>
                                            </div>
                                        </div>

                                        <div class="panel panel-danger">
                                            <div class="panel-heading">
                                                <h3 class="panel-title" id="event_immunization">Immunization</h3>
                                            </div>
                                            <div class="panel-body">
                                                <table id="immunizationDetail" class="table table-bordered table-condensed" width="100%">
                                                    <thead>
                                                    <tr>
                                                        <th>Description</th>
                                                        <th>Codes</th>
                                                        <th>Time</th>
                                                        <th>Reasondescription</th>

                                                    </tr>
                                                    </thead>
                                                </table>
                                            </div>
                                        </div>

                                        <div class="panel panel-primary">
                                            <div class="panel-heading">
                                                <h3 class="panel-title" id="event_observation">Observation</h3>
                                            </div>
                                            <div class="panel-body">
                                                <table id="ObservationDetail" class="table table-bordered table-condensed" width="100%">
                                                    <thead>
                                                    <tr>
                                                        <th>body_height</th>
                                                        <th>body_mass_index</th>
                                                        <th>body_weight</th>
                                                        <th>calcium</th>
                                                        <th>carbon_dioxide</th>
                                                        <th>chloride</th>
                                                        <th>creatinine</th>
                                                        <th>estimated_glomerular_filtration_rate</th>
                                                        <th>glucose</th>
                                                        <th>hemoglobin_A1c_or_hemoglobin_total_in_blood</th>
                                                        <th>high_density_lipoprotein_cholesterol</th>
                                                        <th>low_density_lipoprotein_cholesterol</th>
                                                        <th>microalbumin_creatinine_ratio</th>
                                                        <th>potassium</th>
                                                        <th>sodium</th>
                                                        <th>systolic_blood_pressure</th>
                                                        <th>total_cholesterol</th>
                                                        <th>triglycerides</th>
                                                        <th>urea_uitrogen</th>
                                                    </tr>
                                                    </thead>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                        <!-- <button type="button" class="btn btn-primary">提交更改</button> -->
                                    </div>
                                </div><!-- /.modal-content -->
                            </div><!-- /.modal -->
                        </div>
                        <!-- encounter详情模态框（Modal） -->
                        <div class="panel-heading">
                            <i class="fa fa-cogs fa-fw"></i>关键指标
                        </div>
                        <div>
                            <a href="/ke/patient_analysis/icdlist">查看国际标准ICD-10编码</a>
                            <a href="/ke/patient_analysis/snomedlist">查看SNOMED-CT编码</a>
                            <a href="/ke/patient_analysis/loinclist">查看LOINC编码</a>
                            <a href="/ke/patient_analysis/Rxnormlist">查看Rxnorm编码</a>
                            <a href="/ke/patient_analysis/cvxlist">查看cvx编码</a>
                        </div>

                    </div>
                </div>
                <!-- /.col-lg-4 -->
            </div>
            <!-- /.row -->
        </div>

    </div>
</div>
</body>
<script id="timeline_content-template" type="text/x-handlebars-template">
    {{#each this}}
    {{#if_even @index}}
    <li>
        <div class="tl-circ"></div>
        <div class="timeline-panel">
            <div class="tl-heading">
                <h4 class="encounterName">{{encounter}}</h4>
                <p><small class="text-muted encounterDate"><i class="glyphicon glyphicon-time"></i>{{date}}
                </small></p>
            </div>
            <div class="tl-body">
                <p class="encounterDescription">{{description}}</p>
                <%--<button class="btn" value="${encounter_id}"><a href="/ke/patient_analysis/history_detail" >查看详情</a></button>--%>
                <button class="btn btn-primary btn-sm eventDetail_btn"  data-toggle="modal" value="{{user_id}}" onclick="handlerSelect('{{encounter}}')" data-target="#myEncounterModal">
                    <%--<a href="/ke/patient_analysis/history/getEventdetailById?encounterId=${enc.encounter}" >查看详情</a>--%>
                    查看详情
                </button>
            </div>
        </div>
    </li>
    {{else}}
    <li class="timeline-inverted">
        <div class="tl-circ"></div>
        <div class="timeline-panel">
            <div class="tl-heading">
                <h4 class="encounterName">{{encounter}}</h4>
                <p><small class="text-muted encounterDate"><i class="glyphicon glyphicon-time"></i>{{date}}
                </small></p>
            </div>
            <div class="tl-body">
                <p class="encounterDescription">{{description}}</p>
                <%--<button class="btn" value="${encounter_id}"><a href="/ke/patient_analysis/history_detail" >查看详情</a></button>--%>
                <button class="btn btn-primary btn-sm eventDetail_btn"  data-toggle="modal" value="{{user_id}}" onclick="handlerSelect('{{encounter}}')" data-target="#myEncounterModal">
                    <%--<a href="/ke/patient_analysis/history/getEventdetailById?encounterId=${enc.encounter}" >查看详情</a>--%>
                    查看详情
                </button>
            </div>
        </div>
    </li>
    {{/if_even}}
    {{/each}}
</script>
<jsp:include page="../public/script.jsp">
    <jsp:param value="main/patient/compre_history_info.js" name="loader" />
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>

</html>

