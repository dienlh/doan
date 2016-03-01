'use strict';

angular.module('hotelApp').controller('Type_policyDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Type_policy', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Type_policy, User) {

        $scope.type_policy = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Type_policy.get({id : id}, function(result) {
                $scope.type_policy = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:type_policyUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.type_policy.id != null) {
                Type_policy.update($scope.type_policy, onSaveSuccess, onSaveError);
            } else {
                Type_policy.save($scope.type_policy, onSaveSuccess, onSaveError);
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
