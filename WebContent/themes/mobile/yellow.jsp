
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@page import="com.istarindia.apps.dao.*"%>
<%@page import="com.istarindia.apps.dao.PresentaionDAO"%>
<%@page import="com.istarindia.apps.dao.*"%>
	
<%@page import="org.hibernate.Criteria"%>
<%@page import="org.hibernate.HibernateException"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.Transaction"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
	<% 
	PresentaionDAO dao = new PresentaionDAO();
	Presentaion ppt = new Presentaion();
	
	if(!request.getParameterMap().containsKey("ppt_id")){
		ppt = (Presentaion) request.getAttribute("ppt");
	} else {
		int lessonID = Integer.parseInt(request.getParameter("ppt_id").replaceAll("/", ""));
		ppt =  dao.findById(lessonID);
	}
	String lesson_theme = ppt.getLesson().getLesson_theme();
	String sql = "select * from ui_theme as T where T.id="+lesson_theme;
	UiThemeDAO uiDao = new UiThemeDAO();
	Session uiSession = uiDao.getSession();
	SQLQuery query = uiSession.createSQLQuery(sql);
	query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
	List<HashMap<String, Object>> results = query.list();
	//UiTheme theme = new UiThemeDAO().findById(Integer.parseInt(results.get(0).get("id").toString()));
		HashMap<String, String> theme = (HashMap<String, String>)query.list().get(0);
	%>
<style>

body {
	background-color: <%=theme.get("background_color")%>;
	//#FDC530 !important;
}

@font-face {
    font-family: 'Alice';
    src: url('/themes/fonts/Alice/Alice-Regular.ttf');
}

@font-face {
    font-family: 'Asap-Bold';
    src: url('/themes/fonts/Asap/Asap-Bold.ttf');
}

@font-face {
    font-family: 'Asap-BoldItalic';
    src: url('/themes/fonts/Asap/Asap-BoldItalic.ttf');
}

@font-face {
    font-family: 'Biryani';
    src: url('/themes/fonts/Biryani/Biryani-Regular.ttf');
}

@font-face {
    font-family: 'ComingSoon';
    src: url('/themes/fonts/Coming_Soon/ComingSoon.ttf');
}

@font-face {
    font-family: 'Coockie';
    src: url('/themes/fonts/Coockie/Coockie-Regular.ttf');
}

@font-face {
    font-family: 'Domine';
    src: url('/themes/fonts/Domine/Domine-Regular.ttf');
}

@font-face {
    font-family: 'DroidSerif';
    src: url('/themes/fonts/Droid_Serif/DroidSerif.ttf');
}

@font-face {
    font-family: 'Exo';
    src: url('/themes/fonts/Exo/Exo-Regular.ttf');
}

@font-face {
    font-family: 'Lato';
    src: url('/themes/fonts/Lato/Lato-Regular.ttf');
}

@font-face {
    font-family: 'LeagueScript';
    src: url('/themes/fonts/League_Script/LeagueScript.ttf');
}

@font-face {
    font-family: 'LibreBaskerville';
    src: url('/themes/fonts/Libre_Baskerville/LibreBaskerville-Regular.ttf');
}

@font-face {
    font-family: 'NatoSerif';
    src: url('/themes/fonts/Nato_Serif/NatoSerif-Regular.ttf');
}

@font-face {
    font-family: 'OpenSans';
    src: url('/themes/fonts/Open_Sans/OpenSans-Regular.ttf');
}

@font-face {
    font-family: 'Prata';
    src: url('/themes/fonts/Prata/Prata-Regular.ttf');
}

@font-face {
    font-family: 'Raleway';
    src: url('/themes/fonts/Raleway/Raleway-Regular.ttf');
}

@font-face {
    font-family: 'Quicksand';
    src: url('/themes/fonts/Quicksand/Quicksand-Regular.ttf');
}

@font-face {
    font-family: 'Roboto';
    src: url('/themes/fonts/Roboto/Roboto-Regular.ttf');
}

.reveal  .slide {
	background-color: <%=theme.get("background_color")%>;
}

.reveal  .ONLY_TITLE h2 {
	margin-top: -37%;
	padding-left: 2%;
	color: <%=theme.get("title_____font_color")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-family: '<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
	
}

.reveal  .ONLY_2TITLE h2 {
	margin-top: -26%;
	padding-left: 10%;
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;    text-transform: uppercase;
	font-family: <%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal  .ONLY_2TITLE_IMAGE h2 {
	padding-left: 5%;
    padding-right: 5%;
    color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family:<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .ONLY_TITLE_PARAGRPH_IMAGE h2 {
    padding-left: 3%;
    padding-right: 3%;
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family:<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .ONLY_TITLE_PARAGRAPH  h2 {
	margin-top: -37%;
	padding-left: 2%;
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family:<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .ONLY_TITLE_IMAGE h2 {
    padding-right: 9%;
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family:<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}


.reveal .ONLY_TITLE_VIDEO h2 {
	position: absolute;
    margin-top: -50%;
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family:<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}


.reveal .ONLY_PARAGRAPH_TITLE h2{
	margin-top: 29%;
	padding-left: 10%;
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;    text-transform: uppercase;
	font-family: <%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
	
	}

.reveal .ONLY_TITLE_LIST h2 {
	padding-left: 42px;
	margin-top: -38%;
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family:<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal  .ONLY_2TITLE h3 {
	padding-left: 10%;
	color: <%=theme.get("subtitle_____font_color")%>;
	font-weight: <%=theme.get("subtitle_____font_weight")%>;
	font-size: <%=theme.get("subtitle_____font_size")%>px;
	line-height: <%=theme.get("subtitle_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family: <%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
	
}

.reveal  .ONLY_2TITLE_IMAGE h3 {
	padding-left: 5%;
    padding-top: 4%;
	color: <%=theme.get("subtitle_____font_color")%>;
	font-weight: <%=theme.get("subtitle_____font_weight")%>;
	font-size: <%=theme.get("subtitle_____font_size")%>px;
	line-height: <%=theme.get("subtitle_____line_height")%>;
	text-align: <%=theme.get("subtitle_____text_alignment")%>;
	font-family: <%=theme.get("subtitle_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .ONLY_PARAGRAPH_TITLE .paragraph p{
	padding-left: 40px;
	list-style: none;
	margin-top: 3%;
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;

}

.reveal .ONLY_PARAGRAPH .paragraph p {
	list-style: none;
	z-index: 9999;
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
}

.reveal .ONLY_PARAGRAPH_IMAGE  .paragraph p {
	padding-left: 2%;
	padding-right: 10%;
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
}

.reveal .ONLY_TITLE_PARAGRPH_IMAGE .paragraph p {
	margin-left: 2%;
    margin-right: -5%;
    list-style: none;
    z-index: 9999;
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
}

.reveal .ONLY_TITLE_PARAGRPH_IMAGE  .paragraph p{
	padding-left: -5px;
	list-style: none;
	z-index: 9999;
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
}

.reveal .ONLY_TITLE_PARAGRAPH  .paragraph p {
	padding-left: 40px;
	list-style: none;
	margin-top: 10%;
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
}

.reveal .ONLY_PARAGRAPH_TITLE .paragraph {
	padding-left: 40px;
	list-style: none;
	margin-top: 3%;
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;

}

.reveal .ONLY_PARAGRAPH .paragraph  {
	list-style: none;
	z-index: 9999;
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
}

.reveal .ONLY_PARAGRAPH_IMAGE  .paragraph  {
	padding-left: 2%;
	padding-right: 10%;
	margin-top: -20%;
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
}

.reveal .ONLY_TITLE_PARAGRPH_IMAGE .paragraph  {
	padding-right: 10%;
    padding-left: 5%;
    list-style: none;
    z-index: 9999;
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
}

.reveal .ONLY_TITLE_PARAGRPH_IMAGE  .paragraph {
	padding-left: -5px;
	padding-right: 7px;
	padding-top: 5%;
    list-style: none;
	z-index: 9999;
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
}

.reveal .ONLY_TITLE_PARAGRAPH  .paragraph {
	padding-left: 40px;
	list-style: none;
	margin-top: 10%;
	padding-right: 5%;
	color: <%=theme.get("paragraph_____font_color")%>;
	font-weight: <%=theme.get("paragraph_____font_weight")%>;
	font-size: <%=theme.get("paragraph_____font_size")%>px;
	line-height: <%=theme.get("paragraph_____line_height")%>;
	text-align: <%=theme.get("paragraph_____text_alignment")%>;
	font-family: '<%=theme.get("paragraph_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>', serif;
}

.reveal .ONLY_TITLE_LIST ul li  {
	list-style: none !important;
	padding-bottom: 56px;
	padding-left: 10px;
	padding-right: 52px;
	margin-left: 82px;
	color: <%=theme.get("listitem_____font_color")%>;
	font-weight: <%=theme.get("listitem_____font_weight")%>;
	font-size: <%=theme.get("listitem_____font_size")%>px;
	line-height: <%=theme.get("listitem_____line_height")%>;
	text-align: <%=theme.get("listitem_____text_alignment")%>;
	font-family:<%=theme.get("listitem_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .ONLY_TITLE_LIST ul  {
    padding-left: 10px;
    padding-top: 6%;
	color: <%=theme.get("listitem_____font_color")%>;
	font-weight: <%=theme.get("listitem_____font_weight")%>;
	font-size: <%=theme.get("listitem_____font_size")%>px;
	line-height: <%=theme.get("listitem_____line_height")%>;
	text-align: <%=theme.get("listitem_____text_alignment")%>;
	font-family:<%=theme.get("listitem_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
	/* position: absolute;
    margin-top: -2%; */
}

.reveal .ONLY_TITLE_TREE #data_slide_title {
	padding-left: 42px;
	margin-top: -35% !important;
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family:<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .ONLY_TITLE_TREE li  {
	padding-bottom: 56px;
	padding-left: 29px;
	padding-right: 52px;
	margin-left: 82px;
    list-style-type: lower-roman;
	color: <%=theme.get("listitem_____font_color")%>;
	font-weight: <%=theme.get("listitem_____font_weight")%>;
	font-size: <%=theme.get("listitem_____font_size")%>px;
	line-height: <%=theme.get("listitem_____line_height")%>;
	text-align: <%=theme.get("listitem_____text_alignment")%>;
	font-family:<%=theme.get("listitem_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .ONLY_TITLE_TREE ol  {
	padding-bottom: 56px;
    padding-right: 52px;
    margin-left: 140px;
    margin-top: 45px;
    list-style-type: decimal;
	color: <%=theme.get("listitem_____font_color")%>;
	font-weight: <%=theme.get("listitem_____font_weight")%>;
	font-size: <%=theme.get("listitem_____font_size")%>px;
	line-height: <%=theme.get("listitem_____line_height")%>;
	text-align: <%=theme.get("listitem_____text_alignment")%>;
	font-family:<%=theme.get("listitem_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.reveal .ONLY_LIST  ul{
	margin-top: -30%;
	list-style-type: none;
}

.reveal .ONLY_LIST  ul li{
	padding-bottom: 56px;
	padding-left: 29px;
	padding-right: 52px;
	margin-left: 82px;
	color: <%=theme.get("listitem_____font_color")%>;
	font-weight: <%=theme.get("listitem_____font_weight")%>;
	font-size: <%=theme.get("listitem_____font_size")%>px;
	line-height: <%=theme.get("listitem_____line_height")%>;
	text-align: <%=theme.get("listitem_____text_alignment")%>;
	font-family:<%=theme.get("listitem_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
}

.ONLY_2TITLE_TREE h1 {
	margin-top: -26%;
	padding-left: 10%;
	color: <%=theme.get("title_____font_color")%>;
	font-weight: <%=theme.get("title_____font_weight")%>;
	font-size: <%=theme.get("title_____font_size")%>px;
	line-height: <%=theme.get("title_____line_height")%>;
	text-align: <%=theme.get("title_____text_alignment")%>;
	font-family:<%=theme.get("title_____font_family").replaceAll(".ttf", "").replaceAll("-Regular", "")%>;
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
.reveal .ONLY_PARAGRAPH_IMAGE img {
	width: 180%;
}

.reveal .ONLY_TITLE_IMAGE img {
	width: 180%;
}

.reveal .ONLY_2TITLE_IMAGE img {
	width: 180%;
}

.reveal .ONLY_IMAGE img {
	width: 180%;
}

.reveal .ONLY_TITLE_PARAGRPH_IMAGE img {
	width: 180%;
}

.reveal .ONLY_PARAGRAPH_TITLE #data_slide_paragraph {
	margin-top: -25%;
	
}





/* Below are of no use.. just for the demo */


#top {
	height: 900px;
	padding-top: 300px;
	margin-top: -110%;
	width: 129%;
	margin-left: -10%;
}

#bottom {
	height: 1000px;
	padding-top: 41px;
	margin-left: -10%;
	width: 120%;
}

#top h2 {
	color: rgb(102,197,187);
	text-align: left;
	font-size: 90px;
	padding-left: 5%;
	font-weight: bolder;
	padding-top: 26%;
	padding-left: 11%;
	font-weight: 900;
}
#bottom h3 {
	color: white;
	text-align: left;
	font-size: 90px;
	padding-left: 5%;
	font-weight: bolder;
	padding-top: 26%;
	padding-left: 11%;
	font-weight: 900;
}
#top li {
	color: rgb(93,93,93);
	font-size: 55px;
	list-style: none;
	padding-left: 12%;
	padding-right: 31%;
	text-align: left;
	line-height: 1.1;
}

#bottom li {
	color: white;
	font-size: 55px;
	list-style: none;
	padding-top: 23px;
	padding-left: 12%;
	padding-right: 31%;
	text-align: left;
	line-height: 1.1;
}


.reveal .ONLY_TITLE_ASSESSMENT_2COLUMNS {
	background: white;;
}

.reveal .ONLY_TITLE_ASSESSMENT_2COLUMNS h2 {
	font-weight: 700;
	font-size: 90px;
	color: white;
	background-color: #9cc4a6;
	line-height: 2.7;
	width: 116%;
	padding-top: 130px;
	margin-top: -63%;
	margin-left: -10%;
	text-align: left;
	padding-left: 7%;
}

.reveal .ONLY_TITLE_ASSESSMENT_2COLUMNS table tr td {
	font-weight: 700;
	font-size: 44px;
	line-height: 1.2;
	text-align: left;
	padding-top: 83px !important;
}

.reveal .ONLY_TITLE_ASSESSMENT_2COLUMNS table tr td input {
	width: 121px;
	height: 101px;
	padding-top: 44px;
}

.reveal .ONLY_2TITLE_TABLE h2 {
	font-weight: bolder;
	font-size: 133px;
	padding-top: 130px;
	color: #e89d81;
	line-height: 2.7;
	width: 100%;
	margin-top: -64%;
	text-align: left;
	padding-left: 0%;
}

.reveal .ONLY_2TITLE_TABLE table tr td {
	font-weight: 400;
	font-size: 50px;
	padding-top: 39px;
	padding: 14px;
	text-align: left;
}

.reveal .ONLY_2TITLE_TABLE table tr td input {
	width: 121px;
	height: 101px;
	padding-top: 44px;
}

.reveal .ONLY_2TITLE_TABLE {
	background: white;;
}

.reveal .ONLY_2TITLE_TABLE h3 {
	font-weight: bold;
	font-size: 67px;
	color: #e89d81;
	line-height: 2.7;
	width: 100%;
	margin-top: -17%;
	text-align: left;
	padding-left: 0%;
}

</style>