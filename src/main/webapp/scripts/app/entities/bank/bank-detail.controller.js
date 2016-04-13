'use strict';

angular.module('hotelApp')
    .controller('BankDetailController', function ($scope, $rootScope, $stateParams, entity, Bank, User) {
        $scope.bank = entity;
        $scope.load = function (id) {
            Bank.get({id: id}, function(result) {
                $scope.bank = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:bankUpdate', function(event, result) {
            $scope.bank = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
