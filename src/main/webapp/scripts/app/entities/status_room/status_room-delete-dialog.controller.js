'use strict';

angular.module('hotelApp')
	.controller('Status_roomDeleteController', function($scope, $uibModalInstance, entity, Status_room) {

        $scope.status_room = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Status_room.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
