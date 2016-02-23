'use strict';

angular.module('hotelApp').controller('Bill_serviceDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Bill_service', 'Services', 'Currency', 'Reservation', 'Status_bill_service', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Bill_service, Services, Currency, Reservation, Status_bill_service, User) {

        $scope.bill_service = entity;
        $scope.servicess = Services.query();
        $scope.currencys = Currency.query();
        $scope.reservations = Reservation.query();
        $scope.status_bill_services = Status_bill_service.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            Bill_service.get({id : id}, function(result) {
                $scope.bill_service = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:bill_serviceUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.bill_service.id != null) {
                Bill_service.update($scope.bill_service, onSaveSuccess, onSaveError);
            } else {
                Bill_service.save($scope.bill_service, onSaveSuccess, onSaveError);
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
