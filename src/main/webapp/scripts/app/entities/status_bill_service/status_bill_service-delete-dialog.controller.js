'use strict';

angular.module('hotelApp')
	.controller('Status_bill_serviceDeleteController', function($scope, $uibModalInstance, entity, Status_bill_service) {

        $scope.status_bill_service = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Status_bill_service.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
