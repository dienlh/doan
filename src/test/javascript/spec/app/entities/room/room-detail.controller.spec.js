'use strict';

describe('Controller Tests', function() {

    describe('Room Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRoom, MockType_room, MockCurrency, MockImages, MockStatus_room, MockUser, MockAmenity;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRoom = jasmine.createSpy('MockRoom');
            MockType_room = jasmine.createSpy('MockType_room');
            MockCurrency = jasmine.createSpy('MockCurrency');
            MockImages = jasmine.createSpy('MockImages');
            MockStatus_room = jasmine.createSpy('MockStatus_room');
            MockUser = jasmine.createSpy('MockUser');
            MockAmenity = jasmine.createSpy('MockAmenity');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Room': MockRoom,
                'Type_room': MockType_room,
                'Currency': MockCurrency,
                'Images': MockImages,
                'Status_room': MockStatus_room,
                'User': MockUser,
                'Amenity': MockAmenity
            };
            createController = function() {
                $injector.get('$controller')("RoomDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hotelApp:roomUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
