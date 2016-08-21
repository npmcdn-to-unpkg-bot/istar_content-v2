<%@page import="com.istarindia.apps.services.CMSRegistry"%>
<%@page import="com.istarindia.apps.dao.*"%><%@page import="java.util.*"%>
<%@page import="org.hibernate.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>Course Structure | iStar CMS</title>

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
			<div class="container-fluid">
				<h1 class="pull-left">
					<a href="<%=baseURL %>content_admin/dashboard.jsp">Content  Handouts</a>
				</h1>

			</div>
			<!--/container-->
		</div>
		<div class="container-fluid height-1000" style="padding: 0px !important">
			<div class="panel panel-orange" style="margin-left: 100px; margin-right: 100px">
				<div class="panel-heading">
					<h3 class="panel-title">
						<i class="fa fa-tasks"></i> Course Tree & Handouts
					</h3>
				</div>
				<div class="panel-body">
	                 <label id="err" style="font-size: 150%; display: block;color:#ee9393"></label>
					<div id="html1">
						<ul>
							<li id="none" data-jstree='{"opened":true}'>All Courses
								<ul>
									<% 
									 IstarUserDAO dao = new IstarUserDAO();
					                Session session_hib = dao.getSession();
					                String sql1 ="select * from course";
					                SQLQuery query = session_hib.createSQLQuery(sql1);
					            	query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
					            	DBUTILS util = new DBUTILS();
					        		List<HashMap<String, Object>> results = util.executeQuery(sql1);
									
									int course_sno = 0;
										//CourseDAO dao = new CourseDAO();
										for (HashMap<String, Object> course : results) {
											course_sno++;
											int course_id = (int)course.get("id");
											String course_name = (String) course.get("course_name");
									%>
									<li id="course_<%=course_id%>" data-jstree='{"opened":false}'><a href='view_handout.jsp?course_id=<%=course_sno%>'> <%=course_sno%>. <%=course_name%></a>
										<ul >
											<%  int cmsession_sno = 0;
											
												String sql_mod ="select * from module where course_id ="+course_id;
								                SQLQuery query_mod = session_hib.createSQLQuery(sql_mod);
								            	query_mod.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
								                List<HashMap<String, Object>> results_mod = query_mod.list();
												for (HashMap<String, Object> mod : results_mod) {
													int module_id = (int) mod.get("id");
													String module_name = (String) mod.get("module_name");
											%>
											<li  id="module_<%=module_id%>" data-jstree='{"opened":true}'> <%=module_name%>
												<ul >
													<% 
													String sql_session ="select * from cmsession where module_id ="+module_id;
									                SQLQuery query_session = session_hib.createSQLQuery(sql_session);
									                query_session.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
									                List<HashMap<String, Object>> results_session = query_session.list();
													
 												for (HashMap<String, Object> cms : results_session) {
 													cmsession_sno++;
 													int cmsession_id = (int) cms.get("id");
 													String title = (String) cms.get("title");
 													
 											%>
													<li  id="session_<%=cmsession_id%>" data-jstree='{"opened":true}'>
													
													<span class="label rounded label-sea"> 
													<a style='color:white' href='view_handout.jsp?sess_id=<%=cmsession_id%>'> View Handouts </a></span><%=cmsession_sno%>. <%=title%>
														<ul>
															<% 
															String sql_lesson ="SELECT 	distinct T.status as status, L.id as lesson_id, L.title as title,  T.id as task_id , CC.email as assignee FROM 	lesson L, task T, task_reviewer TR, content_creator CC WHERE T.item_type='LESSON' AND T.item_id = L.id AND CC.id = T.actor_id AND session_id="+cmsession_id;
											                SQLQuery query_lesson = session_hib.createSQLQuery(sql_lesson);
											                query_lesson.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
											                List<HashMap<String, Object>> results_lesson = query_lesson.list();
															
															
															for (HashMap<String, Object> lesson : results_lesson) { 
																int task_id = (int ) lesson.get("task_id");
																int lesson_id = (int )lesson.get("lesson_id");
																String status = (String) lesson.get("status");
																String assignee = (String) lesson.get("assignee");
																
																try {
																String reviewers = "label rounded label-sea";
																String assigned = "label label-purple rounded-2x";
																String statusLabel = "label rounded label-yellow";
																String sql_reviewer ="SELECT * from task_reviewer where task_id="+task_id;
												                SQLQuery query_review = session_hib.createSQLQuery(sql_reviewer);
												                query_review.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
												                List<HashMap<String, Object>> results_review = query_review.list();
																 if(results_review.size()>0) {
																	reviewers = "label label-default ";
																} 
																
																
																if(status.equalsIgnoreCase("CREATED")) {
																	statusLabel = "label label-default ";
																}
																String email = ((IstarUser)request.getSession().getAttribute("user")).getEmail();
																if(assignee.equalsIgnoreCase(email)) {
																	assigned = "label label-default ";
																}
															%>
															<li style="padding:2% !important; margin-bottom: 4px" id="lesson_<%=lesson_id%>" data-jstree='{"opened":true}'><%=title %> 
															<span class="<%=assigned%>"> Assigned to - <%=assignee %></span> 
															 <span class="label rounded label-sea"> 
													<a style='color:white' href='/content/content_creator/edit_handout.jsp?lesson_id=<%=lesson_id%>'> Edit Handouts </a></span>
															<%
												}
																
																catch(Exception e) {
																	System.out.println(lesson_id);
																}
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
				
				jQuery(document).ready(function() {
					App.init();
					$('#html1').jstree({ "plugins" : ["themes","html_data","ui"] });
					$('#selected_items').val("aaaa");
	                document.getElementById("formfield").value = "";

				});
				function DisplayFilePath(){
                    var filepath=$(":file").val();
                    var filename=filepath.substr(filepath.lastIndexOf('\\')+1,filepath.length);
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
