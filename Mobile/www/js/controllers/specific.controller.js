var specificCtrl = function($scope, serverCalls, initial, user, $rootScope, $ionicPopup) {

  $scope.pub = initial.data;
  $scope.favorite = user.isPubFavorite($scope.pub.id);

  serverCalls.getOwner($scope.pub.ownerId).then(function(result){
    $scope.owner = result.data;
  });
  $scope.userLoggedIn = user.isLoggedIn();

  $scope.checkIn = function() {

    var date = new Date();
    var dayOfWeek = date.getDay();
    var dayOfMonth = date.getDate();
    console.log("Day " + dayOfWeek + " of month " + dayOfMonth);
    serverCalls.addVisitor($scope.pub.id, user.getUserId(), dayOfWeek, dayOfMonth).then(function(result){
      console.log(result);
      if (result.data.result == "Operation succeded"){
        $scope.popup = $ionicPopup.show({
          title: 'Succeeded',
          templateUrl: 'templates/checkInSucess.Popover.html',
          scope: $scope
        });
      }
    });
  };

  $scope.updateFavorite = function() {
    serverCalls.favoritePub(user.getUserId(), $scope.pub.id).then(function(response){
        if (response.data.operationSuccessful){
          $scope.pub.favorite = user.toggleFavoritePub($scope.pub.id);
          $rootScope.$applyAsync();
        }
    });
  };

  $scope.closePopup = function() {
    $scope.popup.close();

  };
};

specificCtrl.$inject = ['$scope', 'serverCalls', 'initial', 'user', '$rootScope', '$ionicPopup'];
angular.module('pubme').controller('specificCtrl', specificCtrl);
