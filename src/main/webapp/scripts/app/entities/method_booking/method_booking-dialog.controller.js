'use strict';

angular.module('hotelApp').controller('Method_bookingDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Method_booking', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Method_booking, User) {

        $scope.method_booking = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Method_booking.get({id : id}, function(result) {
                $scope.method_booking = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:method_bookingUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.method_booking.id != null) {
                Method_booking.update($scope.method_booking, onSaveSuccess, onSaveError);
            } else {
                Method_booking.save($scope.method_booking, onSaveSuccess, onSaveError);
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
