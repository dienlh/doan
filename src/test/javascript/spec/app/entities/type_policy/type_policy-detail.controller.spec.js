'use strict';

describe('Controller Tests', function() {

    describe('Type_policy Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockType_policy, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockType_policy = jasmine.createSpy('MockType_policy');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Type_policy': MockType_policy,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("Type_policyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelApp:type_policyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
