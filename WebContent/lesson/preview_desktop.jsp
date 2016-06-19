<%@page import="com.istarindia.apps.dao.*"%>
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

String nuetral = url.substring(0, url.length() - request.getRequestURI().length()) +"/";

String style_body = "background-size: cover;";

%>


<html lang="en">
<head>

<meta charset="utf-8">

<title>TALENTIFY</title>

<meta name="description" content="A framework for easily creating beautiful presentations using HTML">
<meta name="author" content="Hakim El Hattab">

<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">

<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no, minimal-ui">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
<link rel="stylesheet" href="http://lab.hakim.se/reveal-js/css/reveal.css">

<!-- Include the yellow.jsp for styling only if everything is good for inclusion.
lesson may not have theme saved
lesson_theme may have a string in place
lesson_theme may have a theme_id which doesnt have an entry in ui_theme table
 -->

<%
int themeID = 100;
try {
		themeID = Integer.parseInt(lesson_theme);
		if ((new UiThemeDAO()).findById(themeID) != null) {
%>
	<jsp:include page="/themes/desktop/desktop_yellow.jsp"></jsp:include>
<%
	}
	} catch (Exception e) {
		//nothing ToDo
	}
%>
<!-- Code syntax highlighting -->
<script>
			var link = document.createElement( 'link' );
			link.rel = 'stylesheet';
			link.type = 'text/css';
			link.href = window.location.search.match( /print-pdf/gi ) ? '<%=nuetral%>student/css/print/pdf.css' : '<%=nuetral%>student/css/print/paper.css';
			document.getElementsByTagName( 'head' )[0].appendChild( link );
		</script>

</head>
<body style="<%=style_body%>">
	<div class="reveal">
		<div class="slides">
		<%=ppt.outputSlidesForDesktop() %>
		</div>

	</div>
<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
	<script src="<%=nuetral%>student/lib/js/head.min.js"></script>
	<script src="http://lab.hakim.se/reveal-js/js/reveal.js"></script>
		<script src="http://lab.hakim.se/zoom-js/js/zoom.js"></script>

	<script>
		var orginalsize = <%=(new UiThemeDAO()).findById(themeID).getListitemFontSize()%> ;

		var wi = $(window).width();
		Reveal.initialize({
			center : true,
			controls : true,
			width : wi * 1.05
		});

		var orgBgColor = $("body").css("background-color");
		document.body.style.background = $('.present').css('background-color');
		if (($('.present').attr("style")).indexOf("background-color") < 0) {
			document.body.style.background = orgBgColor;
		}

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

		Reveal.addEventListener('fragmentshown', function(event) {
			var new1 = orginalsize;
			$('.fragment').each(function(index, value) {
				try {
					if ($(this).attr('id').indexOf("-") != -1) {
						$('#' + $(this).attr('id')).css({
							'font-size' : orginalsize + 'px'
						});
					}
				} catch (errr) {
					//Console.log($(this).attr('id'));
				}
			});
			$('#' + event.fragment.id).css({
				'font-size' : new1 * 1.5 + 'px'
			});
		});
		
	</script>

</body>
</html>
