<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@page import="com.istarindia.apps.dao.*"%>
<%@page import="com.istarindia.apps.dao.PresentaionDAO"%>
<%@page import="com.istarindia.apps.dao.*"%>
	
	<% 
	PresentaionDAO dao = new PresentaionDAO();
	int lessonID = Integer.parseInt(request.getParameter("ppt_id").replaceAll("/", ""));
	Presentaion ppt =  dao.findById(lessonID);
	String lesson_theme = ppt.getLesson().getLesson_theme();
	UiTheme theme = new UiThemeDAO().findByName(lesson_theme).get(0);
	%>
<style>

body {
	background: #FDC530 !important;
}

@font-face {
    font-family: 'Alice';
    src: url('/themes/fonts/Alice-Regular.ttf');
}

@font-face {
    font-family: 'Asap-Bold';
    src: url('/themes/fonts/Asap-Bold.ttf');
}

@font-face {
    font-family: 'Asap-BoldItalic';
    src: url('/themes/fonts/Asap-BoldItalic.ttf');
}


.reveal  .slide {
	background-color: <%=theme.getBackgroundColor()%>;
	/* #FDC530 !important; */
}

.reveal  .ONLY_TITLE h2 {
	margin-top: -37%;
	padding-left: 2%;
	color: <%=theme.getTitleFontColor()%>;
	font-size: <%=theme.getTitleFontSize()%>px;
	line-height: <%=theme.getTitleLineHeight()%>;
	text-align: <%=theme.getTitleTextAlignment()%>;
	font-weight: <%=theme.getTitleFontWeight()%>;
	font-family: '<%=theme.getTitleFontFamily().replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;;
}

.reveal  .ONLY_2TITLE h2 {
	margin-top: -26%;
	padding-left: 10%;
	color: <%=theme.getTitleFontColor()%>;
	font-weight: <%=theme.getTitleFontWeight()%>;
	font-size: <%=theme.getTitleFontSize()%>px;
	line-height: <%=theme.getTitleLineHeight()%>;
	text-align: <%=theme.getTitleTextAlignment()%>;
	font-family:: <%=theme.getTitleFontFamily().replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal  .ONLY_2TITLE h3 {
	padding-left: 10%;
	color: <%=theme.getSubtitleFontColor()%>;
	font-weight: <%=theme.getSubtitleFontWeight()%>;
	font-size: <%=theme.getSubtitleFontSize()%>px;
	line-height: <%=theme.getSubtitleLineHeight()%>;
	text-align: <%=theme.getSubtitleTextAlignment()%>;
	font-family: <%=theme.getTitleFontFamily().replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal  .ONLY_2TITLE_IMAGE h2 {
	margin-top: -26%;
	padding-left: 5%;
	color: <%=theme.getTitleFontColor()%>;
	font-weight: <%=theme.getTitleFontWeight()%>;
	font-size: <%=theme.getTitleFontSize()%>px;
	line-height: <%=theme.getTitleLineHeight()%>;
	text-align: <%=theme.getTitleTextAlignment()%>;
	font-family:<%=theme.getTitleFontFamily().replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal  .ONLY_2TITLE_IMAGE h3 {
	padding-left: 5%;
	padding-right: 5%;
	color: <%=theme.getSubtitleFontColor()%>;
	font-weight: <%=theme.getSubtitleFontWeight()%>;
	font-size: <%=theme.getSubtitleFontSize()%>px;
	line-height: <%=theme.getSubtitleFontColor()%>;
	text-align: <%=theme.getSubtitleTextAlignment()%>;
	font-family: <%=theme.getTitleFontFamily().replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .ONLY_PARAGRAPH  .paragraph p {
	margin-top: -26%;
	padding-left: 10%;
	color: <%=theme.getParagraphFontColor()%>;
	font-weight: <%=theme.getParagraphFontWeight()%>;
	font-size: <%=theme.getParagraphFontSize()%>px;
	line-height: <%=theme.getParagraphLineHeight()%>;
	text-align: <%=theme.getParagraphTextAlignment()%>;
	font-family: paragraphFont;
}

.reveal .ONLY_PARAGRAPH_IMAGE  .paragraph p {
	padding-left: 10px;
	color: <%=theme.getParagraphFontColor()%>;
	font-weight: <%=theme.getParagraphFontWeight()%>;
	font-size: <%=theme.getParagraphFontSize()%>px;
	line-height: <%=theme.getParagraphLineHeight()%>;
	text-align: <%=theme.getParagraphTextAlignment()%>;
	font-family: paragraphFont;
}

.reveal .ONLY_TITLE_PARAGRPH_IMAGE .paragraph p {
	position: fixed;
	margin-top: -10%;
	padding-left: -5px;
	padding-right: 7px;
	padding-top: 81px;
	z-index: 9999;
	color: <%=theme.getParagraphFontColor()%>;
	font-weight: <%=theme.getParagraphFontWeight()%>;
	font-size: <%=theme.getParagraphFontSize()%>px;
	line-height: <%=theme.getParagraphLineHeight()%>;
	text-align: <%=theme.getParagraphTextAlignment()%>;
	font-family: paragraphFont;
}

.reveal .ONLY_TITLE_PARAGRPH_IMAGE h2 {
	margin-top: -38%;
	position: fixed;
	text-align: center;
	width: 100%;
	color: <%=theme.getTitleFontColor()%>;
	font-weight: <%=theme.getTitleFontWeight()%>;
	font-size: <%=theme.getTitleFontSize()%>px;
	line-height: <%=theme.getTitleLineHeight()%>;
	text-align: <%=theme.getTitleTextAlignment()%>;
	font-family: titleFont;
}

.reveal .ONLY_TITLE_PARAGRAPH  h2 {
	margin-top: -37%;
	padding-left: 2%;
	color: <%=theme.getTitleFontColor()%>;
	font-weight: <%=theme.getTitleFontWeight()%>;
	font-size: <%=theme.getTitleFontSize()%>px;
	line-height: <%=theme.getTitleLineHeight()%>;
	text-align: <%=theme.getTitleTextAlignment()%>;
	font-family: titleFont;
}

.reveal .ONLY_TITLE_PARAGRAPH  .paragraph p {
	padding-left: 40px;
	list-style: none;
	margin-top: 10%;
	color: <%=theme.getParagraphFontColor()%>;
	font-weight: <%=theme.getParagraphFontWeight()%>;
	font-size: <%=theme.getParagraphFontSize()%>px;
	line-height: <%=theme.getParagraphLineHeight()%>;
	text-align: <%=theme.getParagraphTextAlignment()%>;
	font-family: paragraphFont;
}

.reveal .ONLY_TITLE_IMAGE h2 {
	min-height: 799px;
	position: fixed;
	top: -486px;
	padding: 40px;
	color: <%=theme.getTitleFontColor()%>;
	font-weight: <%=theme.getTitleFontWeight()%>;
	font-size: <%=theme.getTitleFontSize()%>px;
	line-height: <%=theme.getTitleLineHeight()%>;
	text-align: <%=theme.getTitleTextAlignment()%>;
	font-family: titleFont;
}

.ONLY_2TITLE_TREE h1 {
	margin-top: -26%;
	padding-left: 10%;
	color: <%=theme.getTitleFontColor()%>;
	font-weight: <%=theme.getTitleFontWeight()%>;
	font-size: <%=theme.getTitleFontSize()%>px;
	line-height: <%=theme.getTitleLineHeight()%>;
	text-align: <%=theme.getTitleTextAlignment()%>;
	font-family: titleFont;
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
	color: <%=theme.getTitleFontColor()%>;
	font-weight: <%=theme.getTitleFontWeight()%>;
	font-size: <%=theme.getTitleFontSize()%>px;
	line-height: <%=theme.getTitleLineHeight()%>;
	text-align: <%=theme.getTitleTextAlignment()%>;
	font-family: titleFont;
}

.reveal .ONLY_TITLE_LIST ol li ul {
	padding-bottom: 56px;
	padding-left: 29px;
	padding-right: 52px;
	/* list-style-image: url('/themes/mobile/bullet_line.png'); */
	margin-left: 82px;
	list-style-type: decimal-leading-zero;
	color: <%=theme.getListitemFontColor()%>;
	font-weight: <%=theme.getListitemFontWeight()%>;
	font-size: <%=theme.getListitemFontSize()%>px;
	line-height: <%=theme.getListitemLineHeight()%>;
	text-align: <%=theme.getListitemTextAlignment()%>;
	font-family: listitemFont;
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