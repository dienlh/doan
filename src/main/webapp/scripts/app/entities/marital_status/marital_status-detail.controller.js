'use strict';

angular.module('hotelApp')
    .controller('Marital_statusDetailController', function ($scope, $rootScope, $stateParams, entity, Marital_status) {
        $scope.marital_status = entity;
        $scope.load = function (id) {
            Marital_status.get({id: id}, function(result) {
                $scope.marital_status = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:marital_statusUpdate', function(event, result) {
            $scope.marital_status = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
