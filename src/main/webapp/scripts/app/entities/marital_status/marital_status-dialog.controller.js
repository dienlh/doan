'use strict';

angular.module('hotelApp').controller('Marital_statusDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Marital_status',
        function($scope, $stateParams, $uibModalInstance, entity, Marital_status) {

        $scope.marital_status = entity;
        $scope.load = function(id) {
            Marital_status.get({id : id}, function(result) {
                $scope.marital_status = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:marital_statusUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.marital_status.id != null) {
                Marital_status.update($scope.marital_status, onSaveSuccess, onSaveError);
            } else {
                Marital_status.save($scope.marital_status, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
