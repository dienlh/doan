'use strict';

angular.module('hotelApp').controller('Came_componentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Came_component', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Came_component, User) {

        $scope.came_component = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Came_component.get({id : id}, function(result) {
                $scope.came_component = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:came_componentUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.came_component.id != null) {
                Came_component.update($scope.came_component, onSaveSuccess, onSaveError);
            } else {
                Came_component.save($scope.came_component, onSaveSuccess, onSaveError);
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
