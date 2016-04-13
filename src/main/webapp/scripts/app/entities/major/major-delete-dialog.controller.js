'use strict';

angular.module('hotelApp')
	.controller('MajorDeleteController', function($scope, $uibModalInstance, entity, Major) {

        $scope.major = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Major.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
