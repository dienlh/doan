'use strict';

describe('Controller Tests', function() {

    describe('Profile Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProfile, MockPosition, MockDepartment, MockStatus_profile, MockEmployee, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProfile = jasmine.createSpy('MockProfile');
            MockPosition = jasmine.createSpy('MockPosition');
            MockDepartment = jasmine.createSpy('MockDepartment');
            MockStatus_profile = jasmine.createSpy('MockStatus_profile');
            MockEmployee = jasmine.createSpy('MockEmployee');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Profile': MockProfile,
                'Position': MockPosition,
                'Department': MockDepartment,
                'Status_profile': MockStatus_profile,
                'Employee': MockEmployee,
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
