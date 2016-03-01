'use strict';

angular.module('hotelApp')
    .controller('EventDetailController', function ($scope, $rootScope, $stateParams, entity, Event, Status_event, User) {
        $scope.event = entity;
        $scope.load = function (id) {
            Event.get({id: id}, function(result) {
                $scope.event = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:eventUpdate', function(event, result) {
            $scope.event = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
