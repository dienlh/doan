'use strict';

angular.module('hotelApp').controller('MajorDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Major',
        function($scope, $stateParams, $uibModalInstance, entity, Major) {

        $scope.major = entity;
        $scope.load = function(id) {
            Major.get({id : id}, function(result) {
                $scope.major = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:majorUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.major.id != null) {
                Major.update($scope.major, onSaveSuccess, onSaveError);
            } else {
                Major.save($scope.major, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
