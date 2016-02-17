'use strict';

angular.module('hotelApp')
	.controller('ReligionDeleteController', function($scope, $uibModalInstance, entity, Religion) {

        $scope.religion = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Religion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
