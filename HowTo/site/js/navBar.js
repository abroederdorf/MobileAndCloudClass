/*******************************
* Author: Alicia Broederdorf
* Date: November 12, 2016
* Description: Functions to create navigation bar shown on every page
*******************************/


//Name: buildNavDiv
//Description: Create div and add innerHTML content to create links for navigation
//Input: None
//Output: 1 div is created and returned
function buildNavDiv()
{
	var newDiv = document.createElement("div");
	var displayContent = "	<nav class='navbar navbar-default navbar-inverse navbar-fixed-top'><div class='container'><div class='navbar-header'><button type='button' class='navbar-toggle collapsed' data-toggle='collapse' data-target='#navBarCollapsed' aria-expanded='false' aria-controls='navbar'><span class='sr-only'>Toggle navigation</span><span class='icon-bar'></span><span class='icon-bar'></span><span class='icon-bar'></span></button><a href='index.html' class='navbar-brand' id='pageTitle'><strong><span class='glyphicon glyphicon-globe'></span>  How-To: Ionic with Google Maps</strong></a></div><div class='collapse navbar-collapse' id='navBarCollapsed'><ul class='nav navbar-nav navbar-right'><li class='dropdown'><a href='#' class='dropdown-toggle' data-toggle='dropdown' role='button' aria-haspopup='true' aria-expanded='false'>Sections <span class='caret'></span></a><ul class='dropdown-menu'><li><a href='gettingStarted.html'>Getting Started</a></li><li role='separator' class='divider'></li><li><a href='basicMap.html'>Basic Map</a></li><li><a href='basicMap.html'>- Cleanup</a></li><li><a href='addMap.html'>- Add Map</a></li><li><a href='centerMap.html'>- Center Map</a></li><li role='separator' class='divider'></li><li><a href='customMarkers.html'>Custom Markers</a></li><li><a href='informationWindows.html'>Information Windows</a></li></ul></div></div></nav>";
	newDiv.innerHTML = displayContent;
	
	return newDiv;
}

var navDivBar = document.getElementById('navBar');
navDivBar.appendChild(buildNavDiv());