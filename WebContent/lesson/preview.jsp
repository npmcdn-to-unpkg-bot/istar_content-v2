<%@page import="com.istarindia.apps.dao.LessonDAO"%>
<%@page import="com.istarindia.apps.dao.PresentaionDAO"%>
<%@page import="com.istarindia.apps.dao.Presentaion"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><!doctype html>
<% String url = request.getRequestURL().toString();
String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";

PresentaionDAO dao = new PresentaionDAO();
int lessonID = Integer.parseInt(request.getParameter("ppt_id").replaceAll("/", ""));
Presentaion ppt =  dao.findById(lessonID);
String lesson_theme = ppt.getLesson().getLesson_theme();


String style_body = "background-size: cover;";
%>
<html lang="en">
<head>
<meta charset="utf-8">

<title>reveal.js The HTML Presentation Framework</title>

<meta name="description" content="A framework for easily creating beautiful presentations using HTML">
<meta name="author" content="Hakim El Hattab">

<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">

<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no, minimal-ui">

<link rel="stylesheet" href="/content/student/css/reveal.css">
<link rel="stylesheet" href="/themes/mobile.css"  type="text/css" /><!-- Printing and PDF exports -->
<link rel="stylesheet" href="/themes/mobile/<%=lesson_theme.toLowerCase()%>.css" id="theme">

<!-- Code syntax highlighting -->
<script>
			var link = document.createElement( 'link' );
			link.rel = 'stylesheet';
			link.type = 'text/css';
			link.href = window.location.search.match( /print-pdf/gi ) ? '../student/css/print/pdf.css' : '../student/css/print/paper.css';
			document.getElementsByTagName( 'head' )[0].appendChild( link );
		</script>

</head>
<body style="<%=style_body%>">
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
				width: 1600,
		    height: 900, 
		    margin: 0.0

			
			});
			function changeImage()
			{
				 Reveal.next();
			}
			Reveal.addEventListener( 'slidechanged', function( event ) {
				Reveal.addEventListener( 'stats', function() {
					console.log( 'stats called!' );
					
					// Jump to next Slide after 30 secs 
					
					setTimeout("changeImage()", 30000);
				} );

			    
			    
			} );

		</script>

</body>
</html>
