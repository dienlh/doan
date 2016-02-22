'use strict';

angular.module('hotelApp')
	.controller('Register_infoDeleteController', function($scope, $uibModalInstance, entity, Register_info) {

        $scope.register_info = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Register_info.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
