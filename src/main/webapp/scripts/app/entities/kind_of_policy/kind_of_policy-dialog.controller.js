'use strict';

angular.module('hotelApp').controller('Kind_of_policyDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Kind_of_policy', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Kind_of_policy, User) {

        $scope.kind_of_policy = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Kind_of_policy.get({id : id}, function(result) {
                $scope.kind_of_policy = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:kind_of_policyUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.kind_of_policy.id != null) {
                Kind_of_policy.update($scope.kind_of_policy, onSaveSuccess, onSaveError);
            } else {
                Kind_of_policy.save($scope.kind_of_policy, onSaveSuccess, onSaveError);
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
