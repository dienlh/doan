'use strict';

angular.module('hotelApp')
	.controller('ServicesDeleteController', function($scope, $uibModalInstance, entity, Services) {

        $scope.services = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Services.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
