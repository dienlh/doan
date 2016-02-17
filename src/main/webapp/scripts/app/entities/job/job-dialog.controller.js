'use strict';

angular.module('hotelApp').controller('JobDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Job',
        function($scope, $stateParams, $uibModalInstance, entity, Job) {

        $scope.job = entity;
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
}]);
