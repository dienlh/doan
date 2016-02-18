'use strict';

describe('Controller Tests', function() {

    describe('Profile Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProfile, MockPosition, MockDepartment, MockCurrency, MockStatus_profile, MockUser, MockEmployee;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProfile = jasmine.createSpy('MockProfile');
            MockPosition = jasmine.createSpy('MockPosition');
            MockDepartment = jasmine.createSpy('MockDepartment');
            MockCurrency = jasmine.createSpy('MockCurrency');
            MockStatus_profile = jasmine.createSpy('MockStatus_profile');
            MockUser = jasmine.createSpy('MockUser');
            MockEmployee = jasmine.createSpy('MockEmployee');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Profile': MockProfile,
                'Position': MockPosition,
                'Department': MockDepartment,
                'Currency': MockCurrency,
                'Status_profile': MockStatus_profile,
                'User': MockUser,
                'Employee': MockEmployee
            };
            createController = function() {
                $injector.get('$controller')("ProfileDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelApp:profileUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
