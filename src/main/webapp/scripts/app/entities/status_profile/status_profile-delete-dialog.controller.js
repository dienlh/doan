'use strict';

angular.module('hotelApp')
	.controller('Status_profileDeleteController', function($scope, $uibModalInstance, entity, Status_profile) {

        $scope.status_profile = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Status_profile.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
