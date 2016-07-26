<%@page import="com.istarindia.apps.dao.PresentaionDAO"%>
<%@page import="com.istarindia.apps.dao.Presentaion"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.istarindia.cms.lessons.*"%><%@page import="com.istarindia.apps.dao.*"%>
<%@page import="com.istarindia.apps.dao.PresentaionDAO"%>
<%@page import="com.istarindia.apps.dao.Presentaion"%>
<%@ page import="javax.xml.bind.*"%><%@ page import="java.io.*"%>
<% String url = request.getRequestURL().toString();
String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
String templateName = request.getParameter("template_name");
String slide_id = request.getParameter("slide_id");
String nuetral = url.substring(0, url.length() - request.getRequestURI().length()) +"/";

%><!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">

<title>reveal.js The HTML Presentation Framework</title>

<meta name="description" content="A framework for easily creating beautiful presentations using HTML">
<meta name="author" content="Hakim El Hattab">

<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">

<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no, minimal-ui">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/reveal/css/reveal.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/animate.css" />

<script>
			var link = document.createElement( 'link' );
			link.rel = 'stylesheet';
			link.type = 'text/css';
			link.href = window.location.search.match( /print-pdf/gi ) ? 'http://localhost:8080/student/css/print/pdf.css' : 'http://localhost:8080/student/css/print/paper.css';
			document.getElementsByTagName( 'head' )[0].appendChild( link );
		</script>


</head>

<body>
<%

String lesson_theme = request.getParameter("lesson_theme");

try {
int themeID = Integer.parseInt(lesson_theme);
if ((new UiThemeDAO()).findById(themeID) != null) {
%>
	<jsp:include page="/themes/mobile/yellow.jsp"></jsp:include>
<% 
	} 
} catch (Exception e){
	//nothing ToDo
}
%>
	<div class="reveal">

		<div class="slides">
			<%=((new CMSerializer()).serializeBlankSlide(templateName, slide_id, "mobile")) %>
		</div>

	</div>

	<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/reveal/js/reveal.js"></script>

	<script>

	<% if(!request.getParameterMap().containsKey("scale")) { %>

	
	Reveal.initialize({
			center : false,
			controls : false,
			width:900,
			height:1600,
			minScale: 0.29,
			maxScale: 0.29,
			transition: 'slide',
		});
		
		
		<% 
			} else { 
				float scale = Float.parseFloat(request.getParameter("scale").toString());
		%>

		
		Reveal.initialize({
				center : false,
				controls : false,
				width:900,
				height:1600,
				minScale: <%=scale%>,
				maxScale: <%=scale%>,
				transition: 'slide',
			});
			
		
		<%}   %>
	
		$(document).ready(function() {
			$('.slides').css('top','75%');
		})
		
		var orgBgColor = $("body").css("background-color");
		document.body.style.background = $('.present').css('background-color');
		if (($('.present').attr("style")).indexOf("background-color") < 0) {
			document.body.style.background = orgBgColor;
		}
		(document.getElementsByClassName('controls')[0]).style.display = 'none';

		Reveal.addEventListener('slidechanged', function(event) {
			document.body.style.background = $('.present').css(
					'background-color');
			if (($('.present').attr("style")).indexOf("background-color") < 0) {
				document.body.style.background = orgBgColor;
			}
			var currentURL = window.location.href; //currentURL+"#/"+ 
			var res = currentURL.split("#");
			currentURL = res[0] ///#1001
			console.log(currentURL + "#/" + event.currentSlide.id);
			history.pushState({}, "URL Rewrite Example", currentURL + "#"
					+ event.currentSlide.id);

		});
	</script>

		<!-- Everything below this point is only used for the reveal.js demo page -->
</body>
</html>
