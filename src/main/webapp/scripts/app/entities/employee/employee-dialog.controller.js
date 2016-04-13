'use strict';

angular.module('hotelApp').controller('EmployeeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Employee', 'Gender', 'Ethnic', 'Religion', 'Job', 'Education_level', 'Major', 'School', 'Marital_status', 'Came_component', 'Bank', 'Profile', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Employee, Gender, Ethnic, Religion, Job, Education_level, Major, School, Marital_status, Came_component, Bank, Profile, User) {

        $scope.employee = entity;
        $scope.genders = Gender.query();
        $scope.ethnics = Ethnic.query();
        $scope.religions = Religion.query();
        $scope.jobs = Job.query();
        $scope.education_levels = Education_level.query();
        $scope.majors = Major.query();
        $scope.schools = School.query();
        $scope.marital_statuss = Marital_status.query();
        $scope.came_components = Came_component.query();
        $scope.banks = Bank.query();
        $scope.profiles = Profile.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            Employee.get({id : id}, function(result) {
                $scope.employee = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:employeeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.employee.id != null) {
                Employee.update($scope.employee, onSaveSuccess, onSaveError);
            } else {
                Employee.save($scope.employee, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForBirthday = {};

        $scope.datePickerForBirthday.status = {
            opened: false
        };

        $scope.datePickerForBirthdayOpen = function($event) {
            $scope.datePickerForBirthday.status.opened = true;
        };
        $scope.datePickerForIc_prov_date = {};

        $scope.datePickerForIc_prov_date.status = {
            opened: false
        };

        $scope.datePickerForIc_prov_dateOpen = function($event) {
            $scope.datePickerForIc_prov_date.status.opened = true;
        };
        $scope.datePickerForSi_date = {};

        $scope.datePickerForSi_date.status = {
            opened: false
        };

        $scope.datePickerForSi_dateOpen = function($event) {
            $scope.datePickerForSi_date.status.opened = true;
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
