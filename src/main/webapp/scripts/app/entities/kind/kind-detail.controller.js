'use strict';

angular.module('hotelApp')
    .controller('KindDetailController', function ($scope, $rootScope, $stateParams, entity, Kind, Policy, Event, User) {
        $scope.kind = entity;
        $scope.load = function (id) {
            Kind.get({id: id}, function(result) {
                $scope.kind = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:kindUpdate', function(event, result) {
            $scope.kind = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
