'use strict';

describe('Controller Tests', function() {

    describe('Services Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockServices, MockCurrency, MockStatus_service, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockServices = jasmine.createSpy('MockServices');
            MockCurrency = jasmine.createSpy('MockCurrency');
            MockStatus_service = jasmine.createSpy('MockStatus_service');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Services': MockServices,
                'Currency': MockCurrency,
                'Status_service': MockStatus_service,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("ServicesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelApp:servicesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
