'use strict';

angular.module('hotelApp').controller(
		'Register_infoController',
		function($scope, $state, Register_info, ParseLinks, Method_payment,
				Status_payment, Method_register, Status_register,$filter) {

			$scope.method_payments = Method_payment.query();
			$scope.status_payments = Status_payment.query();
			$scope.method_registers = Method_register.query();
			$scope.status_registers = Status_register.query();

			$scope.register_infos = [];
			$scope.predicate = 'id';
			$scope.reverse = true;
			$scope.page = 1;
			$scope.defaultvalue={
					id:0
			};
			function checkNull(){
				if(!$scope.method_payment){
					$scope.method_payment=$scope.defaultvalue;
				}
				if(!$scope.status_payment){
					$scope.status_payment=$scope.defaultvalue;
				}
				if(!$scope.method_register){
					$scope.method_register=$scope.defaultvalue;
				}
				if(!$scope.status_register){
					$scope.status_register=$scope.defaultvalue;
				}
			}
			$scope.code="";
			$scope.ipnumber="";
			
			$scope.loadAll = function() {
				checkNull();
				Register_info.findAllByMultiAttr({
					fromDate:$filter('date')($scope.fromDate,'yyyy-MM-dd'),
					toDate:$filter('date')($scope.toDate,'yyyy-MM-dd'),
					code:$scope.code,
					ipnumber:$scope.ipnumber,
					method_payment:$scope.method_payment.id,
					status_payment:$scope.status_payment.id,
					method_register:$scope.method_register.id,
					status_register:$scope.status_register.id,
					page : $scope.page - 1,
					size : 20,
					sort : [
							$scope.predicate + ','
									+ ($scope.reverse ? 'asc' : 'desc'), 'id' ]
				}, function(result, headers) {
					$scope.links = ParseLinks.parse(headers('link'));
					$scope.totalItems = headers('X-Total-Count');
					$scope.register_infos = result;
				});
			};
			$scope.loadPage = function(page) {
				$scope.page = page;
				$scope.loadAll();
			};
			$scope.loadAll();

			$scope.refresh = function() {
				$scope.loadAll();
				$scope.clear();
			};

			$scope.clear = function() {
				$scope.register_info = {
					date_checkin : null,
					date_checkout : null,
					number_of_adult : null,
					number_of_kid : null,
					other_request : null,
					deposit_value : null,
					create_date : null,
					last_modified_date : null,
					id : null
				};
			};
		});
