'use strict';

angular.module('hotelApp').controller(
		'Bill_serviceController',
		function($scope, $state, Bill_service, ParseLinks, Room, Services,
				Status_bill_service,Reservation,$filter) {
			$scope.fromDate=$scope.toDate=new Date();
			$scope.rooms = Room.query();
			$scope.reservations=Reservation.query();
			$scope.status_bill_services = Status_bill_service.query();
			$scope.servicess = Services.query();
			$scope.bill_services = [];
			$scope.predicate = 'id';
			$scope.reverse = true;
			$scope.page = 1;
			$scope.defaultvalue={
					id:0
			};
			function checkNull(){
				if(!$scope.room){
					$scope.room=$scope.defaultvalue;
				}
				if(!$scope.service){
					$scope.service=$scope.defaultvalue;
				}
				if(!$scope.status_bill_service){
					$scope.status_bill_service=$scope.defaultvalue;
				}
			}
			$scope.loadAll = function() {
				checkNull();
				Bill_service.findAllByMultiAttr({
					fromDate:$filter('date')($scope.fromDate,'dd/MM/yyyy'),
					toDate:$filter('date')($scope.toDate,'dd/MM/yyyy'),
					serviceId : $scope.service.id, 
					statusId :$scope.status_bill_service.id, 
					roomId : $scope.room.id,
					page : $scope.page - 1,
					size : 20,
					sort : [
							$scope.predicate + ','
									+ ($scope.reverse ? 'asc' : 'desc'), 'id' ]
				}, function(result, headers) {
					$scope.links = ParseLinks.parse(headers('link'));
					$scope.totalItems = headers('X-Total-Count');
					$scope.bill_services = result;
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
				$scope.bill_service = {
					quantity : null,
					total : null,
					decription : null,
					create_date : null,
					id : null
				};
			};
		});
