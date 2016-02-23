'use strict';

angular.module('hotelApp')
	.controller('Type_roomDeleteController', function($scope, $uibModalInstance, entity, Type_room) {

        $scope.type_room = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Type_room.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
