'use strict';

angular.module('hotelApp')
	.controller('Method_bookingDeleteController', function($scope, $uibModalInstance, entity, Method_booking) {

        $scope.method_booking = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Method_booking.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
