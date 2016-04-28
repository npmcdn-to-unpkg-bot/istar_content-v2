<%@page import="com.istarindia.apps.dao.PresentaionDAO"%>
<%@page import="com.istarindia.apps.dao.Presentaion"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.istarindia.cms.lessons.*"%>
<%@ page import="javax.xml.bind.*"%><%@ page import="java.io.*"%>
<% String url = request.getRequestURL().toString();
String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
String templateName = request.getParameter("template_name");
String slide_id = request.getParameter("slide_id");

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

<link rel="stylesheet" href="/student/css/reveal.css">
<link rel="stylesheet" href="/themes/mobile.css"  type="text/css" /><!-- Printing and PDF exports -->
<link rel="stylesheet" href="/themes/mobile/<%=request.getParameter("lesson_theme").toLowerCase()%>.css" id="theme">

<!-- Code syntax highlighting -->
<script>
			var link = document.createElement( 'link' );
			link.rel = 'stylesheet';
			link.type = 'text/css';
			link.href = window.location.search.match( /print-pdf/gi ) ? 'student/css/print/pdf.css' : 'student/css/print/paper.css';
			document.getElementsByTagName( 'head' )[0].appendChild( link );
		</script>

</head>

<body>

	<div class="reveal">

		<div class="slides">
			<%=((new CMSerializer()).serializeBlankSlide(templateName, slide_id)) %>

		</div>

		<script src="student/lib/js/head.min.js"></script>
		<script src="student/js/reveal.js"></script>

		<script>

			// Full list of configuration options available at:
			// https://github.com/hakimel/reveal.js#configuration
			Reveal.initialize({
				controls: true,
				progress: true,
				history: true,
				center: false,

				transition: 'slide', // none/fade/slide/convex/concave/zoom

				// Optional reveal.js plugins
				dependencies: [
					{ src: 'student/lib/js/classList.js', condition: function() { return !document.body.classList; } },
					{ src: 'student/plugin/markdown/marked.js', condition: function() { return !!document.querySelector( '[data-markdown]' ); } },
					{ src: 'student/plugin/highlight/highlight.js', async: true, callback: function() { hljs.initHighlightingOnLoad(); } },
					{ src: 'student/plugin/zoom-js/zoom.js', async: true },
					{ src: 'student/plugin/notes/notes.js', async: true }
				]
			
			
			});

		</script>

		<!-- Everything below this point is only used for the reveal.js demo page -->
</body>
</html>
