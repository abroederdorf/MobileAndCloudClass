//Add place JS file for checking form input and resetting fields

/*********************************************************************
initializePage()
Clear all input and hide error messages
Input: None
Output: All input fields cleared
*********************************************************************/
function intializePage(){
	document.getElementById("placeName").value = "";
	document.getElementById("latitude").value = "";
	document.getElementById("longitude").value = "";
	document.getElementById("placeStatusOpen").checked = false;
	document.getElementById("placeStatusClosed").checked = false;
	document.getElementById("placeType").value = "";
	document.getElementById("placeFav").checked = false;
}


//Initialize Date Page
document.addEventListener('DOMContentLoaded', intializePage);

//Event listeners
document.getElementById("resetForm").addEventListener('click', intializePage);