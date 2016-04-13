'use strict';

angular.module('hotelApp')
	.controller('Method_paymentDeleteController', function($scope, $uibModalInstance, entity, Method_payment) {

        $scope.method_payment = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Method_payment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
