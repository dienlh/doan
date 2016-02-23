'use strict';

angular.module('hotelApp')
    .controller('RoomDetailController', function ($scope, $rootScope, $stateParams, entity, Room, Kind, Image, Amenity, Currency, Status_room, User) {
        $scope.room = entity;
        $scope.load = function (id) {
            Room.get({id: id}, function(result) {
                $scope.room = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:roomUpdate', function(event, result) {
            $scope.room = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
