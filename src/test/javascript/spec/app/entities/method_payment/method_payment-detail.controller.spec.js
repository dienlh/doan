'use strict';

describe('Controller Tests', function() {

    describe('Method_payment Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockMethod_payment, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockMethod_payment = jasmine.createSpy('MockMethod_payment');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Method_payment': MockMethod_payment,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("Method_paymentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelApp:method_paymentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
