'use strict';

angular.module('hotelApp')
	.controller('PolicyDeleteController', function($scope, $uibModalInstance, entity, Policy) {

        $scope.policy = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Policy.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
