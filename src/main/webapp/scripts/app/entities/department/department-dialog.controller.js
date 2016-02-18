'use strict';

angular.module('hotelApp').controller('DepartmentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Department', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Department, User) {

        $scope.department = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Department.get({id : id}, function(result) {
                $scope.department = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:departmentUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.department.id != null) {
                Department.update($scope.department, onSaveSuccess, onSaveError);
            } else {
                Department.save($scope.department, onSaveSuccess, onSaveError);
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
