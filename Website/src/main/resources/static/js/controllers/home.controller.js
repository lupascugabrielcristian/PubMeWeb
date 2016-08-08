

var homeController = function($scope, $stateParams){
    $scope.loginName = null;
    $scope.loginName = $stateParams.user;

    if ($scope.loginName != null) {
        $scope.title = "PubMe Owner's side - " + $scope.loginName;
    }
    else {
        $scope.title = "PubMe Owner's side";
    }
};


homeController.$inject =['$scope', '$stateParams'];
angular.module('PubMeApp').controller('homeController', homeController);
