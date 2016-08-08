angular.module('PubMeApp').controller('loginController', loginController);
loginController.$inject = ['$scope', 'dataCalls', 'sharedData', '$state'];

function loginController ($scope, dataCalls, sharedData, $state) {

    $scope.loginName = null;
    $scope.email = null;
    $scope.password = null;

    $scope.submitLogin = function() {

        var request = {
            email: $scope.email,
            password: $scope.password
        };

        if ($scope.email){
            var prm = dataCalls.submitLogin(request);
            prm.then(function(owner){
                $scope.loginName = owner.data.fullName;
                sharedData.setLoggedInUser(owner.data);
                $state.go('home', {user: $scope.loginName});
                    //showSuccessWindow();
            });
        }
    };
}