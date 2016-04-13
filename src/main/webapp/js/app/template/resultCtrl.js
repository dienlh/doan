app.controller('resultCtrl', [
		'$scope',
		'$http',
		'$stateParams',
		'$log',
		'$state',
		function($scope, $http, $stateParams, $log, $state) {
			$scope.id = $stateParams.id;
			$http.get('api/register_infos/' + $scope.id, {}).then(
					function(response) {
						$scope.register_info = response.data;
						console.log($scope.register_info);
					}, function(x) {
						alert("Đăng ký không được tìm thấy!")
						$state.go('template.reservation');
					});
			$http.get('api/policys', null).then(function(response) {
				$scope.policys = response.data;
				console.log($scope.policys);
			}, function(x) {
				$scope.authError = 'Server Error';
			});
		} ])
