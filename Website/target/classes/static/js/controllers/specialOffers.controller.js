angular.module('PubMeApp').controller('specialOffersController', specialOffersController);
specialOffersController.$inject = ['$scope', 'sharedData', 'initial', 'dataCalls'];

function specialOffersController ($scope, sharedData, initial, dataCalls) {

    $scope.pub = initial.data;
    $scope.newOfferShow = false;
    $scope.offers = $scope.pub.promotions;

    $scope.addNewOffer = function() {
        $scope.newOfferShow = true;
        $scope.newOffer = new Offer("New offer");
    };

    $scope.submitOffer = function() {
        $scope.newOfferShow = false;
        $scope.pub.promotions.push($scope.newOffer);

        var request = {
            pubToSave: $scope.pub
        };

        dataCalls.updatePub(request).then(function(result){
            console.log(result);
            swal({
                title: "Offer saved",
                type: "success",
                closeOnConfirm: true
            });
        });
    };

    $scope.saveAfterEdit = function(editedOffer) {
        $scope.$applyAsync();

        $scope.pub.promotions.forEach(function(promotion){
           if (promotion == editedOffer){
               console.log("Aici");
           }
        });


        var request = {
            pubToSave: $scope.pub
        };

        dataCalls.updatePub(request).then(function(result){
            console.log("A mers");
            console.log(result);
        });
    };

    $scope.removeOffer = function(offerToRemove) {
        $scope.pub.promotions = $scope.pub.promotions.filter(function(promotion){
            return promotion != offerToRemove;
        });

        var request = {
            pubToSave: $scope.pub
        };

        dataCalls.updatePub(request).then(function(result){
            $scope.offers = $scope.pub.promotions;
            swal("Deleted!", "Offer deleted", "success");
            console.log(result);
        });
    };

    function Offer(title){
        this.title = title;
        this.startDate = "01/01/16 12:00";
        this.endDate = "02/01/16 23:59";
        this.description = "Description";
        this.content = "Content";
    }
}