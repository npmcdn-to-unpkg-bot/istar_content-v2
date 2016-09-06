<%@page import="com.istarindia.apps.dao.*"%>
<%@page import="com.istarindia.apps.dao.PresentaionDAO"%>
<%@page import="com.istarindia.apps.dao.Presentaion"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<!doctype html>

<% 
	String url = request.getRequestURL().toString();
	String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/";
	PresentaionDAO dao = new PresentaionDAO();
	int lessonID = Integer.parseInt(request.getParameter("ppt_id").replaceAll("/", ""));
	Presentaion ppt =  dao.findById(lessonID);
	String status = ppt.getLesson().getTask().getStatus();
	String lesson_theme = ppt.getLesson().getLesson_theme_desktop();
	String style_body = "background-size: cover;";
%>


<html lang="en">
<head>

<meta charset="utf-8">

<title>TALENTIFY</title>
<link rel="shortcut icon" href="<%=baseURL%>assets/img/talentify_logo_fav_48x48.png" />

<meta name="description" content="A framework for easily creating beautiful presentations using HTML">
<meta name="author" content="Hakim El Hattab">

<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">

<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no, minimal-ui">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"   href="<%=baseURL%>assets/plugins/bootstrap/css/bootstrap.min.css" >
<link rel="stylesheet"    href="<%=baseURL%>assets/plugins/reveal/css/reveal.css">
<link rel="stylesheet"    href="<%=baseURL%>assets/css/animate.css" />

<link rel="stylesheet"    href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/css/sky-forms.css">
<link rel="stylesheet"   href="<%=baseURL%>assets/plugins/sky-forms-pro/skyforms/custom/custom-sky-forms.css">
<link rel="stylesheet"   href="<%=baseURL%>assets/css/theme-colors/default.css" id="style_color">
<link rel="stylesheet"   href="<%=baseURL%>assets/css/theme-colors/orange.css" id="style_color">
<link rel="stylesheet"    href="<%=baseURL%>assets/css/style.css">

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
			link.href = window.location.search.match( /print-pdf/gi ) ? '<%=baseURL%>assets/plugins/reveal/css/print/pdf.css' : '<%=baseURL%>assets/plugins/reveal/css/print/paper.css';
			document.getElementsByTagName( 'head' )[0].appendChild( link );
		</script>

</head>

<body style="<%=style_body%>;background-color:  ">
	
	<div class="reveal">
		<div class="slides">
			<%=ppt.outputSlidesForDesktop().replaceAll("data-transition='fade'", "data-transition='slide'").
			replaceAll("data-transition='concave'", "data-transition='slide'").
			replaceAll("data-transition='convex'", "data-transition='slide'").
			replaceAll("data-transition='zoom'", "data-transition='slide'").
			replaceAll("data-background-transition='zoom'", "data-background-transition='slide'").
			replaceAll("data-background-transition='fade'", "data-background-transition='slide'").
			replaceAll("data-background-transition='concave'", "data-background-transition='slide'")%>
		</div>
	</div>
	<% 
	
	if(!request.getParameterMap().containsKey("demo")) {
	IstarUser user = (IstarUser) request.getSession().getAttribute("user");
    String displayName = "Welcome " + user.getName();
    SlideDAO sDao = new SlideDAO();
    
    if(user.getUserType().equalsIgnoreCase("CONTENT_REVIEWER")) { %>
	    <% if(!status.equalsIgnoreCase("PUBLISHED")) { %>
			<button class="btn" data-toggle="modal" data-target="#reviewCommentModal" style="zoom: 80%; padding: 0px; margin: -1%; position: absolute; top: 1%;  right: 1%;  z-index: 999; background: none;">
			<img src="http://i.stack.imgur.com/8BVKM.png"></button>
	    <%  } %>
		<button class="btn" onclick="Reveal.slide(0)" style="position: absolute; top: 1%;  left: 1%; z-index: 999">First Slide </button>
		<button class="btn" onclick="view_teacher_notes()" data-toggle="modal" data-target="#myModal" style="position: absolute; top: 1%;  left: 10%; z-index: 999">Speaker Notes</button>
	<% } else { %>
	    <% if(!status.equalsIgnoreCase("PUBLISHED")) { %>
			<button class="btn" onclick="add_edit()" data-toggle="modal" data-target="#reviewCommentModal" style="zoom: 80%; padding: 0px; margin: -1%; position: absolute; top: 1%;  right: 1%;  z-index: 999; background: none;">
			<img src="http://i.stack.imgur.com/8BVKM.png"></button>
	    <%  } %>
		<button class="btn" onclick="Reveal.slide(0)" style="position: absolute; top: 1%;  left: 1%; z-index: 999">First Slide </button>
		<button class="btn" onclick="view_teacher_notes()" data-toggle="modal" data-target="#myModal" style="position: absolute; top: 1%;  left: 10%; z-index: 999">Speaker Notes</button>
	<% } } %>

	<% if(!request.getParameterMap().containsKey("demo")) { 
			IstarUser user = (IstarUser) request.getSession().getAttribute("user");
			if(user.getUserType().equalsIgnoreCase("CONTENT_REVIEWER")) { %>
	<div class='modal fade' id='reviewCommentModal' tabindex='-1'  role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
					<h4 class="modal-title" id="myModalLabel4">Add comment</h4>
				</div>
				
				<div class="modal-body">
				<form class="sky-form" action="#" method="#">
					<fieldset>							
						<section>								
							<label class="label">Review Notes</label> 
							<label class="textarea"> 
								<textarea rows="3" name="review_notes" id="review-notes" placeholder=" Please enter text"></textarea>
							</label>
							<div class="note">
								<strong>Note:</strong> This is where we will put in the Review Notes.
							</div>
						</section>
					</fieldset>
				</form>
				</div>
				<div class="modal-footer">
					<section style="float: right">
						<button type="submit" id="submit-review" class="btn-u" >Submit</button> 
					</section>
				</div>
				
			</div>
		</div>
	</div>
	<% } } %>
	
	<div class='modal fade' id='notes' tabindex='-1'  role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
					<h4 class="modal-title" id="myModalLabel4">Teacher notes</h4>
				</div>
				
				<div class="modal-body" id="show-notes" style="font-family: 'Roboto', Arial, sans-serif;"> </div>
				
				<div class="modal-footer">
					<section style="float: right">
						<button type="button" id="close-notes" data-dismiss="modal" class=" btn-u" >Return</button> 
					</section>
				</div>
				
			</div>
		</div>
	</div>
	
	
	<script type="text/javascript"  integrity="" src="<%=baseURL%>assets/plugins/jquery/jquery.min.js"></script>
	<script type="text/javascript"  integrity="" src="<%=baseURL%>assets/plugins/bootstrap/js/bootstrap.js"></script>
	<script src="<%=baseURL%>assets/plugins/reveal/lib/js/head.min.js"></script>
	<script src="<%=baseURL%>assets/plugins/reveal/js/reveal.js"></script>
	<script src="<%=baseURL%>assets/plugins/reveal/plugin/zoom-js/zoom.js"></script>
	<script type="text/javascript"  integrity="" src="<%=baseURL%>assets/plugins/typed-js/js/typed.js"></script>
	<script type="text/javascript"  integrity="" src="//cdn.tinymce.com/4/tinymce.min.js"></script>
	<script type="text/javascript"  integrity="" src="https://npmcdn.com/masonry-layout@4.0.0/dist/masonry.pkgd.min.js"></script>
	
	<script>
		var window_size = $(window).width();
		var window_height = $(window).height();
		var orgBgColor = '#ffffff';			
		
		$(document).ready(function(){
			orgBgColor = '<%=(new UiThemeDAO()).findById(themeID).getBackgroundColor()%>';
			updateSlideBgColor();
			
			document.querySelector( '.slides' ).addEventListener( 'click', function( event ) {
				event.preventDefault();
				zoom.to({ element: event.target });
			} );
		});

		Reveal.initialize({
			center : false,
			controls : true, 
			showNotes: false,
		    slideNumber: 'c/t',
			height: window_height,
			width: window_size,
			transition: 'slide', 
			minScale: (0.97), 
			maxScale: (0.97), 
			
		});

		function updateSlideBgColor() {
			if ($('.present').data("bgcolor") == "none") {
				document.body.style.background = orgBgColor;
			} else {
				document.body.style.background = $('.present').data("bgcolor");
			}
		}
				
		Reveal.addEventListener('slidechanged', function(event) {
			$('.present').find('.show-all').each(function(index, value) {
				$(this).removeClass("show-all");
			});
			
			updateSlideBgColor();
			
			var currentURL = window.location.href; 
			var res = currentURL.split("#");
			currentURL = res[0];
			history.pushState({}, "URL Rewrite Example", currentURL + "#" + event.currentSlide.id);

			$('.slide-number-a').text('event.currentSlide.id');
		});

		function add_edit() {
			var currentURL = window.location.href;
			var slideID = $('.present').attr('id');
			var url = '/content/fill_template.jsp?ppt_id=<%=request.getParameter("ppt_id")%>&slide_id='+slideID;
			var win = window.open(url, '_blank');
			win.focus();
		}
		
		$( "#submit-review" ).click(function() {
			var slide_id = $('.present').attr('id');;
			var ppt_id = <%=request.getParameter("ppt_id")%>;
			var review_notes = $('#review-notes').val();
			
			$.ajax({
				  type: "POST", url: '/content/review_lesson',
				  data: 'is_edit=true&from=review_slide&ppt_id='+ppt_id+'&slide_id='+slide_id+'&review_notes='+review_notes,
			});
			
			$('#review-notes').val("");
			$('#reviewCommentModal').modal('hide');
		});

		function view_teacher_notes() {
			var notes = "Not Available";
			
			try {
				notes = $('.present').data("notes");
			} catch (err) {
				console.log(err);
			}

			$('#show-notes').html(notes);
			$('#notes').modal('show');
		}

		Reveal.addEventListener('fragmentshown', function(event) {			
			if ($(event.fragment).attr('id')=="737373") {
				$('.present').find('.fragment').each(function(index, value) {
					$(this).addClass("current-fragment show-all");
				});
				$(event.fragment).removeClass("current-fragment");
			}
			
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
