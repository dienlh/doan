'use strict';

angular.module('hotelApp')
	.controller('Bill_serviceDeleteController', function($scope, $uibModalInstance, entity, Bill_service) {

        $scope.bill_service = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Bill_service.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
