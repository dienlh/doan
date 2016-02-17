'use strict';

angular.module('hotelApp')
	.controller('Marital_statusDeleteController', function($scope, $uibModalInstance, entity, Marital_status) {

        $scope.marital_status = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Marital_status.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
