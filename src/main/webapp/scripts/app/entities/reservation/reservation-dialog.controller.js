'use strict';
app.filter('propsFilter', function() {
	return function(items, props) {
		var out = [];

		if (angular.isArray(items)) {
			items
					.forEach(function(item) {
						var itemMatches = false;

						var keys = Object.keys(props);
						for (var i = 0; i < keys.length; i++) {
							var prop = keys[i];
							var text = props[prop].toLowerCase();
							if (item[prop].toString().toLowerCase().indexOf(
									text) !== -1) {
								itemMatches = true;
								break;
							}
						}

						if (itemMatches) {
							out.push(item);
						}
					});
		} else {
			// Let the output be the input untouched
			out = items;
		}

		return out;
	};
})
angular.module('hotelApp').controller('ReservationDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Reservation', 'Customer', 'Register_info', 'Status_reservation', 'User', 'Bill',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Reservation, Customer, Register_info, Status_reservation, User, Bill) {

        $scope.reservation = entity;
        $scope.checkout=true;
        $scope.customers = Customer.query();
        $scope.register_infos = Register_info.findAllRegisterChecked();
        $q.all([$scope.reservation.$promise, $scope.register_infos.$promise]).then(function() {
            if (!$scope.reservation.register_info || !$scope.reservation.register_info.id) {
                return $q.reject();
            }
            return Register_info.get({id : $scope.reservation.register_info.id}).$promise;
        }).then(function(register_info) {
            $scope.register_infos.push(register_info);
        });
        $scope.status_reservations = Status_reservation.query();
        $scope.users = User.query();
        $scope.bills = Bill.query();
        $scope.load = function(id) {
            Reservation.get({id : id}, function(result) {
                $scope.reservation = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:reservationUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.$watch('checkout',function(){
        	if($scope.checkout){
        		$scope.showcheckout=true;
        	}else{
        		$scope.showcheckout=false;
        	}
        });
       
        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.reservation.id != null) {
            	if ($scope.checkout == true) {
					var r = confirm("Confirm check-out");
					if (r == true) {
						if ($scope.reservation.time_checkout == null) {
							$scope.reservation.time_checkout = new Date();
						}
					} else {
						return false;
					}
					
				}
                Reservation.update($scope.reservation, onSaveSuccess, onSaveError);
            } else {
            	if(!$scope.reservation.customers){
            		alert("Bạn cần thêm danh sách khách ở phòng");
            		return false;
            	}
                Reservation.save($scope.reservation, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForTime_checkin = {};

        $scope.datePickerForTime_checkin.status = {
            opened: false
        };

        $scope.datePickerForTime_checkinOpen = function($event) {
            $scope.datePickerForTime_checkin.status.opened = true;
        };
        $scope.datePickerForTime_checkout = {};

        $scope.datePickerForTime_checkout.status = {
            opened: false
        };

        $scope.datePickerForTime_checkoutOpen = function($event) {
            $scope.datePickerForTime_checkout.status.opened = true;
        };
        $scope.datePickerForCreate_date = {};

        $scope.datePickerForCreate_date.status = {
            opened: false
        };

        $scope.datePickerForCreate_dateOpen = function($event) {
            $scope.datePickerForCreate_date.status.opened = true;
        };
        $scope.datePickerForLast_modified_date = {};

        $scope.datePickerForLast_modified_date.status = {
            opened: false
        };

        $scope.datePickerForLast_modified_dateOpen = function($event) {
            $scope.datePickerForLast_modified_date.status.opened = true;
        };
//        $scope.reservation.customers=Customer.query();

}]);
