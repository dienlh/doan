'use strict';

angular.module('hotelApp').controller('CurrencyDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Currency', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Currency, User) {

        $scope.currency = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Currency.get({id : id}, function(result) {
                $scope.currency = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:currencyUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.currency.id != null) {
                Currency.update($scope.currency, onSaveSuccess, onSaveError);
            } else {
                Currency.save($scope.currency, onSaveSuccess, onSaveError);
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
