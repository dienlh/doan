'use strict';

angular.module('hotelApp').controller('Register_infoDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Register_info', 'Customer', 'Room', 'Currency', 'Method_payment', 'Status_payment', 'Status_register', 'User', 'Reservation',
        function($scope, $stateParams, $uibModalInstance, entity, Register_info, Customer, Room, Currency, Method_payment, Status_payment, Status_register, User, Reservation) {

        $scope.register_info = entity;
        $scope.customers = Customer.query();
        $scope.rooms = Room.query();
        $scope.currencys = Currency.query();
        $scope.method_payments = Method_payment.query();
        $scope.status_payments = Status_payment.query();
        $scope.status_registers = Status_register.query();
        $scope.users = User.query();
        $scope.reservations = Reservation.query();
        $scope.load = function(id) {
            Register_info.get({id : id}, function(result) {
                $scope.register_info = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:register_infoUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.register_info.id != null) {
                Register_info.update($scope.register_info, onSaveSuccess, onSaveError);
            } else {
                Register_info.save($scope.register_info, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForDate_checkin = {};

        $scope.datePickerForDate_checkin.status = {
            opened: false
        };

        $scope.datePickerForDate_checkinOpen = function($event) {
            $scope.datePickerForDate_checkin.status.opened = true;
        };
        $scope.datePickerForDate_checkout = {};

        $scope.datePickerForDate_checkout.status = {
            opened: false
        };

        $scope.datePickerForDate_checkoutOpen = function($event) {
            $scope.datePickerForDate_checkout.status.opened = true;
        };
        $scope.datePickerForCreate_date = {};

        $scope.datePickerForCreate_date.status = {
            opened: false
        };

        $scope.datePickerForCreate_dateOpen = function($event) {
            $scope.datePickerForCreate_date.status.opened = true;
        };
        $scope.datePickerForLast_modified_date = {};

        $scope.datePickerForLast_modified_date.status = {
            opened: false
        };

        $scope.datePickerForLast_modified_dateOpen = function($event) {
            $scope.datePickerForLast_modified_date.status.opened = true;
        };
}]);
