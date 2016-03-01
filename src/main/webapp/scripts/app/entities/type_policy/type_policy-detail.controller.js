'use strict';

angular.module('hotelApp')
    .controller('Type_policyDetailController', function ($scope, $rootScope, $stateParams, entity, Type_policy, User) {
        $scope.type_policy = entity;
        $scope.load = function (id) {
            Type_policy.get({id: id}, function(result) {
                $scope.type_policy = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:type_policyUpdate', function(event, result) {
            $scope.type_policy = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
