'use strict';

angular.module('hotelApp').controller('CustomerDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Customer', 'Gender', 'Company', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Customer, Gender, Company, User) {

        $scope.customer = entity;
        $scope.genders = Gender.query();
        $scope.companys = Company.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            Customer.get({id : id}, function(result) {
                $scope.customer = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:customerUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.customer.id != null) {
                Customer.update($scope.customer, onSaveSuccess, onSaveError);
            } else {
                Customer.save($scope.customer, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForIc_prov_date = {};

        $scope.datePickerForIc_prov_date.status = {
            opened: false
        };

        $scope.datePickerForIc_prov_dateOpen = function($event) {
            $scope.datePickerForIc_prov_date.status.opened = true;
        };
        $scope.datePickerForBirthday = {};

        $scope.datePickerForBirthday.status = {
            opened: false
        };

        $scope.datePickerForBirthdayOpen = function($event) {
            $scope.datePickerForBirthday.status.opened = true;
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
