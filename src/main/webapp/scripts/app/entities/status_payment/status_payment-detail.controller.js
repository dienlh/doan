'use strict';

angular.module('hotelApp')
    .controller('Status_paymentDetailController', function ($scope, $rootScope, $stateParams, entity, Status_payment, User) {
        $scope.status_payment = entity;
        $scope.load = function (id) {
            Status_payment.get({id: id}, function(result) {
                $scope.status_payment = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:status_paymentUpdate', function(event, result) {
            $scope.status_payment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
