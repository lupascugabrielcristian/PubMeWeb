var AllCtrl = function ($scope, initial, user) {
  $scope.var = "Home Controller ok";

  $scope.pubs = initial.data.allpubs;
  $scope.visitors = initial.data.visitors;
};

AllCtrl.$inject = ['$scope', 'initial', 'user'];
angular.module('pubme').controller('AllCtrl', AllCtrl);
