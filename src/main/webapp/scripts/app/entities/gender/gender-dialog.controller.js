'use strict';

angular.module('hotelApp').controller('GenderDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Gender', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Gender, User) {

        $scope.gender = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Gender.get({id : id}, function(result) {
                $scope.gender = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:genderUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.gender.id != null) {
                Gender.update($scope.gender, onSaveSuccess, onSaveError);
            } else {
                Gender.save($scope.gender, onSaveSuccess, onSaveError);
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
