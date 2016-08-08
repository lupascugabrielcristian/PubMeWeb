var LoginCtrl = function ($scope, Auth, user, serverCalls, $state) {
  $scope.var = "LoginCtrl Controller ok";

  $scope.login = function(authMethod) {
    Auth.$authWithOAuthRedirect(authMethod).then(function(authData) {
    }).catch(function(error) {
      if (error.code === 'TRANSPORT_UNAVAILABLE') {
        Auth.$authWithOAuthPopup(authMethod).then(function(authData) {
        });
      } else {
        console.log(error);
      }
    });
  };


  Auth.$onAuth(function(authData) {
    if (authData === null) {
      console.log('Not logged in yet');
    } else {
      user.setUserName(tryGetUserName(authData));
      $scope.name = user.getUserName();
    }
    // This will display the user's name in our view
    $scope.authData = authData;
  });

  $scope.loginUser = function() {
    serverCalls.loginUser(user.getUserId(), user.getUserName()).then(function(result){
      console.log(result.data);
      user.favoritePubsIds = result.data.favoritePubs;
      $state.go('app.home');
    });
  };

  // <editor-fold desc="Implementation">
  function tryGetUserName(authData) {
    if (authData.github){
      user.setUserId(authData.github.id);
      return authData.github.displayName;
    }

    else if (authData.google){
      user.setUserId(authData.google.id);
      return authData.google.displayName;
    }
  }


  // </editor-fold>


};

LoginCtrl.$inject = ['$scope', 'Auth', 'user', 'serverCalls', '$state'];
angular.module('starter.controllers').controller('LoginCtrl', LoginCtrl);
