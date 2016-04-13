app
		.controller(
				'searchCtrl',
				[
						'$scope',
						'$http',
						'$stateParams',
						'$log',
						'$state',
						'$modal',
						'$filter',
						function($scope, $http, $stateParams, $log, $state,
								$modal, $filter) {
							$http.get('api/type_rooms', null).then(
									function(response) {
										$scope.type_rooms = response.data;
										console.log($scope.type_rooms);
									}, function(x) {
										$scope.authError = 'Server Error';
									});

							$scope.fromDate = new Date();
							$scope.toDate = new Date();
							$scope.adults = 0;
							$scope.kids = 0;
							$scope.find = function() {
								$http.get(
										'api/rooms/findAllAvailable',
										{
											params : {
												type_room : $scope.type_room,
												fromDate : $filter('date')(
														$scope.fromDate,
														'yyyy-MM-dd'),
												toDate : $filter('date')(
														$scope.toDate,
														'yyyy-MM-dd'),
												adults : $scope.adults,
												kids : $scope.kids
											}
										}).then(function(response) {
									$scope.findValue = response.data;
									callBackPagination(response.data);
									console.log($scope.findValue);
								}, function(x) {
									$scope.authError = 'Server Error';
								});
							}

							$scope.find();
							// pagination
							function callBackPagination(data) {
								$scope.arCurrent = data;
								$scope.filteredTodos = [],
										$scope.currentPage = 1,
										$scope.numPerPage = 5,
										$scope.maxSize = 5;
								$scope
										.$watch(
												'currentPage + numPerPage',
												function() {
													var begin = (($scope.currentPage - 1) * $scope.numPerPage), end = begin
															+ $scope.numPerPage;
													$scope.filteredTodos = data
															.slice(begin, end);
												});

							}
							// end pagination

							$scope.roomInfo = {};
							$scope.open = function(size, id) {
								$http
										.get('api/rooms/' + id, {})
										.then(
												function(response) {
													var modalInstance = $modal
															.open({
																templateUrl : 'myModalContent.html',
																controller : 'ModalInstanceCtrlRoom',
																size : size,
																resolve : {
																	items : function() {
																		return response.data;
																	},
																	checkin : function() {
																		return $scope.checkin;
																	},
																	checkout : function() {
																		return $scope.checkout;
																	}
																}
															});

													modalInstance.result
															.then(
																	function(
																			selectedItem) {
																		$scope.selected = selectedItem;
																	},
																	function() {
																		$log
																				.info('Modal dismissed at: '
																						+ new Date());
																	});
												},
												function(x) {
													$scope.authError = 'Server Error';
												});
							};
							// end modal
						} ]);
app.controller('ModalInstanceCtrlRoom', [ '$scope', '$modalInstance', 'items',
		'checkin', 'checkout', '$http',
		function($scope, $modalInstance, items, checkin, checkout, $http) {
			$scope.items = items;
			$scope.checkin = checkin;
			$scope.checkout = checkout;

			if ($scope.items.imagess.length == 0) {
				for (var i = 1; i <= 4; i++) {
					$scope.items.imagess.push({
						id : i,
						url : "template/images/imageForm/" + i + ".jpg"
					})
				}
			}

			$scope.showPanoramaApartment = true
			$scope.showPanoramaOffice = false;
			function pageAmenity() {
				var index = 0;
				$scope.pageAmenity = [];
				var arlist = [];
				angular.forEach($scope.items.amenitys, function(item) {
					index++;
					if (index % 2 == 0) {
						arlist.push(item);
						$scope.pageAmenity.push(arlist);
						arlist = [];

					} else {
						arlist.push(item);
					}
				});
			}
			pageAmenity();
			$scope.selected = {
				item : $scope.items[0]
			};

			$scope.ok = function() {
				$modalInstance.close($scope.selected.item);
			};

			$scope.test = function() {
				alert(items);
			}
			$scope.cancel = function() {
				$modalInstance.dismiss('cancel');
			};

			$scope.myInterval = 5000;
		} ]);