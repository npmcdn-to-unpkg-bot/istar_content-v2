<%@page import="com.istarindia.apps.dao.*"%>
<%@page import="com.istarindia.apps.dao.PresentaionDAO"%>
<%@page import="com.istarindia.apps.dao.Presentaion"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><!doctype html>
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

<link rel="stylesheet" href="<%=baseURL%>assets/plugins/reveal/css/reveal.css">
<link rel="stylesheet" href="<%=baseURL%>assets/css/animate.css" />
<!-- Include the yellow.jsp for styling only if everything is good for inclusion.
lesson may not have theme saved
lesson_theme may have a string in place
lesson_theme may have a theme_id which doesnt have an entry in ui_theme table
 -->
<%
int themeID =43;
try {
themeID = Integer.parseInt(lesson_theme);
if ((new UiThemeDAO()).findById(themeID) != null) {
%>
	<jsp:include page="/themes/mobile/yellow.jsp"></jsp:include>
<% 
	} 
} catch (Exception e){
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
		<%=ppt.outputSlides() %>
		</div>

	</div>
<script type="text/javascript"
		src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/reveal/js/reveal.js"></script>
	<script src="<%=baseURL%>assets/plugins/reveal/plugin/zoom-js/zoom.js"></script>

	<script>
		Reveal.initialize({
			center : false,
			controls : false,
		    slideNumber:  'c/t'

		});

		var orgBgColor = '#ffffff';			
		
		$(document).ready(function(){
			orgBgColor = '<%=(new UiThemeDAO()).findById(themeID).getBackgroundColor()%>';
			updateSlideBgColor();
			
			document.querySelector( '.slides' ).addEventListener( 'click', function( event ) {
				event.preventDefault();
				zoom.to({ element: event.target });
			} );
		});


		function updateSlideBgColor() {
			if ($('.present').data("bgcolor") == "none") {
				document.body.style.background = orgBgColor;
			} else {
				document.body.style.background = $('.present').data("bgcolor");
			}
		}
				


		(document.getElementsByClassName('controls')[0]).style.display = 'none';

		Reveal.addEventListener('slidechanged', function(event) {
			updateSlideBgColor();
			var currentURL = window.location.href; //currentURL+"#/"+ 
			var res = currentURL.split("#");
			currentURL = res[0] ///#1001
			console.log(currentURL + "#/" + event.currentSlide.id);
			history.pushState({}, "URL Rewrite Example", currentURL + "#"
					+ event.currentSlide.id);
			
			///console.log(event.currentSlide.id);
			$('.slide-number-a').text('event.currentSlide.id');

		});
		

		Reveal.addEventListener( 'ready', function( event ) {
		    try{
		    	var slide_id = window.location.href.split("#")[1];
				if(slide_id > 0) {
				    var slide_number = 0;
					var temp = -1;
				    
					$( ".slide" ).each(function( index ) {
						if($( this ).attr("id") == slide_id) {
							slide_number = temp;
						  } else {
							  temp = temp + 1;
						  }
					});
					
					if(slide_number >= 0) {
						Reveal.slide(slide_number,0, 0);
						updateSlideBgColor();
					}
				} else {
					Reveal.slide(0);
					updateSlideBgColor();
				}
		    } catch(err) {
		    	console.log(err);
		    }
			
		} );
		
	</script>

</body>
</html>
