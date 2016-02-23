'use strict';

describe('Controller Tests', function() {

    describe('Register_info Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRegister_info, MockCustomer, MockRoom, MockCurrency, MockMethod_payment, MockStatus_payment, MockStatus_register, MockUser, MockReservation;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRegister_info = jasmine.createSpy('MockRegister_info');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockRoom = jasmine.createSpy('MockRoom');
            MockCurrency = jasmine.createSpy('MockCurrency');
            MockMethod_payment = jasmine.createSpy('MockMethod_payment');
            MockStatus_payment = jasmine.createSpy('MockStatus_payment');
            MockStatus_register = jasmine.createSpy('MockStatus_register');
            MockUser = jasmine.createSpy('MockUser');
            MockReservation = jasmine.createSpy('MockReservation');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Register_info': MockRegister_info,
                'Customer': MockCustomer,
                'Room': MockRoom,
                'Currency': MockCurrency,
                'Method_payment': MockMethod_payment,
                'Status_payment': MockStatus_payment,
                'Status_register': MockStatus_register,
                'User': MockUser,
                'Reservation': MockReservation
            };
            createController = function() {
                $injector.get('$controller')("Register_infoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelApp:register_infoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
