'use strict';

describe('Controller Tests', function() {

    describe('Status_profile Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockStatus_profile;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockStatus_profile = jasmine.createSpy('MockStatus_profile');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Status_profile': MockStatus_profile
            };
            createController = function() {
                $injector.get('$controller')("Status_profileDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelApp:status_profileUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
