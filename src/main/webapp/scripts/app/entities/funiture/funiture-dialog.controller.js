'use strict';

angular.module('hotelApp').controller('FunitureDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Funiture', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Funiture, User) {

        $scope.funiture = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Funiture.get({id : id}, function(result) {
                $scope.funiture = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:funitureUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.funiture.id != null) {
                Funiture.update($scope.funiture, onSaveSuccess, onSaveError);
            } else {
                Funiture.save($scope.funiture, onSaveSuccess, onSaveError);
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
