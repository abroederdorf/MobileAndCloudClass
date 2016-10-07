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
    <link type="text/css" rel="stylesheet" href="/stylesheets/style.css"/>
	<title>Runner's Aid</title>
</head>

<body>
	<h1>Runner's Aid</h1>
	<p>Welcome to Runner's Aid! This app tracks places like water fountains and public bathrooms in your area. You can then view which places are open for your use when planning your running route.</p>
<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
        pageContext.setAttribute("user", user);
%>

<p>Hello, ${fn:escapeXml(user.nickname)}! (You can
    <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<%
    } else {
%>
<p>Hello!
    <a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>.</p>
<%
    }
%>

<%-- //[START datastore]--%>


<form action="/add" method="post">
	<fieldset>
		<legend>Add New Place</legend>
		<label>Type:</label>
		<select name="placeType">
			<option value="Water Fountain">Water Fountain</option>
			<option value="Bathroom">Bathroom</option>
		</select>
		<span>&nbsp&nbsp&nbsp&nbsp&nbsp</span><label>Name: <input type="text" name="placeName"></label>
		<br><label>Latitude: <input type="number" name="latitude" step="any"></label>
		<span>&nbsp&nbsp&nbsp&nbsp&nbsp</span><label>Longitude: <input type="number" name="longitude" step="any"></label>
		<br><label>Status:</label> <label><input type="radio" name="placeStatus" value="Open">Open</label> <label><input type="radio" name="placeStatus" value="Closed">Closed</label>
		<span>&nbsp&nbsp&nbsp&nbsp&nbsp</span><label>Favorite? <input type="checkbox" name="placeFav" value="true"></label>
		<br><input type="submit" value="Add Place"/>
	</fieldset>
</form>
<%-- //[END datastore]--%>

</body>
</html>
<%-- //[END all]--%>
