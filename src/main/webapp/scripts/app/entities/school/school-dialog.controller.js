'use strict';

angular.module('hotelApp').controller('SchoolDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'School',
        function($scope, $stateParams, $uibModalInstance, entity, School) {

        $scope.school = entity;
        $scope.load = function(id) {
            School.get({id : id}, function(result) {
                $scope.school = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:schoolUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.school.id != null) {
                School.update($scope.school, onSaveSuccess, onSaveError);
            } else {
                School.save($scope.school, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
