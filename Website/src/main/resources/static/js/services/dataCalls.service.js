angular.module('PubMeApp').factory('dataCalls', dataCalls);
dataCalls.$inject = ['$http'];

function dataCalls ($http) {
    var service = {
        getDatabase: getDatabase,
        getPub: getPub,
        registerOwner: registerOwner,
        submitLogin: submitLogin,
        submitPub: submitPub,
        getOwnersPubs: getOwnersPubs,
        updatePub: updatePub,
        uploadPicture: uploadPicture,
        deletePubFromOwner: deletePubFromOwner,
        getVisitorsDistribution: getVisitorsDistribution
    };

    function getDatabase() {
        return $http.get("/database");
    }

    function getPub(id) {
        return $http.get("/single?id=" + id);
    }

    function registerOwner(request) {
        return $http.post("/register/owner", request);
    }


    function submitLogin(request) {
        return $http.post("/login", request);
    }

    function submitPub(request) {
        return $http.post("/register/pub", request);
    }

    function getOwnersPubs(id){
        return $http.get("/ownersPubs?id=" + id);
    }

    /**
     * UpdatePubRequest is the corresponding java object
     */
    function updatePub(request){
        return $http.post("/update/pub", request);
    }

    function uploadPicture(file){
        var fd = new FormData();
        fd.append('file', file);
        return $http.post("/image", fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        });
    }

    function deletePubFromOwner(request){
        return $http.post("/deleteOwnerPub", request);
    }

    function getVisitorsDistribution(pubid) {
        return $http.get("/visitors?pubid=" + pubid)
    }

    return service;
}