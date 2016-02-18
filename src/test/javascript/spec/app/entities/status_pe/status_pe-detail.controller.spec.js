'use strict';

describe('Controller Tests', function() {

    describe('Status_pe Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockStatus_pe;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockStatus_pe = jasmine.createSpy('MockStatus_pe');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Status_pe': MockStatus_pe
            };
            createController = function() {
                $injector.get('$controller')("Status_peDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelApp:status_peUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
