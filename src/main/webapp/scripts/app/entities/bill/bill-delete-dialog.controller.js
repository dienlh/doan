'use strict';

angular.module('hotelApp')
	.controller('BillDeleteController', function($scope, $uibModalInstance, entity, Bill) {

        $scope.bill = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Bill.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
