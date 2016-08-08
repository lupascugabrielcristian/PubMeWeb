var Auth = function($firebaseAuth) {
    var endPoint = "https://pubme.firebaseio.com/v2/pubme/auth/github/callback";
    var usersRef = new Firebase(endPoint);
    return $firebaseAuth(usersRef);
};

Auth.$inject = ['$firebaseAuth'];
angular.module('pubme').factory('Auth', Auth);
