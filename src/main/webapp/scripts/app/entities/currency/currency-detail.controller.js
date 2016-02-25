'use strict';

angular.module('hotelApp')
    .controller('CurrencyDetailController', function ($scope, $rootScope, $stateParams, entity, Currency, User) {
        $scope.currency = entity;
        $scope.load = function (id) {
            Currency.get({id: id}, function(result) {
                $scope.currency = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:currencyUpdate', function(event, result) {
            $scope.currency = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
