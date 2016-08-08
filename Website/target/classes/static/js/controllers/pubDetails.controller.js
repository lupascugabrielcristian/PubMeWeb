angular.module('PubMeApp').controller('pubDetailsController', pubDetailsController);

pubDetailsController.$inject = ['$scope', 'initial', 'dataCalls'];

function pubDetailsController($scope, initial, dataCalls){
    $scope.pub = initial.data.pub;
    $scope.distribution = initial.data.distribution;

    console.log("Distribution: ");
    console.log($scope.distribution);

    if(!$scope.distribution){
        return;
    }

    var thisWeekData = [];
    for (var key in $scope.distribution.weekDistribution){
        if ($scope.distribution.weekDistribution[key] != 0){
            thisWeekData.push($scope.distribution.weekDistribution[key]);
        }
    }


    var thisMonthData = [];
    for (var key in $scope.distribution.monthDistribution){
        if ($scope.distribution.monthDistribution[key] != 0) {
            thisMonthData.push($scope.distribution.monthDistribution[key]);
        }
    }

    $scope.labels1 = ["Luni", "Marti", "Miercuri", "Joi", "Vineri", "Sambata", "Duminica"];
    $scope.series1 = ['Week Data'];
    $scope.data1 = [thisWeekData];
    $scope.options1 = {
        datasetFill : false
    };
    $scope.onClick = function (points, evt) {
        console.log(points, evt);
    };

    $scope.labels2 = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    $scope.series2 = ['Month Data'];
    $scope.data2 = [thisMonthData];
}



