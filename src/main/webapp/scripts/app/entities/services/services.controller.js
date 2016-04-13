'use strict';

angular.module('hotelApp').controller(
		'ServicesController',
		function($scope, $state, Services, ParseLinks,Status_service) {

			$scope.servicess = [];
			$scope.status_services = Status_service.query();
			$scope.predicate = 'id';
			$scope.reverse = true;
			$scope.page = 1;
			$scope.name="";
			$scope.status_service={
					id:0
			};
			function checkNull(){
				if(!$scope.status_service){
					$scope.status_service={
							id:0
					};
				}
			}
			$scope.loadAll = function() {
				checkNull();
				Services.findAllByNameAndStatus({
					name:$scope.name,
					statusId:$scope.status_service.id,
					page : $scope.page - 1,
					size : 20,
					sort : [
							$scope.predicate + ','
									+ ($scope.reverse ? 'asc' : 'desc'), 'id' ]
				}, function(result, headers) {
					$scope.links = ParseLinks.parse(headers('link'));
					$scope.totalItems = headers('X-Total-Count');
					$scope.servicess = result;
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
				$scope.services = {
					name : null,
					price : null,
					decription : null,
					create_date : null,
					last_modified_date : null,
					id : null
				};
			};
		});
