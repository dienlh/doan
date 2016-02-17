'use strict';

angular.module('hotelApp').controller('EthnicDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ethnic',
        function($scope, $stateParams, $uibModalInstance, entity, Ethnic) {

        $scope.ethnic = entity;
        $scope.load = function(id) {
            Ethnic.get({id : id}, function(result) {
                $scope.ethnic = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:ethnicUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.ethnic.id != null) {
                Ethnic.update($scope.ethnic, onSaveSuccess, onSaveError);
            } else {
                Ethnic.save($scope.ethnic, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
