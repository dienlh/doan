'use strict';

angular.module('hotelApp').controller('Status_billDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Status_bill', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Status_bill, User) {

        $scope.status_bill = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Status_bill.get({id : id}, function(result) {
                $scope.status_bill = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:status_billUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.status_bill.id != null) {
                Status_bill.update($scope.status_bill, onSaveSuccess, onSaveError);
            } else {
                Status_bill.save($scope.status_bill, onSaveSuccess, onSaveError);
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
