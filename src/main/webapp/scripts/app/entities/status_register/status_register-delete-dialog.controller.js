'use strict';

angular.module('hotelApp')
	.controller('Status_registerDeleteController', function($scope, $uibModalInstance, entity, Status_register) {

        $scope.status_register = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Status_register.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
