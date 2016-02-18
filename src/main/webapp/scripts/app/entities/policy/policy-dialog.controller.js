'use strict';

angular.module('hotelApp').controller('PolicyDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Policy', 'Status_pe', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Policy, Status_pe, User) {

        $scope.policy = entity;
        $scope.status_pes = Status_pe.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            Policy.get({id : id}, function(result) {
                $scope.policy = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:policyUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.policy.id != null) {
                Policy.update($scope.policy, onSaveSuccess, onSaveError);
            } else {
                Policy.save($scope.policy, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForStart_date = {};

        $scope.datePickerForStart_date.status = {
            opened: false
        };

        $scope.datePickerForStart_dateOpen = function($event) {
            $scope.datePickerForStart_date.status.opened = true;
        };
        $scope.datePickerForEnd_date = {};

        $scope.datePickerForEnd_date.status = {
            opened: false
        };

        $scope.datePickerForEnd_dateOpen = function($event) {
            $scope.datePickerForEnd_date.status.opened = true;
        };
        $scope.datePickerForCreate_date = {};

        $scope.datePickerForCreate_date.status = {
            opened: false
        };

        $scope.datePickerForCreate_dateOpen = function($event) {
            $scope.datePickerForCreate_date.status.opened = true;
        };
        $scope.datePickerForLast_modified_date = {};

        $scope.datePickerForLast_modified_date.status = {
            opened: false
        };

        $scope.datePickerForLast_modified_dateOpen = function($event) {
            $scope.datePickerForLast_modified_date.status.opened = true;
        };
}]);
