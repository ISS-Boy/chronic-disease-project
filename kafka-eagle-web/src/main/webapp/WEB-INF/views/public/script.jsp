<%@ page pageEncoding="UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="/ke/media/js/public/jquery.js" type="text/javascript"></script>
<script src="/ke/media/js/public/bootstrap.min.js"
	type="text/javascript"></script>
<script src="/ke/media/js/public/bootstrap-select.js"></script>
<script src="/ke/media/js/public/jquery.tmpl.js"></script>
<script src="/ke/media/js/public/raphael.min.js" type="text/javascript"></script>
<script src="/ke/media/js/public/morris.min.js" type="text/javascript"></script>
<script src="/ke/media/js/public/navbar.js" type="text/javascript"></script>
<script src="/ke/media/js/public/bootstrap-treeview.min.js" type="text/javascript"></script>
<script src="/ke/media/js/plugins/handlebars/handlebars-v4.0.10.js" type="text/javascript"></script>
<!--提示框-->
<script src="/ke/media/js/ace/js/jquery.tips.js" type="text/javascript"></script>

<%
	String[] loader = request.getParameterValues("loader");
	if (loader == null) {
		return;
	}
	for (String s : loader) {
%>
<script src="/ke/media/js/<%=s%>"></script>
<%
	}
%>
