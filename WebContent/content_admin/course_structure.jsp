<%@page import="com.istarindia.apps.services.CourseService"%>
<%@page import="com.istarindia.apps.services.CMSRegistry"%>
<%@page import="com.istarindia.apps.*"%>
<%@page import="com.istarindia.apps.dao.*"%><%@page import="java.util.*"%>
<%@page import="org.hibernate.Query"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	String url = request.getRequestURL().toString();
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length())
			+ request.getContextPath() + "/";
	
	long now = System.currentTimeMillis();
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<title>Course Structure | iStar CMS</title>

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Favicon -->
<link rel="shortcut icon" href="favicon.ico">

<!-- Web Fonts -->
<link rel='stylesheet' type='text/css'
	href='//fonts.googleapis.com/css?family=Open+Sans:400,300,600&amp;subset=cyrillic,latin'>

<!-- CSS Global Compulsory -->
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/style.css">

<!-- CSS Header and Footer -->
<link rel="stylesheet"
	href="<%=baseURL%>assets/css/headers/header-default.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/css/footers/footer-v1.css">

<!-- CSS Implementing Plugins -->
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/animate.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/line-icons/line-icons.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/business.style.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/global.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/jstree/themes/default/style.min.css">

<link
	href="<%=baseURL%>assets/plugins/jquery-contextmenu/src/jquery.contextMenu.css"
	rel="stylesheet" type="text/css" />

<!-- CSS Theme -->
<link rel="stylesheet"
	href="<%=baseURL%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet"
	href="<%=baseURL%>assets/css/theme-colors/orange.css" id="style_color">

<link rel="stylesheet" href="<%=baseURL%>assets/plugins/select2-4.0.3/dist/css/select2.css">




<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/custom.css">
</head>

<body>
	<div class="wrapper">
		<jsp:include page="includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container-fluid">
				<h1 class="pull-left">
					<a href="<%=baseURL%>content_admin/dashboard.jsp">Content Admin
						Dashboard</a>
				</h1>

			</div>
			<!--/container-->
		</div>
		<div class="container-fluid height-1000"
			style="padding: 0px !important">
			<div class="panel panel-orange"
				style="margin-left: 100px; margin-right: 100px">
				<div class="panel-heading">
					<h3 class="panel-title">
						<i class="fa fa-tasks"></i> Create Course Syllabus
					</h3>
				</div>
				<div class="panel-body">
					<form action="<%=baseURL%>course_upload" class="sky-form"
						method="post" enctype="multipart/form-data">

						<fieldset>
							<section>
								<p>
									File input &nbsp;&nbsp;&nbsp;&nbsp; (Download sample course
									syllabus from <a
										href="../assets/excel/cms course structure.xls"
										style="color: RED">here</a> )
								</p>
								<label for="file" class="input input-file">
									<div class="button">
										<input type="file" id="file" name="file"
											onchange="DisplayFilePath()">Browse
									</div> <input type="text" id="formfield" readonly>
								</label>
							</section>
						</fieldset>
						<footer>
							<button type="submit" class="btn-u">Submit</button>
						</footer>
					</form>
				</div>
			</div>

			<div class="panel panel-orange"
				style="margin-left: 100px; margin-right: 100px">
				<div class="panel-heading">
					<h3 class="panel-title">
						<i class="fa fa-tasks"></i> Course Tree & Assignment
					</h3>
				</div>
				<div class="panel-body">
					<label id="err"
						style="font-size: 150%; display: block; color: #ee9393"></label>

					<%
						if (((IstarUser) request.getSession().getAttribute("user")).getUserType()
								.equalsIgnoreCase("CONTENT_ADMIN")) {
					%>
					<button class="btn-u" onclick="myFunction()" data-target="#myModal"
						style="position: fixed; right: 108px;">Select Creator and
						Reviewer</button>
					<%
						}
					%>

					<div id="html1">
						<ul>
							<li id="none" data-jstree='{"opened":true}'>All Courses
								<ul>
									<%
										int course_sno = 0;
										CourseDAO dao = new CourseDAO();
										for (Course course : (List<Course>) dao.findAll()) {
											course_sno++;
									%>
									<li id="course_<%=course.getId()%>"
										data-jstree='{"opened":false}'><%=course_sno%>. <%=course.getCourseName()%>
										<ul>
											<%
												int cmsession_sno = 0;
												
												String hql = "from Module as model where model.course=:sid order by order_id";
												Query q = (new IstarUserDAO()).getSession().createQuery(hql);
												q.setInteger("sid", course.getId());
												List<Module> moduleList = q.list();

												for (Module module : moduleList) {
											%>
											<li id="module_<%=module.getId()%>"
												data-jstree='{"opened":true}'><%=module.getModuleName()%>
												<ul>
													<%
														hql = "from Cmsession as model where model.module=:sid  order by order_id";
														q = (new IstarUserDAO()).getSession().createQuery(hql);
														q.setInteger("sid", module.getId());
														List<Cmsession> sessionList = q.list();	
														
														for (Cmsession session1 : sessionList) {
															cmsession_sno++;
													%>
													<li id="session_<%=session1.getId()%>"
														data-jstree='{"opened":true}'><%=cmsession_sno%>. <%=session1.getTitle()%>
														<ul>
														
														</ul></li>

													<%
														}
													%>
												</ul></li>
											<%
												}
											%>
										</ul></li>
									<%
										}
									%>
								</ul>
							</li>
						</ul>
					</div>

				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" data-dismiss="modal" class="close"
						type="button">×</button>
					<h4 id="myModalLabel1" class="modal-title">Session Assignment</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" onsubmit="myFunction()"
						action="/content/course/assignment" method="POST">
						<input type="hidden" id="selected_items" name="selected_items" />
						<div class="form-group">
							<label for="inputEmail1" class="col-lg-4 control-label">Choose
								User to Assign</label>
							<div class="col-lg-6">
								<select name="assign_user" class="form-control input-lg">
									<%
										for (IstarUser user : CMSRegistry.getCUsers()) {
											String tag = "Content";
											if (user.getUserType().equalsIgnoreCase("CREATIVE_CREATOR")) {
												tag = "Artist";
											}
									%>
									<option value="<%=user.getId()%>"><%=tag%> -
										<%=user.getEmail()%></option>
									<%
										}
									%>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword1" class="col-lg-4 control-label">Select
								Content Reviewer</label>
							<div class="col-lg-10">
								<select name="review_user" class="form-control input-lg"
									multiple="multiple">
									<%
										for (IstarUser user : CMSRegistry.getCRUsers()) {
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
								<button type="submit" class="btn-u btn-u-green">Assign
									& Create</button>
							</div>
						</div>
					</form>

				</div>
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn-u btn-u-default"
						type="button">Close</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="replicate-lesson-modal"  role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" data-dismiss="modal" class="close" type="button">x</button>
					<h4 id="myModalLabel1" class="modal-title">Session Assignment</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" action="/content/update_course">
						<input type="hidden" id="source-lesson-id" name="lesson_id" />
						<input type="hidden" id="action" name="action" value="replicate"/>
						<input type="hidden" id="entity_type" name="entity_type" value="lesson"/>
						<div class="form-group">
							<label for="inputEmail1" class="col-lg-4 control-label">Choose Session you want to replicate the lesson into</label>
							<div class="col-lg-6">
								<select name="dest_session_id" id="dest-session-select" class="form-control input-lg">
									<%
										for (Cmsession cmsession : (new CourseService()).gerAllCmsession()) {
									%>
									<option value="<%=cmsession.getId()%>"><%=cmsession.getTitle()%></option>
									<%
										}
									%>
								</select>
							</div>
						</div>
						<div class="form-group">
							<div class="col-lg-offset-2 col-lg-10">
								<button type="submit" class="btn-u btn-u-green">Proceed</button>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn-u btn-u-default"
						type="button">Close</button>
				</div>
			</div>
		</div>
	</div>

	<!-- JS Global Compulsory -->
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jquery/jquery-migrate.min.js"></script>
	<script type="text/javascript"
		src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>

	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!-- JS Implementing Plugins -->
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/back-to-top.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/smoothScroll.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jstree/jstree.js"></script>
	<script
		src="<%=baseURL%>assets/plugins/jquery-contextmenu/src/jquery.contextMenu.js"
		type="text/javascript"></script>

	<!-- JS Customization -->
	<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/plugins/select2-4.0.3/dist/js/select2.full.js"></script>
	
	<script type="text/javascript">
		jQuery(document).ready(function() {
			
			$("#dest-session-select").select2({
			    placeholder: "Select session",
			    allowClear: true
			});

			$.contextMenu({
				selector : '.context-menu-previliged',
				
				items : {
					"paste" : {
						name : "Publish Lesson",
						icon : "paste",
						callback : function(key, options) {
							var taskId = $(this).data('task-id');
							$.ajax({
								type : "GET",
								url : "/content/change_status?new_status=PUBLISHED&task_id=" + taskId,
							});
							location.reload();
						}
					},
					
					"replicate" : {
						name : "Replicate Lesson",
						icon : "paste",
						callback : function(key, options) {
							var lesson_id = $(this).data('lesson-id');
							$('#source-lesson-id').val(lesson_id);
							$('#replicate-lesson-modal').modal('show');
						}
					},

					"view" : {
						name : "View Lesson",
						icon : "paste",
						callback : function(key, options) {
							var taskId = $(this).data('task-id');
							var url = "<%=baseURL%>/edit_lesson?task_id=" + taskId;
							window.open(url, '_blank');
						}
					}
				}
			});

			$.contextMenu({
				selector : '.context-menu',
				items : {
					"view" : {
						name : "View Lesson",
						icon : "paste",
						callback : function(key, options) {
							var url = "<%=baseURL%>/edit_lesson?task_id=" + $(this).data('task-id');
							window.open(url, '_blank');
						}
					},

					"replicate" : {
						name : "Replicate Lesson",
						icon : "paste",
						callback : function(key, options) {
							var lesson_id = $(this).data('lesson-id');
							$('#source-lesson-id').val(lesson_id);
							$('#replicate-lesson-modal').modal('show');
						}
					}
				}

			});
			
			$.contextMenu({
				selector : '.context-menu-nonreplicable',
				items : {
					"view" : {
						name : "View Lesson",
						icon : "paste",
						callback : function(key, options) {
							var url = "<%=baseURL%>edit_lesson?task_id=" + $(this).data('task-id');
							window.open(url, '_blank');
						}
					}
				}

			});

			App.init();

			$('#html1').jstree({
				"core" : { 
					"themes" : { "variant" : "large" }
				},
				"checkbox" : {
					"keep_selected_style" : false
				},
				"plugins" : [ "checkbox" ]
			});

			$('#selected_items').val("aaaa");
			document.getElementById("formfield").value = "";

		});

		function myFunction() {
			var selectedElmsIds = $('#html1').jstree("get_selected");
			if (selectedElmsIds == "") {
				document.getElementById("err").innerHTML = "Please select the lesson";
				$(window).scrollTop(0);
				return false;
			} else {
				$('#myModal').modal('show');
				document.getElementById("err").innerHTML = "";
			}
			$('#selected_items').val(selectedElmsIds);
		}

		function DisplayFilePath() {
			var filepath = $(":file").val();
			var filename = filepath.substr(filepath.lastIndexOf('\\') + 1,
					filepath.length);
			document.getElementById("formfield").value = filename;
		}
	</script>
	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->
</body>
</html>
