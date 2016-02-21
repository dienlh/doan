'use strict';

angular.module('hotelApp')
	.controller('KindDeleteController', function($scope, $uibModalInstance, entity, Kind) {

        $scope.kind = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Kind.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
