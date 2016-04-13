'use strict';

angular.module('hotelApp')
	.controller('Came_componentDeleteController', function($scope, $uibModalInstance, entity, Came_component) {

        $scope.came_component = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Came_component.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
