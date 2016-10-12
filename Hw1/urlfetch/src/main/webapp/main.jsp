<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- //[START imports]--%>
<%@ page import="com.osu.cs496.mybusstop.BusInfo" %>
<%@ page import="com.osu.cs496.mybusstop.BusStop" %>

<%-- //[END imports]--%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>My Bus Stops</title>
	<link href="/css/bootstrap.min.css" rel="stylesheet">
	<link href="/css/style.css" rel="stylesheet" type="text/css">
</head>

<body>
	<div class="container" id="pageDiv">

	<nav class='navbar navbar-default navbar-fixed-top'>
		<div class='container'>
			<div class='navbar-header'>
				<a href='http://urlfetchex.appspot.com/' class='navbar-brand' id='pageTitle'><strong><span class='glyphicon glyphicon-flag'></span>   My Bus Stops</strong></a>
			</div>
		</div>
	</nav>

	<%
	ArrayList<BusStop> allStops = (ArrayList<BusStop>)request.getAttribute("stops");
	
	if (allStops != null){
		if (allStops.isEmpty()){		
	%>
		<p>No bus stops selected</p>
	<%	}else{
			for(BusStop thisStop : allStops){
				if (thisStop != null){
					pageContext.setAttribute("stopName", thisStop.name);
	%>
		<h4>${fn:escapeXml(stopName)}</h4>	
	<%
					if (thisStop.buses.isEmpty()){		
	%>	
			<p>No buses arriving at this stop</p>
	<%
					}else{
	%>
		<div class="row">
			<div class="col-sm-off-3 col-sm-6 col-xs-12">
			<table class="table table-hover table-bordered">
				<thead>
					<tr>
						<th>Route</th>
						<th>Arrival</th>
						<th>Wait Time</th>
					</tr>
				</thead>
				<tbody>	
	<%
						for (BusInfo bus : thisStop.buses){
							pageContext.setAttribute("busRoute", bus.route);
							pageContext.setAttribute("busArrival", bus.arrival);
							pageContext.setAttribute("busWait", bus.wait);
	%>
				<tr>
					<td>${fn:escapeXml(busRoute)}</td>
					<td>${fn:escapeXml(busArrival)}</td>
					<td>${fn:escapeXml(busWait)}</td>
				</tr>
	<%
							
						}
	%>
			</tbody>
		</table>
		</div>
		</div>
	
	<%
					}
				}
			}
		}
	}else{
	%>
	<p>Not within first if</p>
	<%	
	}
	%>
	<p>Public transit data powered by OneBusAway</p>

	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
</body>
</html>
<%-- //[END all]--%>
