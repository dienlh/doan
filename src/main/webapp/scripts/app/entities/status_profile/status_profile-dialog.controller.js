'use strict';

angular.module('hotelApp').controller('Status_profileDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Status_profile',
        function($scope, $stateParams, $uibModalInstance, entity, Status_profile) {

        $scope.status_profile = entity;
        $scope.load = function(id) {
            Status_profile.get({id : id}, function(result) {
                $scope.status_profile = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:status_profileUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.status_profile.id != null) {
                Status_profile.update($scope.status_profile, onSaveSuccess, onSaveError);
            } else {
                Status_profile.save($scope.status_profile, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
