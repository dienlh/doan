'use strict';

angular.module('hotelApp')
	.controller('Status_serviceDeleteController', function($scope, $uibModalInstance, entity, Status_service) {

        $scope.status_service = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Status_service.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
