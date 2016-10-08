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
	
	<h3>Edit Place</h3>
<%-- //[START datastore]--%>
<%
    long id = Long.parseLong(request.getParameter("id"));
	
	//Run query to get places to list.
      List<Place> places = ObjectifyService.ofy()
          .load()
          .type(Place.class) // We want only Greetings
          .list();

    if (places.isEmpty()) {
%>
	<p>Unexpected error, place not found.</p>
	<%
	} else {
		int count = 0, index= 0;
		for (Place place : places){
			if (place.id == id)
				index = count;
			count++;
		}
		Place thisPlace = places.get(index);
		
		pageContext.setAttribute("valName", thisPlace.name);
		pageContext.setAttribute("valLat", thisPlace.latitude);
		pageContext.setAttribute("valLong", thisPlace.longitude);	
		pageContext.setAttribute("valId", id);
	%>
	<form action="/update" method="post">
		<input type="hidden" name="placeId" value="${fn:escapeXml(valId)}">
		<!--Type and Name-->
		<div class="form-inline">
			<div class="row">
				<div class="col-sm-5 col-xs-12">
					<div class="form-group">
						<div class="input-group">
							<label class="control-label input-group-addon" for="date">Type:</label>
							<select class="form-control input-group" name="placeType" id="placeType" autofocus required>
								<option value="">Please select...</option>
							<%
							if (thisPlace.type.equals("Water Fountain")){
							%>
								<option value="Water Fountain" selected>Water Fountain</option>
								<option value="Bathroom">Bathroom</option>
							<%
							}else{
							%>
								<option value="Water Fountain">Water Fountain</option>
								<option value="Bathroom" selected>Bathroom</option>
							<%
							}
							%>
							</select>
						</div>
					</div>
				</div>
				<div class="col-sm-4 col-xs-12">
					<div class="form-group">
						<div class="input-group">
							<label class="control-label input-group-addon" for="placeName">Name:</label>
							<input type="text" required class="form-control hidden-xs" name="placeName" id="placeName" value="${fn:escapeXml(valName)}"> 
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<br>
		
		<!--Latitude and Longitude-->
		<div class="form-inline">
			<div class="row">
				<div class="col-sm-4 col-xs-12">
					<div class="form-group">
						<div class="input-group">
							<label class="control-label input-group-addon" for="latitude">Latitude:</label>
							<input type="number" required class="form-control" name="latitude" id="latitude" step="any" value="${fn:escapeXml(valLat)}"> 
						</div>
					</div>
				</div>
				<div class="col-sm-offset-1 col-sm-4 col-xs-12">
					<div class="form-group">
						<div class="input-group">
							<label class="control-label input-group-addon col-sm-offset-1" for="submitElevation">Longitude:</label>
							<input type="number" required class="form-control" name="longitude" id="longitude" step="any" value="${fn:escapeXml(valLong)}"> 
						</div>
					</div>
				</div>
			</div>
		</div>
		<Br>
		
		<!--Status and Favorite-->
		<div class="form-inline">
			<div class="row">
				<div class="col-sm-5 col-xs-12">
					<div class="form-group">
						<div class="input-group">
							<span class="control-label input-group-addon">Status:</span>
							<%
							if (thisPlace.status.equals("Open")){
							%>
							<label class="checkbox-inline" ><input type="radio" name="placeStatus" id="placeStatusOpen" value="Open" required checked> Open</label>
							<label class="checkbox-inline"><input type="radio" name="placeStatus" id="placeStatusClosed" value="Closed"> Closed</label>
								<%
							}else {
							%>
							<label class="checkbox-inline" ><input type="radio" name="placeStatus" id="placeStatusOpen" value="Open" required> Open</label>
							<label class="checkbox-inline"><input type="radio" name="placeStatus" id="placeStatusClosed" value="Closed" checked> Closed</label>	
							<%}%>
						</div>
					</div>
				</div>
				<div class="col-sm-4 col-xs-12">
					<div class="form-group">
						<div class="input-group">
							<span class="control-label input-group-addon">Favorite?</span>
							<label class="checkbox-inline">&nbsp&nbsp&nbsp&nbsp
							<%
							if (thisPlace.favorite){
							%>
							<input type="checkbox" name="placeFav" id="placeFav" value="true" checked></label>
							<%
							}else{
							%>
							<input type="checkbox" name="placeFav" id="placeFav" value="true"></label>
							<%
							}
	}
							%>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<br>
		<!--Buttons-->
		<div class="row">
			<div class="col-sm-6">
				<div class="btn-group">
				  <button type="submit" class="btn btn-success" value="Update Place">Update Place</button></form>
				</div>
				<div class="btn-group">
					<form action="/" method="get"><button type="submit" class="btn btn-danger" value="Cancel">Cancel</button></form>
				</div>
			 </div>
		 </div>
	</form>
<%-- //[END datastore]--%>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
</body>
</html>
<%-- //[END all]--%>
