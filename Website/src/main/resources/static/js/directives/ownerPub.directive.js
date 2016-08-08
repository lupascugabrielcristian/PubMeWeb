angular.module('PubMeApp').directive('ownerPub', ownerPub);
ownerPub.$inject = ['$state', 'dataCalls', 'sharedData'];

function ownerPub ($state, dataCalls, sharedData) {
    return {
        restrict: 'EC',
        templateUrl: '/js/directives/ownerPub.templ.html',
        scope: {
            item: '='
        },
        controller: function($scope, $attrs){
            $scope.var = "Directive ok";
            if ($scope.item.images[0] != undefined){
                $scope.imagePath = $scope.item.images[0];
            }
            else {
                $scope.imagePath = "/img/sample_pub.png";
            }


            $scope.editPub = function(id) {
                $state.go('editPub', {'pubId': id} );
            };
            $scope.viewSpecialOffers = function(id) {
                $state.go('specialOffers', {'pubId' : id});
            };

            $scope.deleteOwnerPub = function(pubid) {
                var request = {
                    pubId: pubid,
                    ownerId: sharedData.getLoggedInUser().id
                };

                dataCalls.deletePubFromOwner(request).then(function(result){
                    if (result.data.operationSuccess){
                        swal("Pub deleted");
                    }
                    else {
                        swal("Operaion failed");
                    }
                    console.log(result.data);
                });
            };

            $scope.showDetails = function(pubId){
                $state.go('pubDetails', {'pubId': pubId});
            }
        }
    }
}