'use strict';

angular.module('hotelApp')
    .controller('BillDetailController', function ($scope, $rootScope, $stateParams, entity, Bill, Currency, Customer, Method_payment, Status_payment, Reservation, Status_bill, User) {
        $scope.bill = entity;
        $scope.load = function (id) {
            Bill.get({id: id}, function(result) {
                $scope.bill = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:billUpdate', function(event, result) {
            $scope.bill = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
