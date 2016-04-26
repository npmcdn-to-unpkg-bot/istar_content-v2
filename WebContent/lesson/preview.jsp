<%@page import="com.istarindia.apps.dao.LessonDAO"%>
<%@page import="com.istarindia.apps.dao.PresentaionDAO"%>
<%@page import="com.istarindia.apps.dao.Presentaion"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><!doctype html>
<% String url = request.getRequestURL().toString();
String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";

PresentaionDAO dao = new PresentaionDAO();
int lessonID = Integer.parseInt(request.getParameter("ppt_id"));
Presentaion ppt =  dao.findById(lessonID);

%>
<html lang="en">
<head>
<meta charset="utf-8">
<title>reveal.js - The HTML Presentation Framework</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" href="<%=baseURL %>student/css/reveal.css"><link
	href="<%=baseURL%>themes/mobile/<%=ppt.getLesson().getLesson_theme().toLowerCase()%>.css"
	rel="stylesheet" />
<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>	
	<%
	//if (request.getHeader("User-Agent").indexOf("Mobile") != -1) {
		if (true) {
%><link href="<%=baseURL%>themes/mobile.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" href="<%=baseURL %>student/css/reveal.css"><link
	href="<%=baseURL%>themes/mobile/<%=ppt.getLesson().getLesson_theme().toLowerCase()%>.css"
	rel="stylesheet" />
<%
	} else {
%>
<link href="css/style.css" rel="stylesheet">
<link rel="stylesheet" href="<%=baseURL %>student/css/reveal.css"><link
	href="<%=baseURL%>themes/desktop/<%=ppt.getLesson().getLesson_theme().toLowerCase()%>.css"
	rel="stylesheet" />
<%
	}
%>

	</head>
<body style="    background-size: cover;background-image: url('<%=baseURL%>student/subject_images/<%=ppt.getLesson().getLesson_subject().toLowerCase()%>.png')">
	<div class="reveal">
		<div class="slides">
		<%=ppt.outputSlides() %>
		</div>

	</div>
<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
	<script src="<%=baseURL %>student/lib/js/head.min.js"></script>
	<script src="<%=baseURL %>student/js/reveal.js"></script>

	<script>

			Reveal.initialize({  
				center: false, 
				width: 1210,
		    height: 450, 
		    margin: 0.0

			
			});
			
			Reveal.addEventListener( 'slidechanged', function( event ) {
			    // event.previousSlide, event.currentSlide, event.indexh, event.indexv
			    //$('#prv').contents().find('.slide-background').css('background-color', $('#slide_color').val());
			   // console.log(event.indexh + " -- "+ $('#'+event.indexh).css('background-image') );
			    
			    $('body').css('background-image', $('#'+event.indexh).css('background-image'));
			    $('body').css('background-size', 'cover');
			} );

		</script>

</body>
</html>
