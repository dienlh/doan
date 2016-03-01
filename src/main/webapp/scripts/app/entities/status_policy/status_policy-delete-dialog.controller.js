'use strict';

angular.module('hotelApp')
	.controller('Status_policyDeleteController', function($scope, $uibModalInstance, entity, Status_policy) {

        $scope.status_policy = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Status_policy.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
