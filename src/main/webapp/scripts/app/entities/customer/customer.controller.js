'use strict';

angular.module('hotelApp').controller(
		'CustomerController',
		function($scope, $state, Customer, ParseLinks) {

			$scope.customers = [];
			$scope.predicate = 'id';
			$scope.reverse = true;
			$scope.page = 1;
			$scope.email="";
			$scope.ic_number="";
			$scope.loadAll = function() {
				Customer.findAllByIcPassportNumberAndEmail({
//					email:$scope.email,
					ipnumber:$scope.ic_number,
					page : $scope.page - 1,
					size : 10,
					sort : [
							$scope.predicate + ','
									+ ($scope.reverse ? 'asc' : 'desc'), 'id' ]
				}, function(result, headers) {
					$scope.links = ParseLinks.parse(headers('link'));
					$scope.totalItems = headers('X-Total-Count');
					$scope.customers = result;
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
				$scope.customer = {
					full_name : null,
					ic_passport_number : null,
					ic_passport_prov_date : null,
					ic_passport_prov_add : null,
					email : null,
					phone_number : null,
					birthday : null,
					address : null,
					create_date : null,
					last_modified_date : null,
					id : null
				};
			};
		});
