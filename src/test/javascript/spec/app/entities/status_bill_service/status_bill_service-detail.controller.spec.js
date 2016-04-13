'use strict';

describe('Controller Tests', function() {

    describe('Status_bill_service Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockStatus_bill_service, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockStatus_bill_service = jasmine.createSpy('MockStatus_bill_service');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Status_bill_service': MockStatus_bill_service,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("Status_bill_serviceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelApp:status_bill_serviceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
