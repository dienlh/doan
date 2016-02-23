'use strict';

angular.module('hotelApp').controller('CompanyDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Company', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Company, User) {

        $scope.company = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Company.get({id : id}, function(result) {
                $scope.company = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:companyUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.company.id != null) {
                Company.update($scope.company, onSaveSuccess, onSaveError);
            } else {
                Company.save($scope.company, onSaveSuccess, onSaveError);
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
        $scope.datePickerForLast_modified_date = {};

        $scope.datePickerForLast_modified_date.status = {
            opened: false
        };

        $scope.datePickerForLast_modified_dateOpen = function($event) {
            $scope.datePickerForLast_modified_date.status.opened = true;
        };
}]);
