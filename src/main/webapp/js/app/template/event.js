
app.controller('eventCtrl', [ '$scope', '$http', '$stateParams', '$log',
		'$state', '$modal', '$filter',
		function($scope, $http, $stateParams, $log, $state, $modal, $filter) {
			$http.get('api/events/'+$stateParams.id, null).then(function(response) {
				$scope.event = response.data;
				console.log($scope.event);
			}, function(x) {
				$scope.authError = 'Server Error';
			});

			$http.get('api/events', null).then(function(response) {
				$scope.events = response.data;
				console.log($scope.events);
			}, function(x) {
				$scope.authError = 'Server Error';
			});
		} ]);
