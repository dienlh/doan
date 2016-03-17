'use strict';

angular.module('hotelApp').controller(
		'RoomController',
		function($scope, $state, Room, ParseLinks,Type_room,Status_room,$filter) {

			$scope.type_rooms = Type_room.query();
	        $scope.status_rooms = Status_room.query();
			$scope.rooms = [];
			$scope.predicate = 'id';
			$scope.reverse = true;
			$scope.page = 1;
			$scope.code="";
			function checkNull(){
				if(!$scope.type_room){
					$scope.type_room={
							id:0
					}
				}
				if(!$scope.status_room){
					$scope.status_room={
							id:0
					}
				}
			}
			$scope.loadAll = function() {
				checkNull();
				Room.findAllByTypeAndStatus({
					code:$scope.code,
					type_room:$scope.type_room.id,
					status_room:$scope.status_room.id,
					page : $scope.page - 1,
					size : 10,
					sort : [
							$scope.predicate + ','
									+ ($scope.reverse ? 'asc' : 'desc'), 'id' ]
				}, function(result, headers) {
					$scope.links = ParseLinks.parse(headers('link'));
					$scope.totalItems = headers('X-Total-Count');
					$scope.rooms = result;
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
				$scope.room = {
					code : null,
					key_code : null,
					title : null,
					is_pet : false,
					is_bed_kid : false,
					number_of_livingroom : null,
					number_of_bedroom : null,
					number_of_toilet : null,
					number_of_kitchen : null,
					number_of_bathroom : null,
					floor : null,
					orientation : null,
					surface_size : null,
					max_adults : null,
					max_kids : null,
					hourly_price : null,
					daily_price : null,
					monthly_price : null,
					create_date : null,
					last_modified_date : null,
					id : null
				};
			};
		});
