'use strict';

angular.module('hotelApp')
	.controller('Education_levelDeleteController', function($scope, $uibModalInstance, entity, Education_level) {

        $scope.education_level = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Education_level.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
