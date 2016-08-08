var user = function() {
  var userName;
  var id;
  var loggedIn;

  var service = {
    isLoggedIn: isLoggedIn,
    getUserName: getUserName,
    setUserName: setUserName,
    getUserId: getUserId,
    setUserId: setUserId,
    favoritePubsIds: [],
    isPubFavorite: isPubFavorite,
    toggleFavoritePub: toggleFavoritePub

  };

  return service;

  function isLoggedIn() {

    if (!loggedIn){
      loggedIn = false;
    }

    return loggedIn;
  }

  function getUserName(){
    return userName;
  }

  function setUserName(name) {
    userName = name;
    loggedIn = true;
  }

  function getUserId() {
    return id;
  }

  function setUserId(uid) {
    id = uid;
    loggedIn = true;
  }

  function isPubFavorite(pubId) {
    return service.favoritePubsIds.some(function(id){
      return pubId == id;
    });
  }

  function toggleFavoritePub(pubId) {
    if (!loggedIn) {
      console.log("Should be logged in");
      return;
    }

    if (isPubFavorite(pubId)){
      service.favoritePubsIds = service.favoritePubsIds.filter(function (favoritePubId){
        return pubId != favoritePubId;
      });
      console.log("pub favorite toggled. Now are " + service.favoritePubsIds.length + " favorite pub ids");
      return false;
    }
    else {
      service.favoritePubsIds.push(pubId);
      console.log("pub favorite toggled. Now are " + service.favoritePubsIds.length + " favorite pub ids");
      return true;
    }
  }
};


angular.module('pubme').factory('user', user);
