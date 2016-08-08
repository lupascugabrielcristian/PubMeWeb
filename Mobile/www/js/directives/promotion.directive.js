var promotionsRow = function(){
  return {
    restrict: 'E',
    scope: {
      item: '='
    },
    templateUrl: 'templates/promotionsRow.html',
    link: function($scope){
      $scope.directiveVariable = "Directive variable";
    }
  }

};

angular.module('pubme').directive('promotionsRow', promotionsRow);
