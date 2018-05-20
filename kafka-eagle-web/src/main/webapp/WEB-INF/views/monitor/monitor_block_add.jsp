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

    <jsp:include page="../public/css.jsp"></jsp:include>
    <jsp:include page="../public/tcss.jsp"></jsp:include>
    <%--<link rel="stylesheet" type="text/css" media="all" href="/ke/media/css/chronicD/monitor_block.css">--%>
    <title>多条件搜索</title>

    <!-- jquery 引入-->
    <script type="text/javascript" src="/ke/media/js/monitor/jquery.js"></script>
    <%--<script type="text/javascript" src="/ke/media/js/monitor/jquery.tmpl.js"></script>--%>

    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <%--<link rel="stylesheet" type="text/css" href="/ke/media/css/public/bootstrap.最小值.css">--%>
    <%--<link rel="stylesheet" type="text/css" href="/ke/media/css/public/bootstrap-select.css">--%>

    <!-- 自定义css文件 -->
    <!-- 具有浏览器兼容问题-->

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <%--<script src="/ke/media/js/public/bootstrap.最小值.js"></script>--%>
    <%--<script type="text/javascript" src="/ke/media/js/monitor/bootstrap-select.js"></script>--%>

    <!-- 自定义js脚本 -->
    <%--<script type="text/javascript" src="/ke/media/js/monitor/blockSearch.js"></script>--%>
    <script type="text/javascript" src="/ke/media/js/monitor/control.js"></script>

    <style type="text/css">
        .block_tmpl {
            border: 2px solid lightblue;
            border-radius: 5px;
            margin-bottom: 10px;
        }

        .aggregation_item_templ {
            margin-bottom: 5px;
        }

        .delAggregation {
            margin-top: 58px;
            margin-left: 20px;
        }

        .filter_row {
            float: left;
            padding-left: 15px;
        }

        .col-md-3 {
            margin-top: 10px;
        }

        .filter_row {
            margin-top: 10px;
        }

        .window_ {
            border: 1px solid #eee;
            padding: 10px;
            margin-left: 10px;
            float: left;
        }

        .window_row {
            float: left;
            padding-left: 15px;
        }

        .aggregation_item {
            /*float: left;*/
            border: 1px solid #eee;
            margin-bottom: 10px;
            margin-right: 20px;
            margin-top: 10px;
        }

        .aggregation_row {
            float: left;
            margin: 10px;

        }

        .groupfla {
            margin-top: 10px;
        }

        .aggregation_group {
            /*float: left;*/
            border: 1px solid #eee;
            /*width:90%;*/
            margin-bottom: 10px;

        }

        .aggregation_group_filter {
            float: left;

            margin: 5px;
        }

        .select_row {
            float: left;
            padding-left: 15px;
            margin-top: 10px;

        }

        .loading {
            width: 160px;
            height: 56px;
            position: fixed;
            top: 50%;
            left: 50%;
            margin-left: auto;
            margin-top: auto;
            line-height: 56px;
            color: #fff;
            padding-left: 60px;
            font-size: 15px;
            background: #000 url(/ke/media/img/loader.gif) no-repeat 10px 50%;
            opacity: 0.7;
            z-index: 9999;
            -moz-border-radius: 20px;
            -webkit-border-radius: 20px;
            border-radius: 20px;
            filter: progid:DXImageTransform.Microsoft.Alpha(opacity=70);
        }

        .cover {
            width: 2000px;
            height: 2000px;
            position: fixed;
            top: 0%;
            left: 0%;
            margin-left: auto;
            margin-top: auto;
            background-color: #000000;
            opacity: 0.6;
            z-index: 9998;
        }

    </style>

    <script id="blockTmpl" type="text/x-jquery-tmpl">
<div class="block_tmpl col-md-12">

	 <div class="row" style="padding: 5px 0 5px 10px;background-color: #eee">
		 <label>请为查询仪表命名:</label>
		 <input type="text"  name="monitorName" class="monitorName" required/>
	 </div>

	 <div class="row">
		 <div class="panel panel-default">
			 <div class="panel-body">
				 <div class="input-group">
					 <div class="row">
                           <div class="col-md-12">
								<span>数据源</span>
						   </div>
						</div>
						<div class="row source">
							<div class="col-md-3 source_cow">
								<select class="selectpicker" name="source" multiple data-live-search="true">
									<option>数据源</option>
								</select>
							</div>
						</div>
					</div>
				</div>
			</div>
	</div>
	<div class="row">
			<div class="panel panel-default">
				<div class="panel-body">
					<div class="input-group">
						<div class="row">
							<div class="col-md-12">
								<div class="col-md-2">
								    <h6 style="width:100px">聚集</h6>
								</div>
								<div class="window_row_tmpl" >
                                    <div class="panel window_">
                                        <div class="panel-heading">
                                            <h3 class="panel-title">时间窗口</h3>
                                        </div>

                                        <div class="window_row">
                                           <span>窗口类型</span> <select class="selectpicker" data-live-search="true" name="w_type">
                                                <option>滑动窗口</option>
                                                <option>翻滚窗口</option>
                                                <option>跳跃窗口</option>
                                            </select>
                                        </div>

                                        <div class="window_row">
                                            <input type="number" min="0" class="form-control" name="w_interval" placeholder="interval" aria-describedby="basic-addon1" required>
                                        </div>

                                        <div class="window_row">
                                            <input type="number" min="0" class="form-control" name="w_length" placeholder="length" aria-describedby="basic-addon1" required>
                                        </div>

                                    </div>
                                </div>
							</div>
						</div>
						<div class="row aggregation">
							<div class="col-md-1">
								<h6 style="width: 100px">聚集项<span onclick="add_aggregation(this)"><img src="/ke/media/img/01.gif"/></span></h6>

							</div>
							<%--aggregation_item_outpanel--%>
							<div class="aggregation_item_outpanel col-md-11">
								<%--aggregation_item_templ--%>
								<div class="aggregation_item_templ col-md-11">
									<div class="row calculation col-md-11" style="border: 1px solid #eee">
											<%--<div style="float: left;margin-left: 20px;"><h6>聚集项</h6></div>--%>
											<div class="aggregation_item col-md-11 col-md-offset-1">

													<div class="aggregation_row col-xs-2">
														<input type="text" name="a_name" class="form-control cal_interval" placeholder="name" required>
													</div>
													<div class="aggregation_row" >
														<select class="selectpicker"  data-live-search="true" name="a_source" data-width="fit">
															<option>数据源</option>
														</select>
													</div>
													<div class="aggregation_row">
														<select class="selectpicker"  data-live-search="true" name="a_measure" data-width="auto">
															<option>数据项</option>
														</select>
													</div>

													<div class="aggregation_row">
														<select class="selectpicker"  data-live-search="true" name="a_type" data-width="100%">
															<option>最小值</option>
															<option>最大值</option>
															<option>平均值</option>
															<option>计数</option>
															<option>求和</option>
															<option>增幅</option>
															<option>增比</option>
														</select>
													</div>
													<%--<div class="aggregation_row" style="padding-top: 4px;">--%>
														<%--<span onclick="add_aggregation(this)"><img src="/ke/media/img/01.gif"/></span>--%>
														<%--<span onclick="remove_aggregation(this)"><img src="/ke/media/img/02.gif"/></span>--%>
													<%--</div>--%>
											</div>
											<!--aggregation_group_panel-->
											<div class="aggregation_group_panel col-md-12" >
												<div  class="col-md-2 groupfla">
													组内过滤
													<span onclick="add_aggregation_predicate(this)"><img src="/ke/media/img/01.gif"/></span>
												</div>
												<div class="aggregation_group col-md-10 ">
													<div class="aggregation_group_templ col-md-12">
														<div class="aggregation_group_filter" >
															<select class="selectpicker"  data-live-search="true" name="g_source" data-width="fit">
																<option>数据源</option>
															</select>
														</div>
														<div class="aggregation_group_filter">
															<select class="selectpicker"  data-live-search="true" name="g_measure" data-width="fit">
																<option>数据项</option>
															</select>
														</div>

														<div class="aggregation_group_filter">
															<select class="selectpicker"  data-live-search="true" name="g_op" data-width="100%">
																<option>></option>
																<option><</option>
																<option>>=</option>
																<option><=</option>
																<option>=</option>
															</select>
														</div>

														<div class="aggregation_group_filter col-xs-3">
															<input type="number" min="0" class="form-control" name="g_threshold" placeholder="threshold" aria-describedby="basic-addon1"  required />
														</div>

														<div class="aggregation_group_filter">
															<select class="selectpicker"  data-live-search="true" name="g_boolExp" data-width="100%">
																<option>且</option>
																<option>或</option>
															</select>
														</div>
														<div class="aggregation_group_filter" style="padding-top: 4px;">
															<%--<span onclick="add_aggregation_predicate(this)"><img src="/ke/media/img/01.gif"/></span>--%>
															<span onclick="remove_aggregation_predicate(this)"><img src="/ke/media/img/02.gif"/></span>
														</div>
													</div>
												</div>

											</div>
											<!--aggregation_group_panel-->
									</div>
									<div class="col-md-1 delAggregation">
										<span onclick="remove_aggregation(this)"><img src="/ke/media/img/02.gif"/></span>
									</div>
								</div>
									<%--aggregation_item_templ--%>
							</div>
							<%--aggregation_item_outpanel--%>
						</div>
					</div>
				</div>
			</div>
	</div>
	<div class="row">
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="input-group">
					<div class="row">
						<div class="col-md-12">
							<span>过滤</span>
							<span onclick="add_filter(this)"><img src="/ke/media/img/01.gif"/></span>
							<%--<span onclick="remove_filter(this)"><img src="/ke/media/img/02.gif"/></span>--%>
						</div>
					</div>
					<div class="row filter">
						<div class="filter_templ">
							<div class="filter_row">
								<select class="selectpicker"  data-live-search="true" name="f_source" data-width="auto">
									<option>数据源</option>
								</select>
							</div>
							<div class="filter_row">
								<select class="selectpicker"  data-live-search="true" name="f_measure" data-width="auto">
									<option>数据项</option>
								</select>
							</div>

							<div class="filter_row">
								<select class="selectpicker"  data-live-search="true" name="f_op" data-width="auto">
									<option>></option>
									<option><</option>
									<option>>=</option>
									<option><=</option>
									<option>=</option>
								</select>
							</div>

							<div class="filter_row col-xs-2" >
								  <input type="number" min="0" class="form-control" name="f_threshold" placeholder="threshold" aria-describedby="basic-addon1" required />
							</div>

							<div class="filter_row">
								<select class="selectpicker"  data-live-search="true" name="f_boolExp" data-width="auto">
									<option>且</option>
									<option>或</option>
								</select>
							</div>
							<div class="filter_row" style="padding-top: 4px;">
								<%--<span onclick="add_filter(this)"><img src="/ke/media/img/01.gif"/></span>--%>
								<span onclick="remove_filter(this)"><img src="/ke/media/img/02.gif"/></span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="panel panel-default">
			<div class="panel-body">
                <div class="input-group select_parent">
					<div class="row">
					    <div class="col-md-12">
                            <span>选择</span>
                            <span onclick="add_select(this)"><img
                                    src="/ke/media/img/01.gif"/></span>
                            <span style="margin-left: 10%">
                                <input type="checkbox" name="s_alert" value="只看警报">只看警报
                            </span>
                        </div>
					</div>
					<div class="row select">
						<div class="select_templ col-md-12">
							<div class="select_row">
								<select class="selectpicker"  data-live-search="true" name="s_source">
									<option>数据源</option>
								</select>
							</div>

							<div class="select_row">
								<select class="selectpicker"  data-live-search="true" name="s_meaOrCal">
									<option>数据项或聚集值</option>
								</select>
							</div>
							<div class="select_row" style="padding-top: 4px">
								<span onclick="remove_select(this)"><img src="/ke/media/img/02.gif"/></span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</script>

    <script id="aggregationWindowTmpl" type="text/x-jquery-tmpl">
    <div class="panel window_">

        <div class="panel-heading">
            <h3 class="panel-title">时间窗口</h3>
        </div>

        <div class="window_row">
            <span>窗口类型</span> <select class="selectpicker"
                                      data-live-search="true" name="w_type">
            <option>滑动窗口</option>
            <option>翻滚窗口</option>
            <option>跳跃窗口</option>
        </select>
        </div>

        <div class="window_row">
            <input type="number" min="0" class="form-control"
                   name="w_interval" placeholder="interval"
                   aria-describedby="basic-addon1" required>
        </div>

        <div class="window_row">
            <input type="number" min="0" class="form-control"
                   name="w_length" placeholder="length"
                   aria-describedby="basic-addon1" required>
        </div>
    </div>


    </script>
    <script id="aggregationTmpl" type="text/x-jquery-tmpl">
<div class="aggregation_item_templ col-md-11">
	<div class="row calculation col-md-11" style="border: 1px solid #eee">


			<%--<div style="float: left;margin-left: 20px;"><h6>聚集项</h6></div>--%>
			<div class="aggregation_item col-md-11 col-md-offset-1">

					<div class="aggregation_row col-xs-2">
						<input type="text" name="a_name" class="form-control cal_interval" placeholder="name" required>
					</div>
					<div class="aggregation_row" >
						<select class="selectpicker"  data-live-search="true" name="a_source" data-width="fit">
							<option>数据源</option>
						</select>
					</div>
					<div class="aggregation_row">
						<select class="selectpicker"  data-live-search="true" name="a_measure" data-width="auto">
							<option>数据项</option>
						</select>
					</div>

					<div class="aggregation_row">
						<select class="selectpicker"  data-live-search="true" name="a_type" data-width="100%">
							<option>最小值</option>
							<option>最大值</option>
							<option>平均值</option>
							<option>计数</option>
							<option>求和</option>
							<option>增幅</option>
							<option>增比</option>
						</select>
					</div>
					<%--<div class="aggregation_row" style="padding-top: 4px;">--%>
						<%--<span onclick="add_aggregation(this)"><img src="/ke/media/img/01.gif"/></span>--%>
						<%--<span onclick="remove_aggregation(this)"><img src="/ke/media/img/02.gif"/></span>--%>
					<%--</div>--%>
			</div>
			<!--aggregation_group_panel-->
			<div class="aggregation_group_panel col-md-12" >
				<div class="col-md-2 groupfla">
					组内过滤
					<span onclick="add_aggregation_predicate(this)"><img src="/ke/media/img/01.gif"/></span>
				</div>
				<div class="aggregation_group col-md-10 ">
					<div class="aggregation_group_templ col-md-12">
						<div class="aggregation_group_filter" >
							<select class="selectpicker"  data-live-search="true" name="g_source" data-width="fit">
								<option>数据源</option>
							</select>
						</div>
						<div class="aggregation_group_filter">
							<select class="selectpicker"  data-live-search="true" name="g_measure" data-width="fit">
								<option>数据项</option>
							</select>
						</div>

						<div class="aggregation_group_filter">
							<select class="selectpicker"  data-live-search="true" name="g_op" data-width="100%">
								<option>></option>
								<option><</option>
								<option>>=</option>
								<option><=</option>
								<option>=</option>
							</select>
						</div>

						<div class="aggregation_group_filter col-xs-3">
							<input type="number" min="0" class="form-control" name="g_threshold" placeholder="threshold" aria-describedby="basic-addon1"  required />
						</div>

						<div class="aggregation_group_filter">
							<select class="selectpicker"  data-live-search="true" name="g_boolExp" data-width="100%">
								<option>且</option>
								<option>或</option>
							</select>
						</div>
						<div class="aggregation_group_filter" style="padding-top: 4px;">
							<%--<span onclick="add_aggregation_predicate(this)"><img src="/ke/media/img/01.gif"/></span>--%>
							<span onclick="remove_aggregation_predicate(this)"><img src="/ke/media/img/02.gif"/></span>
						</div>
					</div>
				</div>

			</div>
			<!--aggregation_group_panel-->
	</div>
	<div class="col-md-1 delAggregation">
		<span onclick="remove_aggregation(this)"><img src="/ke/media/img/02.gif"/></span>
	</div>

</div>



    </script>

    <script id="calculationTmpl" type="text/x-jquery-tmpl">
    <div class="aggregation_group_templ col-md-12">
		<div class="aggregation_group_filter" >
			<select class="selectpicker"  data-live-search="true" name="g_source" data-width="fit">
				<option>数据源</option>
			</select>
		</div>
		<div class="aggregation_group_filter">
			<select class="selectpicker"  data-live-search="true" name="g_measure" data-width="fit">
				<option>数据项</option>
			</select>
		</div>

		<div class="aggregation_group_filter">
			<select class="selectpicker" data-live-search="true" name="g_op" data-width="100%">
				<option>></option>
				<option><</option>
				<option>>=</option>
				<option><=</option>
				<option>=</option>
			</select>
		</div>

		<div class="aggregation_group_filter col-xs-3">
			<input type="number" min="0" class="form-control" name="g_threshold" placeholder="threshold" aria-describedby="basic-addon1"  required />
		</div>

		<div class="aggregation_group_filter">
			<select class="selectpicker"  data-live-search="true" name="g_boolExp" data-width="100%">
				<option>且</option>
				<option>或</option>
			</select>
		</div>
		<div class="aggregation_group_filter" style="padding-top: 4px;">
			<%--<span onclick="add_aggregation_predicate(this)"><img src="/ke/media/img/01.gif"/></span>--%>
			<span onclick="remove_aggregation_predicate(this)"><img src="/ke/media/img/02.gif"/></span>
		</div>
	</div>



    </script>
    <script id="filterTempl" type="text/x-jquery-tmpl">
    <div class="filter_templ" >
        <div class="filter_row"> 
            <select class="selectpicker"  data-live-search="true" name="f_source" data-width="auto">
                <option>数据源</option>
            </select>
        </div>
        <div class="filter_row"> 
            <select class="selectpicker"  data-live-search="true" name="f_measure" data-width="auto">
                <option>数据项</option>
            </select>
        </div>
       
        <div class="filter_row"> 
            <select class="selectpicker" data-live-search="true" name="f_op" data-width="auto">
                <option>></option>
                <option><</option>
                <option>>=</option>
                <option><=</option>
                <option>=</option>
            </select>
        </div>

        <div class="filter_row col-xs-2">
              <input type="number" min="0" class="form-control" name="f_threshold" placeholder="threshold" aria-describedby="basic-addon1" required>
        </div>

        <div class="filter_row"> 
            <select class="selectpicker"  data-live-search="true" name="f_boolExp" data-width="auto">
                <option>且</option>
                <option>或</option>
            </select>
        </div>
        <div class="filter_row" style="padding-top: 4px;">
			<%--<span onclick="add_filter(this)"><img src="/ke/media/img/01.gif"/></span>--%>
			<span onclick="remove_filter(this)"><img src="/ke/media/img/02.gif"/></span>
		</div>
    </div>



    </script>
    <script id="selectTempl" type="text/x-jquery-tmpl">
    <div class="select_templ col-md-12" >
        <div class="select_row"> 
            <select class="selectpicker" data-live-search="true" name="s_source">
                <option>数据源</option>
            </select>
        </div>

        <div class="select_row"> 
            <select class="selectpicker" data-live-search="true" name="s_meaOrCal">
                <option>数据项或聚集值</option>
            </select>
        </div>
        <div class="select_row" style="padding-top: 4px">
			<span onclick="remove_select(this)"><img src="/ke/media/img/02.gif"/></span>
		</div>
    </div>
    </script>
</head>
<body>
<jsp:include page="../public/navbar.jsp"></jsp:include>
<div id="wrapper">
    <div id="page-wrapper">
        <div class="container">
            <form>
                <div class="row">
                    <div class="col-md-3">

                    </div>
                    <label style="font-size: 22px">请为当前查询命名:</label>
                    <input type="text" class="blockGroupName" required/>
                    <span onclick="add_block()"><img src="/ke/media/img/01.gif" width="20px" height="20px"/></span>
                    <span onclick="remove_block()"><img src="/ke/media/img/02.gif" width="20px" height="20px"/></span>
                    <div class="col-md-6">
                        <%--<span style="font-size: 30px">block</span>--%>
                        <%--<span onclick="add_block()"><img src="/ke/media/img/01.gif" width="20px" height="20px" /></span>--%>
                        <%--<span onclick="remove_block()"><img src="/ke/media/img/02.gif" width="20px" height="20px"/></span>--%>

                    </div>

                </div>

                <div class="row block">
                    <!--block_tmpl -->
                    <div class="block_tmpl col-md-12">

                        <div class="row" style="padding: 5px 0 5px 10px;background-color: #eee">
                            <label>请为查询仪表命名:</label>
                            <input type="text" name="monitorName" class="monitorName" required/>
                        </div>

                        <div class="row">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <div class="input-group">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <span>数据源</span>
                                            </div>
                                        </div>
                                        <div class="row source">
                                            <div class="col-md-3 source_cow">
                                                <select class="selectpicker" name="source" multiple
                                                        data-live-search="true">
                                                    <option>数据源</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <div class="input-group">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="col-md-2">
                                                    <h6 style="width: 100px">聚集</h6>
                                                </div>
                                                <div class="window_row_tmpl">
                                                    <div class="panel window_">

                                                        <div class="panel-heading">
                                                            <h3 class="panel-title">时间窗口</h3>
                                                        </div>

                                                        <div class="window_row">
                                                            <span>窗口类型</span> <select class="selectpicker"
                                                                                      data-live-search="true"
                                                                                      name="w_type">
                                                            <option>滑动窗口</option>
                                                            <option>翻滚窗口</option>
                                                            <option>跳跃窗口</option>
                                                        </select>
                                                        </div>

                                                        <div class="window_row">
                                                            <input type="number" min="0" class="form-control"
                                                                   name="w_interval" placeholder="interval"
                                                                   aria-describedby="basic-addon1" required>
                                                        </div>

                                                        <div class="window_row">
                                                            <input type="number" min="0" class="form-control"
                                                                   name="w_length" placeholder="length"
                                                                   aria-describedby="basic-addon1" required>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row aggregation">
                                            <div class="col-md-1">
                                                <h6 style="width: 100px">聚集项 <span onclick="add_aggregation(this)"><img
                                                        src="/ke/media/img/01.gif"/></span></h6>

                                            </div>
                                            <%--aggregation_item_outpanel--%>
                                            <div class="aggregation_item_outpanel col-md-11">
                                                <%--aggregation_item_templ--%>
                                                <div class="aggregation_item_templ col-md-11">
                                                    <div class="row calculation col-md-11"
                                                         style="border: 1px solid #eee">
                                                        <%--<div style="float: left;margin-left: 20px;"><h6>聚集项</h6></div>--%>
                                                        <div class="aggregation_item col-md-11 col-md-offset-1">
                                                            <div class="aggregation_row col-xs-2">
                                                                <input type="text" name="a_name"
                                                                       class="form-control cal_interval"
                                                                       placeholder="name" required>
                                                            </div>
                                                            <div class="aggregation_row">
                                                                <select class="selectpicker" data-live-search="true"
                                                                        name="a_source" data-width="fit">
                                                                    <option>数据源</option>
                                                                </select>
                                                            </div>
                                                            <div class="aggregation_row">
                                                                <select class="selectpicker" data-live-search="true"
                                                                        name="a_measure" data-width="auto">
                                                                    <option>数据项</option>
                                                                </select>
                                                            </div>

                                                            <div class="aggregation_row">
                                                                <select class="selectpicker" data-live-search="true"
                                                                        name="a_type" data-width="100%">
                                                                    <option>最小值</option>
                                                                    <option>最大值</option>
                                                                    <option>平均值</option>
                                                                    <option>计数</option>
                                                                    <option>求和</option>
                                                                    <option>增幅</option>
                                                                    <option>增比</option>
                                                                </select>
                                                            </div>
                                                            <%--<div class="aggregation_row" style="padding-top: 4px;">--%>
                                                            <%--<span onclick="add_aggregation(this)"><img src="/ke/media/img/01.gif"/></span>--%>
                                                            <%--<span onclick="remove_aggregation(this)"><img src="/ke/media/img/02.gif"/></span>--%>
                                                            <%--</div>--%>
                                                        </div>
                                                        <!--aggregation_group_panel-->
                                                        <div class="aggregation_group_panel col-md-12">
                                                            <div class="col-md-2 groupfla" style="margin-top: 10px;">
                                                                组内过滤
                                                                <span onclick="add_aggregation_predicate(this)"><img
                                                                        src="/ke/media/img/01.gif"/></span>
                                                            </div>
                                                            <div class="aggregation_group col-md-10">
                                                                <div class="aggregation_group_templ col-md-12">
                                                                    <div class="aggregation_group_filter">
                                                                        <select class="selectpicker"
                                                                                data-live-search="true" name="g_source"
                                                                                data-width="fit">
                                                                            <option>数据源</option>
                                                                        </select>
                                                                    </div>
                                                                    <div class="aggregation_group_filter">
                                                                        <select class="selectpicker"
                                                                                data-live-search="true" name="g_measure"
                                                                                data-width="fit">
                                                                            <option>数据项</option>
                                                                        </select>
                                                                    </div>

                                                                    <div class="aggregation_group_filter">
                                                                        <select class="selectpicker"
                                                                                data-live-search="true" name="g_op"
                                                                                data-width="100%">
                                                                            <option>></option>
                                                                            <option><</option>
                                                                            <option>>=</option>
                                                                            <option><=</option>
                                                                            <option>=</option>
                                                                        </select>
                                                                    </div>

                                                                    <div class="aggregation_group_filter col-xs-3">
                                                                        <input type="number" min="0"
                                                                               class="form-control" name="g_threshold"
                                                                               placeholder="threshold"
                                                                               aria-describedby="basic-addon1"
                                                                               required/>
                                                                    </div>

                                                                    <div class="aggregation_group_filter">
                                                                        <select class="selectpicker"
                                                                                data-live-search="true" name="g_boolExp"
                                                                                data-width="100%">
                                                                            <option>且</option>
                                                                            <option>或</option>
                                                                        </select>
                                                                    </div>
                                                                    <div class="aggregation_group_filter"
                                                                         style="padding-top: 4px;">
                                                                        <%--<span onclick="add_aggregation_predicate(this)"><img src="/ke/media/img/01.gif"/></span>--%>
                                                                        <span onclick="remove_aggregation_predicate(this)"><img
                                                                                src="/ke/media/img/02.gif"/></span>
                                                                    </div>


                                                                </div>
                                                            </div>

                                                        </div>
                                                        <!--aggregation_group_panel-->
                                                    </div>
                                                    <div class="col-md-1 delAggregation">
                                                        <span onclick="remove_aggregation(this)"><img
                                                                src="/ke/media/img/02.gif"/></span>
                                                    </div>
                                                </div>
                                                <%--aggregation_item_templ--%>
                                            </div>
                                            <%--aggregation_item_outpanel--%>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <div class="input-group">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <span>过滤</span>
                                                <span onclick="add_filter(this)"><img
                                                        src="/ke/media/img/01.gif"/></span>
                                                <%--<span onclick="remove_filter(this)"><img src="/ke/media/img/02.gif"/></span>--%>
                                            </div>
                                        </div>
                                        <div class="row filter">
                                            <div class="filter_templ">
                                                <div class="filter_row">
                                                    <select class="selectpicker" data-live-search="true" name="f_source"
                                                            data-width="auto">
                                                        <option>数据源</option>
                                                    </select>
                                                </div>
                                                <div class="filter_row">
                                                    <select class="selectpicker" data-live-search="true"
                                                            name="f_measure" data-width="auto">
                                                        <option>数据项</option>
                                                    </select>
                                                </div>

                                                <div class="filter_row">
                                                    <select class="selectpicker" data-live-search="true" name="f_op"
                                                            data-width="auto">
                                                        <option>></option>
                                                        <option><</option>
                                                        <option>>=</option>
                                                        <option><=</option>
                                                        <option>=</option>
                                                    </select>
                                                </div>

                                                <div class="filter_row col-xs-2">
                                                    <input type="number" min="0" class="form-control" name="f_threshold"
                                                           placeholder="threshold" aria-describedby="basic-addon1"
                                                           required/>
                                                </div>

                                                <div class="filter_row">
                                                    <select class="selectpicker" data-live-search="true"
                                                            name="f_boolExp" data-width="auto">
                                                        <option>且</option>
                                                        <option>或</option>
                                                    </select>
                                                </div>
                                                <div class="filter_row" style="padding-top: 4px;">
                                                    <%--<span onclick="add_filter(this)"><img src="/ke/media/img/01.gif"/></span>--%>
                                                    <span onclick="remove_filter(this)"><img
                                                            src="/ke/media/img/02.gif"/></span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <div class="input-group select_parent">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <span>选择</span>
                                                <span onclick="add_select(this)" name = "add_select_block"><img
                                                        src="/ke/media/img/01.gif"/></span>
                                                <span style="margin-left: 10%">
                                                    <input type="checkbox" name="s_alert" value="只看警报">只看警报
                                                </span>
                                            </div>
                                        </div>
                                        <div class="row select" style="width: 800px">
                                            <div class="select_templ">
                                                <div class="select_row" style="margin-left: 15px">
                                                    <select class="selectpicker" data-live-search="true"
                                                            name="s_source">
                                                        <option>数据源</option>
                                                    </select>
                                                </div>

                                                <div class="select_row">
                                                    <select class="selectpicker" data-live-search="true"
                                                            name="s_meaOrCal">
                                                        <option>数据项或聚集值</option>
                                                    </select>
                                                </div>

                                                <div class="select_row" style="padding-top: 4px">
                                                    <span onclick="remove_select(this)"><img
                                                            src="/ke/media/img/02.gif"/></span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--block_tmpl -->
                </div>
                <button type="button" class="btn btn-primary btn-lg block_submit">提交</button>
                <button type="button" class="btn btn-primary btn-lg block_reset">重置</button>
            </form>
        </div>
    </div>
    <div id="loading" class="loading" style="display: none">Loading...</div>
    <div id="cover" class="cover" style="display: none"></div>
    <div id="hideCopy"></div>
</div>
</body>
<jsp:include page="../public/script.jsp">
    <jsp:param value="monitor/blockSearch.js" name="loader"/>
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>
</html>