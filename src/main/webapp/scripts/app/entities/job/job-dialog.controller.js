'use strict';

angular.module('hotelApp').controller('JobDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Job', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Job, User) {

        $scope.job = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Job.get({id : id}, function(result) {
                $scope.job = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:jobUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.job.id != null) {
                Job.update($scope.job, onSaveSuccess, onSaveError);
            } else {
                Job.save($scope.job, onSaveSuccess, onSaveError);
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
