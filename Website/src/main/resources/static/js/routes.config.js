angular.module('PubMeApp')

.config(function($stateProvider, $urlRouterProvider){

    $urlRouterProvider.otherwise("/home/");

    $stateProvider
        .state('home', {
            url: "/home/:user",
            templateUrl: "/tmpl/home.html",
            controller: 'homeController'
        })
        .state('login', {
            url: "/login",
            templateUrl: "/tmpl/login.html",
            controller: 'loginController'
        })
        .state('register', {
            url: "/register",
            templateUrl: "/tmpl/register.html",
            controller: 'registerController'
        })
        .state('yourPub', {
            url: "/yourPub",
            templateUrl: "/tmpl/yourPub.html",
            controller: 'yourPubController',
            resolve : {
                initial: function(dataCalls, sharedData){
                    if (sharedData.getLoggedInUser() == null){
                        return [];
                    }
                    return dataCalls.getOwnersPubs(sharedData.getLoggedInUser().id);
                 }
            }
        })
        .state('registerPub', {
            url: "/registerPub",
            templateUrl: "/tmpl/registerPub.html",
            controller: 'registerPubController'
        })
        .state('editPub', {
            url: "/editPub/:pubId",
            templateUrl: "/tmpl/editPub.html",
            controller: 'editPubController',
            resolve: {
                initial: function(dataCalls, $stateParams){
                    return dataCalls.getPub($stateParams.pubId);
                }
            }
        })
        .state('specialOffers', {
            url: "/specialOffers/:pubId",
            templateUrl: "/tmpl/specialOffers.html",
            controller: 'specialOffersController',
            resolve: {
                initial: function(dataCalls, $stateParams){
                    return dataCalls.getPub($stateParams.pubId);
                }
            }
        })
        .state('pubDetails', {
            url: "/pubDetails/:pubId",
            templateUrl: "/tmpl/pubDetails.html",
            controller: "pubDetailsController",
            resolve: {
                initial: function(dataCalls, $stateParams){
                    return dataCalls.getVisitorsDistribution($stateParams.pubId);
                }
            }
        })
        .state('why', {
            url: "/why",
            templateUrl: "/tmpl/why.html"
        })
        .state('ourSolutions', {
            url: "/ourSolutions",
            templateUrl: "/tmpl/ourSolutions.html"
        })
});