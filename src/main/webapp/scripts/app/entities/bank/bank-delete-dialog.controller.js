'use strict';

angular.module('hotelApp')
	.controller('BankDeleteController', function($scope, $uibModalInstance, entity, Bank) {

        $scope.bank = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Bank.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
