'use strict';

angular.module('hotelApp')
    .controller('PolicyDetailController', function ($scope, $rootScope, $stateParams, entity, Policy, Status_pe, User) {
        $scope.policy = entity;
        $scope.load = function (id) {
            Policy.get({id: id}, function(result) {
                $scope.policy = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:policyUpdate', function(event, result) {
            $scope.policy = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
