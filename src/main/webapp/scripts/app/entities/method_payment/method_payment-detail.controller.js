'use strict';

angular.module('hotelApp')
    .controller('Method_paymentDetailController', function ($scope, $rootScope, $stateParams, entity, Method_payment, User) {
        $scope.method_payment = entity;
        $scope.load = function (id) {
            Method_payment.get({id: id}, function(result) {
                $scope.method_payment = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:method_paymentUpdate', function(event, result) {
            $scope.method_payment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
