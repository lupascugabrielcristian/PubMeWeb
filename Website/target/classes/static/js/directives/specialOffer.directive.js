angular.module('PubMeApp').directive('specialOffer', specialOffer);
specialOffer.$inject = ['$state'];

function specialOffer ($state) {
    return {
        restrict: 'AE',
        templateUrl: '/js/directives/specialOffer.templ.html',
        scope: {
            item: '=',
            savefc: '=',
            removefc: '='
        },
        controller: function($scope, $attrs){
            $scope.var = $scope.item;
            $scope.modalShow = false;
            $scope.modalData = {
                description: "none"
            };

            $scope.editOffer = function(){
                $scope.modalShow = true;
                $scope.modalData = $scope.item;
            };

            $scope.removeOffer = function(){
                swal({
                    title: "Delete Offer",
                    type: "warning",
                    showCancelButton: true,
                    closeOnConfirm: false,
                    closeOnCancel: false
                },
                function(isConfirm){
                    if (isConfirm){
                        $scope.removefc($scope.item);
                    }
                    else {
                        swal("Cancelled", "Action is canceled", "error");
                    }
                });
            };

            $scope.save = function() {
                $scope.modalShow = false;
                console.log($scope.var);
                $scope.modalData = null;

                $scope.savefc($scope.item);
            };
        }
    }
}