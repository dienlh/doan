'use strict';

describe('Controller Tests', function() {

    describe('Bill_service Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBill_service, MockCurrency, MockServices, MockReservation, MockStatus_bill_service, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBill_service = jasmine.createSpy('MockBill_service');
            MockCurrency = jasmine.createSpy('MockCurrency');
            MockServices = jasmine.createSpy('MockServices');
            MockReservation = jasmine.createSpy('MockReservation');
            MockStatus_bill_service = jasmine.createSpy('MockStatus_bill_service');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Bill_service': MockBill_service,
                'Currency': MockCurrency,
                'Services': MockServices,
                'Reservation': MockReservation,
                'Status_bill_service': MockStatus_bill_service,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("Bill_serviceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelApp:bill_serviceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
