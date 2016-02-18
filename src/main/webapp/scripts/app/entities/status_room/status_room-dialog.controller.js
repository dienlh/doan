'use strict';

angular.module('hotelApp').controller('Status_roomDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Status_room',
        function($scope, $stateParams, $uibModalInstance, entity, Status_room) {

        $scope.status_room = entity;
        $scope.load = function(id) {
            Status_room.get({id : id}, function(result) {
                $scope.status_room = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:status_roomUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.status_room.id != null) {
                Status_room.update($scope.status_room, onSaveSuccess, onSaveError);
            } else {
                Status_room.save($scope.status_room, onSaveSuccess, onSaveError);
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
