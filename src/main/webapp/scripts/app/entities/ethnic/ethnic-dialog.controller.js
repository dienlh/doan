'use strict';

angular.module('hotelApp').controller('EthnicDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Ethnic', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Ethnic, User) {

        $scope.ethnic = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Ethnic.get({id : id}, function(result) {
                $scope.ethnic = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:ethnicUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.ethnic.id != null) {
                Ethnic.update($scope.ethnic, onSaveSuccess, onSaveError);
            } else {
                Ethnic.save($scope.ethnic, onSaveSuccess, onSaveError);
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
