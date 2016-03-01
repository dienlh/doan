'use strict';

describe('Controller Tests', function() {

    describe('Kind_of_policy Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockKind_of_policy, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockKind_of_policy = jasmine.createSpy('MockKind_of_policy');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Kind_of_policy': MockKind_of_policy,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("Kind_of_policyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelApp:kind_of_policyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
