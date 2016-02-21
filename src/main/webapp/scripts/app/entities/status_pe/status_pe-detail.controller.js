'use strict';

angular.module('hotelApp')
    .controller('Status_peDetailController', function ($scope, $rootScope, $stateParams, entity, Status_pe, User) {
        $scope.status_pe = entity;
        $scope.load = function (id) {
            Status_pe.get({id: id}, function(result) {
                $scope.status_pe = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:status_peUpdate', function(event, result) {
            $scope.status_pe = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
