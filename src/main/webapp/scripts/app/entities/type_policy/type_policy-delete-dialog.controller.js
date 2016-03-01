'use strict';

angular.module('hotelApp')
	.controller('Type_policyDeleteController', function($scope, $uibModalInstance, entity, Type_policy) {

        $scope.type_policy = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Type_policy.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
