'use strict';

angular.module('hotelApp')
    .controller('PositionDetailController', function ($scope, $rootScope, $stateParams, entity, Position, User) {
        $scope.position = entity;
        $scope.load = function (id) {
            Position.get({id: id}, function(result) {
                $scope.position = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:positionUpdate', function(event, result) {
            $scope.position = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
