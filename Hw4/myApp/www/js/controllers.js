angular.module('starter.controllers', [])

.constant('ApiEndpoint', {
	url:'http://192.168.1.11:8100/api'
})
//url:'http://192.168.1.11:8100/api'
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
		//queryStr += $stateParams.fstatus ? "Open" : "Closed";
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

.controller('PlaceCtrl', function($scope, $stateParams, $http, ApiEndpoint) {
	$scope.place = "";
	
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
			//$stateParams.place.vote = newData.vote;
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
			//$stateParams.place.vote = newData.vote;
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
		console.log("Status: " + data.pstatus);
		
		$state.go('app.places', {ftype: data.type, fstatus: data.pstatus, fradius: data.radius, flat: data.location, flong: data.location});
	}

})

//, $cordovaGeolocation, $ionicLoading
.controller('AddPlaceCtrl', function($scope, $http, ApiEndpoint){
	$scope.data = "";
	
	/*ionic.Platform.ready(function(){
        var posOptions = {timeout: 10000, enableHighAccuracy: false};
	$cordovaGeolocation
	.getCurrentPosition(posOptions)
	
   .then(function (position) {
      console.log(position.coords.latitude + '   ' + position.coords.longitude);
	  $scoped.data = {"latitude": position.coords.latitude, "longitude": position.coords.longitude};
   }, function(err) {
      console.log(err);
   })
    });*/
	
});
