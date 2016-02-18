'use strict';

angular.module('hotelApp')
	.controller('Status_peDeleteController', function($scope, $uibModalInstance, entity, Status_pe) {

        $scope.status_pe = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Status_pe.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
