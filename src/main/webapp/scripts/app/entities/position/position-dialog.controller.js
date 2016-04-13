'use strict';

angular.module('hotelApp').controller('PositionDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Position', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Position, User) {

        $scope.position = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Position.get({id : id}, function(result) {
                $scope.position = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:positionUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.position.id != null) {
                Position.update($scope.position, onSaveSuccess, onSaveError);
            } else {
                Position.save($scope.position, onSaveSuccess, onSaveError);
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
