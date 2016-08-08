var editPubController = function($scope, initial, dataCalls){

    $scope.pub = initial.data;
    $scope.nume = $scope.pub.name;
    $scope.description = $scope.pub.description;
    $scope.address = $scope.pub.address;
    $scope.type = $scope.pub.type;

    $scope.visibility = {
        nume: false,
        description: false,
        type: false,
        address: false
    };

    $scope.changeVisibility = function(atribute) {
        if ($scope.visibility[atribute]){
            $scope.visibility[atribute] = false;
        }
        else {
            $scope.visibility[atribute] = true;
        }
    };

    $scope.saveChanges = function() {
        var request = {
            pubToSave: $scope.pub
        };
        dataCalls.updatePub(request).then(function(result){
            swal({
                title: "Changes saved"
            });
        });
    };
};


editPubController.$inject =['$scope', 'initial', 'dataCalls'];
angular.module('PubMeApp').controller('editPubController', editPubController);
