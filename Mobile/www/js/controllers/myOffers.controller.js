var myOffersCtrl = function ($scope, initial, user) {

  $scope.userLogedIn = user.isLoggedIn();
  $scope.offers = initial.data.result;

};

myOffersCtrl.$inject = ['$scope', 'initial', 'user'];
angular.module('pubme').controller('myOffersCtrl', myOffersCtrl);
