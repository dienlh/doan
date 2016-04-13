'use strict';

angular.module('hotelApp').controller('Status_serviceDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Status_service', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Status_service, User) {

        $scope.status_service = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Status_service.get({id : id}, function(result) {
                $scope.status_service = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:status_serviceUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.status_service.id != null) {
                Status_service.update($scope.status_service, onSaveSuccess, onSaveError);
            } else {
                Status_service.save($scope.status_service, onSaveSuccess, onSaveError);
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
