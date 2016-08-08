var browseRow = function(user, $rootScope){
  return {
    restrict: 'E',
    scope: {
      item: '=',
      visitors: '='
    },
    templateUrl: 'templates/browseRow.html',
    link: function($scope){
      $scope.item.favorite = user.isPubFavorite($scope.item.id);

      $rootScope.$watch('user.favoritePubsIds', function(){
        console.log("Change in user.favoritePubsIds received in browser directive for pub " +
        $scope.item.name + " and found " + $scope.item.favorite);
      });
    },
    controller: function($scope) {

      $scope.gotoThisPub = function() {
        console.log("Going to pub " + $scope.item.name);
      };

    }

  }

};


browseRow.$inject = ['user', '$rootScope'];
angular.module('pubme').directive('browseRow', browseRow);
