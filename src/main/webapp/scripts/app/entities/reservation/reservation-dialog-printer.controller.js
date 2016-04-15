'use strict';
angular.module('hotelApp').controller('ReservationDialogPrinterController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Reservation', 'Customer', 'Register_info', 'Status_reservation', 'User', 'Bill',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Reservation, Customer, Register_info, Status_reservation, User, Bill) {

    	console.log($stateParams);
    	$scope.url="api/reservations/exportPDF/"+$stateParams.id;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
