'use strict';

angular.module('hotelApp')
    .controller('Status_eventDetailController', function ($scope, $rootScope, $stateParams, entity, Status_event, User) {
        $scope.status_event = entity;
        $scope.load = function (id) {
            Status_event.get({id: id}, function(result) {
                $scope.status_event = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:status_eventUpdate', function(event, result) {
            $scope.status_event = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
