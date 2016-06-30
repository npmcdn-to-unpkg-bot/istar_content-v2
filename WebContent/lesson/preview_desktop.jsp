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
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/bootstrap/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
<link rel="stylesheet" href="<%=baseURL%>assets/plugins/reveal/css/reveal.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/animate.css" />

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

<body style="<%=style_body%>;background-color:  ">
	
	<div class="reveal">
		<div class="slides">
			<%=ppt.outputSlidesForDesktop() %>
		</div>
	</div>

	<script type="text/javascript" src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
	<script src="<%=nuetral%>student/lib/js/head.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/reveal/js/reveal.js"></script>
	<script src="<%=baseURL%>assets/plugins/reveal/plugin/zoom-js/zoom.js"></script>
	<script type="text/javascript" src="<%=baseURL%>assets/plugins/typed-js/js/typed.js"></script>

	<script>
	
		var orginal_listitem_font_size = <%=(new UiThemeDAO()).findById(themeID).getListitemFontSize()%> ;
		var window_size = $(window).width();
		
		Reveal.initialize({
			center : true,
			controls : true,
			width: window_size 
		});
		
		//Save background colour from the theme to a global variable
		var orgBgColor = '<%=(new UiThemeDAO()).findById(themeID).getBackgroundColor()%>';			
		//console.log("background colour from theme: "+orgBgColor);
		
		//Set first slide's bg color to the body
		document.body.style.background = $('.present').data("bgcolor");								
		if ($('.present').data("bgcolor") == "none") {
			//console.log("first silde doesnt have bg color");
			//if the first slide doesn't have bg color set; then reset the body color to the original from theme
			document.body.style.background = orgBgColor;
			//console.log("#95: body-bgcolor: "+document.body.style.background);
		}

		Reveal.addEventListener('slidechanged', function(event) {
			//Set present slide's bg color to the body
			document.body.style.background = $('.present').data("bgcolor");
			
			//console.log('#111 slide bg color: '+ $('.present').data("bgcolor") );
			//console.log('#112 body bg color: '+ document.body.style.background );
			if ($('.present').data("bgcolor") == "none") {
				
				//if the present slide doesn't have bg color set; then reset the body color to the original from theme
				document.body.style.background = orgBgColor;
				//console.log('#115 body bg color(to be theme) ->'+ document.body.style.background );
			}

			var currentURL = window.location.href; //currentURL+"#/"+ 
			var res = currentURL.split("#");
			currentURL = res[0] ///#1001
			console.log(currentURL + "#/" + event.currentSlide.id);
			history.pushState({}, "URL Rewrite Example", currentURL + "#"
					+ event.currentSlide.id);

			//To make the title animated as typing - 
			
			//var slideID = $('.present').attr('id');
			//console.log(" typing " + slideID);
			//$('#'+slideID + " #data_slide_title").addClass( "animated infinite bounce" );// css('class','css-typing');

		});

		Reveal.addEventListener('fragmentshown', function(event) {
			//var temp = orginal_listitem_font_size;

			$('.fragment').each(function(index, value) {
				try {

					if ($(this).attr('id').indexOf("-") != -1) {
						$('#' + $(this).attr('id')).css({
							'font-size' : orginal_listitem_font_size + 'px'
						});
					}
				} catch (errr) {
					//Console.log($(this).attr('id'));
				}
			});
			
			$('#' + event.fragment.id).css({
				'font-size' : orginal_listitem_font_size * 1.5 + 'px'
			});
		});
		
	</script>

</body>
</html>
