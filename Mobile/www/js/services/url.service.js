angular.module('pubme').factory('url', function(){
    var service = {
      databaseClosest: "http://localhost:8100/database/around?",
      databaseClosest2: "http://localhost:8100/database/aroundParams",
      specificPub: "http://localhost:8100/single?",
      addVisitorUrl: "http://localhost:8100/addVisitor",
      getOwnerUrl: "http://localhost:8100/get/owner?",
      loginUserUrl: "/login/user",
      favoritePubUrl: "/pub/favorite",
      userOffersUrl: "/user/offers?",
      userPubsUrl: "/get/favoritePubs?"
    };

    return service;
  });
