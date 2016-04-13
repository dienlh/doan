'use strict';

describe('Controller Tests', function() {

    describe('Status_reservation Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockStatus_reservation, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockStatus_reservation = jasmine.createSpy('MockStatus_reservation');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Status_reservation': MockStatus_reservation,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("Status_reservationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelApp:status_reservationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
