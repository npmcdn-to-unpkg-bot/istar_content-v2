<%@page import="org.apache.velocity.util.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@ page import="java.util.*"%>
<%@page import="com.istarindia.apps.cmsutils.TableUtils"%>
<%@page import="com.istarindia.apps.dao.*"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.hibernate.Criteria"%>
<%@page import="org.hibernate.HibernateException"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.Transaction"%>
<%@page import="com.istarindia.apps.cmsutils.reports.*"%>
<%
	String url = request.getRequestURL().toString();
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length())
			+ request.getContextPath() + "/";
	int theme_id= 1;
	if (request.getParameterMap().containsKey("theme_id")) {
		theme_id = Integer.parseInt(request.getParameter("theme_id"));
	}
	String sql = "select * from ui_theme as T where T.id="+theme_id;
	
	DBUTILS db = new DBUTILS();
	List<HashMap<String, Object>> results = db.executeQuery(sql);
	HashMap<String, Object> theme = results.get(0);
	
	
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
				<h1>Edit theme</h1>
			</div>
		</div>
		<form action="/content/theme_editor" class="sky-form">
		<input name="is_edit" value="true" type="hidden"/>
		<input name="theme_id" value=<%=theme_id %> type="hidden"/>
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
										placeholder="Please enter new theme name" value="<%=theme.get("name") %>">
									</label>
								</section>
								
								<section class="col col-md-6">
								<label class="label">Background Color</label>
									<label class="select"> <input type="color" id='bg_color' name="background_color" value="<%=theme.get("background_color") %>">
									</label>
								</section>
							</fieldset>
						</div>
					</div>
					
					<%
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
						
					%>
					<!--title-->
					<div class="row">
						<header>TITLE</header>
						<div class="row">
							<fieldset>
								<section class="col col-4">
									<!-- key.toUpperCase() -->
									<label class="label">Title
										Font Size </label> <label class="input"> <input id='id_title_____font_size' type="number"
										min="20" max="300" name="title_____font_size" value="<%=theme.get("title_____font_size")%>">
									</label>
								</section>

								<section class="col col-4">
									<label class="label">Title
										Line height</label> <label class="input"> <input type="number" id="id_title_____line_height" 
										name="title_____line_height" step="0.1" min="0.5" max="3" value="<%=theme.get("title_____line_height")%>">
									</label>
								</section>

								<section class="col col-4">
									<label class="label">Title
										Font Weight</label> <label class="input"> <input type="number" id="id_title_____font_weight" name="title_____font_weight" 
										step="100" min="100" max="900" value="<%=theme.get("title_____font_weight") %>" >
									</label>
								</section>

								<section class="col col-4">
									<label class="label">Title
										Text alignment</label> <label class="select"> <select
										name="title_____text_alignment" id="id_title_____text_alignment" >
											<option value="<%=theme.get("title_____text_alignment") %>" selected><%=theme.get("title_____text_alignment") %></option>
											<option value="center">Center</option>
											<option value="left">Left</option>
											<option value="right">Right</option>
									</select>
									</label>
								</section>

								<section class="col col-4">
									<label class="label">Title
										Font Family</label> <label class="select"> <select id="id_title_____font_family"  
										name="title_____font_family">
											<option value="<%=theme.get("title_____font_family") %>" selected><%=theme.get("title_____font_family").toString().split(".ttf")[0] %></option>
										<% for(String fontName : fontNames) { %>
											<option value="<%=fontName %>"><%=fontName.split(".ttf")[0] %></option>
										<% } %>
									</select>
									</label>
								</section>

								<section class="col col-4">
									<label class="label">Title
										Color</label> <label class="select"> <input type="color" id="id_title_____font_color"
										name="title_____font_color" value="<%=theme.get("title_____font_color") %>" >
									</label>
								</section>
							</fieldset>
						</div>
					</div>
					
					<!-- subtitle-->
					
					<div class="row">
						<header>SUBTITLE</header>
						<div class="row">
							<fieldset>
								<section class="col col-4">
									<!-- key.toUpperCase() -->
									<label class="label">Subtitle
										Font Size </label> <label class="input"> <input type="number"
										min="20" max="300" name="subtitle_____font_size" value="<%=theme.get("subtitle_____font_size") %>">
									</label>
								</section>

								<section class="col col-4">
									<label class="label">Subtitle
										Line height</label> <label class="input"> <input type="number"
										name="subtitle_____line_height" step="0.1" min="0.5" max="3" value="<%=theme.get("subtitle_____line_height") %>"
										>
									</label>
								</section>

								<section class="col col-4">
									<label class="label">Subtitle
										Font Weight</label> <label class="input"> <input type="number"
										name="subtitle_____font_weight" step="100" min="100" value="<%=theme.get("subtitle_____font_weight") %>"
										max="900">
									</label>
								</section>

								<section class="col col-4">
									<label class="label">Subtitle
										Text alignment</label> <label class="select"> <select
										name="subtitle_____text_alignment">
											<option value="<%=theme.get("subtitle_____text_alignment") %>" selected><%=theme.get("subtitle_____text_alignment") %></option>
											<option value="center">Center</option>
											<option value="left" >Left</option>
											<option value="right">Right</option>
									</select>
									</label>
								</section>

								<section class="col col-4">
									<label class="label">Subtitle
										Font Family</label> <label class="select"> <select
										name="subtitle_____font_family">
											<option value="<%=theme.get("subtitle_____font_family") %>" selected><%=theme.get("subtitle_____font_family").toString().split(".ttf")[0] %></option>
										<% for(String fontName : fontNames) { %>
											<option value="<%=fontName %>"><%=fontName.split(".ttf")[0] %></option>
										<% } %>
									</select>
									</label>
								</section>

								<section class="col col-4">
									<label class="label">Subtitle
										Color</label> <label class="select"> <input type="color"
										name="subtitle_____font_color" value="<%=theme.get("subtitle_____font_color") %>">
									</label>
								</section>
							</fieldset>
						</div>
					</div>
					
					<!-- listitem -->
					
					<div class="row">
						<header>LISTITEM</header>
						<div class="row">
							<fieldset>
								<section class="col col-4">
									<!-- key.toUpperCase() -->
									<label class="label">List Item
										Font Size </label> <label class="input"> <input type="number"
										min="20" max="300" name="listitem_____font_size" value="<%=theme.get("listitem_____font_size") %>">
									</label>
								</section>

								<section class="col col-4">
									<label class="label">List Item
										Line height</label> <label class="input"> <input type="number"
										name="listitem_____line_height" step="0.1" min="0.5" max="3" value="<%=theme.get("listitem_____line_height") %>">
									</label>
								</section>

								<section class="col col-4">
									<label class="label">List Item
										Font Weight</label> <label class="input"> <input type="number"
										name="listitem_____font_weight" step="100" min="100" value="<%=theme.get("listitem_____font_weight") %>"
										max="900" >
									</label>
								</section>

								<section class="col col-4">
									<label class="label">List Item
										Text alignment</label> <label class="select"> <select
										name="listitem_____text_alignment">
											<option value="<%=theme.get("listitem_____text_alignment") %>" selected><%=theme.get("listitem_____text_alignment") %></option>
											<option value="center">Center</option>
											<option value="left" >Left</option>
											<option value="right">Right</option>
									</select>
									</label>
								</section>

								<section class="col col-4">
									<label class="label">List Item
										Font Family</label> <label class="select"> <select
										name="listitem_____font_family">
											<option value="<%=theme.get("listitem_____font_family") %>" selected><%=theme.get("listitem_____font_family").toString().split(".ttf")[0] %></option>
										<% for(String fontName : fontNames) { %>
											<option value="<%=fontName %>"><%=fontName.split(".ttf")[0] %></option>
										<% } %>
									</select>
									</label>
								</section>

								<section class="col col-4">
									<label class="label">List Item
										Color</label> <label class="select"> <input type="color"
										name="listitem_____font_color" value="<%=theme.get("listitem_____font_color") %>">
									</label>
								</section>
							</fieldset>
						</div>
					</div>
					
					<!-- paragraph -->
					
					<div class="row">
						<header>PARAGRAPH</header>
						<div class="row">
							<fieldset>
								<section class="col col-4">
									<!-- key.toUpperCase() -->
									<label class="label">Paragraph
										Font Size </label> <label class="input"> <input type="number"
										min="20" max="300" name="paragraph_____font_size" value="<%=theme.get("paragraph_____font_size") %>">
									</label>
								</section>

								<section class="col col-4">
									<label class="label">Paragraph
										Line height</label> <label class="input"> <input type="number"
										name="paragraph_____line_height" step="0.1" min="0.5" max="3" value="<%=theme.get("paragraph_____line_height") %>">
									</label>
								</section>

								<section class="col col-4">
									<label class="label">Paragraph
										Font Weight</label> <label class="input"> <input type="number"
										name="paragraph_____font_weight" step="100" min="100"
										max="900" value="<%=theme.get("paragraph_____font_weight") %>">
									</label>
								</section>

								<section class="col col-4">
									<label class="label">Paragraph
										Text alignment</label> <label class="select"> <select
										name="paragraph_____text_alignment">
											<option value="<%=theme.get("paragraph_____text_alignment") %>" selected><%=theme.get("paragraph_____text_alignment") %></option>
											<option value="center">Center</option>
											<option value="left" >Left</option>
											<option value="right">Right</option>
									</select>
									</label>
								</section>

								<section class="col col-4">
									<label class="label">Paragraph
										Font Family</label> <label class="select"> <select
										name="paragraph_____font_family">
											<option value="<%=theme.get("paragraph_____font_family") %>" selected><%=theme.get("paragraph_____font_family").toString().split(".ttf")[0] %></option>
										<% for(String fontName : fontNames) { %>
											<option value="<%=fontName %>"><%=fontName.split(".ttf")[0] %></option>
										<% } %>
									</select>
									</label>
								</section>

								<section class="col col-4">
									<label class="label">Paragraph
										Color</label> <label class="select"> <input type="color"
										name="paragraph_____font_color" value="<%=theme.get("paragraph_____font_color") %>">
									</label>
								</section>
							</fieldset>
						</div>
					</div>
					
					<footer>
						<button type="submit" class="btn-u">Submit</button>
						<button type="button" class="btn-u btn-u-default"
							onclick="window.history.back();">Back</button>
					</footer>
				</div>
			
			<div  class="col-md-7">
			
			<% 
			try {
			
			SlideDAO dao = new SlideDAO();
			Slide s = dao.findById(Integer.parseInt(request.getParameter("slide_id")));
			
			%>
			<iframe style="width: 100%; height: 100vh;" id='theme_preview' src="/content/lesson/preview_desktop.jsp?ppt_id=<%=s.getPresentaion().getId()%>"></iframe>
			
			<% } catch(Exception e) {} %>
			
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

			
			// on chnage of params do this ... 
			$( "#bg_color" ).change(function () {
			    var str = $('#bg_color').val();
			    console.log(str);
			    $('#theme_preview').contents().find('body').css('background-color',str);//: #ffffff;
			    
			    $('#theme_preview').contents().find('.slide-background').css('background-color',str);//: #ffffff;
			    
			    	
			}) ;
			
			
			$( "#id_title_____font_size" ).change(function () {
			    var str = $('#id_title_____font_size').val();
			    console.log(str);
			    $('#theme_preview').contents().find('h2').css('font-size',str+"px");//: #ffffff;
			    $('#theme_preview').contents().find('#data_slide_title').css('font-size',str+"px");//: #ffffff;
			}) ;
			
			
			$( "#id_title_____line_height" ).change(function () {
			    var str = $('#id_title_____line_height').val();
			    console.log(str);
			    $('#theme_preview').contents().find('h2.updated').css('line-height',str);//: #ffffff;
			    $('#theme_preview').contents().find('#data_slide_title').css('line-height',str);//: #ffffff;
			}) ;
			
			
			$( "#id_title_____font_weight" ).change(function () {
			    var str = $('#id_title_____font_weight').val();
			    console.log(str);
			    $('#theme_preview').contents().find('h2.updated').css('font-weight',str);//: #ffffff;
			    $('#theme_preview').contents().find('#data_slide_title').css('font-weight',str);//: #ffffff;

			}) ;
			
			
			$( "#id_title_____text_alignment" ).change(function () {
			    var str = $('#id_title_____text_alignment').val();
			    console.log(str);
			    $('#theme_preview').contents().find('h2').css('text-align',str);//: #ffffff;
			    $('#theme_preview').contents().find('#data_slide_title').css('text-align',str);//: #ffffff;

			}) ;
			
			
			$( "#id_title_____font_family" ).change(function () {
			    var str = $('#id_title_____font_family').val();
			    console.log(str);
			    $('#theme_preview').contents().find('h2').css('font-family',str.replace('-Regular.ttf',''));//: #ffffff;
			    $('#theme_preview').contents().find('#data_slide_title').css('font-family',str.replace('-Regular.ttf',''));//: #ffffff;

			}) ;
			
			$( "#id_title_____font_color" ).change(function () {
			    var str = $('#id_title_____font_color').val();
			    console.log(str);
			    $('#theme_preview').contents().find('h2').css('color',str);//: #ffffff;
			    $('#theme_preview').contents().find('#data_slide_title').css('color',str);//: #ffffff;

			}) ;
			
			
			
		});
	</script>
	<!--[if lt IE 9]>
	<script src="assets/plugins/respond.js"></script>
	<script src="assets/plugins/html5shiv.js"></script>
	<script src="assets/plugins/placeholder-IE-fixes.js"></script>
	<![endif]-->

</body>
</html>
