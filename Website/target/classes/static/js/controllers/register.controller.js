angular.module('PubMeApp').controller('registerController', registerController);
registerController.$inject = ['$scope', 'dataCalls'];

function registerController ($scope, dataCalls) {
    $scope.firstName = null;
    $scope.lastName = "";
    $scope.email = "";

    $scope.registerOwner = function() {
        if ($scope.firstName){

            var request = {
                firstName: $scope.firstName,
                lastName: $scope.lastName,
                email: $scope.email
            };

            var prm = dataCalls.registerOwner(request);
            prm.then(function(data){
               $scope.showOk();
            });
        }
    };


    $scope.showOk = function() {
        swal({
            title: "Registration OK"
        })
    };
}