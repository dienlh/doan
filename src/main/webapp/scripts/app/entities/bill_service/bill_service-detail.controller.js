'use strict';

angular.module('hotelApp')
    .controller('Bill_serviceDetailController', function ($scope, $rootScope, $stateParams, entity, Bill_service, Services, Currency, Reservation, Status_bill_service, User) {
        $scope.bill_service = entity;
        $scope.load = function (id) {
            Bill_service.get({id: id}, function(result) {
                $scope.bill_service = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:bill_serviceUpdate', function(event, result) {
            $scope.bill_service = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
