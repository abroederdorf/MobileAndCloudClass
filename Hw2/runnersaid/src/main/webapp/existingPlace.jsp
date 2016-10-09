<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%-- //[START imports]--%>
<%@ page import="com.osu.cs496.runnersaid.Place" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%-- //[END imports]--%>

<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Runner's Aid</title>
	<link href="/css/bootstrap.min.css" rel="stylesheet">
	<link href="/css/style.css" rel="stylesheet" type="text/css">
</head>

<body>
	<div class="container" id="pageDiv">

		<nav class='navbar navbar-default navbar-fixed-top'>
			<div class='container'>
				<div class='navbar-header'>
					<button type='button' class='navbar-toggle collapsed' data-toggle='collapse' data-target='#navBarCollapsed' aria-expanded='false' aria-controls='navbar'>
						<span class='sr-only'>Toggle navigation</span>
						<span class='icon-bar'></span>
						<span class='icon-bar'></span>
						<span class='icon-bar'></span>
					</button>
					<a href='http://runnersaidapp.appspot.com/' class='navbar-brand' id='pageTitle'><strong><span class='glyphicon glyphicon-road'></span>   Runner's Aid</strong></a>
				</div>
				<div class='collapse navbar-collapse' id='navBarCollapsed'>
					<ul class='nav navbar-nav'>
						<li><a href='http://runnersaidapp.appspot.com/'><span class='glyphicon glyphicon-search'></span>   View Places</a></li>
						<li><a href='http://runnersaidapp.appspot.com/addPlace.jsp'><span class='glyphicon glyphicon-plus'></span>   Add a Place</a></li>
					</ul>
					<%
						UserService userService = UserServiceFactory.getUserService();
						User user = userService.getCurrentUser();
						if (user != null) {
							pageContext.setAttribute("user", user);
					%>
					<span class='navbar-text pull-right'> ${fn:escapeXml(user.nickname)} | <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">Sign Out</a> </span>
					<%
						} else {
					%>
					<span class='navbar-text pull-right'> <a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a> </span>
					<%
						}
					%>
				</div>
			</div>
		</nav>
		
		<h3>Existing Place</h3>
		<p>Sorry, this place already exists. Please go to <a href="http://runnersaidapp.appspot.com/">view places</a> to find the existing place and edit its details from there.</p>
	
	</div>
	

<%-- //[END datastore]--%>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script src="/js/addPlace.js"></script>
</body>
</html>
<%-- //[END all]--%>
