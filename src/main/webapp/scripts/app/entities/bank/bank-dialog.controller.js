'use strict';

angular.module('hotelApp').controller('BankDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Bank', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Bank, User) {

        $scope.bank = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Bank.get({id : id}, function(result) {
                $scope.bank = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:bankUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.bank.id != null) {
                Bank.update($scope.bank, onSaveSuccess, onSaveError);
            } else {
                Bank.save($scope.bank, onSaveSuccess, onSaveError);
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
