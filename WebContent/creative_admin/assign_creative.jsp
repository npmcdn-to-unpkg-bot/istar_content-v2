<%@page import="com.istarindia.apps.StatusTypes"%>
<%@page import="com.istarindia.apps.services.MediaService"%>
<%@page import="com.istarindia.apps.services.CMSRegistry"%>
<%@page import="com.istarindia.apps.dao.*"%><%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	String url = request.getRequestURL().toString();
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length())
			+ request.getContextPath() + "/";
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<title> Assigned Creative | iStar CMS</title>

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
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/style.css">

<!-- CSS Header and Footer -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/headers/header-default.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/footers/footer-v1.css">

<!-- CSS Implementing Plugins -->
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/animate.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/line-icons/line-icons.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/business.style.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/global.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/jstree/themes/default/style.min.css">

<!-- CSS Theme -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/orange.css" id="style_color">

<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/custom.css">
</head>

<body>
	<div class="wrapper">
		<jsp:include page="includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<!--/container-->
		</div>
		<div class="container-fluid height-1000" style="padding: 0px !important">
			<div class="panel panel-orange" style="margin-left: 100px; margin-right: 100px">
				<div class="panel-heading">
					<h3 class="panel-title">
						<i class="fa fa-tasks"></i> Assign Creative Creator
					</h3>
				</div>
				<div class="panel-body">
					<label id="err" style="display: block;color:#ee9393"></label>
					<button id="demo" onclick="myFunction()" class="btn-u"  data-target="#myModal">Select Creative Creator</button>
							
					
					<div id="html1">
						<ul>
							<li id="none" data-jstree='{"opened":true}'>Tasks Not Assigned
								<ul>
									<%
										TaskDAO dao = new TaskDAO();
										for (Task task : (List<Task>) dao.findByItemType("IMAGE")) {
									try {
											Image img = new ImageDAO().findById(task.getItemId());
												if(task.getStatus().equalsIgnoreCase(StatusTypes.CREATED))
												{
											%>
									<li id="task_<%=task.getId()%>" data-jstree='{"opened":true}'><span class="label label-purple rounded-2x"><%=task.getTaskName()%> > <%=img.getTitle()%></span>
									<% 		
												}
												}	catch(Exception e) {
													e.printStackTrace();
												}
										}
										for (Task task : (List<Task>) dao.findByItemType("VIDEO")) {
											Video vid = new VideoDAO().findById(task.getItemId());
											if(task.getStatus().equalsIgnoreCase(StatusTypes.CREATED))
											{
												%>
												<li id="task_<%=task.getId()%>" data-jstree='{"opened":true}'><span class="label label-green rounded-2x"><%=task.getTaskName()%> > <%=vid.getTitle()%></span>
												<% 
											}
										}
								%>
								</ul>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
							<h4 id="myModalLabel1" class="modal-title">Session Assignment</h4>
						</div>
						<div class="modal-body">
							<form class="form-horizontal" role="form"" action="/content/assign_creator">
								<input type="hidden" id="selected_items" name="selected_items" />
								<div class="form-group">
									<label for="inputEmail1" class="col-lg-4 control-label">Choose Creative Creator</label>
									<div class="col-lg-6">
										<select name="assign_user" class="form-control input-lg">
											<%
												for (IstarUser user : CMSRegistry.getCreativeCreators()) {
											%>
											<option value="<%=user.getId()%>"><%=user.getEmail()%></option>
											<%
												}
											%>
										</select>
									</div>
								</div>
								<div class="form-group">
									<div class="col-lg-offset-2 col-lg-10">
										<button type="submit" class="btn-u btn-u-green">Assign & Create</button>
									</div>
								</div>
							</form>
						</div>
						<div class="modal-footer">
							<button data-dismiss="modal" class="btn-u btn-u-default" type="button">Close</button>
						</div>
					</div>
				</div>
			</div>
			</form>
			<!-- JS Global Compulsory -->
			<script type="text/javascript" src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
			<script type="text/javascript" src="<%=baseURL%>assets/plugins/jquery/jquery-migrate.min.js"></script>
			<script type="text/javascript" src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>

			<script type="text/javascript" src="<%=baseURL%>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
			<!-- JS Implementing Plugins -->
			<script type="text/javascript" src="<%=baseURL%>assets/plugins/back-to-top.js"></script>
			<script type="text/javascript" src="<%=baseURL%>assets/plugins/smoothScroll.js"></script>
			<script type="text/javascript" src="<%=baseURL%>assets/plugins/jstree/jstree.js"></script>

			<!-- JS Customization -->
			<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
			<!-- JS Page Level -->
			<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
			<script type="text/javascript">
			function myFunction() {
					var selectedElmsIds = $('#html1').jstree("get_selected");
					if (selectedElmsIds == ""){
						//event.preventDefault();
						document.getElementById("err").innerHTML = "Please select the media task";
						return false; 
						}
					else {
						document.getElementById("err").innerHTML = "";
						$('#myModal').modal('show');
					}
					$('#selected_items').val(selectedElmsIds);
				}
				jQuery(document).ready(function() {
					App.init();
					$('#html1').jstree({
						"core" : {
							"themes" : {
								"variant" : "large"
							}
						},
						"checkbox" : {
							"keep_selected_style" : false,
							"three_state" : false,
						},
						"plugins" : [ "checkbox" ]
					});
					$('#selected_items').val("aaaa");

				});
			</script>
			<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->
</body>
</html>
