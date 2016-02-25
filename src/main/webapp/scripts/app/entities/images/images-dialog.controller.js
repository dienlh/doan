'use strict';

angular.module('hotelApp').controller('ImagesDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Images',
        function($scope, $stateParams, $uibModalInstance, entity, Images) {

        $scope.images = entity;
        $scope.load = function(id) {
            Images.get({id : id}, function(result) {
                $scope.images = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:imagesUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.images.id != null) {
                Images.update($scope.images, onSaveSuccess, onSaveError);
            } else {
                Images.save($scope.images, onSaveSuccess, onSaveError);
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
