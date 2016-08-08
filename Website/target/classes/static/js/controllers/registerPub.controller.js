angular.module('PubMeApp').controller('registerPubController', registerPubController);
registerPubController.$inject = ['$scope', 'dataCalls', 'sharedData', 'NgMap', 'FileUploader'];

function registerPubController ($scope, dataCalls,  sharedData, NgMap, FileUploader) {
    $scope.types = ['BAR', 'CLUB', 'RESTORANT', 'COFFEYSHOP', 'TERASA', 'UNKOWN'];

    NgMap.getMap().then(function(map) {
        $scope.map = map;
    });

    $scope.markers = [];
    $scope.marker = null;
    $scope.location = null;
    $scope.center = [44.44, 26.1];
    $scope.mapClick = function(event) {
        $scope.location = {
            lat: event.latLng.lat(),
            lng: event.latLng.lng()
        };

        $scope.marker = {
            position: $scope.location.lat + ", " + $scope.location.lng
        };
        $scope.center = [$scope.location.lat, $scope.location.lng];
    };

    $scope.address = null;
    $scope.setAddress = function() {
        $scope.center = $scope.address;
        $scope.marker = {
            position: $scope.address
        }
    };

    $scope.pubName = null;
    $scope.description = "Something catchy";
    $scope.type = "UNKNOWN";

    $scope.submitPub = function() {
        // test to make sure fields are not null


        // Java server = RegisterPubRequest
        var request = {
            name: $scope.pubName,
            ownerId: sharedData.getLoggedInUser().id,
            type: $scope.type,
            description: $scope.description,
            location: {
                latitude:  $scope.location.lat,
                longitude:  $scope.location.lng
            },
            address: $scope.address,
            promotions: [],
            imagePath: $scope.path
        };

        dataCalls.submitPub(request).then(function(result){
            swal("Pub " + result.data.registeredPub.name + " saved");
        }, function(reason){
            swal("Registration failed");
        });
    };

    $scope.chooseType = function(index) {
        $scope.type = $scope.types[index];
    };



    //Uploader
    $scope.uploader = new FileUploader({
        url: "/image"
    });

    $scope.getSize = function (sizeInBytes) {
        var size = sizeInBytes / 1048576;
        size = size.toFixed(2);

        if (size < 0.02) {
            size = sizeInBytes / 1024;
            size = size.toFixed(2);

            return size + "KB";
        }
        else {
            return size + " MB";
        }
    };

    $scope.uploader.onCompleteItem = function(item, response, status, headers){
        $scope.path = response.path;
    };

    $scope.myFile = "";
    $scope.fileName = "Choose a file";

    $scope.shouUtils = function() {
        console.log($scope.uploader);
    };

    function hideMarkers() {
        for (var key in $scope.map.markers) {
            $scope.map.markers[key].setMap(null);
        }
    }
}