'use strict';

angular.module('hotelApp').controller('BankDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Bank',
        function($scope, $stateParams, $uibModalInstance, entity, Bank) {

        $scope.bank = entity;
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
}]);
