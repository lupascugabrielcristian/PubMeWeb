angular.module('PubMeApp').factory('sharedData', sharedData);

//dataCalls.$inject = [];

function sharedData () {
    var owner = null;

    var service = {
        setLoggedInUser: setLoggedInUser,
        getLoggedInUser: getLoggedInUser
    };

    function setLoggedInUser(ownerObject){
        owner = ownerObject;
    }

    function getLoggedInUser() {
        return owner;
    }

    return service;
}