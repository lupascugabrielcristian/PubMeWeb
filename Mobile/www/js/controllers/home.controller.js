var HomeCtrl = function ($scope, user) {
  $scope.var = "Home Controller ok";
  $scope.userLoggedIn = user.isLoggedIn();
};

HomeCtrl.$inject = ['$scope', 'user'];
angular.module('starter.controllers').controller('HomeCtrl', HomeCtrl);
