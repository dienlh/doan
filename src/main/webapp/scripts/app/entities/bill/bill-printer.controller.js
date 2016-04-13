'use strict';

angular.module('hotelApp').controller('BillPrinterController',
    ['$scope', '$stateParams', '$uibModalInstance', 'Bill_service', 'Currency', 'Services', 'Reservation', 'Status_bill_service', 'User',
        function($scope, $stateParams, $uibModalInstance, Bill_service, Currency, Services, Reservation, Status_bill_service, User) {

    	console.log($stateParams);
    	$scope.url="api/bills/exportPDF/"+$stateParams.id;
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