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
	
<%-- //[START datastore]--%>
	<h3>Add New Place</h3>
	<form action="/add" method="post">
		
		<!--Type and Name-->
		<div class="form-inline">
			<div class="row">
				<div class="col-sm-5 col-xs-12">
					<div class="form-group">
						<div class="input-group">
							<label class="control-label input-group-addon" for="date">Type:</label>
							<select class="form-control input-group" name="placeType" id="placeType" autofocus required>
								<option value="">Please select...</option>
								<option value="Water Fountain">Water Fountain</option>
								<option value="Bathroom">Bathroom</option>
							</select>
						</div>
					</div>
				</div>
				<div class="col-sm-4 col-xs-12">
					<div class="form-group">
						<div class="input-group">
							<label class="control-label input-group-addon" for="placeName">Name:</label>
							<input type="text" required class="form-control" name="placeName" id="placeName"> 
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
							<input type="number" required class="form-control" name="latitude" id="latitude" step="any"> 
						</div>
					</div>
				</div>
				<div class="col-sm-offset-1 col-sm-4 col-xs-12">
					<div class="form-group">
						<div class="input-group">
							<label class="control-label input-group-addon col-sm-offset-1" for="submitElevation">Longitude:</label>
							<input type="number" required class="form-control" name="longitude" id="longitude" step="any"> 
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
							<label class="checkbox-inline" ><input type="radio" name="placeStatus" id="placeStatusOpen" value="Open" required> Open</label>
							<label class="checkbox-inline"><input type="radio" name="placeStatus" id="placeStatusClosed" value="Closed"> Closed</label>
						</div>
					</div>
				</div>
				<div class="col-sm-4 col-xs-12">
					<div class="form-group">
						<div class="input-group">
							<span class="control-label input-group-addon">Favorite?</span>
							<label class="checkbox-inline">&nbsp&nbsp&nbsp&nbsp<input type="checkbox" name="placeFav" id="placeFav" value="true"></label>
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
				  <button type="submit" class="btn btn-success" value="Add Place">Add Place</button>
				</div>
				<div class="btn-group">
				  <button type="button" id="resetForm" class="btn btn-default">Reset Fields</button>
				</div>
			 </div>
		 </div>		
	</form>
<%-- //[END datastore]--%>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
	<script src="/js/addPlace.js"></script>
</body>
</html>
<%-- //[END all]--%>
