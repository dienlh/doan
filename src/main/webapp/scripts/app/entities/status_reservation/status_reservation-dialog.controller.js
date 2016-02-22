'use strict';

angular.module('hotelApp').controller('Status_reservationDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Status_reservation', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Status_reservation, User) {

        $scope.status_reservation = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Status_reservation.get({id : id}, function(result) {
                $scope.status_reservation = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:status_reservationUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.status_reservation.id != null) {
                Status_reservation.update($scope.status_reservation, onSaveSuccess, onSaveError);
            } else {
                Status_reservation.save($scope.status_reservation, onSaveSuccess, onSaveError);
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
