<%@page import="com.istarindia.apps.dao.PresentaionDAO"%>
<%@page import="com.istarindia.apps.dao.Presentaion"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.istarindia.cms.lessons.*"%>
<%@ page import="javax.xml.bind.*"%><%@ page import="java.io.*"%>
<% String url = request.getRequestURL().toString();
String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
Presentaion ppt = (new PresentaionDAO()).findById(Integer.parseInt(request.getParameter("ppt_id")));
int cm_session_id = ppt.getLesson().getCmsession().getId();
%><!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">

<title>reveal.js The HTML Presentation Framework</title>

 <link href="<%=baseURL %>impress/css/video-js.css" rel="stylesheet">

    <link href="http://fonts.googleapis.com/css?family=Open+Sans:regular,semibold,italic,italicsemibold|PT+Sans:400,700,400italic,700italic|PT+Serif:400,700,400italic,700italic" rel="stylesheet" />
    <link href="<%=baseURL %>impress/css/impress.css" rel="stylesheet" />
    <link href="<%=baseURL %>impress/css/bootstrap.css" rel="stylesheet" />
    <link href="<%=baseURL %>impress/css/substep.css" rel="stylesheet" />
    <link
	href="<%=baseURL %>impress/themes/<%=ppt.getLesson().getLesson_theme().toLowerCase() %>.css"
	rel="stylesheet" />
    <% if(request.getHeader("User-Agent").indexOf("Mobile") != -1) {
%><link href="<%=baseURL %>impress/css/style.css" rel="stylesheet" type="text/css"  />

<%  } else { %>
<link href="<%=baseURL %>impress/css/style.css" rel="stylesheet" type="text/css"  />
<%  } %>

</head>

<body class="impress-not-supported" id="ppt" data-next_item="<%=request.getAttribute("next_item")%>" data-ppt_ID="<%=ppt.getId() %>">
<!-- <div class="fallback-message">
    <p>Your browser <b>doesn't support the features required</b> by impress.js, so you are presented with a simplified version of this presentation.</p>
    <p>For the best experience please use the latest <b>Chrome</b>, <b>Safari</b> or <b>Firefox</b> browser.</p>
</div> -->

<div id="impress">

			<%=((new CMSerializerImpress()).serializeLesson(ppt,"speaker")) %>

		</div>

	<!--[if lt IE 9]>
		<script src="assets/crossbrowserjs/html5shiv.js"></script>
		<script src="assets/crossbrowserjs/respond.min.js"></script>
		<script src="assets/crossbrowserjs/excanvas.min.js"></script>
	<![endif]-->
<script src="<%=baseURL %>assets/plugins/jquery/jquery.js"></script> 
<script src="<%=baseURL %>impress/js/impress.js"></script>
  <script src="<%=baseURL %>impress/js/video.js"></script>
<script src="<%=baseURL %>impress/js/substeps2.js"></script> 


<script>
document.onreadystatechange = function () {
    if (document.readyState == "complete") {
    	 impress().init();
    	 
    	 function setHeight() {
    		    windowHeight = $(window).innerHeight();
    		    $('.slide').css('min-height', windowHeight - 50);
    		    $('.video-js').css('max-height', windowHeight-50);
    		    $('#my-video_html5_api').css('max-height', windowHeight-200);
    	 };
    	 function setWidth() {
 		    windowWidth = $(window).innerWidth();
 		    $('.slide').css('min-width', windowWidth-40);
 	 };
    	setHeight();
    	setWidth();
 		  $(window).resize(function() {
 		    setHeight();setWidth();
 		  });
 		  
 		  
  }
}
</script>

		<!-- Everything below this point is only used for the reveal.js demo page -->
</body>
</html>
`