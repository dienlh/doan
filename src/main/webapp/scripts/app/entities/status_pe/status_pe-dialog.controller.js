'use strict';

angular.module('hotelApp').controller('Status_peDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Status_pe',
        function($scope, $stateParams, $uibModalInstance, entity, Status_pe) {

        $scope.status_pe = entity;
        $scope.load = function(id) {
            Status_pe.get({id : id}, function(result) {
                $scope.status_pe = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:status_peUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.status_pe.id != null) {
                Status_pe.update($scope.status_pe, onSaveSuccess, onSaveError);
            } else {
                Status_pe.save($scope.status_pe, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
