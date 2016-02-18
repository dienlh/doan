'use strict';

angular.module('hotelApp')
	.controller('FunitureDeleteController', function($scope, $uibModalInstance, entity, Funiture) {

        $scope.funiture = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Funiture.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
