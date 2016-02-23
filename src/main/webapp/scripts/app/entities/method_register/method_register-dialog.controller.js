'use strict';

angular.module('hotelApp').controller('Method_registerDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Method_register', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Method_register, User) {

        $scope.method_register = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Method_register.get({id : id}, function(result) {
                $scope.method_register = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:method_registerUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.method_register.id != null) {
                Method_register.update($scope.method_register, onSaveSuccess, onSaveError);
            } else {
                Method_register.save($scope.method_register, onSaveSuccess, onSaveError);
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
