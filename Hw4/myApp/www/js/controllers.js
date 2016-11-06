angular.module('starter.controllers', [])

.constant('ApiEndpoint', {
	url:'http://localhost:8100/api'
})
//url:'http://192.168.1.11:8100/api'
//url:'http://localhost:8100/api'
//url:'http://runnersaidapp2.appspot.com/api/v1'

.controller('AppCtrl', function($scope, $ionicModal, $timeout) {

  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //$scope.$on('$ionicView.enter', function(e) {
  //});

  // Form data for the login modal
  $scope.loginData = {};

  // Create the login modal that we will use later
  $ionicModal.fromTemplateUrl('templates/login.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.modal = modal;
  });

  // Triggered in the login modal to close it
  $scope.closeLogin = function() {
    $scope.modal.hide();
  };

  // Open the login modal
  $scope.login = function() {
    $scope.modal.show();
  };

  // Perform the login action when the user submits the login form
  $scope.doLogin = function() {
    console.log('Doing login', $scope.loginData);

    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system
    $timeout(function() {
      $scope.closeLogin();
    }, 1000);
  };
})

.controller('ViewPlacesCtrl', function($scope, $http, ApiEndpoint, $state, $stateParams){
	$scope.places = "";
	
	//Build query string
	var queryStr = "";
	var numQuery = 0;
	if ($stateParams.fstatus != null ||
		$stateParams.ftype != null ||
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
	}
	console.log("Query: " + queryStr)
	
	$http.get(ApiEndpoint.url + '/search' + queryStr)
		.success(function(data, status, headers, config){
			console.log('Success');
			console.log(data);
			$scope.places = data;
		})
		.error(function(data, status, headers, config){
			console.log('Failure');
			console.log(data);
		})
	
	$scope.favorite = function(place, $event){
		alert("Now a favorite");
		$event.preventDefault();
	}
	
	$scope.go = function(path){
		$state.go(path);
	}
})

.controller('PlaceCtrl', function($scope, $stateParams, $http, ApiEndpoint, $ionicLoading) {
	$scope.place = "";
	
	$http.get(ApiEndpoint.url + '/places?id=' + $stateParams.placeId)
		.success(function(data, status, headers, config){
			console.log('Success');
			console.log(data);
			$scope.place = data;
			init();
		})
		.error(function(data, status, headers, config){
			console.log('Failure');
			console.log(data);
		})	
	
	var init = function() {
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
		console.log("Id: " + placeId);

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
		
	}
	
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
	}
})

.controller('FilterCtrl', function($scope, $stateParams, $http, ApiEndpoint, $state) {
	$scope.filter = "";
	console.log($scope.data);
	
	$scope.applyFilter = function(data){
		$state.go('app.places', {ftype: data.type, fstatus: data.pstatus, fradius: data.radius, flat: data.location, flong: data.location});
	}

})

.controller('AddPlaceCtrl', function($scope, $http, ApiEndpoint, $ionicLoading){
	$scope.data = {};
	
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

	
	$scope.init =  function() {
		var posLat, posLong;
		
		navigator.geolocation.getCurrentPosition(function(pos) {
			posLat = pos.coords.latitude;
			var trLat = posLat.toFixed(5);
			posLong = pos.coords.longitude;
			var trLong = posLong.toFixed(5);
			
			updateMap(posLat, posLong, false);
		});
    };
	
	$scope.getCurrent =  function() {
		var posLat, posLong;
		
		navigator.geolocation.getCurrentPosition(function(pos) {
			posLat = pos.coords.latitude;
			posLong = pos.coords.longitude;
			
			updateMap(posLat, posLong, true);
		});
    };
	
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

	
});
