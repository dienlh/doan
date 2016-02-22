'use strict';

angular.module('hotelApp')
    .controller('Register_infoDetailController', function ($scope, $rootScope, $stateParams, entity, Register_info, Customer, Room, Currency, Method_payment, Status_payment, Status_register, User, Reservation) {
        $scope.register_info = entity;
        $scope.load = function (id) {
            Register_info.get({id: id}, function(result) {
                $scope.register_info = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:register_infoUpdate', function(event, result) {
            $scope.register_info = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
