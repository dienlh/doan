'use strict';

angular.module('hotelApp')
    .controller('Method_bookingDetailController', function ($scope, $rootScope, $stateParams, entity, Method_booking, User) {
        $scope.method_booking = entity;
        $scope.load = function (id) {
            Method_booking.get({id: id}, function(result) {
                $scope.method_booking = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:method_bookingUpdate', function(event, result) {
            $scope.method_booking = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
