<%@page import="org.apache.velocity.util.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@ page import="java.util.*"%>
<%@page import="com.istarindia.apps.cmsutils.TableUtils"%>
<%@page import="com.istarindia.apps.dao.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.istarindia.apps.cmsutils.reports.*"%>
<%
	String url = request.getRequestURL().toString();
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length())
			+ request.getContextPath() + "/";

	UiTheme theme = new UiTheme();
	if (request.getParameterMap().containsKey("theme_id")) {
		theme = (new UiThemeDAO()).findById(Integer.parseInt(request.getParameter("theme_id")));
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Published Media | iStar CMS</title>

<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link rel="shortcut icon" href="<%=baseURL%>assets/img/talentify_logo_fav_48x48.png" />

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
<link rel="stylesheet" href="<%=baseURL%>assets/css/pages/profile.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/app.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/css/sky-forms.css">
<link rel="stylesheet"
	href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/app.css">
<!-- CSS Theme -->
<link rel="stylesheet"
	href="<%=baseURL%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet"
	href="<%=baseURL%>assets/css/theme-colors/orange.css" id="style_color">

<!-- CSS Customization -->
<link rel="stylesheet" href="<%=baseURL%>assets/css/custom.css">
</head>

<body>

	<div class="wrapper">
		<jsp:include page="includes/header.jsp"></jsp:include>
		<div class="breadcrumbs">
			<div class="container-fluid">
				<h1>Create new theme</h1>
			</div>
		</div>
		<form action="/content/theme_editor" class="sky-form">
			<div class="container-fluid height-1000 content"
				style="padding: 0px !important">
				<div class="col-md-4">
					<br />
					<div class="row">

						<header>THEME DETAILS</header>

						<div class="row">
							<fieldset>
								<section class="col col-md-6">
								<label class="label">Name</label>
									<label class="input"> <input type="text" name="name"
										placeholder="Please enter new theme name">
									</label>
								</section>
								
								<section class="col col-md-6">
								<label class="label">Background Color</label>
									<label class="select"> <input type="color"
										name="background_color" value="#ffffff">
									</label>
								</section>
							</fieldset>
						</div>
					</div>
					<%
						ArrayList<String> entities = new ArrayList();
						entities.add("title");
						entities.add("subtitle");
						entities.add("listitem");
						entities.add("paragraph");;

						
						ArrayList<String> fontNames = new ArrayList();
						fontNames.add("Roboto-Regular.ttf");
						fontNames.add("Alice-Regular.ttf");
						fontNames.add("Asap-Regular.ttf");
						fontNames.add("Biryani-Regular.ttf");
						fontNames.add("ComingSoon.ttf");
						fontNames.add("Cookie-Regular.ttf");
						fontNames.add("Domine-Regular.ttf");
						fontNames.add("DroidSerif.ttf");
						fontNames.add("Exo-Regular.ttf");
						fontNames.add("Lato-Regular.ttf");
						fontNames.add("LeagueScript.ttf");
						fontNames.add("LibreBaskerville-Regular.ttf");
						fontNames.add("NotoSerif-Regular.ttf");
						fontNames.add("OpenSans-Regular.ttf");
						fontNames.add("Prata-Regular.ttf");
						fontNames.add("Quicksand-Regular.ttf");
						fontNames.add("Raleway-Regular.ttf");
						
						int default_font_size = 60;
						
						for (String key : entities) {
					%>
					<div class="row">
						<header><%=key.toUpperCase()%></header>
						<div class="row">
							<fieldset>
								<section class="col col-4">
								<%
									
									switch(key){
										case "title":
											default_font_size = 100;
											break;
										case "subtitle":
											default_font_size = 120;
											break;
										case "listitem":
											default_font_size = 37;
											break;
										case "paragraph":
											default_font_size = 47;
											break;
									}
								%>
								
									<label class="label"><%=StringUtils.capitalizeFirstLetter(key)%>
										Font Size </label> <label class="input"> <input type="number"
										min="20" max="200" name="<%=key%>_____font_size" value="<%=default_font_size%>">
									</label>
								</section>

								<section class="col col-4">
									<label class="label"><%=StringUtils.capitalizeFirstLetter(key)%>
										Line height</label> <label class="input"> <input type="number"
										name="<%=key%>_____line_height" step="0.1" min="0.5" max="3"
										value="1">
									</label>
								</section>

								<section class="col col-4">
									<label class="label"><%=StringUtils.capitalizeFirstLetter(key)%>
										Font Weight</label> <label class="input"> <input type="number"
										name="<%=key%>_____font_weight" step="100" min="100"
										max="900" value="100">
									</label>
								</section>

								<section class="col col-4">
									<label class="label"><%=StringUtils.capitalizeFirstLetter(key)%>
										Text alignment</label> <label class="select"> <select
										name="<%=key%>_____text_alignment">
											<option value="center">Center</option>
											<option value="left" selected>Left</option>
											<option value="right">Right</option>
									</select>
									</label>
								</section>
								
								<section class="col col-4">
									<label class="label"><%=StringUtils.capitalizeFirstLetter(key)%>
										Font Family</label> <label class="select"> <select
										name="<%=key%>_____font_family">
										<% for(String fontName : fontNames) {%>
											<option value="<%=fontName %>"><%=fontName.split(".ttf")[0] %></option>
										<% }%>
									</select>
									</label>
								</section>

								<section class="col col-4">
									<label class="label"><%=StringUtils.capitalizeFirstLetter(key)%>
										Color</label> <label class="select"> <input type="color"
										name="<%=key%>_____font_color" value="#5d5d5d">
									</label>
								</section>
							</fieldset>
						</div>
					</div>
					<%
						}
					%>
					<footer>
						<button type="submit" class="btn-u">Submit</button>
						<button type="button" class="btn-u btn-u-default"
							onclick="window.history.back();">Back</button>
					</footer>
				</div>
			
			
			
			
			</div>
		</form>
		<jsp:include page="includes/footer.jsp"></jsp:include>
	</div>
	</div>
	<!-- JS Global Compulsory -->
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jquery/jquery-migrate.min.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/bootstrap/js/bootstrap.min.js"></script>
	<!-- JS Implementing Plugins -->
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/back-to-top.js"></script>
	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/smoothScroll.js"></script>
	<!-- JS Customization -->
	<script type="text/javascript" src="<%=baseURL%>assets/js/custom.js"></script>
	<!-- JS Page Level -->
	<script type="text/javascript" src="<%=baseURL%>assets/js/app.js"></script>
	<script
		src="<%=baseURL%>assets/plugins/datatables/jquery.dataTables.min.js"></script>
	<script
		src="<%=baseURL%>assets/plugins/datatables/dataTables.colVis.min.js"></script>
	<script
		src="<%=baseURL%>assets/plugins/datatables/dataTables.tableTools.min.js"></script>
	<script
		src="<%=baseURL%>assets/plugins/datatables/dataTables.bootstrap.min.js"></script>
	<script
		src="<%=baseURL%>assets/plugins/datatable-responsive/datatables.responsive.min.js"></script>
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
