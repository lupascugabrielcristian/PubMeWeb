var myPubsCtrl = function ($scope, initial, user) {

  $scope.userPubs = initial.data.pubs;
  $scope.visitors = initial.data.visitors;

};

myPubsCtrl.$inject = ['$scope', 'initial', 'user'];
angular.module('pubme').controller('myPubsCtrl', myPubsCtrl);
