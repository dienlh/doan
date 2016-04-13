'use strict';

angular.module('hotelApp').controller('MajorDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Major', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Major, User) {

        $scope.major = entity;
        $scope.users = User.query();
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
        $scope.datePickerForCreate_date = {};

        $scope.datePickerForCreate_date.status = {
            opened: false
        };

        $scope.datePickerForCreate_dateOpen = function($event) {
            $scope.datePickerForCreate_date.status.opened = true;
        };
}]);
