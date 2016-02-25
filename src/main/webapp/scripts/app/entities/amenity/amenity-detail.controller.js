'use strict';

angular.module('hotelApp')
    .controller('AmenityDetailController', function ($scope, $rootScope, $stateParams, entity, Amenity, User) {
        $scope.amenity = entity;
        $scope.load = function (id) {
            Amenity.get({id: id}, function(result) {
                $scope.amenity = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:amenityUpdate', function(event, result) {
            $scope.amenity = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
