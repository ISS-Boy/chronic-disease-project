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
    <link rel="stylesheet" type="text/css" href="/ke/media/css/chronicD/location.css">
</head>
<body>
<jsp:include page="../public/navbar.jsp"></jsp:include>

<div id="wrapper">
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <div>
                    <select  id="user_id"  name="val1" >
                        <option >the-user-1</option>
                        <option >the-user-2</option>
                        <option >the-user-3</option>
                        <option >the-user-4</option>
                        <option >the-user-5</option>
                        <option >the-user-6</option>
                        <option >the-user-7</option>
                        <option >the-user-8</option>
                        <option >the-user-9</option>
                        <option >the-user-10</option>
                        <option >the-user-11</option>
                        <option >the-user-12</option>
                        <option >the-user-13</option>
                        <option >the-user-14</option>
                        <option >the-user-15</option>
                        <option >the-user-16</option>
                        <option >the-user-17</option>
                        <option >the-user-18</option>
                        <option >the-user-19</option>
                        <option >the-user-20</option>
                    </select>
                    <button id="longitude_sub" type="button">刷新</button>
                </div>
            </div>
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <i class="fa fa-cogs fa-fw"></i> 个人轨迹信息
                    </div>
                    <!-- /.panel-heading -->
                    <div id = panel_loading>
                        <div class="panel-body">
                            <div id="main" style="height:800px;"></div>
                            <%--<img style="margin:20px" width="280" height="140"
                                 src="http://api.map.baidu.com/staticimage/v2?ak=etLBbgQqfQQE3Cb25G29FjDKZtQnzVCp&width=680&height=640&zoom=11"/>--%>
                            <script src="/ke/media/js/track/echarts/echarts.js"></script>
                            <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ogQzr2weGLe00PmSAZWf1eZ912ldWp1s"></script>
                            <script src="/ke/media/js/track/t_js/jquery.min.js"></script>
                            <script src="/ke/media/js/track/t_js/require.js"></script>
                            <script src="/ke/media/js/track/t_js/example3.js"></script>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript">
    $(function (){
        $('#longitude_sub').click(function(){
            var value1 = $('#user_id').val();
            $.ajax({
                url:"/ke/monitor/longtitude/selected?value1="+value1,
                type:"get",
                dataType:'json',
                success:function(){
                    getdata()
                },
                error : function() {
                }
            })

        })
    })
</script>





<jsp:include page="../public/script.jsp">
    <jsp:param value="main/patient/currentTime.js" name="loader" />
</jsp:include>
<jsp:include page="../public/tscript.jsp"></jsp:include>
</html>
