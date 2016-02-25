'use strict';

angular.module('hotelApp')
    .controller('Status_reservationDetailController', function ($scope, $rootScope, $stateParams, entity, Status_reservation, User) {
        $scope.status_reservation = entity;
        $scope.load = function (id) {
            Status_reservation.get({id: id}, function(result) {
                $scope.status_reservation = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:status_reservationUpdate', function(event, result) {
            $scope.status_reservation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
