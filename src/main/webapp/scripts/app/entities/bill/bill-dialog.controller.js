'use strict';

angular.module('hotelApp').controller('BillDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', '$state', 'entity', 'Bill', 'Currency', 'Customer', 'Method_payment', 'Status_payment', 'Reservation', 'Status_bill', 'User',
        function($scope, $stateParams, $uibModalInstance, $q, $state, entity, Bill, Currency, Customer, Method_payment, Status_payment, Reservation, Status_bill, User) {

        $scope.bill = entity;
        $scope.currencys = Currency.query();
        $scope.customers = Customer.query();
        $scope.method_payments = Method_payment.query();
        $scope.status_payments = Status_payment.query();
        $scope.reservations = Reservation.findReservationNotCheckout();
        $q.all([$scope.bill.$promise, $scope.reservations.$promise]).then(function() {
            if (!$scope.bill.reservation || !$scope.bill.reservation.id) {
                return $q.reject();
            }
            return Reservation.get({id : $scope.bill.reservation.id}).$promise;
        }).then(function(reservation) {
            $scope.reservations.push(reservation);
        });
        $scope.status_bills = Status_bill.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            Bill.get({id : id}, function(result) {
                $scope.bill = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:billUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.bill.id != null) {
            	var r = confirm("Bạn có chắc chắn muốn lập phiếu thanh toán!");
				if (r == true) {
					Bill.update($scope.bill, onSaveSuccess, onSaveError);
					$state.go('bill.printer',{idbill:$scope.bill.id});
				} else {
				    return false;
				}
                
                
            } else {
                Bill.save($scope.bill, onSaveSuccess, onSaveError);
            }
        };

        $scope.saveUpdate = function () {
            $scope.isSaving = true;
            if ($scope.bill.id != null) {
            	var r = confirm("Bạn có chắc chắn muốn cập nhật trạng thái phiếu thanh toán!");
				if (r == true) {
					Bill.update($scope.bill, onSaveSuccess, onSaveError);
				} else {
				    return false;
				}
            } else {
                Bill.save($scope.bill, onSaveSuccess, onSaveError);
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
