<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

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
		<link rel="stylesheet" type="text/css" media="all" href="/ke/media/css/chronicD/monitor_block.css">
	<title>多条件搜索</title>

	<!-- jquery 引入-->
	<script type="text/javascript" src="/ke/media/js/monitor/jquery.js"></script>
	<%--<script type="text/javascript" src="/ke/media/js/monitor/jquery.tmpl.js"></script>--%>

	<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
	<%--<link rel="stylesheet" type="text/css" href="/ke/media/css/public/bootstrap.min.css">--%>
	<%--<link rel="stylesheet" type="text/css" href="/ke/media/css/public/bootstrap-select.css">--%>

	<!-- 自定义css文件 -->
	<!-- 具有浏览器兼容问题-->

	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<%--<script src="/ke/media/js/public/bootstrap.min.js"></script>--%>
	<%--<script type="text/javascript" src="/ke/media/js/monitor/bootstrap-select.js"></script>--%>

	<!-- 自定义js脚本 -->
	<%--<script type="text/javascript" src="/ke/media/js/monitor/blockSearch.js"></script>--%>
	<script type="text/javascript" src="/ke/media/js/monitor/control.js"></script>

	<style type="text/css">
		.block_tmpl{
			border:2px solid lightblue;
			border-radius: 5px;
			margin-bottom: 10px;
		}
		.filter_row{
			float: left;
			padding-left:15px;
		}
		.col-md-3{
			margin-top: 10px;
		}
		.filter_row{
			margin-top: 10px;
		}
		.window_{
			border: 1px solid #eee;
			padding: 10px;
			float: right;
			margin-right:200px;
		}
		.window_row{
			float: left;
			padding-left:15px;
		}
		.select_row{
			float: left;
			padding-left:15px;
			margin-top: 10px;
			width: 50%;
		}
		.loading{
			width:160px;
			height:56px;
			position: absolute;
			top:50%;
			left:50%;
			line-height:56px;
			color:#fff;
			padding-left:60px;
			font-size:15px;
			background: #000 url(/ke/media/img/loader.gif) no-repeat 10px 50%;
			opacity: 0.7;
			z-index:9999;
			-moz-border-radius:20px;
			-webkit-border-radius:20px;
			border-radius:20px;
			filter:progid:DXImageTransform.Microsoft.Alpha(opacity=70);
		}
	</style>

<script id="blockTmpl" type="text/x-jquery-tmpl">
<div class="block_tmpl col-md-12">
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="input-group">
                    <div class="row">
                        <div class="col-md-12">
                            <span>source</span>
                            <span onclick="add_source()"><img src="/ke/media/img/01.gif"/></span>
                            <span onclick="remove_source()"><img src="/ke/media/img/02.gif"/></span>
                        </div>
                    </div>
                    <div class="row source">
                        <div class="col-md-3 source_cow"> 
                            <select class="selectpicker" name="source" multiple data-live-search="true">
                                <option >source</option>
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
                            <span>calculation</span>
                            <span onclick="add_calculation(this)"><img src="/ke/media/img/01.gif"/></span>
                            <span onclick="remove_calculation(this)"><img src="/ke/media/img/02.gif"/></span>

                            <div class="panel window_">
                                <div class="panel-heading">
                                    <h3 class="panel-title">window</h3>
                                </div>
                                <div class="window_row"> 
                                    <select class="selectpicker" data-live-search="true" name="w_type">
                                        <option>type</option>
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
                        <div class="row calculation">
                            <div class="calculation_templ">    
                                <div class="col-md-3"> 
                                    <input type="text" name="c_name" class="form-control cal_interval" placeholder="name" value="" required>
                                </div>
                                <div class="col-md-3"> 
                                    <select class="selectpicker" data-live-search="true" name="c_source">
                                        <option>source</option>
                                    </select>
                                </div>
                                <div class="col-md-3"> 
                                    <select class="selectpicker" data-live-search="true" name="c_measure">
                                        <option>measure</option>
                                    </select>
                                </div>
                               
                                <div class="col-md-3 calcu_interval">
                                    <select class="selectpicker" data-live-search="true" name="c_type">
                                        <option>min</option>
										<option>max</option>
										<option>average</option>
										<option>count</option>
										<option>sum</option>
										<option>amplitude</option>
										<option>incrementary ratio</option>
                                    </select>
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
                <div class="input-group">
                    <div class="row">
                        <div class="col-md-12">
                            <span>filter</span>
                            <span onclick="add_filter(this)"><img src="/ke/media/img/01.gif"/></span>
                            <span onclick="remove_filter(this)"><img src="/ke/media/img/02.gif"/></span>
                        </div>
                    </div>
                    <div class="row filter">
                        <div class="filter_templ">
                            <div class="filter_row"> 
                                <select class="selectpicker"  data-live-search="true" name="f_source">
                                    <option>source</option>
                                </select>
                            </div>
                            <div class="filter_row"> 
                                <select class="selectpicker"  data-live-search="true" name="f_measure">
                                    <option>measure</option>
                                </select>
                            </div>
                           
                            <div class="filter_row"> 
                                <select class="selectpicker"  data-live-search="true" name="f_op">
                                    <option>></option>
									<option><</option>
									<option>>=</option>
									<option><=</option>
									<option>=</option>
                                </select>
                            </div>

                            <div class="filter_row"> 
                                  <input type="number" min="0" class="form-control" name="f_threshold" placeholder="threshold" aria-describedby="basic-addon1" required>
                            </div>

                            <div class="filter_row"> 
                                <select class="selectpicker"  data-live-search="true" name="f_boolExp">
									<option>And</option>
									<option>Or</option>
                                </select>
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
                <div class="input-group">
                    <div class="row">
                        <div class="col-md-12">
                            <span>select</span>
                            <span onclick="add_select(this)"><img src="/ke/media/img/01.gif"/></span>
                            <span onclick="remove_select(this)"><img src="/ke/media/img/02.gif"/></span>
                        </div>
                    </div>
                    <div class="row select">
                        <div class="select_templ">
                            <div class="select_row"> 
                                <select class="selectpicker"  data-live-search="true" name="s_source">
                                    <option>source</option>
                                </select>
                            </div>

                            <div class="select_row"> 
                                <select class="selectpicker"  data-live-search="true" name="s_meaOrCal">
                                    <option>measureOrCalculation</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div> 
</script>

<script id="calculationTmpl" type="text/x-jquery-tmpl"> 
    <div class="calculation_templ">
        <div class="col-md-3 calculation_row"> 
            <input type="text" name="c_name" class="form-control cal_interval" placeholder="name" value="" required>
        </div>
        <div class="col-md-3 calculation_row"> 
            <select class="selectpicker" data-live-search="true" name="c_source">
                <option>source</option>
            </select>
        </div>
        <div class="col-md-3 calculation_row"> 
            <select class="selectpicker" data-live-search="true" name="c_measure">
                <option>measure</option>
            </select>
        </div> 
        
        <div class="col-md-3 calculation_row">
            <select class="selectpicker" data-live-search="true" name="c_type">
                <option>min</option>
				<option>max</option>
				<option>average</option>
				<option>count</option>
				<option>sum</option>
				<option>amplitude</option>
				<option>incrementary ratio</option>
            </select>
        </div>
    </div>
</script>
<script id="filterTempl" type="text/x-jquery-tmpl">
    <div class="filter_templ" >
        <div class="filter_row"> 
            <select class="selectpicker"  data-live-search="true" name="f_source">
                <option>source</option>
            </select>
        </div>
        <div class="filter_row"> 
            <select class="selectpicker"  data-live-search="true" name="f_measure">
                <option>measure</option>
            </select>
        </div>
       
        <div class="filter_row"> 
            <select class="selectpicker" data-live-search="true" name="f_op">
                <option>></option>
                <option><</option>
                <option>>=</option>
                <option><=</option>
                <option>=</option>
            </select>
        </div>

        <div class="filter_row"> 
              <input type="number" min="0" class="form-control" name="f_threshold" placeholder="threshold" aria-describedby="basic-addon1" required>
        </div>

        <div class="filter_row"> 
            <select class="selectpicker"  data-live-search="true" name="f_boolExp">
                <option>And</option>
                <option>Or</option>
            </select>
        </div>
    </div>
</script>
<script  id="selectTempl" type="text/x-jquery-tmpl">
    <div class="select_templ" >
        <div class="select_row"> 
            <select class="selectpicker" data-live-search="true" name="s_source">
                <option>source</option>
            </select>
        </div>

        <div class="select_row"> 
            <select class="selectpicker" data-live-search="true" name="s_meaOrCal">
                <option>measureOrCalculation</option>
            </select>
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
            <div class="col-md-3"></div>
            <div class="col-md-6 col-md-offset-2">
            	<span style="font-size: 30px">block</span>
            	<span onclick="add_block()"><img src="/ke/media/img/01.gif" width="20px" height="20px" /></span>
        		<span onclick="remove_block()"><img src="/ke/media/img/02.gif" width="20px" height="20px"/></span>
            </div>
            <div class="col-md-3"></div>
        </div>

        <div class="row block">
         <div class="block_tmpl col-md-12">
	        <div class="row">
	                <div class="panel panel-default">
	                    <div class="panel-body">
	                        <div class="input-group">
	                            <div class="row">
	                                <div class="col-md-12">
	                                    <span>source</span>
	                                    <span onclick="add_source()"><img src="/ke/media/img/01.gif"/></span>
	                                    <span onclick="remove_source()"><img src="/ke/media/img/02.gif"/></span>
	                                </div>
	                            </div>
	                            <div class="row source">
	                                <div class="col-md-3 source_cow"> 
	                                    <select class="selectpicker" name="source" multiple data-live-search="true">
	                                        <option >source</option>
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
	                                    <span>calculation</span>
	                                    <span onclick="add_calculation(this)"><img src="/ke/media/img/01.gif"/></span>
	                                    <span onclick="remove_calculation(this)"><img src="/ke/media/img/02.gif"/></span>

	                                    <div class="panel window_">
	                                        <div class="panel-heading">
	                                            <h3 class="panel-title">window</h3>
	                                        </div>
	                                        <div class="window_row">
	                                            <select class="selectpicker" data-live-search="true" name="w_type">
													<option>sliding window</option>
													<option>tumbling window</option>
													<option>hopping window</option>
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
	                            <div class="row calculation">
	                                <div class="calculation_templ">    
	                                    <div class="col-md-3"> 
	                                        <input type="text" name="c_name" class="form-control cal_interval" placeholder="name" required>
	                                    </div>
	                                    <div class="col-md-3"> 
	                                        <select class="selectpicker" data-live-search="true" name="c_source">
	                                            <option>source</option>
	                                        </select>
	                                    </div>
	                                    <div class="col-md-3"> 
	                                        <select class="selectpicker" data-live-search="true" name="c_measure">
	                                            <option>measure</option>
	                                        </select>
	                                    </div>
	                                   
	                                    <div class="col-md-3 calcu_interval">
	                                        <select class="selectpicker" data-live-search="true" name="c_type">
												<option>min</option>
												<option>max</option>
												<option>average</option>
												<option>count</option>
												<option>sum</option>
												<option>amplitude</option>
												<option>incrementary ratio</option>
	                                        </select>
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
	                    <div class="input-group">
	                        <div class="row">
	                            <div class="col-md-12">
	                                <span>filter</span>
	                                <span onclick="add_filter(this)"><img src="/ke/media/img/01.gif"/></span>
	                                <span onclick="remove_filter(this)"><img src="/ke/media/img/02.gif"/></span>
	                            </div>
	                        </div>
	                        <div class="row filter">
	                            <div class="filter_templ">
	                                <div class="filter_row"> 
	                                    <select class="selectpicker"  data-live-search="true" name="f_source">
	                                        <option>source</option>
	                                    </select>
	                                </div>
	                                <div class="filter_row"> 
	                                    <select class="selectpicker"  data-live-search="true" name="f_measure">
	                                        <option>measure</option>
	                                    </select>
	                                </div>
	                               
	                                <div class="filter_row"> 
	                                    <select class="selectpicker"  data-live-search="true" name="f_op">
											<option>></option>
											<option><</option>
											<option>>=</option>
											<option><=</option>
											<option>=</option>
	                                    </select>
	                                </div>

	                                <div class="filter_row"> 
	                                      <input type="number" min="0" class="form-control" name="f_threshold" placeholder="threshold" aria-describedby="basic-addon1" required />
	                                </div>

	                                <div class="filter_row"> 
	                                    <select class="selectpicker"  data-live-search="true" name="f_boolExp">
											<option>And</option>
											<option>Or</option>
	                                    </select>
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
	                    <div class="input-group">
	                        <div class="row">
	                            <div class="col-md-12">
	                                <span>select</span>
	                                <span onclick="add_select(this)"><img src="/ke/media/img/01.gif"/></span>
	                                <span onclick="remove_select(this)"><img src="/ke/media/img/02.gif"/></span>
	                            </div>
	                        </div>
	                        <div class="row select">
	                            <div class="select_templ">
	                                <div class="select_row"> 
	                                    <select class="selectpicker"  data-live-search="true" name="s_source">
	                                        <option>source</option>
	                                    </select>
	                                </div>

	                                <div class="select_row"> 
	                                    <select class="selectpicker"  data-live-search="true" name="s_meaOrCal">
	                                        <option>measureOrCalculation</option>
	                                    </select>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
		  </div>
        </div>
        <button type="button" class="btn btn-primary btn-lg block_submit">提交</button>
    </form>
    </div>
	<div id="loading" class="loading" style="display:none">Loading...</div>
	</div>
</div>
</body>
	<jsp:include page="../public/script.jsp">
		<jsp:param value="monitor/blockSearch.js" name="loader" />
	</jsp:include>
	<jsp:include page="../public/tscript.jsp"></jsp:include>
</html>