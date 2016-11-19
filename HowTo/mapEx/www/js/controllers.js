angular.module('starter.controllers', [])

.controller('AppCtrl', function($scope, $ionicModal, $timeout) {

})

.controller('MapCtrl', function($scope, $stateParams, $http) {
	$scope.data = {};
	
	//Initially centers map in Seattle
	$scope.init = function(){
		$scope.map = {};
		var myLatlng = new google.maps.LatLng(47.606209, -122.332071);
		getData(myLatlng);
	};
	
	//Gets current location and calls update map with coordinates
	$scope.getCurrent =  function() {
		navigator.geolocation.getCurrentPosition(function(pos) {
			var posLat = pos.coords.latitude;
			var posLong = pos.coords.longitude;
			var myLatlng = new google.maps.LatLng(posLat, posLong);
			getData(myLatlng);
		});
    };
	
	//Get geocode for address, then call update map with new coordinates
	$scope.getAddress =  function() {
		var geocoder = new google.maps.Geocoder();
		var address = $scope.data.address;
		console.log(address);
		geocoder.geocode({'address': address}, function(results, status){
			if (status == 'OK'){
				console.log(results);
				var posLat = results[0].geometry.location.lat();
				var posLong = results[0].geometry.location.lng();
				var myLatlng = new google.maps.LatLng(posLat, posLong);
				getData(myLatlng);
			} else{
				alert('Location address not valid, please enter a new address and try again');
			}
		});
    };
	
	//Updates map with coordinates
	var updateMap = function(center, markers){
	 
		var mapOptions = {
			center: center,
			zoom: 12,
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};
 
		var map = new google.maps.Map(document.getElementById("myMap"), mapOptions);
 
		markers.forEach(function(markerPos){
			var marker = new google.maps.Marker({
				position: markerPos.location,
				icon: markerPos.icon,
				map: map
			});
			
			//Create info window if marker has data
			if (markerPos.category != null){
				infoWindow = new google.maps.InfoWindow();
				google.maps.event.addListener(marker, 'click', function(){
					//Define inner content of info window
					var infoWinContent = '<div id="infoWin">';
					infoWinContent += '<div><b>' + markerPos.address + '</b></div>';  //title
					infoWinContent += '<br><div>' + markerPos.category + '<br>$' + markerPos.value; //data
					infoWinContent += '<br><a href="#/app/permit/' + markerPos.id + '">View Details</a></div>';
					infoWinContent += '</div>';
					infoWindow.setContent(infoWinContent);
					infoWindow.open(map, marker);
				});
			}
		})
		
		$scope.map = map;
	};
	
	//Get data
	getData = function(center){	
		$http.get("https://data.seattle.gov/resource/mags-97de.json?permit_type=Construction&$where=issue_date>'2016-11-07T00:00:00.000'")
			.success(function(results, status, headers, config){
				console.log('Success');
				console.log(results);
				$scope.permits = results;
				
				//Plot markers
				var theMarkers = [];
				$scope.permits.forEach(function(permit){
					var theLatlng = new google.maps.LatLng(permit.latitude, permit.longitude);
					
					//Assign letter to marker for permit category
					if (permit.category == "MULTIFAMILY")
						var letter = 'M';
					else if (permit.category == "COMMERCIAL")
						var letter = 'C';
					else if (permit.category == "INSTITUTIONAL")
						var letter = 'I';
					else
						var letter = 'S';
					
					//Assign color to marker for value
					if (permit.value < 50000)
						var color = "82E0AA";
					else if (permit.value >= 50000 && permit.value < 100000)
						var color = "5DADE2"
					else if (permit.value >= 100000 && permit.value < 500000)
						var color = "F7DC6F"
					else
						var color = "FE7569"
					
					//Add marker to array
					theMarkers.push(new myMarker(theLatlng, "http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=" + letter + "|" + color, permit.category, permit.address, permit.value, permit.application_permit_number));
				})
				
				updateMap(center, theMarkers);
			})
			.error(function(results, status, headers, config){
				console.log('Failure');
				console.log(results);
			})
	}
})

.controller('PermitCtrl', function($scope, $stateParams, $http) {
	$scope.permit = "";
	
	$http.get("https://data.seattle.gov/resource/mags-97de.json?application_permit_number=" + $stateParams.permitId)
		.success(function(data, status, headers, config){
			console.log('Success');
			console.log(data);
			$scope.permit = data[0];
		})
		.error(function(data, status, headers, config){
			console.log('Failure');
			console.log(data);
		})	
		
	$scope.openWindow = function(url){
		window.open(url, '_blank');
	}
});

class myMarker{
	constructor(location, icon, category, address, value, id){
		this.location = location;
		this.icon = icon;
		this.category = category;
		this.address = address;
		this.value = value;
		this.id = id;
	}
}
