'use strict';

angular.module('hotelApp').controller('Status_policyDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Status_policy', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Status_policy, User) {

        $scope.status_policy = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Status_policy.get({id : id}, function(result) {
                $scope.status_policy = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:status_policyUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.status_policy.id != null) {
                Status_policy.update($scope.status_policy, onSaveSuccess, onSaveError);
            } else {
                Status_policy.save($scope.status_policy, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForCreate_date = {};

        $scope.datePickerForCreate_date.status = {
            opened: false
        };

        $scope.datePickerForCreate_dateOpen = function($event) {
            $scope.datePickerForCreate_date.status.opened = true;
        };
}]);
