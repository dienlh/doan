'use strict';

angular.module('hotelApp').controller('Method_paymentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Method_payment', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Method_payment, User) {

        $scope.method_payment = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Method_payment.get({id : id}, function(result) {
                $scope.method_payment = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:method_paymentUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.method_payment.id != null) {
                Method_payment.update($scope.method_payment, onSaveSuccess, onSaveError);
            } else {
                Method_payment.save($scope.method_payment, onSaveSuccess, onSaveError);
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
