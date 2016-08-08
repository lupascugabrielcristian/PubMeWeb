// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.controllers' is found in controllers.js
angular.module('starter', ['ionic', 'starter.controllers', 'pubme', 'ngCordova', 'firebase'])

.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
    if (window.cordova && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
      cordova.plugins.Keyboard.disableScroll(true);

    }
    if (window.StatusBar) {
      // org.apache.cordova.statusbar required
      StatusBar.styleDefault();
    }
  });
})

.config(function($stateProvider, $urlRouterProvider) {
  $stateProvider

    .state('app', {
    url: '/app',
    abstract: true,
    templateUrl: 'templates/menu.html',
    controller: 'AppCtrl'
  })

  .state('app.map', {
    url: '/map',
    views: {
      'menuContent': {
        templateUrl: 'templates/map.html',
        controller: 'MapCtrl',
        resolve: {
          initial: function(serverCalls){
            return serverCalls.getDatabase();
          }
        }
      }
    }
  })
  .state('app.login', {
    url: '/login',
    views: {
      'menuContent': {
        templateUrl: 'templates/login-minimal.html',
        controller: 'LoginCtrl'
      }
    }
  })
  .state('app.about', {
    url: '/about',
    views: {
      'menuContent': {
        templateUrl: 'templates/about.html',
        controller: 'AboutCtrl'
      }
    }
  })
    .state('app.all', {
      url: '/all',
      views: {
        'menuContent': {
          templateUrl: 'templates/all.html',
          controller: 'AllCtrl',
          resolve: {
            initial: function(serverCalls){
              return serverCalls.getDatabase();
            }
          }
        }
      }
    })

    .state('app.specific', {
      url: '/specific/:pubid',
      views: {
        'menuContent': {
          templateUrl: 'templates/specific.html',
          controller: 'specificCtrl',
          resolve: {
            initial: function($stateParams, serverCalls){
              return serverCalls.getSpecificPub($stateParams.pubid);
            }
          }
        }
      }
    })

    .state('app.myoffers', {
      url: '/myoffers',
      views: {
        'menuContent': {
          templateUrl: 'templates/myoffers.html',
          controller: 'myOffersCtrl',
          resolve: {
            initial: function(user, serverCalls){
              return serverCalls.getUserOffers(user.getUserId());
            }
          }
        }
      }
    })

    .state('app.mypubs', {
      url: '/mypubs',
      views: {
        'menuContent': {
          templateUrl: 'templates/mypubs.html',
          controller: 'myPubsCtrl',
          resolve: {
            initial: function(user, serverCalls){
              return serverCalls.getUserPubs(user.getUserId());
            }
          }
        }
      }
    })

  .state('app.home', {
    url: '/home',
    views: {
      'menuContent': {
        templateUrl: 'templates/home.html',
        controller: 'HomeCtrl'
      }
    }
  });

  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/app/home');
});
