var markerProvider = function ($rootScope) {

  var service = {
    registerHomeMarker: registerHomeMarker,
    registerCloseMarker: registerCloseMarker,
    registerBarMarker: registerBarMarker,
    registerClubMarker: registerClubMarker,
    registerRestaurantMarker: registerRestaurantMarker,
    registerCoffeyShopMarker: registerCoffeyShopMarker,
    registerTerasaMarker: registerTerasaMarker,
    registerUnknownMarker: registerUnknownMarker,
    getMarkersForPubs: getMarkersForPubs,
    getMarkersNearby: getMarkersNearby,
    getSelected: getSelected
  };

  return service;

  function registerHomeMarker(map, home) {
    var icon = "/img/markers/home.png";
    registerHome(map, home, icon);
  }

  function registerCloseMarker(map, item, selectedItem) {
    var icon = "/img/markers/Wine.png";
    registercustomMarker(map, item, icon, selectedItem);
  }

  function registerBarMarker(map, item, selectedItem) {
    var icon = "/img/markers/Bar.png";
    registercustomMarker(map, item, icon, selectedItem);
  }

  function registerClubMarker(map, item, selectedItem) {
    var icon = "/img/markers/c.png";
    registercustomMarker(map, item, icon, selectedItem);
  }

  function registerRestaurantMarker(map, item, selectedItem) {
    var icon = "/img/markers/Fork.png";
    registercustomMarker(map, item, icon, selectedItem);
  }

  function registerCoffeyShopMarker(map, item, selectedItem) {
    var icon = "/img/markers/Cafe.png";
    registercustomMarker(map, item, icon, selectedItem);
  }

  function registerTerasaMarker(map, item, selectedItem) {
    var icon = "/img/markers/Beer.png";
    registercustomMarker(map, item, icon, selectedItem);
  }

  function registerUnknownMarker(map, item, selectedItem) {
    var icon = "/img/markers/u.png";
    registercustomMarker(map, item, icon, selectedItem);
  }

  function getSelected() {
    return selected;
  }


  function getMarkersForPubs(allPubs, map) {
    var markers = [];

    function addMarker(pub) {
      var latLng = {
        lat: pub.location.latitude,
        lng: pub.location.longitude
      };

      function actionOnClick() {
        $rootScope.$broadcast("clicked", pub);
        $rootScope.$applyAsync();
      }

      var iconLink = setIcon(pub);
      var newMarker = generateMarker(map, latLng, iconLink, actionOnClick);
      markers.push(newMarker);
    }

    allPubs.map(addMarker);

    return markers;
  }

  function generateMarker(map, latLng, iconLink, actionOnClick) {
    var marker = new google.maps.Marker({
      map: map,
      position: latLng,
      icon: iconLink
    });

    marker.addListener('click', function() {
      actionOnClick();
    });

    return marker;
  }

  function setIcon(pub) {
    if (pub.type.indexOf("BAR") != -1){
      return  "/img/markers/Bar.png";
    }

    if (pub.type.indexOf("CLUB") != -1){
      return "/img/markers/c.png";
    }

    if (pub.type.indexOf("COFFEYSHOP") != -1){
      return "/img/markers/Cafe.png";
    }

    if (pub.type.indexOf("TERASA") != -1){
      return "/img/markers/Beer.png";
    }

    if (pub.type.indexOf("RESTORANT") != -1){
      return "/img/markers/Fork.png";
    }

    return "/img/markers/u.png";

  }

  function getMarkersNearby(pubs) {
    var markers = [];

    pubs.forEach(function(pub){
      var marker = {
        latitude: pub.location.latitude,
        longitude: pub.location.longitude,
        id: 'c-' + pubs.indexOf(pub),
        icon:  "/img/markers/location.png",
        onMarkerClick: function(marker, event, model){
          $rootScope.$broadcast('changedNear', {index: model.id});
        }
      };

      markers.push(marker);
    });
    return markers;
  }



  function registercustomMarker(map, item, image, selectedItem) {
    var contentString = item.name;
    var itemPosition = {
      lat: item.location.latitude,
      lng: item.location.longitude
    };

    var marker = new google.maps.Marker({
      map: map,
      animation: google.maps.Animation.FIX,
      position: itemPosition,
      icon: image
    });

    var infowindow = new google.maps.InfoWindow({
      content: contentString
    });

    marker.addListener('click', function() {
      infowindow.open(map, marker);
      //selected = item;
      selectedPub.updateSelectedPub(item);

      $rootScope.$broadcast('colapse');
    });
  }
};


markerProvider.$inject = ['$rootScope'];
angular.module('pubme').factory("markerProvider", markerProvider);
