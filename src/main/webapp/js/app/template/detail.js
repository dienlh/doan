
app
		.controller(
				'detailCtrl',
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
										'api/rooms/findAvailable',
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

							$scope.myInterval = 5000;
							$scope.noWrapSlides = false;
							$scope.active = 0;
							var slides = $scope.slides = [];
							var currIndex = 0;

							$scope.addSlide = function() {
								var newWidth = 600 + slides.length + 1;
								slides
										.push({
											image : 'http://lorempixel.com/'
													+ newWidth + '/300',
											text : [ 'Nice image',
													'Awesome photograph',
													'That is so cool',
													'I love that' ][slides.length % 4],
											id : currIndex++
										});
							};

							$scope.randomize = function() {
								var indexes = generateIndexesArray();
								assignNewIndexesToSlides(indexes);
							};

							for (var i = 0; i < 4; i++) {
								$scope.addSlide();
							}

							// Randomize logic below

							function assignNewIndexesToSlides(indexes) {
								for (var i = 0, l = slides.length; i < l; i++) {
									slides[i].id = indexes.pop();
								}
							}

							function generateIndexesArray() {
								var indexes = [];
								for (var i = 0; i < currIndex; ++i) {
									indexes[i] = i;
								}
								return shuffle(indexes);
							}

							// http://stackoverflow.com/questions/962802#962890
							function shuffle(array) {
								var tmp, current, top = array.length;

								if (top) {
									while (--top) {
										current = Math.floor(Math.random()
												* (top + 1));
										tmp = array[current];
										array[current] = array[top];
										array[top] = tmp;
									}
								}

								return array;
							}
						} ]);
>>>>>>> 0a0a82aacfba12efcd03d8a5496369204a1414b6
