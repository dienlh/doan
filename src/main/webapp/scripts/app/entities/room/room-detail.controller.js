'use strict';

angular.module('hotelApp')
    .controller('RoomDetailController', function ($scope, $rootScope, $stateParams, entity, Room, Type_room, Currency, Images, Status_room, User, Amenity) {
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
