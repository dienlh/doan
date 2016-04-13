'use strict';

angular.module('hotelApp')
    .controller('Type_roomDetailController', function ($scope, $rootScope, $stateParams, entity, Type_room, User) {
        $scope.type_room = entity;
        $scope.load = function (id) {
            Type_room.get({id: id}, function(result) {
                $scope.type_room = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:type_roomUpdate', function(event, result) {
            $scope.type_room = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
