
var serverCalls = function ($http, url) {
  var databaseRead = "http://localhost:8100/database";
  var saveToDatabaseUrl = "http://localhost:8100/put?content=";
  var deleteFromDatabaseUrl = "http://localhost:8100/delete?query=";

  var service = {
    getDatabase: getDatabase,
    getClosest: getClosest,
    getClosestAdvanced: getClosestAdvanced,
    saveToDatabase: saveToDatabase,
    deleteFromDatabase: deleteFromDatabase,
    getSpecificPub: getSpecificPub,
    addVisitor: addVisitor,
    getOwner: getOwner,
    loginUser: loginUser,
    favoritePub: favoritePub,
    getUserPubs: getUserPubs,
    getUserOffers: getUserOffers
  };

  function getDatabase(){
    return $http.get(databaseRead);
  }

  function getClosest(latitude, longitude) {
    return $http.post(url.databaseClosest  + "latitude=" + latitude + "&longitude=" + longitude);
  }

  function getClosestAdvanced(latitude, longitude) {

    var request = {
      latitude: latitude,
      longitude: longitude,
      batchSize: 3
    };

    return $http.post(url.databaseClosest2, request);
  }

  function saveToDatabase(content){
    if (content == "")
      content = "without content";

    return $http.post(saveToDatabaseUrl + content);
  }

  function deleteFromDatabase(query){
    if (query == "")
      return;
    return $http.post(deleteFromDatabaseUrl + query);
  }

  function getSpecificPub(id) {
    return $http.get(url.specificPub + "id=" + id);
  }

  function addVisitor(id, userId, weekDay, monthDay) {

    var request = {
      pubId: id,
      userId: userId,
      weekDayNumber: weekDay,
      monthDayNumber: monthDay
    };

    return $http.post(url.addVisitorUrl, request);
  }

  function getOwner(id) {
    return $http.get(url.getOwnerUrl + "id=" + id);
  }

  function loginUser(userId, userName) {

    var request = {
      id: userId,
      name: userName
    };

    return $http.post(url.loginUserUrl, request);
  }

  function favoritePub(userId, pubId) {
    var request = {
      userId: userId,
      pubId: pubId
    };

    return $http.post(url.favoritePubUrl, request);
  }

  function getUserPubs(userId) {
    return $http.get(url.userPubsUrl + "userid=" + userId);
  }

  function getUserOffers(userId){
    return $http.get(url.userOffersUrl + "userid=" + userId);
  }

  return service;

};


serverCalls.$inject = ['$http', 'url'];
angular.module('pubme').factory('serverCalls', serverCalls);
