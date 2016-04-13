'use strict';

describe('Controller Tests', function() {

    describe('Register_info Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRegister_info, MockCurrency, MockRoom, MockCustomer, MockMethod_payment, MockStatus_payment, MockMethod_register, MockStatus_register, MockUser, MockReservation;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRegister_info = jasmine.createSpy('MockRegister_info');
            MockCurrency = jasmine.createSpy('MockCurrency');
            MockRoom = jasmine.createSpy('MockRoom');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockMethod_payment = jasmine.createSpy('MockMethod_payment');
            MockStatus_payment = jasmine.createSpy('MockStatus_payment');
            MockMethod_register = jasmine.createSpy('MockMethod_register');
            MockStatus_register = jasmine.createSpy('MockStatus_register');
            MockUser = jasmine.createSpy('MockUser');
            MockReservation = jasmine.createSpy('MockReservation');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Register_info': MockRegister_info,
                'Currency': MockCurrency,
                'Room': MockRoom,
                'Customer': MockCustomer,
                'Method_payment': MockMethod_payment,
                'Status_payment': MockStatus_payment,
                'Method_register': MockMethod_register,
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
