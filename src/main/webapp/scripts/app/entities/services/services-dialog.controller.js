'use strict';

angular.module('hotelApp').controller('ServicesDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Services', 'Status_service', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Services, Status_service, User) {

        $scope.services = entity;
        $scope.status_services = Status_service.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            Services.get({id : id}, function(result) {
                $scope.services = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:servicesUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.services.id != null) {
                Services.update($scope.services, onSaveSuccess, onSaveError);
            } else {
                Services.save($scope.services, onSaveSuccess, onSaveError);
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
        $scope.datePickerForLast_modified_date = {};

        $scope.datePickerForLast_modified_date.status = {
            opened: false
        };

        $scope.datePickerForLast_modified_dateOpen = function($event) {
            $scope.datePickerForLast_modified_date.status.opened = true;
        };
}]);
