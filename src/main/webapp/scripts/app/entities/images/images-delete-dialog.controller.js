'use strict';

angular.module('hotelApp')
	.controller('ImagesDeleteController', function($scope, $uibModalInstance, entity, Images) {

        $scope.images = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Images.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
