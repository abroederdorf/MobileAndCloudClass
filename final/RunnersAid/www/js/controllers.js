var currentUserId = -1;

class myPlace{
	constructor(placeObj, fav){ 
		this.id = placeObj.id;
		this.name = placeObj.name;
		this.type = placeObj.type;
		this.status = placeObj.status;
		this.statusDate = placeObj.statusDate;
		this.vote = placeObj.vote;
		this.latitude = placeObj.latitude;
		this.longitude = placeObj.longitude;
		this.fav = fav;
	}
}

angular.module('starter.controllers', [])

//Note: Google maps source is https://www.thepolyglotdeveloper.com/2014/10/implement-google-maps-using-ionicframework/. Elements from this tutorial used in several functions

.constant('ApiEndpoint', {
	url:'http://runnersaidapi.appspot.com/api/v1'
})
//url:'http://localhost:8100/api'
//url:'http://runnersaidapi.appspot.com/api/v1'

.controller('AppCtrl', function($scope, $ionicModal, $timeout, $state) {

})

.controller('LoginCtrl', function($scope, $http, ApiEndpoint, $state, $ionicHistory){
	$scope.data = {};
	
	$scope.login = function(){
		
		//Valiate form
		var valid = true;
		if ($scope.data.username == null || $scope.data.username == ""){
			valid = false;
			alert('Please enter a valid email for your username');
		}
		
		if (valid){
			$http.get(ApiEndpoint.url + '/users?username=' + $scope.data.username + '&password=' + $scope.data.password)
			.success(function(data, status, headers, config){
				console.log('Success');
				console.log(data);
				currentUserId = data;
				$ionicHistory.nextViewOptions({
					disableBack: true
				});
				$scope.data = {};
				$state.go('app.places');
			})
			.error(function(data, status, headers, config){
				console.log('Failure');
				console.log(data);
				alert("Login information doesn't match a registered user");
			})
		}
	}
	
	$scope.logout = function(){
		currentUserId = -1;
		$ionicHistory.nextViewOptions({
			disableBack: true
		  });
		
		$state.go('app.places');
	}
	
	$scope.create = function(){
		$state.go('app.account');
	}
	
})

.controller('AccountCtrl', function($scope, $http, ApiEndpoint, $state,$ionicHistory){
	$scope.data = {};
	
	$scope.addUser = function(){
		
		//Validate fieds
		var valid = true;
		if ($scope.data.uname == null || $scope.data.uname == ""){
			valid = false;
			alert('Please enter a valid email as your username');
		}
		if($scope.data.pword != $scope.data.pword2){
			valid = false;
			alert('Passwords do not match, please try again');
		}

		if (valid){
			var req = {
				method: 'POST', 
				url: ApiEndpoint.url + '/users',
				data: "username=" + $scope.data.uname + "&password=" + $scope.data.pword,
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			}
			$http(req).success(function(postData) { 
				console.log('Success');
				console.log(postData);
				currentUserId = postData.id;
				$ionicHistory.nextViewOptions({
					disableBack: true
				  });
				$scope.data = {};
				$state.go('app.places');
			}).error(function(postData) { 
				console.log('Failed to add user');
				console.log(postData);  
				alert("Error occurred, please try again");
			});
		}
		
	}
})

.controller('ViewPlacesCtrl', function($scope, $http, ApiEndpoint, $state, $stateParams, $ionicHistory){
	$scope.places = "";
	console.log($stateParams);
	
	//Build query string
	var queryStr = "";
	var numQuery = 0;
	if ($stateParams.fstatus != null ||
		$stateParams.ftype != null ||
		$stateParams.ffavs != null ||
		($stateParams.fradius != null &&
		$stateParams.flat != null &&
		$stateParams.flong != null))
		queryStr += "?";
	if ($stateParams.fstatus != null){
		queryStr = queryStr + "status=" + $stateParams.fstatus;
		numQuery++;
	}
	if ($stateParams.ftype != null){
		if (numQuery > 0)
			queryStr += "&";
		queryStr = queryStr + "type=" + $stateParams.ftype;
		numQuery++;
	}
	if ($stateParams.fradius != null && 
		$stateParams.flat != null && 
		$stateParams.flong != null){
			if (numQuery > 0)
				queryStr += "&";
			queryStr = queryStr + "radius=" + $stateParams.fradius + "&latitude=" + $stateParams.flat + "&longitude=" + $stateParams.flong;
			numQuery++;
	}
	if ($stateParams.ffavs){
		if (numQuery > 0)
				queryStr += "&";
			queryStr = queryStr + "user=" + currentUserId;
	}
	
	console.log("Query: " + queryStr)
	
	$http.get(ApiEndpoint.url + '/search' + queryStr)
		.success(function(data, status, headers, config){
			console.log('Success');
			console.log(data);
			var places = [];
			if (currentUserId != -1){
				$http.get(ApiEndpoint.url + '/users?id=' + currentUserId + "&fields=favorite&favType=ids")
					.success(function(userdata, status, headers, config){
						console.log('Success');
						console.log(userdata);
						data.forEach(function(elm){
							if (userdata.includes(elm.id))
								places.push(new myPlace(elm, true));
							else
								places.push(new myPlace(elm, false));
						})
						$scope.places = places;
						console.log($scope.places);
					})
					.error(function(userdata, status, headers, config){
						console.log('Failure');
						console.log(userdata);
					})
			}
			else{
				data.forEach(function(elm){
					places.push(new myPlace(elm, false));
				})
				$scope.places = places;
				console.log($scope.places);
			}
		})
		.error(function(data, status, headers, config){
			console.log('Failure');
			console.log(data);
		})
	
	$scope.favorite = function(id, fav, $event){
		
		//Add place to favorites
		if (!fav){
			console.log("Add place " + id + " to favorites");
			var req = {
				method: 'PUT', 
				url: ApiEndpoint.url + '/users',
				data: "id=" + currentUserId + "&favorite=" + id,
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			}
			$http(req).success(function(resData) { 
				console.log('Success');
				console.log(resData);
				$state.reload();
			}).error(function(resData) { 
				console.log('Failed to update place');
				console.log(resData); 
			});
		}
		//Remove place from favorites
		else{
			console.log("Remove place " + id + " from favorites");
			
			var req = {
				method: 'DELETE', 
				url: ApiEndpoint.url + '/users/' + currentUserId + '/place?id=' + id
			}
			$http(req).success(function(resData) { 
				console.log('Success');
				console.log(resData);
				$state.reload();
			});
		}
		
		$event.preventDefault();
	};

	
	$scope.navMap = function(){
		$ionicHistory.nextViewOptions({
			disableBack: true
		});
		
		$state.go('app.map', {ftype: $stateParams.ftype, fstatus: $stateParams.fstatus, ffavs: $stateParams.ffavs, fradius: $stateParams.fradius, flat: $stateParams.flat, flong: $stateParams.flong});
	}
	
	$scope.go = function(path){
		console.log("Ftype: " + $stateParams.ftype);
		if (path == "app.filter")
			$state.go(path, {fpath: "app.places"});
	};
})

.controller('PlacesMapCtrl', function($scope, $http, $stateParams, $state, ApiEndpoint, $ionicHistory){
	console.log($stateParams);	
	
	$scope.init =  function() {
		$scope.map = {};
		
		var myLatlng = new google.maps.LatLng(47.6601, -122.3428);
 
        var mapOptions = {
            center: myLatlng,
            zoom: 12,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
 
        var map = new google.maps.Map(document.getElementById("mapView"), mapOptions);
 
        navigator.geolocation.getCurrentPosition(function(pos) {
            map.setCenter(new google.maps.LatLng(pos.coords.latitude, pos.coords.longitude));
        });
		
		//Build query string
		var queryStr = "";
		var numQuery = 0;
		if ($stateParams.fstatus != null ||
			$stateParams.ftype != null ||
			$stateParams.ffavs != null ||
			($stateParams.fradius != null &&
			$stateParams.flat != null &&
			$stateParams.flong != null))
			queryStr += "?";
		if ($stateParams.fstatus != null){
			queryStr = queryStr + "status=" + $stateParams.fstatus;
			numQuery++;
		}
		if ($stateParams.ftype != null){
			if (numQuery > 0)
				queryStr += "&";
			queryStr = queryStr + "type=" + $stateParams.ftype;
			numQuery++;
		}
		if ($stateParams.fradius != null && 
			$stateParams.flat != null && 
			$stateParams.flong != null){
				if (numQuery > 0)
					queryStr += "&";
				queryStr = queryStr + "radius=" + $stateParams.fradius + "&latitude=" + $stateParams.flat + "&longitude=" + $stateParams.flong;
				numQuery++;
		}
		if ($stateParams.ffavs){
			if (numQuery > 0)
					queryStr += "&";
				queryStr = queryStr + "user=" + currentUserId;
		}
		
		$http.get(ApiEndpoint.url + '/search' + queryStr)
		.success(function(data, status, headers, config){
			console.log('Success');
			console.log(data);
			$scope.places = data;
			
			//Plot markers
			if ($scope.places.length > 0){
				$scope.places.forEach(function(element){
					var myLatlng = new google.maps.LatLng(element.latitude, element.longitude);
					
					//Source for changing icons:
					//http://stackoverflow.com/questions/7095574/google-maps-api-3-custom-marker-color-for-default-dot-marker
					if (element.type == "Bathroom")
						var letter = 'B';
					else
						var letter = 'W';
					
					if (element.status == "Open")
						var color = "82E0AA";
					else
						var color = "FE7569"
					
					var marker = new google.maps.Marker({
						position: myLatlng,
						title: element.name,
						icon: "http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=" + letter + "|" + color,
						map: map
					});
					
					//Create windows
					//Source: http://en.marnoto.com/2013/12/mapa-com-varios-marcadores-google-maps.html
					infoWindow = new google.maps.InfoWindow();
					
					google.maps.event.addListener(marker, 'click', function() {
			  
					  // Variable to define the HTML content to be inserted in the infowindow
					  var iwContent = '<div id="iw_container">' +
					  '<div class="iw_title"><span class="infoText"><b>' + element.name + '</b></span></div><br>' +
					  '<div class="iw_content"><span class="infoText">' + element.type + ' | ' + element.status + ' | Votes: ' + element.vote + '' + '</span><br><a href="#/app/places/' + element.id + '">View Details</a></div></div>';
					  
					  // including content to the infowindow
					  infoWindow.setContent(iwContent);

					  // opening the infowindow in the current map and at the current marker location
					  infoWindow.open(map, marker);
				   });
				})
			}
			
			
			//http://stackoverflow.com/questions/17264989/how-to-manually-reload-google-map-with-javascript
			google.maps.event.trigger(map, 'resize');
			
			//Recenter
			navigator.geolocation.getCurrentPosition(function(pos) {
				map.setCenter(new google.maps.LatLng(pos.coords.latitude, pos.coords.longitude));
			});
				
		})
		.error(function(data, status, headers, config){
			console.log('Failure');
			console.log(data);
		})	
		
		$scope.map = map;
    };
	
	$scope.navPlaces = function(){
		$ionicHistory.nextViewOptions({
			disableBack: true
		});
		
		$state.go('app.places', {ftype: $stateParams.ftype, fstatus: $stateParams.fstatus, ffavs: $stateParams.ffavs, fradius: $stateParams.fradius, flat: $stateParams.flat, flong: $stateParams.flong});
	}
	
	$scope.go = function(path){
		if (path == "app.filter")
			$state.go(path, {fpath: "app.map"});
	};
})

.controller('PlaceCtrl', function($scope, $stateParams, $http, ApiEndpoint, $ionicLoading, $state) {
	$scope.place = {};
	
	$http.get(ApiEndpoint.url + '/places?id=' + $stateParams.placeId)
	.success(function(data, status, headers, config){
		console.log('Success');
		console.log(data);
		
		if (currentUserId != -1){
			$http.get(ApiEndpoint.url + '/users?id=' + currentUserId + "&fields=favorite&favType=ids")
				.success(function(userdata, status, headers, config){
					console.log('Success');
					console.log(userdata);
					if (userdata.includes(data.id))
						$scope.place = new myPlace(data, true);
					else
						$scope.place = new myPlace(data, false);
					initMap();
					console.log($scope.place);
				})
				.error(function(userdata, status, headers, config){
					console.log('Failure');
					console.log(userdata);
				})
		}
		else{
			$scope.place = new myPlace(elm, false);
			initMap();
			console.log($scope.place);
		}

	})
	.error(function(data, status, headers, config){
		console.log('Failure');
		console.log(data);
	})	
	
	var initMap = function() {
        var myLatlng = new google.maps.LatLng($scope.place.latitude, $scope.place.longitude);
 
        var mapOptions = {
            center: myLatlng,
            zoom: 15,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
 
        var map = new google.maps.Map(document.getElementById("map"), mapOptions);
		
		var marker = new google.maps.Marker({
			position: myLatlng,
			map: map
		});
	};
		
	$scope.voteUp = function(placeId){
		//console.log("Id: " + placeId);

		var req = {
			method: 'PUT', 
			url: ApiEndpoint.url + '/places',
			data: "id=" + placeId + "&vote=up",
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}
		$http(req).success(function(data) { 
			console.log('Success');
			console.log(data);
			$scope.place = data;
		}).error(function(data) { 
			console.log('Failure');
			console.log(data);  
		});
		
		$http.get(ApiEndpoint.url + '/places?id=' + $stateParams.placeId)
		.success(function(data, status, headers, config){
			console.log('Success');
			console.log(data);
			$scope.place = data;
		})
		.error(function(data, status, headers, config){
			console.log('Failure');
			console.log(data);
		})	
		
	};
	
	$scope.voteDown = function(placeId){
		console.log("Id: " + placeId);

		var req = {
			method: 'PUT', 
			url: ApiEndpoint.url + '/places',
			data: "id=" + placeId + "&vote=down",
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}
		$http(req).success(function(data) { 
			console.log('Success');
			console.log(data);
			$scope.place = data;
		}).error(function(data) { 
			console.log('Failure');
			console.log(data);  
		});
		
		$http.get(ApiEndpoint.url + '/places?id=' + $stateParams.placeId)
		.success(function(data, status, headers, config){
			console.log('Success');
			console.log(data);
			$scope.place = data;
		})
		.error(function(data, status, headers, config){
			console.log('Failure');
			console.log(data);
		})	
	};
	
	$scope.favorite = function(id, fav, $event){
		
		//Add place to favorites
		if (!fav){
			console.log("Add place " + id + " to favorites");
			var req = {
				method: 'PUT', 
				url: ApiEndpoint.url + '/users',
				data: "id=" + currentUserId + "&favorite=" + id,
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			}
			$http(req).success(function(resData) { 
				console.log('Success');
				console.log(resData);
				$state.reload();
			}).error(function(resData) { 
				console.log('Failed to update place');
				console.log(resData); 
			});
		}
		//Remove place from favorites
		else{
			console.log("Remove place " + id + " from favorites");
			
			var req = {
				method: 'DELETE', 
				url: ApiEndpoint.url + '/users/' + currentUserId + '/place?id=' + id
			}
			$http(req).success(function(resData) { 
				console.log('Success');
				console.log(resData);
				$state.reload();
			});
		}
	};
	
	$scope.go = function(path, id){
		$state.go(path, {placeId: id});
	};
})

.controller('FilterCtrl', function($scope, $stateParams, $http, ApiEndpoint, $state, $ionicHistory) {
	$scope.data = {};
	//console.log($scope.data);
	
	//Updates map with coordinates and sets the data.location variable in the scope
	var updateMap = function(posLat, posLong, notInit){
		if (notInit){
			var trLat = posLat.toFixed(5);
			var trLong = posLong.toFixed(5);
			$scope.data.flocation = trLat + ", " + trLong;
		}
		
		var myLatlng = new google.maps.LatLng(posLat, posLong);
	 
		var mapOptions = {
			center: myLatlng,
			zoom: 16,
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};
 
		var map = new google.maps.Map(document.getElementById("mapFilter"), mapOptions);
 
		var marker = new google.maps.Marker({
			position: myLatlng,
			map: map
		});
	};
	
	//Initializes map to current position and calls update map with coordinates
	$scope.init =  function() {
		navigator.geolocation.getCurrentPosition(function(pos) {
			var posLat = pos.coords.latitude;
			var posLong = pos.coords.longitude;
			
			updateMap(posLat, posLong, false);
		});
		$scope.data = {};
    };
	
	//Gets current location and calls update map with coordinates
	$scope.getCurrent =  function() {
		navigator.geolocation.getCurrentPosition(function(pos) {
			var posLat = pos.coords.latitude;
			var posLong = pos.coords.longitude;
			
			updateMap(posLat, posLong, true);
		});
    };
	
	//Get geocode for address, then call update map with new coordinates
	$scope.getAddress =  function() {
		var geocoder = new google.maps.Geocoder();
		var address = $scope.data.flocation;
		console.log(address);
		geocoder.geocode({'address': address}, function(results, status){
			if (status == 'OK'){
				console.log(results);
				var posLat = results[0].geometry.location.lat();
				var posLong = results[0].geometry.location.lng();
			
				updateMap(posLat, posLong,true);
			} else{
				alert('Location address not valid, please enter a new address and hit the update button again');
			}
		});
    };

	//Send search criteria to view places
	$scope.applyFilter = function(data){
		
		var path = $stateParams.fpath;
		console.log(path);
		
		console.log("Location: " + data.location);
		console.log("Radius: " + data.radius);
		console.log("Type: " + data.type);
		console.log("Status: " + data.pstatus);
		console.log("Favorites: " + data.favs)
		var userFavs = false;
		if ($scope.data.favs)
			userFavs = true;
		
		if (data.flocation != null && data.radius != null){
			var geocoder = new google.maps.Geocoder();
			var address = data.flocation;
			console.log(address);
			geocoder.geocode({'address': address}, function(results, status){
				if (status == 'OK'){
					console.log(results);
					var posLat = results[0].geometry.location.lat();
					var posLong = results[0].geometry.location.lng();
				
					updateMap(posLat, posLong,true);
					$state.go(path, {ftype: data.type, fstatus: data.pstatus, ffavs: data.favs, fradius: data.radius, flat: posLat, flong: posLong});
				} else{
					alert('Location address not valid, please enter a new address');
				}
			});
		}
		else{
			$ionicHistory.nextViewOptions({
				disableBack: true
			});
			
			$state.go(path, {ftype: data.type, fstatus: data.pstatus, ffavs: data.favs});
		}
	};
	
	$scope.resetFilter = function(){
		$scope.data = {};
	}
})

.controller('AddPlaceCtrl', function($scope, $http, $state, ApiEndpoint, $ionicLoading){
	$scope.data = {};
	
	//Updates map with coordinates and sets the data.location variable in the scope
	var updateMap = function(posLat, posLong, notInit){
		if (notInit){
			var trLat = posLat.toFixed(5);
			var trLong = posLong.toFixed(5);
			$scope.data.location = trLat + ", " + trLong;
		}
		
		var myLatlng = new google.maps.LatLng(posLat, posLong);
	 
		var mapOptions = {
			center: myLatlng,
			zoom: 16,
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};
 
		var map = new google.maps.Map(document.getElementById("mapAdd"), mapOptions);
 
		var marker = new google.maps.Marker({
			position: myLatlng,
			map: map
		});
	};
	
	//Initializes map to current position and calls update map with coordinates
	$scope.init =  function() {
		navigator.geolocation.getCurrentPosition(function(pos) {
			var posLat = pos.coords.latitude;
			var posLong = pos.coords.longitude;
			
			updateMap(posLat, posLong, false);
		});
    };
	
	//Gets current location and calls update map with coordinates
	$scope.getCurrent =  function() {
		navigator.geolocation.getCurrentPosition(function(pos) {
			var posLat = pos.coords.latitude;
			var posLong = pos.coords.longitude;
			
			updateMap(posLat, posLong, true);
		});
    };
	
	//Get geocode for address, then call update map with new coordinates
	$scope.getAddress =  function() {
		var geocoder = new google.maps.Geocoder();
		var address = $scope.data.location;
		console.log(address);
		geocoder.geocode({'address': address}, function(results, status){
			if (status == 'OK'){
				console.log(results);
				var posLat = results[0].geometry.location.lat();
				var posLong = results[0].geometry.location.lng();
			
				updateMap(posLat, posLong,true);
			} else{
				alert('Location address not valid, please enter a new address and hit the update button again');
			}
		});
    };

	//Get data from form, validate, and send post request
	$scope.addPlace = function(){
		
		//Validate form
		var valid = false, vLoc = false;
		
		//Check name and type
		var vName = ($scope.data.name != null) ? true : false;
		var vType = ($scope.data.type != null) ? true : false;
		//Get status
		var pStatus = ($scope.data.status == true) ? 'Open' : 'Closed';
			
		//Check location
		if ($scope.data.location != null)
			vLoc = true;
		
		//Check if all fields valid, otherwise issue alert
		console.log("Valid: " + vName + vType + vLoc);
		if (vName && vType && vLoc)
			valid = true;
		else{
			var alertStr = "";
			var fCount = 0;
			if (!vName){
				alertStr += "Please provide a name";
				fCount++;
			}
			if (!vLoc){
				if (fCount > 0)
					alertStr += " and the location";
				else
					alertStr += "Please provide the location";
				fCount++;
			}
			if (fCount > 0)
				alertStr += " for the place you are adding."
			if (!vType){
				if (fCount > 0)
					alertStr += " Also select a type for the place.";
				else
					alertStr += "Please select a type for the place you are adding.";
			}
			alert(alertStr);
		}
		
		//Check that user is logged in
		if (currentUserId == -1){
			alert('Please login to add a new place');
			valid = false;
		}
		
		if (valid){
			//Get address coordinates
			var geocoder = new google.maps.Geocoder();
			var address = $scope.data.location;
			geocoder.geocode({'address': address}, function(results, status){
				if (status == 'OK'){
					console.log(results);
					var posLat = results[0].geometry.location.lat();
					var posLong = results[0].geometry.location.lng();
					updateMap(posLat, posLong,true);
					
					//Create post request to add place
					//Login not implemented yet so hardcoding user Id
					var req = {
						method: 'POST', 
						url: ApiEndpoint.url + '/places',
						data: "type=" + $scope.data.type + "&name=" + $scope.data.name + "&status=" + pStatus + "&userId=" + currentUserId + "&latitude=" + posLat + "&longitude=" + posLong,
						headers: {'Content-Type': 'application/x-www-form-urlencoded'}
					}
					$http(req).success(function(postData) { 
						console.log('Success');
						console.log(postData);
						$scope.place = postData;
						$scope.data = {};
						$state.go('app.single', {placeId: $scope.place.id});
					}).error(function(data) { 
						console.log('Failed to add place');
						console.log(data);  
						alert("This place already exists.");
					});
				} else{
					alert('Location address not valid, please enter a new address');
				}
			});
		}		
	};
	
	//Reset form
	$scope.addReset = function(){
		$scope.data = {};
	}
})

.controller('EditPlaceCtrl', function($scope, $http, $state, $stateParams, ApiEndpoint, $ionicLoading){
	$scope.data = {};
	
	//Initialize page - update map and fill in values
	$scope.init = function() {
		
		//Get data
		$http.get(ApiEndpoint.url + '/places?id=' + $stateParams.placeId)
		.success(function(results, status, headers, config){
			console.log('Success');
			console.log(results);
			$scope.data = results;
			
			//Update form
			var name = document.getElementById('editName');
			name.value = $scope.data.name;
			
			var type = document.getElementById('editType');
			type.value = $scope.data.type;
			
			console.log("Check status: " + ($scope.data.status == "Open"));
			if ($scope.data.status == "Open")
				$scope.data.status = true;
			else
				$scope.data.status = false;
				
			var location = document.getElementById('editLoc');
			var eLat = $scope.data.latitude;
			var eLong = $scope.data.longitude;
			var trLat = eLat.toFixed(5);
			var trLong = eLong.toFixed(5);
			location.value = trLat + ", " + trLong;
			console.log(location.value);
			
			//Update map
			var myLatlng = new google.maps.LatLng(eLat, eLong);
 
			var mapOptions = {
				center: myLatlng,
				zoom: 15,
				mapTypeId: google.maps.MapTypeId.ROADMAP
			};
	 
			var map = new google.maps.Map(document.getElementById("mapEdit"), mapOptions);
			
			var marker = new google.maps.Marker({
				position: myLatlng,
				map: map
			});
		})
		.error(function(results, status, headers, config){
			console.log('Failure');
			console.log(results);
		})
	};
	
	//Updates map with coordinates and sets the data.location variable in the scope
	var updateMap = function(posLat, posLong, notInit){
		if (notInit){
			var trLat = posLat.toFixed(5);
			var trLong = posLong.toFixed(5);
			document.getElementById('editLoc').value = trLat + ", " + trLong;
		}
		
		var myLatlng = new google.maps.LatLng(posLat, posLong);
	 
		var mapOptions = {
			center: myLatlng,
			zoom: 16,
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};
 
		var map = new google.maps.Map(document.getElementById("mapEdit"), mapOptions);
 
		var marker = new google.maps.Marker({
			position: myLatlng,
			map: map
		});
	};
	
	//Gets current location and calls update map with coordinates
	$scope.getCurrent =  function() {
		navigator.geolocation.getCurrentPosition(function(pos) {
			var posLat = pos.coords.latitude;
			var posLong = pos.coords.longitude;
			
			updateMap(posLat, posLong, true);
		});
    };
	
	//Get geocode for address, then call update map with new coordinates
	$scope.getAddress =  function() {
		var geocoder = new google.maps.Geocoder();
		var address = document.getElementById('editLoc').value;
		console.log("Address: " + address);
		geocoder.geocode({'address': address}, function(results, status){
			if (status == 'OK'){
				console.log(results);
				var posLat = results[0].geometry.location.lat();
				var posLong = results[0].geometry.location.lng();
			
				updateMap(posLat, posLong,true);
			} else{
				alert('Location address not valid, please enter a new address and hit the update button again');
			}
		});
    };

	//Get data from form, validate, and send post request
	$scope.editPlace = function(){
		
		//Validate form
		var valid = false, vLoc = false;
		
		//Check name and type
		var vName = ($scope.data.name != null) ? true : false;
		var vType = ($scope.data.type != null) ? true : false;
		//Get status
		var pStatus = ($scope.data.status == true) ? 'Open' : 'Closed';
			
		//Check location
		console.log("Location: " + $scope.data.location);
		console.log("Location: " + document.getElementById('editLoc').value);
		if (document.getElementById('editLoc').value != null)
			vLoc = true;
		
		//Check if all fields valid, otherwise issue alert
		console.log("Valid: " + vName + vType + vLoc);
		if (vName && vType && vLoc)
			valid = true;
		else{
			var alertStr = "";
			var fCount = 0;
			if (!vName){
				alertStr += "Please provide a name";
				fCount++;
			}
			if (!vLoc){
				if (fCount > 0)
					alertStr += " and the location";
				else
					alertStr += "Please provide the location";
				fCount++;
			}
			if (fCount > 0)
				alertStr += " for the place you are adding."
			if (!vType){
				if (fCount > 0)
					alertStr += " Also select a type for the place.";
				else
					alertStr += "Please select a type for the place you are adding.";
			}
			alert(alertStr);
		}
		
		if (valid){
			//Get address coordinates
			var geocoder = new google.maps.Geocoder();
			var address = document.getElementById('editLoc').value;
			console.log("Address: " + address);
			geocoder.geocode({'address': address}, function(results, status){
				if (status == 'OK'){
					console.log(results);
					var posLat = results[0].geometry.location.lat();
					var posLong = results[0].geometry.location.lng();
					updateMap(posLat, posLong,true);
					
					//Create post request to add place
					//Login not implemented yet so hardcoding user Id
					var req = {
						method: 'PUT', 
						url: ApiEndpoint.url + '/places',
						data: "type=" + $scope.data.type + "&name=" + $scope.data.name + "&status=" + pStatus + "&latitude=" + posLat + "&longitude=" + posLong + "&id=" + $stateParams.placeId,
						headers: {'Content-Type': 'application/x-www-form-urlencoded'}
					}
					$http(req).success(function(postData) { 
						console.log('Success');
						console.log(postData);
						$scope.place = postData;
						$scope.data = {};
						$state.go('app.single', {placeId: $scope.place.id});
					}).error(function(data) { 
						console.log('Failed to update place');
						console.log(data); 
						alert("The details for this update already exist in another place. Please update that place as needed.");
					});
				} else{
					alert('Location address not valid, please enter a new address');
				}
			});
		}		
	};
	
	$scope.go = function(path, id){
		$state.go(path, {placeId: id});
	};

});
