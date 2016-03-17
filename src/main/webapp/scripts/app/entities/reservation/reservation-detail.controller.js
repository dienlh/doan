'use strict';

angular.module('hotelApp').controller(
		'ReservationDetailController',
		function($scope, $rootScope, $stateParams, entity, Reservation,
				Customer, Register_info, Status_reservation, User, Bill,Bill_service) {
			$scope.reservation = entity;
			$scope.page=1;
			$scope.bill_service = Bill_service.query({
				reservationId:$stateParams.id,
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
			$scope.bill=Bill.findOneByReservationId({reservationId : $stateParams.id});
			$scope.load = function(id) {
				Reservation.get({
					id : id
				}, function(result) {
					$scope.reservation = result;
				});
			};
			var unsubscribe = $rootScope.$on('hotelApp:reservationUpdate',
					function(event, result) {
						$scope.reservation = result;
					});
			$scope.$on('$destroy', unsubscribe);

		});
