'use strict';

angular.module('hotelApp')
	.controller('EthnicDeleteController', function($scope, $uibModalInstance, entity, Ethnic) {

        $scope.ethnic = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Ethnic.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
