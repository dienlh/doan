'use strict';

angular.module('hotelApp')
    .controller('CustomerDetailController', function ($scope, $rootScope, $stateParams, entity, Customer, Gender, Ethnic, Religion, Company, User) {
        $scope.customer = entity;
        $scope.load = function (id) {
            Customer.get({id: id}, function(result) {
                $scope.customer = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:customerUpdate', function(event, result) {
            $scope.customer = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
