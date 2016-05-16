'use strict';

angular
		.module('hotelApp')
		.controller(
				'ReservationDetailController',
				function($scope, $rootScope, $stateParams, entity, Reservation,
						Customer, Register_info, Status_reservation, User,
						Bill, Bill_service, ParseLinks, $state,$http) {
					$scope.reservation = entity;
					$scope.showExport = false;
					$scope.page = 1;
					$scope.reverse = true;
					$scope.predicate = 'id';
					$scope.bill_services = Bill_service.findAllByReservationId(
							{
								reservationId : $stateParams.id,
								page : $scope.page - 1,
								size : 20,
								sort : [
										$scope.predicate
												+ ','
												+ ($scope.reverse ? 'asc'
														: 'desc'), 'id' ]
							}, function(result, headers) {
								$scope.links = ParseLinks
										.parse(headers('link'));
								$scope.totalItems = headers('X-Total-Count');
								$scope.bill_services = result;
							});
					Bill.findOneByReservationId({
						reservationId : $stateParams.id
					}, function(result) {
						$scope.bills=[result];
						$scope.showExport=false;
					}, function(x) {
						$scope.showExport=true;
					});
					$scope.load = function(id) {
						Reservation.get({
							id : id
						}, function(result) {
							$scope.reservation = result;
						});
					};
					$scope.exportBillForm = function() {
						if ($scope.reservation.status_reservation.id == 1) {
							alert("Bạn chưa check-out phòng!");
							return false;
						} else {
							var r = confirm("Bạn có chắc chắn muốn xuất phiếu thanh toán!");
							if (r == true) {
								$http.get('api/bill_services/findAllByReservationIdAndStatus?reservationId='+$stateParams.id).then(function(response) {
									console.log(response.data);
									if(response.data.length != 0){
										alert("Bạn chưa xử lý phiếu đăng ký dịch vụ!");
										return false;
									}else{
										$state.go("bill.new");
									}
								}, function(x) {
									alert("Hệ thống lỗi!");
									return false;
								});
							} else {
								return false;
							}
						}
					}

					var unsubscribe = $rootScope.$on(
							'hotelApp:reservationUpdate', function(event,
									result) {
								$scope.reservation = result;
							});
					$scope.$on('$destroy', unsubscribe);
					
					$scope.printer = function(id){
						var r = confirm("Bạn có chắc chắn muốn lập phiếu thanh toán!");
						if (r == true) {
							console.log(id);
							$state.go('bill.printer',{idbill:id});
						} else {
						    return false;
						}
						
					}
				});
