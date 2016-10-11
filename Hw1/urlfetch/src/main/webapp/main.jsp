<%--
Copyright 2016 Google Inc. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!-- [START base] -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">
<head>
    <title>My Bus Stops</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="shortcut icon" href="">
	<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <h1>My Bus Stops</h1>
	
	<h3>Stone and 40th</h3>
	<table>
		<thead>
			<tr>
				<th>Route</th>
				<th>Arrival</th>
				<th>Wait Time</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<c:if test="${not empty route7380_0}">
				<td>${route7380_0}</td>
				</c:if>
				<c:if test="${not empty arrival7380_0}">
				<td>${arrival7380_0}</td>
				</c:if>
				<c:if test="${not empty arrival7380_0}">
				<td>${wait7380_0}</td>
				</c:if>
			</tr>
			<tr>
				<c:if test="${not empty route7380_1}">
				<td>${route7380_1}</td>
				</c:if>
				<c:if test="${not empty arrival7380_1}">
				<td>${arrival7380_1}</td>
				</c:if>
				<c:if test="${not empty arrival7380_1}">
				<td>${wait7380_1}</td>
				</c:if>
			</tr>
			<tr>
				<c:if test="${not empty route7380_2}">
				<td>${route7380_2}</td>
				</c:if>
				<c:if test="${not empty arrival7380_2}">
				<td>${arrival7380_2}</td>
				</c:if>
				<c:if test="${not empty arrival7380_2}">
				<td>${wait7380_2}</td>
				</c:if>
			</tr>
		</tbody>
	</table>
		
	<h3>Fremont and 41st</h3>
	<table>
		<thead>
			<tr>
				<th>Route</th>
				<th>Arrival</th>
				<th>Wait Time</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<c:if test="${not empty route7380_3}">
				<td>${route7380_3}</td>
				</c:if>
				<c:if test="${not empty arrival7380_3}">
				<td>${arrival7380_3}</td>
				</c:if>
				<c:if test="${not empty arrival7380_3}">
				<td>${wait7380_3}</td>
				</c:if>
			</tr>
			<tr>
				<c:if test="${not empty route7380_4}">
				<td>${route7380_4}</td>
				</c:if>
				<c:if test="${not empty arrival7380_4}">
				<td>${arrival7380_4}</td>
				</c:if>
				<c:if test="${not empty arrival7380_4}">
				<td>${wait7380_4}</td>
				</c:if>
			</tr>
		</tbody>
	</table>
	
	<h3>Aurora and 38th</h3>
	<table>
		<thead>
			<tr>
				<th>Route</th>
				<th>Arrival</th>
				<th>Wait Time</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<c:if test="${not empty route7380_5}">
				<td>${route7380_5}</td>
				</c:if>
				<c:if test="${not empty arrival7380_5}">
				<td>${arrival7380_5}</td>
				</c:if>
				<c:if test="${not empty arrival7380_5}">
				<td>${wait7380_5}</td>
				</c:if>
			</tr>
			<tr>
				<c:if test="${not empty route7380_6}">
				<td>${route7380_6}</td>
				</c:if>
				<c:if test="${not empty arrival7380_6}">
				<td>${arrival7380_6}</td>
				</c:if>
				<c:if test="${not empty arrival7380_6}">
				<td>${wait7380_6}</td>
				</c:if>
			</tr>
			<tr>
				<c:if test="${not empty route7380_7}">
				<td>${route7380_7}</td>
				</c:if>
				<c:if test="${not empty arrival7380_7}">
				<td>${arrival7380_7}</td>
				</c:if>
				<c:if test="${not empty arrival7380_7}">
				<td>${wait7380_7}</td>
				</c:if>
			</tr>
			<tr>
				<c:if test="${not empty route7380_8}">
				<td>${route7380_8}</td>
				</c:if>
				<c:if test="${not empty arrival7380_8}">
				<td>${arrival7380_8}</td>
				</c:if>
				<c:if test="${not empty arrival7380_8}">
				<td>${wait7380_8}</td>
				</c:if>
			</tr>
		</tbody>
	</table>
	<p>Public transit data powered by OneBusAway</p>
	
</body>
</html>
<!-- [END base]-->