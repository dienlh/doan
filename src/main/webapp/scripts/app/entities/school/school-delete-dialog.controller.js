'use strict';

angular.module('hotelApp')
	.controller('SchoolDeleteController', function($scope, $uibModalInstance, entity, School) {

        $scope.school = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            School.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
