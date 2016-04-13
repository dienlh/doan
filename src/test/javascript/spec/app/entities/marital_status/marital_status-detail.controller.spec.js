'use strict';

describe('Controller Tests', function() {

    describe('Marital_status Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockMarital_status, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockMarital_status = jasmine.createSpy('MockMarital_status');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Marital_status': MockMarital_status,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("Marital_statusDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelApp:marital_statusUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
