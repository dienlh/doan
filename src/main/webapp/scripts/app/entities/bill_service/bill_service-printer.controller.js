'use strict';

angular.module('hotelApp').controller('Bill_servicePrinterController',
    ['$scope', '$stateParams', '$uibModalInstance', 'Bill_service', 'Currency', 'Services', 'Reservation', 'Status_bill_service', 'User',
        function($scope, $stateParams, $uibModalInstance, Bill_service, Currency, Services, Reservation, Status_bill_service, User) {

    	console.log($stateParams);
    	$scope.url="api/bill_services/exportPDF?fromDate="+$stateParams.fromDate+"&toDate="+$stateParams.toDate+"&serviceId="+$stateParams.serviceId+"&reservationId="+$stateParams.reservationId+"&statusId="+$stateParams.statusId;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForCreate_date = {};

        $scope.datePickerForCreate_date.status = {
            opened: false
        };

        $scope.datePickerForCreate_dateOpen = function($event) {
            $scope.datePickerForCreate_date.status.opened = true;
        };
}]);
