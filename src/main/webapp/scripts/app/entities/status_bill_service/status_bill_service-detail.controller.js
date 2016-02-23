'use strict';

angular.module('hotelApp')
    .controller('Status_bill_serviceDetailController', function ($scope, $rootScope, $stateParams, entity, Status_bill_service, User) {
        $scope.status_bill_service = entity;
        $scope.load = function (id) {
            Status_bill_service.get({id: id}, function(result) {
                $scope.status_bill_service = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:status_bill_serviceUpdate', function(event, result) {
            $scope.status_bill_service = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
