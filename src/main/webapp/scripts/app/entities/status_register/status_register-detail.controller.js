'use strict';

angular.module('hotelApp')
    .controller('Status_registerDetailController', function ($scope, $rootScope, $stateParams, entity, Status_register, User) {
        $scope.status_register = entity;
        $scope.load = function (id) {
            Status_register.get({id: id}, function(result) {
                $scope.status_register = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:status_registerUpdate', function(event, result) {
            $scope.status_register = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
