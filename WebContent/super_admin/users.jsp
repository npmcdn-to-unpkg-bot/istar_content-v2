<%@page import="org.apache.catalina.User"%>
<%@page import="com.istarindia.apps.services.UserService"%>
<%@page import="com.istarindia.apps.dao.IstarUser"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<%
	String url = request.getRequestURL().toString();
	String basePath = url.substring(0, url.length() - request.getRequestURI().length())
			+ request.getContextPath() + "/";
%>
<title>Super Admin Dahsboard | Istar Skill Development</title>
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
<link rel="stylesheet" href="<%=basePath%>assets/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=basePath%>assets/css/style.css">

<!-- CSS Header and Footer -->
<link rel="stylesheet" href="<%=basePath%>assets/css/headers/header-default.css">
<link rel="stylesheet" href="<%=basePath%>assets/css/footers/footer-v1.css">

<!-- CSS Implementing Plugins -->
<link rel="stylesheet" href="<%=basePath%>assets/plugins/animate.css">
<link rel="stylesheet" href="<%=basePath%>assets/plugins/line-icons/line-icons.css">
<link rel="stylesheet" href="<%=basePath%>assets/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=basePath%>assets/plugins/scrollbar/css/jquery.mCustomScrollbar.css">
<link rel="stylesheet" href="<%=basePath%>assets/plugins/sky-forms-pro/skyforms/css/sky-forms.css">
<link rel="stylesheet" href="<%=basePath%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">

<!-- CSS Page Style -->
<link rel="stylesheet" href="<%=basePath%>assets/css/pages/profile.css">

<!-- CSS Theme -->
<link rel="stylesheet" href="<%=basePath%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=basePath%>assets/css/theme-colors/red.css">
<link rel="stylesheet" href="<%=basePath%>assets/css/app.css">
<!-- CSS Customization -->
<link rel="stylesheet" href="<%=basePath%>assets/css/custom.css">
</head>

<body>
	<div class="wrapper">
		<jsp:include page="includes/header.jsp"></jsp:include>
		<div class="container-fluid  content profile">
			<div class="row">
				<jsp:include page="includes/sidebar.jsp"></jsp:include>	
							<div class="col-md-10">
					<div class="profile-body">
					<div class="panel panel-red margin-bottom-40">
								<div class="panel-heading">
									<h3 class="panel-title"><i class="fa fa-edit"></i>List of Users</h3>
								</div>
								<table class="table table-striped table-bordered" width="100%" id="datatable_fixed_column">
									<thead>
										<tr>
											<th><input type="text" class="form-control" placeholder="Filter Name" /></th>
											<th><input type="text" class="form-control" placeholder="Filter Name" /></th>
											<th><input type="text" class="form-control" placeholder="Filter Name" /></th>
											<th><input type="text" class="form-control" placeholder="Filter Name" /></th>
											
											<th></th>
										</tr>
										<tr>
											<th>#</th>
											<th>Email</th>
											<th>Name</th>
											<th>Type of User</th>
											<th>Actions</th>
										</tr>
									</thead>
									<tbody>
										
										<% for(IstarUser user : (new UserService()).getUserList()) { %>
										<tr>
											<td><%=user.getId() %></td>
											<td><%=user.getEmail() %></td>
											<td><%=user.getName() %></td>
											<td><%=user.getUserType()%></td>
											<td><button class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> EDIT</button>
											<button class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> View Profile</button></td>
										</tr>
										<% } %>
									</tbody>
								</table>
							</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- JS Global Compulsory -->
	<script type="text/javascript" src="<%=basePath%>assets/plugins/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/plugins/jquery/jquery-migrate.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!-- JS Implementing Plugins -->
	<script type="text/javascript" src="<%=basePath%>assets/plugins/back-to-top.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/plugins/smoothScroll.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/plugins/counter/waypoints.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/plugins/counter/jquery.counterup.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/plugins/sky-forms-pro/skyforms/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/plugins/scrollbar/js/jquery.mCustomScrollbar.concat.min.js"></script>
	<!-- JS Customization -->
	<script type="text/javascript" src="<%=basePath%>assets/js/custom.js"></script>
	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=basePath%>assets/js/app.js"></script>
	<script type="text/javascript" src="<%=basePath%>assets/js/plugins/datepicker.js"></script>
<script src="<%=basePath%>assets/plugins/datatables/jquery.dataTables.min.js"></script>
		<script src="<%=basePath%>assets/plugins/datatables/dataTables.colVis.min.js"></script>
		<script src="<%=basePath%>assets/plugins/datatables/dataTables.tableTools.min.js"></script>
		<script src="<%=basePath%>assets/plugins/datatables/dataTables.bootstrap.min.js"></script>
		<script src="<%=basePath%>assets/plugins/datatable-responsive/datatables.responsive.min.js"></script>

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
			App.initCounter();
			App.initScrollBar();
			 var otable =  $('#datatable_fixed_column').DataTable({
				 
				"sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6 hidden-xs'f><'col-sm-6 col-xs-12 hidden-xs'<'toolbar'>>r>"+
						"t"+
						"<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>",
				"autoWidth" : true,
				"preDrawCallback" : function() {
					// Initialize the responsive datatables helper once.
					if (!responsiveHelper_datatable_fixed_column) {
						responsiveHelper_datatable_fixed_column = new ResponsiveDatatablesHelper($('#datatable_fixed_column'), breakpointDefinition);
					}
				},
				"rowCallback" : function(nRow) {
					responsiveHelper_datatable_fixed_column.createExpandIcon(nRow);
				},
				"drawCallback" : function(oSettings) {
					responsiveHelper_datatable_fixed_column.respond();
				}		
				// custom toolbar
			    //$("div.toolbar").html('<div class="text-right"><img src="img/logo.png" alt="SmartAdmin" style="width: 111px; margin-top: 3px; margin-right: 10px;"></div>');
			    	   
			    // Apply the filter
			    

		    });
			   
			 $("#datatable_fixed_column thead th input[type=text]").on( 'keyup change', function () {
			        otable
			            .column( $(this).parent().index()+':visible' )
			            .search( this.value )
			            .draw();
			            
			    });
			
		});
	</script>
	<!--[if lt IE 9]>
	<script src="<%=basePath%>assets/plugins/respond.js"></script>
	<script src="<%=basePath%>assets/plugins/html5shiv.js"></script>
	<script src="<%=basePath%>assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->

</body>
</html>
