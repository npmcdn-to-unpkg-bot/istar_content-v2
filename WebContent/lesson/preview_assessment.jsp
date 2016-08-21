	<%@page import="com.istarindia.apps.dao.AssessmentDAO"%>
<%@page import="com.istarindia.apps.dao.Assessment"%>
<%@page import="com.istarindia.apps.dao.PresentaionDAO"%>
<%@page import="com.istarindia.apps.dao.Presentaion"%>
<%@page import="com.istarindia.apps.dao.UiThemeDAO"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.istarindia.cms.lessons.*"%>
<%@ page import="javax.xml.bind.*"%><%@ page import="java.io.*"%>
<% String url = request.getRequestURL().toString();
String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
Assessment assessment = /* (new AssessmentDAO()).findById(1);//  */  (new AssessmentDAO()).findById(Integer.parseInt(request.getParameter("assessment_id")));
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

<link rel="stylesheet" href="<%=baseURL %>assets/plugins/reveal/css/reveal.css">

<!-- Code syntax highlighting -->
<link rel="stylesheet" href="<%=baseURL %>assets/plugins/reveal/lib/css/zenburn.css">


<%
try {
	String lesson_theme = assessment.getLesson().getLesson_theme();

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
<!-- Printing and PDF exports -->
<script>
			var link = document.createElement( 'link' );
			link.rel = 'stylesheet';
			link.type = 'text/css';
			link.href = window.location.search.match( /print-pdf/gi ) ? '<%=baseURL %>assets/plugins/reveal/css/print/pdf.css' : '<%=baseURL %>assets/plugins/reveal/css/print/paper.css';
			document.getElementsByTagName( 'head' )[0].appendChild( link );
		</script>

<!--[if lt IE 9]>
		<script src="lib/js/html5shiv.js"></script>
		<![endif]-->
</head>

<body>

	<div class="reveal">

		<div class="slides">
			<%=((new CMSerializer()).serializeLesson(assessment)) %>

		</div>

		<script src="<%=baseURL %>assets/plugins/reveal/lib/js/head.min.js"></script>
		<script src="<%=baseURL %>assets/plugins/reveal/js/reveal.js"></script>

		<script>

			// Full list of configuration options available at:
			// https://github.com/hakimel/reveal.js#configuration
			Reveal.initialize({
				controls: true,
				progress: true,
				history: false,
				center: false,

				transition: 'none', // none/fade/slide/convex/concave/zoom

				// Optional reveal.js plugins
				dependencies: [
					{ src: '<%=baseURL %>assets/plugins/reveal/lib/js/classList.js', condition: function() { return !document.body.classList; } },
					{ src: '<%=baseURL %>assets/plugins/reveal/plugin/markdown/marked.js', condition: function() { return !!document.querySelector( '[data-markdown]' ); } },
					{ src: '<%=baseURL %>assets/plugins/reveal/plugin/markdown/markdown.js', condition: function() { return !!document.querySelector( '[data-markdown]' ); } },
					{ src: '<%=baseURL %>assets/plugins/reveal/plugin/highlight/highlight.js', async: true, callback: function() { hljs.initHighlightingOnLoad(); } },
					{ src: '<%=baseURL %>assets/plugins/reveal/plugin/zoom-js/zoom.js', async: true },
					{ src: '<%=baseURL %>assets/plugins/reveal/plugin/notes/notes.js', async: true }
				]
			});
			
			 document.onreadystatechange = function () {
			     if (document.readyState == "complete") {
			    	 console.log("Ready");
			    	 try { initSlide(); } catch (err) {
						// TODO: handle exception
					}
			   }
			 }
			
			
			

		</script>
</div>
		<!-- Everything below this point is only used for the reveal.js demo page -->
</body>
</html>
