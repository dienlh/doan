'use strict';

angular.module('hotelApp').controller('ProfileDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Profile', 'Position', 'Department', 'Currency', 'Status_profile', 'User', 'Employee',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Profile, Position, Department, Currency, Status_profile, User, Employee) {

        $scope.profile = entity;
        $scope.positions = Position.query();
        $scope.departments = Department.query();
        $scope.currencys = Currency.query();
        $scope.status_profiles = Status_profile.query();
        $scope.users = User.query();
        $scope.employees = Employee.query({filter: 'profile-is-null'});
        $q.all([$scope.profile.$promise, $scope.employees.$promise]).then(function() {
            if (!$scope.profile.employee || !$scope.profile.employee.id) {
                return $q.reject();
            }
            return Employee.get({id : $scope.profile.employee.id}).$promise;
        }).then(function(employee) {
            $scope.employees.push(employee);
        });
        $scope.load = function(id) {
            Profile.get({id : id}, function(result) {
                $scope.profile = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:profileUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.profile.id != null) {
                Profile.update($scope.profile, onSaveSuccess, onSaveError);
            } else {
                Profile.save($scope.profile, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForJoin_date = {};

        $scope.datePickerForJoin_date.status = {
            opened: false
        };

        $scope.datePickerForJoin_dateOpen = function($event) {
            $scope.datePickerForJoin_date.status.opened = true;
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
