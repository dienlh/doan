'use strict';

describe('Controller Tests', function() {

    describe('Policy Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPolicy, MockStatus_pe, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPolicy = jasmine.createSpy('MockPolicy');
            MockStatus_pe = jasmine.createSpy('MockStatus_pe');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Policy': MockPolicy,
                'Status_pe': MockStatus_pe,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("PolicyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelApp:policyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
