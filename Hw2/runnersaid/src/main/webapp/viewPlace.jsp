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
	
	<p>Welcome to Runner's Aid! This app tracks places like water fountains and public bathrooms in your area. You can then view which places are open for your use when planning your running route.</p>
	<hr>
	<h3>View Places</h3>
<%-- //[START datastore]--%>
<%
    //Run query to get places to list.
      List<Place> places = ObjectifyService.ofy()
          .load()
          .type(Place.class) // We want only Greetings
		  .order("createDate")
          .list();

    if (places.isEmpty()) {
%>
	<p>There are no places in the system. Be the first to <a href="http://runnersaidapp.appspot.com/addPlace.jsp">add</a> one!</p>
	<%
	} else {
	%>
	<table class="table table-hover table-bordered table-condensed">
		<thead>
			<tr>
				<th class="text-center">Type</th>
				<th class="text-center">Name</th>
				<th class="text-center">Status</th>
				<th class="text-center">Latitude</th>
				<th class="text-center">Longitude</th>
				<th class="text-center">Favorite?</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		<%
		for (Place place : places) {
			pageContext.setAttribute("placeType", place.type);
			pageContext.setAttribute("placeName", place.name);
			pageContext.setAttribute("placeLat", place.latitude);
			pageContext.setAttribute("placeLong", place.longitude);	
			pageContext.setAttribute("placeStatus", place.status);
			pageContext.setAttribute("placeId", place.id);
			if (place.favorite)
				pageContext.setAttribute("placeFav", "Yes");	
			else
				pageContext.setAttribute("placeFav", "No");
		%>
			<tr>
				<td>${fn:escapeXml(placeType)}</td>
				<td>${fn:escapeXml(placeName)}</td>
				<td class="text-center">${fn:escapeXml(placeStatus)}</td>
				<td class="text-center">${fn:escapeXml(placeLat)}</td>
				<td class="text-center">${fn:escapeXml(placeLong)}</td>
				<td class="text-center">${fn:escapeXml(placeFav)}</td>
				<td class="text-center">
					<form method="post" action="/edit">
					<input type="hidden" name="placeId" value="${fn:escapeXml(placeId)}">
					<button type="submit" class="btn btn-info" value="Edit">Edit</button>
					</form>
				</td>
			</tr>
	<%
		}
	%>
		</tbody>
	</table>
	<%
	}
		%>
	
<%-- //[END datastore]--%>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
</body>
</html>
<%-- //[END all]--%>
