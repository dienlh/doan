'use strict';

angular.module('hotelApp')
    .controller('Status_roomDetailController', function ($scope, $rootScope, $stateParams, entity, Status_room) {
        $scope.status_room = entity;
        $scope.load = function (id) {
            Status_room.get({id: id}, function(result) {
                $scope.status_room = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:status_roomUpdate', function(event, result) {
            $scope.status_room = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
