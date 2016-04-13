app.controller('bookingCtrl', [
		'$scope',
		'$http',
		'$stateParams',
		'$log',
		'$state','$filter',
		function($scope, $http, $stateParams, $log, $state,$filter) {
			$http.get('api/policys', null).then(function(response) {
				$scope.policys = response.data;
				console.log($scope.policys);
			}, function(x) {
				$scope.authError = 'Server Error';
			});
			$scope.idRoom = $stateParams.idRoom;
			$scope.checkin = $stateParams.checkin;
			$scope.checkout = $stateParams.checkout;
			
			if(!$stateParams.message){
				$scope.message="";
			}else{
				$scope.message=$stateParams.message;
			}
			console.log($scope.message);
			var datecheckin = parseDate($scope.checkin);
			var datecheckout = parseDate($scope.checkout);
			function parseDate(input, format) {
				format = format || 'yyyy-mm-dd'; // default format
				var parts = input.match(/(\d+)/g), i = 0, fmt = {};
				// extract date-part indexes from the format
				format.replace(/(yyyy|dd|mm)/g, function(part) {
					fmt[part] = i++;
				});

				return new Date(parts[fmt['yyyy']], parts[fmt['mm']] - 1,
						parts[fmt['dd']]);
			}
			if (!(datecheckin instanceof Date && !isNaN(datecheckin.valueOf()))
					|| !(datecheckout instanceof Date && !isNaN(datecheckout
							.valueOf()))) {
				alert("lỗi định dạng ngày");
				$state.go('template.reservation');
				return false;
			}
			$scope.rangerTime = Math.round(new Date($scope.checkout
					+ " 00:00:00").getTime()
					- new Date($scope.checkin + " 00:00:00").getTime())
					/ (24 * 60 * 60 * 1000);
			if($scope.rangerTime<0){
				alert("Ngày nhận phòng phải trước ngày trả phòng");
				$state.go('template.reservation');
			}
			if ($scope.rangerTime == 0) {
				$scope.rangerTime++;
			}

			$http.get('api/rooms/findOneAvailable', {
				params:{
					fromDate:$filter('date')($scope.checkin,"yyyy-MM-dd"),
					toDate:$filter('date')($scope.checkout,"yyyy-MM-dd"),
					room:$scope.idRoom
				}
			}).then(function(response) {
				if (response.data == "") {
					alert("Phòng này không có sẵn!")
					$state.go('template.reservation');
					return false;
				}
				$scope.room = response.data;
				$scope.changeValuePayment($scope.method);

			}, function(x) {
				alert("Lỗi lấy dữ liệu từ service!")
				$state.go('template.reservation');
			});
			$scope.paymentClick = function() {
				$scope.customerInfo = {
					customerInfo : $scope.contact,
					registerInfo : $scope.registerInfo
				}
			}

			// params 1: Đặt cọc
			// params 2: Thanh toán toàn bộ
			// params 3: Thanh toán sau
			$scope.method_payment = "Đặt cọc";
			$scope.method = 1;

			$scope.changeValuePayment = function() {
				if ($scope.rangerTime >= 0 && $scope.rangerTime < 30) {
					$scope.total = $scope.room.daily_price * $scope.rangerTime;
				} else if ($scope.rangerTime >= 30) {
					$scope.total = $scope.room.monthly_price
							* $scope.rangerTime / 30;

				}
				$scope.deposit = $scope.total * 0.25;
			}
		} ])
