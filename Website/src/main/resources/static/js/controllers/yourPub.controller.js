angular.module('PubMeApp').controller('yourPubController', yourPubController);
yourPubController.$inject = ['$scope', 'sharedData', 'initial'];

function yourPubController ($scope, sharedData, initial) {
    $scope.showNoPubsWarning = false;
    if (initial.data == undefined){
        $scope.showNoPubsWarning = true;
    }

    $scope.pubs = initial.data.pubs;

    if (sharedData.getLoggedInUser() != null){
        $scope.showOwnerTitle = true;
        $scope.ownerName = sharedData.getLoggedInUser().fullName;
    }
    else {
        $scope.showOwnerTitle = false;
    }
}