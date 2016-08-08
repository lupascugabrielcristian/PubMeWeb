var AboutCtrl = function ($scope) {
  $scope.var = "About Controller ok";
};

AboutCtrl.$inject = ['$scope'];
angular.module('starter.controllers').controller('AboutCtrl', AboutCtrl);
