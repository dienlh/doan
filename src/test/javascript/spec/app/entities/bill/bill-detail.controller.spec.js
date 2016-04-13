'use strict';

describe('Controller Tests', function() {

    describe('Bill Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBill, MockCurrency, MockCustomer, MockMethod_payment, MockStatus_payment, MockReservation, MockStatus_bill, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBill = jasmine.createSpy('MockBill');
            MockCurrency = jasmine.createSpy('MockCurrency');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockMethod_payment = jasmine.createSpy('MockMethod_payment');
            MockStatus_payment = jasmine.createSpy('MockStatus_payment');
            MockReservation = jasmine.createSpy('MockReservation');
            MockStatus_bill = jasmine.createSpy('MockStatus_bill');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Bill': MockBill,
                'Currency': MockCurrency,
                'Customer': MockCustomer,
                'Method_payment': MockMethod_payment,
                'Status_payment': MockStatus_payment,
                'Reservation': MockReservation,
                'Status_bill': MockStatus_bill,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("BillDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelApp:billUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
