'use strict';

describe('Controller Tests', function() {

    describe('Method_booking Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockMethod_booking, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockMethod_booking = jasmine.createSpy('MockMethod_booking');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Method_booking': MockMethod_booking,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("Method_bookingDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelApp:method_bookingUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
