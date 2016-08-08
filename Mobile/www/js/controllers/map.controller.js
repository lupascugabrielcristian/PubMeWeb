var MapCtrl = function ($scope, $cordovaGeolocation, $ionicPopup, markerProvider, initial) {

  var allPubs = initial.data;
  var options = {timeout: 10000, enableHighAccuracy: true};

  ionic.Platform.ready(function(){
    // Code goes here


    $cordovaGeolocation.getCurrentPosition(options).then(function (position) {

        var latLng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);

        var mapOptions = {
          center: latLng,
          zoom: 13,
          mapTypeId: google.maps.MapTypeId.ROADMAP,
          disableDefaultUI: true,
          zoomControl: true
        };

        $scope.map = new google.maps.Map(document.getElementById("map"), mapOptions);

        google.maps.event.addListenerOnce($scope.map, 'idle', function(){

          markerProvider.getMarkersForPubs(allPubs, $scope.map);
        });

    }, function (error) {
      console.log("Could not get location");
    });

  });

  $scope.clickedPub = null;


  var clickOnMap = 0;
  var clickOnPub = 0;
  $scope.$on("clicked", function(event, args){
      $scope.clickedPub = args;
      clickOnPub = 1;
  });

  $scope.cancelPub = function() {
    clickOnMap = 1;
    setTimeout(function(){
      if (clickOnMap && !clickOnPub){
        clickOnMap = 0;
        clickOnPub = 0;
        $scope.clickedPub = null;
        $scope.$applyAsync();
      }
      else {
        clickOnPub = 0;
      }
    }, 100);
  };

  $scope.popup;
  $scope.openPopup = function() {
    $scope.popup = $ionicPopup.show({
      title: 'Details',
      templateUrl: 'templates/pubDetails.Popover.html',
      scope: $scope
    });

    $scope.popup.then(function(res) {
      if(res) {
        console.log('You are sure');
      } else {
        console.log('You are not sure');
      }
    });
  };


  $scope.closePopup = function() {
    $scope.popup.close();

  };
};

MapCtrl.$inject = ['$scope', '$cordovaGeolocation', '$ionicPopup', 'markerProvider', 'initial'];
angular.module('starter.controllers').controller('MapCtrl', MapCtrl);
