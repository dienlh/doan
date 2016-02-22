'use strict';

angular.module('hotelApp')
	.controller('AmenityDeleteController', function($scope, $uibModalInstance, entity, Amenity) {

        $scope.amenity = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Amenity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
