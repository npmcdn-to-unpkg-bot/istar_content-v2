<%@page import="com.istarindia.apps.dao.DoubtDAO"%>
<%@page import="com.istarindia.apps.cmsutils.TableUtils"%>
<%@page import="javax.swing.plaf.TableUI"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ page import="java.util.*"%>
<%@page import="com.istarindia.apps.dao.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.istarindia.apps.cmsutils.reports.*"%>

<%
	String url = request.getRequestURL().toString();
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
<title>Assigned Lessons | iStar CMS</title>

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
<link rel="stylesheet" href="<%=baseURL%>assets/css/pages/profile.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/css/sky-forms.css">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">

<link rel="stylesheet" href="<%=baseURL%>assets/css/app.css">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/r/bs-3.3.5/jq-2.1.4,dt-1.10.8/datatables.min.css" />

<!-- CSS Theme -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet" href="<%=baseURL%>assets/css/theme-colors/orange.css" id="style_color">

<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/custom.css">
</head>
<% 
int doubtID = Integer.parseInt(request.getParameter("id"));
DoubtDAO dao = new DoubtDAO();
Doubt d = dao.findById(doubtID);
%>
<body>

	<div class="wrapper">
		<jsp:include page="includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container-fluid height-1000" style="padding: 0px !important">
				<div class="col-md-12">
					<form action="/content/answer_doubt" class="sky-form">
						<header>Answer Doubt</header>
						<input type="hidden" name="doubt_id" value="<%=request.getParameter("id")%>">
						<fieldset>
							<section>
								<p>
									<span class="dropcap"><%=d.getQuestion().substring(0,1) %></span><%=d.getQuestion() %>
								</p>
							</section>

							<section style="margin-top: 90px">
								<label class="label">Answer</label> <label class="textarea"> <textarea  name="answer" rows="3"></textarea>
								</label>
								<div class="note">
									<strong>Note:</strong> Please type in the doubt 
								</div>
							</section>

							
						</fieldset>
						<footer>
							<button type="submit" class="btn-u">Submit</button>
						</footer>
						</form>
				</div>
			</div>
			<jsp:include page="includes/footer.jsp"></jsp:include>
		</div>

		<!-- JS Global Compulsory -->
		<script type="text/javascript" src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="<%=baseURL%>assets/plugins/jquery/jquery-migrate.min.js"></script>
		<script type="text/javascript" src="<%=baseURL%>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
		<!-- JS Implementing Plugins -->
		<script type="text/javascript" src="<%=baseURL%>assets/plugins/back-to-top.js"></script>
		<script type="text/javascript" src="<%=baseURL%>assets/plugins/smoothScroll.js"></script>
		<!-- JS Customization -->
		<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
		<!-- JS Page Level -->
		<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
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

			});
		</script>
		<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->
</body>
</html>
