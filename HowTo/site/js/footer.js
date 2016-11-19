/*******************************
* Author: Alicia Broederdorf
* Date: November 12, 2016
* Description: Functions to create footer shown on every page
*******************************/


//Name: buildFooter
//Description: Create div and add innerHTML content to create footer
//Input: None
//Output: 1 div is created and returned
function buildFooter()
{
	var newDiv = document.createElement("div");
	var displayContent = "<hr><span>Alicia Broederdorf | CS 496 | November 2016</span>";
	newDiv.innerHTML = displayContent;
	
	return newDiv;
}

var navDivBar = document.getElementById('pageFooter');
navDivBar.appendChild(buildFooter());