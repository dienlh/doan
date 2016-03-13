'use strict';

angular.module('hotelApp').controller(
		'EventController',
		function($scope, $state, Event, ParseLinks,Status_event,$filter) {

			$scope.required=false;
			$scope.events = [];
			$scope.status_events = Status_event.query();
			$scope.predicate = 'id';
			$scope.reverse = true;
			$scope.page = 1;
			function checkNull() {
				if (!$scope.status_event) {
					$scope.status_event = {
							id:0
					};
				}
			}
			$scope.loadAll = function() {
				checkNull();
				Event.findAllByRangerDateAndStatus({
					fromDate:$filter('date')($scope.fromDate,'yyyy-MM-dd'),
					toDate:$filter('date')($scope.toDate,'yyyy-MM-dd'),
					statusId:$scope.status_event.id,
					page : $scope.page - 1,
					size : 10,
					sort : [
							$scope.predicate + ','
									+ ($scope.reverse ? 'asc' : 'desc'), 'id' ]
				}, function(result, headers) {
					$scope.links = ParseLinks.parse(headers('link'));
					$scope.totalItems = headers('X-Total-Count');
					$scope.events = result;
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
			
			$scope.$watch('fromDate',function(){
				if(!$scope.fromDate){
					$scope.required=false;
				}else{
					$scope.required=true;
				}
			});
			$scope.$watch('toDate',function(){
				if(!$scope.toDate){
					$scope.required=false;
				}else{
					$scope.required=true;
				}
			});
			$scope.clear = function() {
				$scope.event = {
					title : null,
					decription : null,
					start_date : null,
					end_date : null,
					create_date : null,
					last_modified_date : null,
					id : null
				};
			};
		});
