'use strict';

angular.module('hotelApp').controller('Status_eventDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Status_event', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Status_event, User) {

        $scope.status_event = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Status_event.get({id : id}, function(result) {
                $scope.status_event = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:status_eventUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.status_event.id != null) {
                Status_event.update($scope.status_event, onSaveSuccess, onSaveError);
            } else {
                Status_event.save($scope.status_event, onSaveSuccess, onSaveError);
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
