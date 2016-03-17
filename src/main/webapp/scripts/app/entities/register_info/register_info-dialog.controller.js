'use strict';

angular
		.module('hotelApp')
		.controller(
				'Register_infoDialogController',
				[
						'$scope',
						'$stateParams',
						'$uibModalInstance',
						'entity',
						'Register_info',
						'Currency',
						'Room',
						'Customer',
						'Method_payment',
						'Status_payment',
						'Method_register',
						'Status_register',
						'User',
						'Reservation','$filter',
						function($scope, $stateParams, $uibModalInstance,
								entity, Register_info, Currency, Room,
								Customer, Method_payment, Status_payment,
								Method_register, Status_register, User,
								Reservation,$filter) {

							$scope.register_info = entity;
							$scope.currencys = Currency.query();
							$scope.rooms = Room.query();
							$scope.customers = Customer.query();
							$scope.method_payments = Method_payment.query();
							$scope.status_payments = Status_payment.query();
							$scope.method_registers = Method_register.query();
							$scope.status_registers = Status_register.query();
							$scope.users = User.query();
							$scope.reservations = Reservation.query();
							$scope.load = function(id) {
								Register_info.get({
									id : id
								}, function(result) {
									$scope.register_info = result;
								});
							};

							var onSaveSuccess = function(result) {
								$scope.$emit('hotelApp:register_infoUpdate',
										result);
								$uibModalInstance.close(result);
								$scope.isSaving = false;
							};

							var onSaveError = function(result) {
								$scope.isSaving = false;
							};

							$scope.save = function() {
								$scope.isSaving = true;
								if ($scope.register_info.id != null) {
									Register_info.update($scope.register_info,
											onSaveSuccess, onSaveError);
								} else {
									Register_info.save($scope.register_info,
											onSaveSuccess, onSaveError);
								}
							};

							$scope.clear = function() {
								$uibModalInstance.dismiss('cancel');
							};
							$scope.datePickerForDate_checkin = {};

							$scope.datePickerForDate_checkin.status = {
								opened : false
							};

							$scope.datePickerForDate_checkinOpen = function(
									$event) {
								$scope.datePickerForDate_checkin.status.opened = true;
							};
							$scope.datePickerForDate_checkout = {};

							$scope.datePickerForDate_checkout.status = {
								opened : false
							};

							$scope.datePickerForDate_checkoutOpen = function(
									$event) {
								$scope.datePickerForDate_checkout.status.opened = true;
							};
							$scope.datePickerForCreate_date = {};

							$scope.datePickerForCreate_date.status = {
								opened : false
							};

							$scope.datePickerForCreate_dateOpen = function(
									$event) {
								$scope.datePickerForCreate_date.status.opened = true;
							};
							$scope.datePickerForLast_modified_date = {};

							$scope.datePickerForLast_modified_date.status = {
								opened : false
							};

							$scope.datePickerForLast_modified_dateOpen = function(
									$event) {
								$scope.datePickerForLast_modified_date.status.opened = true;
							};

							$scope.findAllByRangerTime = function() {
								if (!$scope.register_info.date_checkin) {
									console.log("check repreat");
									return;
								}
								$scope.rooms = Room.findAllByRangerTime({
									fromDate : $filter('date')(
											$scope.register_info.date_checkin,
											'yyyy-MM-dd'),
									toDate : $filter('date')(
											$scope.register_info.date_checkout,
											'yyyy-MM-dd'),
								});
							}
							$scope.$watch('register_info.date_checkin',
									function() {
										$scope.findAllByRangerTime();
									})
							$scope.$watch('register_info.date_checkout',
									function() {
										$scope.findAllByRangerTime();
									})
						} ]);
