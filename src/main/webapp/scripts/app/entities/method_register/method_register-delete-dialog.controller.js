'use strict';

angular.module('hotelApp')
	.controller('Method_registerDeleteController', function($scope, $uibModalInstance, entity, Method_register) {

        $scope.method_register = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Method_register.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
