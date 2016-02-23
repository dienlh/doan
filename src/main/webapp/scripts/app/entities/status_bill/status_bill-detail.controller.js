'use strict';

angular.module('hotelApp')
    .controller('Status_billDetailController', function ($scope, $rootScope, $stateParams, entity, Status_bill, User) {
        $scope.status_bill = entity;
        $scope.load = function (id) {
            Status_bill.get({id: id}, function(result) {
                $scope.status_bill = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:status_billUpdate', function(event, result) {
            $scope.status_bill = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
