<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@page import="com.istarindia.apps.dao.*"%>
<%@page import="com.istarindia.apps.dao.PresentaionDAO"%>
<%@page import="com.istarindia.apps.dao.Presentaion"%>
	
	<% 
	PresentaionDAO dao = new PresentaionDAO();
	int lessonID = Integer.parseInt(request.getParameter("ppt_id").replaceAll("/", ""));
	Presentaion ppt =  dao.findById(lessonID);
	String lesson_theme = ppt.getLesson().getLesson_theme();
	UiTheme theme = new UiThemeDAO().findByName(lesson_theme).get(0);
	%>
<style>
@import
	url(https://fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700,700italic,900,900italic)
	;

body {
	background: #FDC530 !important;
}

.reveal  .slide {
	background-color: <%=theme.getBackground_color()%> ; /* #FDC530 !important; */
}

.reveal  .ONLY_TITLE h2 {
	margin-top: -37%;
	padding-left: 2%;
	color: <%=theme.getTitleColor()%>;
	font-size: <%=theme.getTitleSize()%>px;
	line-height: <%=theme.getTitleLineHeight()%> ;
	text-align:  <%=theme.getTitleTextAlignment()%> ;
}

.reveal  .ONLY_2TITLE h2 {
	margin-top: -26%;
	padding-left: 10%;
	color: <%=theme.getTitleColor()%>;
	font-weight: <%=theme.getTitleFontWeight()%>;
	font-size: <%=theme.getTitleSize()%>px;
	line-height: <%=theme.getTitleLineHeight()%> ;
	text-align:  <%=theme.getTitleTextAlignment()%> ;
}

.reveal  .ONLY_2TITLE h3 {
	padding-left: 10%;
	color: <%=theme.getSubtitleColor()%>;
	font-weight: <%=theme.getSubtitleFontWeight()%>;
	font-size: <%=theme.getSubtitleSize()%>px;
	line-height: <%=theme.getSubtitleColor()%> ;
	text-align:  <%=theme.getSubtitleTextAlignment()%> ;
}

.reveal  .ONLY_2TITLE_IMAGE h2 {
	margin-top: -26%;
	padding-left: 5%;
	color: <%=theme.getTitleColor()%>;
	font-weight: <%=theme.getTitleFontWeight()%>;
	font-size: <%=theme.getTitleSize()%>px;
	line-height: <%=theme.getTitleLineHeight()%> ;
	text-align:  <%=theme.getTitleTextAlignment()%> ;
}

.reveal  .ONLY_2TITLE_IMAGE h3 {
	padding-left: 5%;
	padding-right: 5%;
	color: <%=theme.getSubtitleColor()%>;
	font-weight: <%=theme.getSubtitleFontWeight()%>;
	font-size: <%=theme.getSubtitleSize()%>px;
	line-height: <%=theme.getSubtitleColor()%> ;
	text-align:  <%=theme.getSubtitleTextAlignment()%> ;
}

.reveal .ONLY_PARAGRAPH  .paragraph {
	margin-top: -26%;
	padding-left: 10%;
	color: <%=theme.getParagraphColor()%>;
	font-weight: <%=theme.getParagraphFontWeight()%>;
	font-size: <%=theme.getParagraphSize()%>px;
	line-height: <%=theme.getParagraphLineHeight()%> ;
	text-align:  <%=theme.getParagraphTextAlignment()%> ;
}

.reveal .ONLY_PARAGRAPH  .p {
	margin-top: -26%;
	padding-left: 10%;
	color: <%=theme.getParagraphColor()%>;
	font-weight: <%=theme.getParagraphFontWeight()%>;
	font-size: <%=theme.getParagraphSize()%>px;
	line-height: <%=theme.getParagraphLineHeight()%> ;
	text-align:  <%=theme.getParagraphTextAlignment()%> ;
}

.reveal .ONLY_PARAGRAPH_IMAGE  .paragraph {
	padding-left: 10px;
	color: <%=theme.getParagraphColor()%>;
	font-weight: <%=theme.getParagraphFontWeight()%>;
	font-size: <%=theme.getParagraphSize()%>px;
	line-height: <%=theme.getParagraphLineHeight()%> ;
	text-align:  <%=theme.getParagraphTextAlignment()%> ;
}

.reveal .ONLY_PARAGRAPH_IMAGE  .p {
	padding-left: 10px;
	color: <%=theme.getParagraphColor()%>;
	font-weight: <%=theme.getParagraphFontWeight()%>;
	font-size: <%=theme.getParagraphSize()%>px;
	line-height: <%=theme.getParagraphLineHeight()%> ;
	text-align:  <%=theme.getParagraphTextAlignment()%> ;
}

.reveal .ONLY_TITLE_PARAGRPH_IMAGE .paragraph p{
	position: fixed;
	margin-top: -10%;
	padding-left: -5px;
	padding-right: 7px;
	padding-top: 81px;
	z-index: 9999;
	color: <%=theme.getParagraphColor()%>;
	font-weight: <%=theme.getParagraphFontWeight()%>;
	font-size: <%=theme.getParagraphSize()%>px;
	line-height: <%=theme.getParagraphLineHeight()%> ;
	text-align:  <%=theme.getParagraphTextAlignment()%> ;
}

.reveal .ONLY_TITLE_PARAGRPH_IMAGE h2 {
	margin-top: -38%;
	position: fixed;
	text-align: center;
	width: 100%;
	color: <%=theme.getTitleColor()%>;
	font-weight: <%=theme.getTitleFontWeight()%>;
	font-size: <%=theme.getTitleSize()%>px;
	line-height: <%=theme.getTitleLineHeight()%> ;
	text-align:  <%=theme.getTitleTextAlignment()%> ;
}

.reveal .ONLY_TITLE_PARAGRAPH  h2 {
	margin-top: -37%;
	padding-left: 2%;
	color: <%=theme.getTitleColor()%>;
	font-weight: <%=theme.getTitleFontWeight()%>;
	font-size: <%=theme.getTitleSize()%>px;
	line-height: <%=theme.getTitleLineHeight()%> ;
	text-align:  <%=theme.getTitleTextAlignment()%> ;
}

.reveal .ONLY_TITLE_PARAGRAPH  .paragraph p{
	padding-left: 40px;
	list-style: none;
	margin-top: 10%;
	color: <%=theme.getParagraphColor()%>;
	font-weight: <%=theme.getParagraphFontWeight()%>;
	font-size: <%=theme.getParagraphSize()%>px;
	line-height: <%=theme.getParagraphLineHeight()%> ;
	text-align:  <%=theme.getParagraphTextAlignment()%> ;
}

.reveal .ONLY_TITLE_IMAGE h2 {
	min-height: 799px;
	position: fixed;
	top: -486px;
	padding: 40px;
	color: <%=theme.getTitleColor()%>;
	font-weight: <%=theme.getTitleFontWeight()%>;
	font-size: <%=theme.getTitleSize()%>px;
	line-height: <%=theme.getTitleLineHeight()%> ;
	text-align:  <%=theme.getTitleTextAlignment()%> ;
}

.ONLY_2TITLE_TREE h1 {
	margin-top: -26%;
	padding-left: 10%;
	color: <%=theme.getTitleColor()%>;
	font-weight: <%=theme.getTitleFontWeight()%>;
	font-size: <%=theme.getTitleSize()%>px;
	line-height: <%=theme.getTitleLineHeight()%> ;
	text-align:  <%=theme.getTitleTextAlignment()%> ;
}

.ONLY_2TITLE_TREE h2 {
	text-align: left;
	padding-left: 10%;
	color: #7E4B0E;
	font-weight: 400;
	margin-top: 13%;
	position: absolute;
}

.ONLY_2TITLE_TREE h3 {
	text-align: left;
	padding-left: 10%;
	color: #FDC530;
	font-weight: 400;
	position: absolute;
	margin-top: 49%;
}

.ONLY_2TITLE_TREE h4 {
	text-align: left;
	padding-left: 10%;
	color: #FDC530;
	font-weight: 400;
	position: absolute;
	margin-top: 75%;
}

.ONLY_2TITLE_TREE h5 {
	text-align: left;
	padding-left: 10%;
	color: #FDC530;
	font-weight: 400;
	position: absolute;
	margin-top: 49%;
	margin-left: 55%;
}

.ONLY_2TITLE_TREE h6 {
	text-align: left;
	padding-left: 10%;
	color: #FDC530;
	font-weight: 400;
	position: absolute;
	margin-top: 104%;
}

.reveal .ONLY_TITLE_LIST #data_slide_title {
	padding-left: 42px;
	margin-top: -35% !important;
	color: <%=theme.getTitleColor()%>;
	font-weight: <%=theme.getTitleFontWeight()%>;
	font-size: <%=theme.getTitleSize()%>px;
	line-height: <%=theme.getTitleLineHeight()%> ;
	text-align:  <%=theme.getTitleTextAlignment()%> ;
}

.reveal .ONLY_TITLE_LIST ol li ul{
	padding-bottom: 56px;
	padding-left: 29px;
	padding-right: 52px;
	/* list-style-image: url('/themes/mobile/bullet_line.png'); */
	margin-left: 82px;
	list-style-type: decimal-leading-zero;
	color: <%=theme.getListitemColor()%>;
	font-weight: <%=theme.getListitemFontWeight()%>;
	font-size: <%=theme.getListitemSize()%>px;
	line-height: <%=theme.getListitemLineHeight()%> ;
	text-align:  <%=theme.getListitemTextAlignment()%> ;
}

.reveal .ONLY_PARAGRAPH_IMAGE img {
	width: 184%;
}

.reveal .ONLY_TITLE_IMAGE img {
	width: 122%;
	margin-left: -10%;
}

.reveal .ONLY_TITLE_PARAGRPH_IMAGE img {
	width: 118%;
	margin-left: -5.5%;
}

.reveal .ONLY_TITLE_IMAGE .holder .holder1 {
	/*     border: 1px solid red; */
	margin-top: 20% !important;
}

</style>