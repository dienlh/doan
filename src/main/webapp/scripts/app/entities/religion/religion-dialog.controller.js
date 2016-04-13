'use strict';

angular.module('hotelApp').controller('ReligionDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Religion', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Religion, User) {

        $scope.religion = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Religion.get({id : id}, function(result) {
                $scope.religion = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:religionUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.religion.id != null) {
                Religion.update($scope.religion, onSaveSuccess, onSaveError);
            } else {
                Religion.save($scope.religion, onSaveSuccess, onSaveError);
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
