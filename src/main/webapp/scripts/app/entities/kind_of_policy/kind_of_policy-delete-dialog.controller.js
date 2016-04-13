'use strict';

angular.module('hotelApp')
	.controller('Kind_of_policyDeleteController', function($scope, $uibModalInstance, entity, Kind_of_policy) {

        $scope.kind_of_policy = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Kind_of_policy.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
