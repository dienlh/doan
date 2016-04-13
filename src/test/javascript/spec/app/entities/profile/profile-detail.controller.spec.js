'use strict';

describe('Controller Tests', function() {

    describe('Profile Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProfile, MockCurrency, MockPosition, MockDepartment, MockEmployee, MockStatus_profile, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProfile = jasmine.createSpy('MockProfile');
            MockCurrency = jasmine.createSpy('MockCurrency');
            MockPosition = jasmine.createSpy('MockPosition');
            MockDepartment = jasmine.createSpy('MockDepartment');
            MockEmployee = jasmine.createSpy('MockEmployee');
            MockStatus_profile = jasmine.createSpy('MockStatus_profile');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Profile': MockProfile,
                'Currency': MockCurrency,
                'Position': MockPosition,
                'Department': MockDepartment,
                'Employee': MockEmployee,
                'Status_profile': MockStatus_profile,
                'User': MockUser
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
