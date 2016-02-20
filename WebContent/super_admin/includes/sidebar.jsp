<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%><div class="col-md-2 md-margin-bottom-40">
	<%
	String url = request.getRequestURL().toString();
	String basePath = url.substring(0, url.length() - request.getRequestURI().length())
			+ request.getContextPath() + "/";
%>

	<ul class="list-group sidebar-nav-v1 margin-bottom-40" id="sidebar-nav-1">
		<li class="list-group-item active"><a href="page_profile.html"><i class="fa fa-bar-chart-o"></i> Dashboard</a></li>
		<li class="list-group-item"><a href="users.jsp"><i class="fa fa-user"></i> Users</a></li>
		<li class="list-group-item"><a href="organizations.jsp"><i class="fa fa-group"></i> Organization(s)</a></li>
		<li class="list-group-item"><a href="content_snapshot.jsp"><i class="fa fa-cubes"></i> Content</a></li>
		<li class="list-group-item"><a href="creative_snapshot.jsp"><i class="fa fa-comments"></i> Creative</a></li>

	</ul>

</div>