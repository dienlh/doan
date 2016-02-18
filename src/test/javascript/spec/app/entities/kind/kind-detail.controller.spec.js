'use strict';

describe('Controller Tests', function() {

    describe('Kind Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockKind, MockPolicy, MockEvent, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockKind = jasmine.createSpy('MockKind');
            MockPolicy = jasmine.createSpy('MockPolicy');
            MockEvent = jasmine.createSpy('MockEvent');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Kind': MockKind,
                'Policy': MockPolicy,
                'Event': MockEvent,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("KindDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelApp:kindUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
