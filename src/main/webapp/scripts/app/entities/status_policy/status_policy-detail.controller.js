'use strict';

angular.module('hotelApp')
    .controller('Status_policyDetailController', function ($scope, $rootScope, $stateParams, entity, Status_policy, User) {
        $scope.status_policy = entity;
        $scope.load = function (id) {
            Status_policy.get({id: id}, function(result) {
                $scope.status_policy = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:status_policyUpdate', function(event, result) {
            $scope.status_policy = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
