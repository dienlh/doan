'use strict';

angular.module('hotelApp').controller('Status_paymentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Status_payment', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Status_payment, User) {

        $scope.status_payment = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Status_payment.get({id : id}, function(result) {
                $scope.status_payment = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:status_paymentUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.status_payment.id != null) {
                Status_payment.update($scope.status_payment, onSaveSuccess, onSaveError);
            } else {
                Status_payment.save($scope.status_payment, onSaveSuccess, onSaveError);
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
