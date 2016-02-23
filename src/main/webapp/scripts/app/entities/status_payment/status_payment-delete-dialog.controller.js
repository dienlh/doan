'use strict';

angular.module('hotelApp')
	.controller('Status_paymentDeleteController', function($scope, $uibModalInstance, entity, Status_payment) {

        $scope.status_payment = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Status_payment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
