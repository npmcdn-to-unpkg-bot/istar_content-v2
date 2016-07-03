<!doctype html>
<html lang="en"><%@page import="com.istarindia.apps.dao.*"%>
<%@page import="com.istarindia.apps.dao.PresentaionDAO"%>
<%@page import="com.istarindia.apps.dao.Presentaion"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	String url = request.getRequestURL().toString();
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length())
			+ request.getContextPath() + "/";
	PresentaionDAO dao = new PresentaionDAO();
	int lessonID = Integer.parseInt(request.getParameter("ppt_id").replaceAll("/", ""));
	Presentaion ppt = dao.findById(lessonID);
	String lesson_theme = ppt.getLesson().getLesson_theme();
	String nuetral = url.substring(0, url.length() - request.getRequestURI().length()) + "/";
	String style_body = "background-size: cover;";
%>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=1024" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<title>impress.js | presentation tool based on the power of CSS3 transforms and transitions in modern browsers | by Bartek Szopka @bartaz</title>

<meta name="description" content="impress.js is a presentation tool based on the power of CSS3 transforms and transitions in modern browsers and inspired by the idea behind prezi.com." />
<meta name="author" content="Bartek Szopka" />
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/bootstrap/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
   <link href="http://lipen.co/impress.js-substeps/css/substeps.css" rel="stylesheet"> 

<link href="http://fonts.googleapis.com/css?family=Open+Sans:regular,semibold,italic,italicsemibold|PT+Sans:400,700,400italic,700italic|PT+Serif:400,700,400italic,700italic" rel="stylesheet" />
<link href="/content/impress-demo.css" rel="stylesheet" />
<link rel="shortcut icon" href="favicon.png" />
<link rel="apple-touch-icon" href="apple-touch-icon.png" />
<%
UiTheme theme = new UiTheme();

try {
int themeID = Integer.parseInt(lesson_theme);

theme = (new UiThemeDAO()).findById(themeID);
if ((new UiThemeDAO()).findById(themeID) != null) {
%>
	<jsp:include page="/themes/desktop/desktop_yellow.jsp"></jsp:include>
<% 
	} 
} catch (Exception e){
	//nothing ToDo
}
%>	
<style type="text/css">
.slide {
	width: 1750px !important;
    height: 850px !important;
}

body {
background: #7db9e8; /* Old browsers */
background: -moz-linear-gradient(top, <%=theme.getBackgroundColor() %> 0%, #ffffff 0%, #eeefd1 100%); /* FF3.6-15 */
background: -webkit-linear-gradient(top, <%=theme.getBackgroundColor() %> 0%,#ffffff 0%,#eeefd1 100%); /* Chrome10-25,Safari5.1-6 */
background: linear-gradient(to bottom, <%=theme.getBackgroundColor() %> 0%,#ffffff 0%,#eeefd1 100%); /* W3C, IE10+, FF16+, Chrome26+, Opera12+, Safari7+ */
filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='<%=theme.getBackgroundColor() %>', endColorstr='#eeefd1',GradientType=0 );
}

.slide {
   background: #7db9e8; /* Old browsers */
background: -moz-linear-gradient(top, #ffffff 0%, <%=theme.getBackgroundColor() %> 0%, #ffffff 100%); /* FF3.6-15 */
background: -webkit-linear-gradient(top, #ffffff 0%,<%=theme.getBackgroundColor() %> 0%,#ffffff 100%); /* Chrome10-25,Safari5.1-6 */
background: linear-gradient(to bottom, #ffffff 0%,<%=theme.getBackgroundColor() %> 0%,#ffffff 100%); /* W3C, IE10+, FF16+, Chrome26+, Opera12+, Safari7+ */
filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#ffffff', endColorstr='#ffffff',GradientType=0 );

}
</style>
</head>
<body class="impress-not-supported">



	<div class="fallback-message">
		<p>
			Your browser <b>doesn't support the features required</b> by impress.js, so you are presented with a simplified version of this presentation.
		</p>
		<p>
			For the best experience please use the latest <b>Chrome</b>, <b>Safari</b> or <b>Firefox</b> browser.
		</p>
	</div>



	<div id="impress" class="reveal">

		<!--  
				<div id="overview" class="step" data-x="3000" data-y="1500" data-scale="10">  </div>
				
				fragment  substep fadeIn
 		-->

		<%=ppt.outputSlidesForDesktop().replaceAll("<section", "<div").replaceAll("</section>", "</div>").replaceAll("fragment", "substep fadeIn")%>

	</div>



</body>
<script src="http://lipen.co/impress.js-substeps/js/substeps2.js"></script>
<script src="http://lipen.co/impress.js-substeps/js/impress.js"></script>
<script>
	impress().init();
</script>
</html>

