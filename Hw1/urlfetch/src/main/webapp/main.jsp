<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- //[START imports]--%>
<%@ page import="com.osu.cs496.mybusstop.BusInfo" %>
<%@ page import="com.osu.cs496.mybusstop.BusStop" %>

<%-- //[END imports]--%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.lang.String" %>
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
					pageContext.setAttribute("stopDirection", thisStop.direction);
					String divId = thisStop.id + "_div";
					pageContext.setAttribute("stopId", divId);
	%>
		<h4><button type="button" class="btn btn-default" data-toggle="collapse" data-target="#${fn:escapeXml(stopId)}"> ${fn:escapeXml(stopDirection)} </button>${fn:escapeXml(stopName)}</h4>	
	<%
					if (thisStop.buses.isEmpty()){		
	%>	
			<p>No buses arriving at this stop</p>
	<%
					}else{
	%>
		<div class="row collapse" id="${fn:escapeXml(stopId)}">
			<div class="col-sm-off-3 col-sm-6 col-xs-12">
			<table class="table table-hover table-bordered">
				<thead>
					<tr>
						<th>Route</th>
						<th>Arrival</th>
						<th>Wait Time</th>
						<th>Scheduled</th>
					</tr>
				</thead>
				<tbody>	
	<%
						for (BusInfo bus : thisStop.buses){
							pageContext.setAttribute("busRoute", bus.route);
							pageContext.setAttribute("busArrival", bus.arrival);
							pageContext.setAttribute("busWait", bus.wait);
							pageContext.setAttribute("busSched", bus.scheduled);
	%>
				<tr>
					<td>${fn:escapeXml(busRoute)}</td>
					<td>${fn:escapeXml(busArrival)}</td>
					<td>${fn:escapeXml(busWait)}</td>
					<td>${fn:escapeXml(busSched)}</td>
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
	}
	%>
	<p>Public transit data powered by OneBusAway</p>
	<h4><button type="button" class="btn btn-default" data-toggle="collapse" data-target="#proposedSched"> <span class="glyphicon glyphicon-list-alt"></span> </button>Proposed Schedules</h4>
	<div class="row collapse" id="proposedSched">
		<div class="col-sm-off-3 col-sm-6 col-xs-12">
			<p>
				<strong>Wait Times</strong>: 62 - 26 < 5 min | 8 < 6 min
				<br>
				<strong>Length of Ride</strong>: 26 - 10 mins | 62 - 15 mins ||| 8 - 8 mins | Walk - 14 mins
			</p>
			<table class="table table-hover table-bordered">
				<thead>
					<tr>
						<th>Leave Home</th>
						<th>Depart</th>
						<th>Route</th>
						<th>Depart</th>
						<th>Route</th>
						<th>Arrive Work</th>
					</tr>
				</thead>
				<tbody>	
					<tr>
						<td>7:05a</td>
						<td>7:13a</td>
						<td>26</td>
						<td>7:25a</td>
						<td>Walk</td>
						<td>7:39a</td>
					</tr>
					<tr>
						<td>7:10a</td>
						<td>7:18a</td>
						<td>62</td>
						<td>7:36a</td>
						<td>8</td>
						<td>7:44a</td>
					</tr>
					<tr>
						<td>7:20a</td>
						<td>7:28a</td>
						<td>26</td>
						<td>7:46a</td>
						<td>8/W</td>
						<td>7:54a</td>
					</tr>
					<tr>
						<td>7:35a</td>
						<td>7:43a</td>
						<td>26</td>
						<td>7:56a</td>
						<td>8</td>
						<td>8:04a</td>
					</tr>
					<tr>
						<td>7:45a</td>
						<td>7:52a</td>
						<td>26</td>
						<td>8:06a</td>
						<td>8</td>
						<td>8:14a</td>
					</tr>
					<tr>
						<td>7:55a</td>
						<td>8:03a</td>
						<td>26</td>
						<td>8:16a</td>
						<td>8</td>
						<td>8:24a</td>
					</tr>
					<tr>
						<td>8:05a</td>
						<td>8:13a</td>
						<td>26</td>
						<td>8:26a</td>
						<td>8</td>
						<td>8:34a</td>
					</tr>
					<tr>
						<td>8:15a</td>
						<td>8:23a</td>
						<td>26</td>
						<td>8:36a</td>
						<td>8</td>
						<td>8:46a</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	

	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>
</body>
</html>
<%-- //[END all]--%>
