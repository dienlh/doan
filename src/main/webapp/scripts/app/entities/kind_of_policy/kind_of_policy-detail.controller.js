'use strict';

angular.module('hotelApp')
    .controller('Kind_of_policyDetailController', function ($scope, $rootScope, $stateParams, entity, Kind_of_policy, User) {
        $scope.kind_of_policy = entity;
        $scope.load = function (id) {
            Kind_of_policy.get({id: id}, function(result) {
                $scope.kind_of_policy = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:kind_of_policyUpdate', function(event, result) {
            $scope.kind_of_policy = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
