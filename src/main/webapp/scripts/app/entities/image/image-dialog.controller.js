'use strict';

angular.module('hotelApp').controller('ImageDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Image', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Image, User) {

        $scope.image = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Image.get({id : id}, function(result) {
                $scope.image = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:imageUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.image.id != null) {
                Image.update($scope.image, onSaveSuccess, onSaveError);
            } else {
                Image.save($scope.image, onSaveSuccess, onSaveError);
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
