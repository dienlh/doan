'use strict';

describe('Controller Tests', function() {

    describe('Employee Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockEmployee, MockGender, MockEthnic, MockReligion, MockJob, MockEducation_level, MockMajor, MockSchool, MockMarital_status, MockCame_component, MockBank, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockEmployee = jasmine.createSpy('MockEmployee');
            MockGender = jasmine.createSpy('MockGender');
            MockEthnic = jasmine.createSpy('MockEthnic');
            MockReligion = jasmine.createSpy('MockReligion');
            MockJob = jasmine.createSpy('MockJob');
            MockEducation_level = jasmine.createSpy('MockEducation_level');
            MockMajor = jasmine.createSpy('MockMajor');
            MockSchool = jasmine.createSpy('MockSchool');
            MockMarital_status = jasmine.createSpy('MockMarital_status');
            MockCame_component = jasmine.createSpy('MockCame_component');
            MockBank = jasmine.createSpy('MockBank');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Employee': MockEmployee,
                'Gender': MockGender,
                'Ethnic': MockEthnic,
                'Religion': MockReligion,
                'Job': MockJob,
                'Education_level': MockEducation_level,
                'Major': MockMajor,
                'School': MockSchool,
                'Marital_status': MockMarital_status,
                'Came_component': MockCame_component,
                'Bank': MockBank,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("EmployeeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelApp:employeeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
