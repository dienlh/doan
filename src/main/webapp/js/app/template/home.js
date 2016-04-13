
app
		.controller(
				'homeCtrl',
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
							$http.get('api/events', null).then(
									function(response) {
										$scope.events = response.data;
										callBackPagination($scope.events);
									}, function(x) {
										$scope.authError = 'Server Error';
									});

							$scope.fromDate = new Date();
							$scope.toDate = new Date();
							$scope.adults = 0;
							$scope.kids = 0;
							
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
						} ]);
