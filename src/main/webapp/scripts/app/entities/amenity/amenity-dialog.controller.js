'use strict';

angular.module('hotelApp').controller('AmenityDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Amenity', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Amenity, User) {

        $scope.amenity = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Amenity.get({id : id}, function(result) {
                $scope.amenity = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:amenityUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.amenity.id != null) {
                Amenity.update($scope.amenity, onSaveSuccess, onSaveError);
            } else {
                Amenity.save($scope.amenity, onSaveSuccess, onSaveError);
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
