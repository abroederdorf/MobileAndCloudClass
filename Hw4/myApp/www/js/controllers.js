angular.module('starter.controllers', [])
.constant('ApiEndpoint', {
	url:'http://runnersaidapp2.appspot.com/api/v1'
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

.controller('PlaylistCtrl', function($scope, $stateParams) {
})

.controller('ViewPlacesCtrl', function($scope, $http, ApiEndpoint){
	$scope.places = "";
	
	$http.get(ApiEndpoint.url + '/places')
		.success(function(data, status, headers, config){
			console.log('Success');
			console.log(data);
			$scope.places = data;
		})
		.error(function(data, status, headers, config){
			console.log('Failure');
			console.log(data);
		})
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
