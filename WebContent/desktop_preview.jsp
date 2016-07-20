<%@page import="com.istarindia.apps.dao.*"%>
<%@page import="com.istarindia.apps.dao.Presentaion"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.istarindia.cms.lessons.*"%>
<%@ page import="javax.xml.bind.*"%><%@ page import="java.io.*"%>
<%
	String url = request.getRequestURL().toString();
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length())
			+ request.getContextPath() + "/";
	String templateName = request.getParameter("template_name");
	String slide_id = request.getParameter("slide_id");

	String nuetral = url.substring(0, url.length() - request.getRequestURI().length()) + "/";

	String style_body = "background-size: cover;";
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
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/bootstrap/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

<link rel="stylesheet" href="<%=baseURL%>assets/plugins/reveal/css/reveal.css">

</head>
<%
	int themeID = 100;
	try {
		themeID = Integer.parseInt(request.getParameter("lesson_theme"));
		if ((new UiThemeDAO()).findById(themeID) != null) {
%>
<jsp:include page="/themes/desktop/desktop_yellow.jsp"></jsp:include>
<%
	}
	} catch (Exception e) {
		//nothing ToDo
	}
%>
<body>

	<div class="reveal">
		<div class="slides">
			<%=((new CMSerializer()).serializeBlankSlide(templateName, slide_id, "desktop"))%>
		</div>
	</div>

	<script src="<%=baseURL%>assets/plugins/reveal/lib/js/head.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/reveal/js/reveal.js"></script>

	<script>

			// Full list of configuration options available at:
			// https://github.com/hakimel/reveal.js#configuration
			Reveal.initialize({
				controls: true,
				progress: false,
				history: true,
				width:1024,
				height:768,
				minScale: 1,
				maxScale: 1,
				transition: 'slide', 

				// Optional reveal.js plugins
				dependencies: [
					{ src: '<%=baseURL%>assets/plugins/reveal/lib/js/classList.js', condition: function() { return !document.body.classList; } },
					{ src: '<%=baseURL%>assets/plugins/reveal/plugin/markdown/marked.js', condition: function() { return !!document.querySelector( '[data-markdown]' ); } },
					{ src: '<%=baseURL%>assets/plugins/reveal/plugin/markdown/markdown.js', condition: function() { return !!document.querySelector( '[data-markdown]' ); } },
					{ src: '<%=baseURL%>assets/plugins/reveal/plugin/highlight/highlight.js', async: true, callback: function() { hljs.initHighlightingOnLoad(); } },
					{ src: '<%=baseURL%>assets/plugins/reveal/plugin/zoom-js/zoom.js', async: true },
					{ src: '<%=baseURL%>assets/plugins/reveal/plugin/notes/notes.js', async : true } ]
				});
			

			
	</script>

	<!-- Everything below this point is only used for the reveal.js demo page -->
</body>
</html>
