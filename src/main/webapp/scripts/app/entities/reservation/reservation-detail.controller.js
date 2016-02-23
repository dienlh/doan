'use strict';

angular.module('hotelApp')
    .controller('ReservationDetailController', function ($scope, $rootScope, $stateParams, entity, Reservation, Customer, Register_info, Status_reservation, User, Bill) {
        $scope.reservation = entity;
        $scope.load = function (id) {
            Reservation.get({id: id}, function(result) {
                $scope.reservation = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:reservationUpdate', function(event, result) {
            $scope.reservation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
