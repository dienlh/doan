'use strict';

angular.module('hotelApp')
	.controller('Status_billDeleteController', function($scope, $uibModalInstance, entity, Status_bill) {

        $scope.status_bill = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Status_bill.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
