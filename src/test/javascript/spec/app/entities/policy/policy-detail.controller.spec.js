'use strict';

describe('Controller Tests', function() {

    describe('Policy Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPolicy, MockKind_of_policy, MockType_policy, MockStatus_policy, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPolicy = jasmine.createSpy('MockPolicy');
            MockKind_of_policy = jasmine.createSpy('MockKind_of_policy');
            MockType_policy = jasmine.createSpy('MockType_policy');
            MockStatus_policy = jasmine.createSpy('MockStatus_policy');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Policy': MockPolicy,
                'Kind_of_policy': MockKind_of_policy,
                'Type_policy': MockType_policy,
                'Status_policy': MockStatus_policy,
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
