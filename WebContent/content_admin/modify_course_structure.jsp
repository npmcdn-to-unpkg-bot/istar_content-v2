<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%><%@ page import="java.util.*"%>
<%@page import="com.istarindia.apps.cmsutils.TableUtils"%>
<%@page import="com.istarindia.apps.dao.IstarUser"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.istarindia.apps.cmsutils.reports.*"%>
<% String url = request.getRequestURL().toString();
String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<title> Edit Course | iStar CMS</title>

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Favicon -->
<link rel="shortcut icon" href="favicon.ico">

<!-- Web Fonts -->
<link rel='stylesheet' type='text/css' href='//fonts.googleapis.com/css?family=Open+Sans:400,300,600&amp;subset=cyrillic,latin'>

<!-- CSS Global Compulsory -->
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=baseURL %>assets/css/style.css">

<!-- CSS Header and Footer -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/headers/header-default.css">
<link rel="stylesheet" href="<%=baseURL %>assets/css/footers/footer-v1.css">

<!-- CSS Implementing Plugins -->
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/animate.css">
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/line-icons/line-icons.css">
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=baseURL %>assets/css/business.style.css">
<link rel="stylesheet" href="<%=baseURL %>assets/css/global.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/pages/profile.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/app.css">

<!-- CSS Theme -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL %>assets/css/theme-colors/orange.css" id="style_color">

<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL %>assets/css/custom.css">
<link href="http://code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css" rel="stylesheet">
</head>

<body>

	<div class="wrapper">
		<jsp:include page="includes/header.jsp"></jsp:include>
		<div class="breadcrumbs"><div class="container-fluid">
				<h1 class="pull-left">
					<a href="<%=baseURL %>content_admin/dashboard.jsp">Modify Course Details</a>
				</h1>

			</div>
			</div>
		<div class="container-fluid height-1000" style="padding: 0px !important">
			
			
			<div class="col-md-12">
				<% HashMap<String, String> conditions = new HashMap();
				//conditions.put("actor_id",((IstarUser)request.getSession().getAttribute("user")).getId().toString());
				int reportID = 30;
				String entityID = new String();
				if(request.getParameterMap().containsKey("course_id")){
					reportID = 31;
					entityID = (request.getParameter("course_id")).toString();
					conditions.put("course_id", entityID);
				%>
					<form id="update_order" action="/content/update_order">
            			<input id='course_id' name='course_id' type='hidden' value='<%=entityID%>'>
            			<input id='entity_type' name='entity_type' type='hidden' value='modules'>
						<input id='order_holder' name='order_holder' type='hidden'>
            			<button type="submit" class="btn-u" style="float:right;">Update Order</button>
            		</form>
				<%
				} else if (request.getParameterMap().containsKey("module_id")){
					reportID = 32;
					entityID = (request.getParameter("module_id")).toString();
					conditions.put("module_id", entityID);
				%>
					<form id="update_order" action="/content/update_order">
            			<input id='module_id' name='module_id' type='hidden' value='<%=entityID%>'>
            			<input id='entity_type' name='entity_type' type='hidden' value='sessions'>
						<input id='order_holder' name='order_holder' type='hidden'>
            			<button type="submit" class="btn-u" style="float:right;">Update Order</button>
            		</form>
				<%
				} else if (request.getParameterMap().containsKey("session_id")){
					reportID = 33;
					entityID = (request.getParameter("session_id")).toString();
					conditions.put("session_id", entityID);
				%>
					<form id="update_order" action="/content/update_order">
            			<input id='session_id' name='session_id' type='hidden' value='<%=entityID%>'>
            			<input id='entity_type' name='entity_type' type='hidden' value='lessons'>
						<input id='order_holder' name='order_holder' type='hidden'>
            			<button type="submit" class="btn-u" style="float:right;">Update Order</button>
            		</form>
				<%
				}
				%>
            	<%=(new ReportUtils()).getReport(reportID, conditions, ((IstarUser)request.getSession().getAttribute("user")), "NONE").toString() %>
					
			</div>
		</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
	</div>

	<!-- JS Global Compulsory -->
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/jquery/jquery.min.js"></script>
	<script src="http://code.jquery.com/jquery-2.2.3.js"></script>
	<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/jquery/jquery-migrate.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!-- JS Implementing Plugins -->
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/back-to-top.js"></script>
	<script type="text/javascript" src="<%=baseURL %>assets/plugins/smoothScroll.js"></script>
	<!-- JS Customization -->
	<script type="text/javascript" src="<%=baseURL %>assets/js/custom.js"></script>
	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL %>assets/js/app.js"></script>
	<script src="<%=baseURL%>assets/plugins/datatables/jquery.dataTables.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/datatables/dataTables.colVis.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/datatables/dataTables.tableTools.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/datatable-responsive/datatables.responsive.min.js"></script>
		<script type="text/javascript">
	var responsiveHelper_dt_basic = undefined;
	var responsiveHelper_datatable_fixed_column = undefined;
	var responsiveHelper_datatable_col_reorder = undefined;
	var responsiveHelper_datatable_tabletools = undefined;
	
	var breakpointDefinition = {
		tablet : 1024,
		phone : 480
	};
	jQuery(document).ready(function() {
			App.init();

			var report_id = <%=reportID%>;
			
			if (report_id<=33 && report_id>=31) {
				var tableID = "datatable_report_"+report_id;
        		var tableBodyID = "datatable_report_"+report_id+"_body";
	            //table contents are sortable
    	        $( "#"+tableBodyID).sortable();
        	    $( "#update_order" ).submit(function( event ) {
            		var idsInOrder = $("#"+tableBodyID).sortable("toArray");
            		$('#order_holder').val(idsInOrder);
            		});
        	    
        	    // Show all results in single page. No need for search
        	    // Retrieve the existing DataTable instance and destroy and then reconfigure
        	    if ( $.fn.dataTable.isDataTable( "#"+tableID ) ) {
        	        table = $("#"+tableID).DataTable({
        	        	destroy: true,
        	        	sorting: false,
        	        	searching: false,
        	        	paging: false
        	        } );
        	    }
        	    else {
        	        table = $("#"+tableID).DataTable( {
        	            paging: false
        	        } );
        	    }
			}
		});
	</script>
	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->

</body>
</html>
