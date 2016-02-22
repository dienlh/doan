'use strict';

describe('Controller Tests', function() {

    describe('Reservation Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockReservation, MockCustomer, MockRegister_info, MockStatus_reservation, MockUser, MockBill;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockReservation = jasmine.createSpy('MockReservation');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockRegister_info = jasmine.createSpy('MockRegister_info');
            MockStatus_reservation = jasmine.createSpy('MockStatus_reservation');
            MockUser = jasmine.createSpy('MockUser');
            MockBill = jasmine.createSpy('MockBill');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Reservation': MockReservation,
                'Customer': MockCustomer,
                'Register_info': MockRegister_info,
                'Status_reservation': MockStatus_reservation,
                'User': MockUser,
                'Bill': MockBill
            };
            createController = function() {
                $injector.get('$controller')("ReservationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelApp:reservationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
