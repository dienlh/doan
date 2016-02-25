'use strict';

angular.module('hotelApp')
	.controller('Status_reservationDeleteController', function($scope, $uibModalInstance, entity, Status_reservation) {

        $scope.status_reservation = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Status_reservation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
