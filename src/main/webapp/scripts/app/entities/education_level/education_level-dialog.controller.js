'use strict';

angular.module('hotelApp').controller('Education_levelDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Education_level', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Education_level, User) {

        $scope.education_level = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Education_level.get({id : id}, function(result) {
                $scope.education_level = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:education_levelUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.education_level.id != null) {
                Education_level.update($scope.education_level, onSaveSuccess, onSaveError);
            } else {
                Education_level.save($scope.education_level, onSaveSuccess, onSaveError);
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
