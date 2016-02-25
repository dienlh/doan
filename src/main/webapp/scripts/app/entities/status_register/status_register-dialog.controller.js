'use strict';

angular.module('hotelApp').controller('Status_registerDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Status_register', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Status_register, User) {

        $scope.status_register = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Status_register.get({id : id}, function(result) {
                $scope.status_register = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:status_registerUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.status_register.id != null) {
                Status_register.update($scope.status_register, onSaveSuccess, onSaveError);
            } else {
                Status_register.save($scope.status_register, onSaveSuccess, onSaveError);
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
