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

<title>Role - KafkaEagle</title>
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
						新建离线任务
					</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<div class="row" style="height: 38px;">
				<div class="col-lg-12">
					<div>
						<form id="searchForm" action="/ke/offlineLearning/toAdd" method="post">
							<input type="hidden" name="type" id="type" value="1"/>
							<div style="width: 150px;float: left;overflow: hidden">
								<div style="float: left">
									年龄：<input id="ageStart" style="width: 42px" name="ageStart" onblur="checkAge();" type="number" placeholder="48" value="${ageStart}"/>
								</div>
								<div style="float: left">
									 -<input id="ageEnd" name="ageEnd" style="width: 42px" onblur="checkAge();" type="number" placeholder="68" value="${ageEnd}"/>
								</div>
							</div>
							<div style="width: 100px;height:26px;float: left;overflow: hidden">性别
								<select style="height: 26px" name="gender" id="gender" title="性别">
									<option value="all">全部</option>
									<option value="F">女</option>
									<option value="M">男</option>
								</select>
							</div>
							<div style="width: 360px;height:26px;float: left;overflow: hidden">
								病史
								<select style="width: 300px;height: 26px" name="disease" id="disease" title="病史">
									<option value="all">============全选============</option>
									<c:forEach items="${allDisease}" var="disease">
										<option value="${disease.diseaseName }">${disease.diseaseName }</option>
									</c:forEach>
								</select>
							</div>
							<div style="width: 50px;height:36px;float: left;overflow: hidden">
								<a class='btn btn-primary' title="筛选" onclick="searchUsers();"><i class="fa fa-search"></i></a>
							</div>
							<div style="width: 50px;height:36px;float: left;overflow: hidden">
								<a class='btn btn-primary' id="nextStep" title="下一步" onclick="toNext();"><i class="fa fa-hand-o-right"></i></a>
							</div>
						</form>
					</div>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<i class="fa fa-cogs fa-fw"></i> 用户列表
							<div class="pull-right"></div>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<table id="table_report" class="table table-bordered table-condensed">
								<thead>
								<tr>
									<th class="center">
										<label><input type="checkbox" onclick="selectAll();" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center">序号</th>
									<th class='center'>姓名</th>
									<th class='center'>年龄</th>
									<th class='center'>性别</th>
									<th class='center'>民族</th>
									<th class='center'>病史</th>
								</tr>
								</thead>
								<c:choose>
									<c:when test="${not empty patients}">
										<c:forEach items="${patients}" var="patient" varStatus="vs">
											<tr>
												<td class='center' style="width: 30px;">
													<label><input type='checkbox' name='ids' value="${patient.userId }" id="${patient.userId }"/><span class="lbl"></span></label>
												</td>
												<td class="center">${vs.index+1}</td>
												<td class='center'>${patient.name }</td>
												<td>${patient.age}</td>
												<c:if test="${patient.gender == 'F'}">
													<td class='center'>女</td>
												</c:if>
												<c:if test="${patient.gender == 'M'}">
													<td class='center'>男</td>
												</c:if>
												<td class='center'>${patient.race }</td>
												<td class='center'>${patient.disease }</td>
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
					<!-- /.col-lg-4 -->
				</div>
				<!-- /.row -->
			</div>
			<!-- modal -->
			<div class="modal fade" aria-labelledby="keModalLabel"
				aria-hidden="true" id="ke_setting_dialog" tabindex="-1"
				role="dialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button class="close" type="button" data-dismiss="modal">×</button>
							<h4 class="modal-title" id="keModalLabel">
								Role Setting
							</h4>
							<div id="ke_role_alert_mssage"></div>
						</div>
						<!-- /.row -->
						<form role="form" action="#" method="post"
							onsubmit="return contextFormValid();return false;">
							<div class="modal-body">
								<div id="treeview-checkable" class=""></div>
							</div>
							<div id="remove_div" class="modal-footer">
								<button type="button" class="btn btn-primary"
									data-dismiss="modal">Close</button>
							</div>
						</form>
					</div>
				</div>
			</div>
			<!-- /#page-wrapper -->
		</div>
	</div>
</body>
<jsp:include page="../public/script.jsp">
	<jsp:param value="main/offlineLearning/learning.js" name="loader" />
	<jsp:param value="ace/js/jquery.tips.js" name="loader" />
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>
<script>

	$(document).ready(function () {

	    if('${ageStart}' != '' && '${ageStart}' != '0')
            $('input[name="ageStart"]').val(${ageStart})

        if('${ageEnd}' != '' && '${ageEnd}' != '0')
        	$('input[name="ageEnd"]').val(${ageEnd})

	    if('${gender}' != '')
			$('select[name="gender"]').find($('option')).each(function () {
				if($(this).val() == '${gender}')
				    $(this).attr('selected', true)
            })

		if('${disease}' != '')
			$('select[name="disease"]').find($('option')).each(function () {
				if($(this).val() == '${disease}')
					$(this).attr('selected', true)
			})
    })

    function checkAge() {
        if (0 > $('#ageStart').val()) {
            $("#ageStart").tips({
                side:3,
                msg:'年龄不能小于0',
                bg:'#AE81FF',
                time:3
            });
            $("#ageStart").focus();
            setTimeout("$('#ageStart').val('')",2000);
            return;
        }

        if (0 > $('#ageEnd').val()) {
            $("#ageEnd").tips({
                side:3,
                msg:'年龄不能小于0',
                bg:'#AE81FF',
                time:3
            });
            $("#ageEnd").focus();
            setTimeout("$('#ageEnd').val('')",2000);
            return;
        }

        if ($('#ageStart').val() > $('#ageEnd').val()) {
            $("#ageEnd").tips({
                side:3,
                msg:'年龄起止时间冲突',
                bg:'#AE81FF',
                time:3
            });
            $("#ageEnd").focus();
            setTimeout("$('#ageEnd').val('')",2000);
            return;
        }
    }

    function searchUsers() {
        $('#searchForm').submit();
    }


    var userIds = new Array();
    var ages="", gender="", diseases="", metric="", dateTo="";
    var kResult=0, rThreshold=0, frequencyThreshold=0, analysisWindowStartSize=3, alphabetSize=0, paaSize=0, slidingWindowSize=0;

    function toNext() {
        var j = 0;
        for(var i=0;i < document.getElementsByName('ids').length;i++)
        {
            if(document.getElementsByName('ids')[i].checked){
                userIds[j] = document.getElementsByName('ids')[i].value;
                j++;
            }
        }
        // userIds = "123456";
        ages = $('#ageStart').val() + ",age," + $('#ageEnd').val();
        gender = $('#gender').val();
        diseases = $('#disease').val();
        if (userIds == "") {
            $("#nextStep").tips({
                side:3,
                msg:'还未选择用户',
                bg:'#AE81FF',
                time:3
            });
            $("#nextStep").focus();
            return;
        }
        window.location.href="/ke/offlineLearning/nextStep?userIds=" + userIds + "&&ages=" + ages+ "&&gender=" + gender + "&&diseases=" + diseases;
    }

    var isCheckAll = false;
    function selectAll() {
        if (isCheckAll) {
            $("input[type='checkbox']").each(function() {
                this.checked = false;
            });
            isCheckAll = false;
        } else {
            $("input[type='checkbox']").each(function() {
                this.checked = true;
            });
            isCheckAll = true;
        }
    }


    function runDemo() {
        $.ajax({
            type: "POST",
            url: '/ke/offlineLearning/runDemo',
            dataType:'json',
            cache: false,
            success: function(rusult){
                if (rusult.msg == "success") {
                    window.location.href = "/ke/offlineLearning/getAllConfigure";
                }
            }
        });
    }
</script>
</html>
