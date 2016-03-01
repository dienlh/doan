'use strict';

angular.module('hotelApp')
	.controller('Status_eventDeleteController', function($scope, $uibModalInstance, entity, Status_event) {

        $scope.status_event = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Status_event.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
