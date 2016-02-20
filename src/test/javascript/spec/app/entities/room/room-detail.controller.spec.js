'use strict';

describe('Controller Tests', function() {

    describe('Room Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRoom, MockKind, MockImage, MockFuniture, MockCurrency, MockStatus_room, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRoom = jasmine.createSpy('MockRoom');
            MockKind = jasmine.createSpy('MockKind');
            MockImage = jasmine.createSpy('MockImage');
            MockFuniture = jasmine.createSpy('MockFuniture');
            MockCurrency = jasmine.createSpy('MockCurrency');
            MockStatus_room = jasmine.createSpy('MockStatus_room');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Room': MockRoom,
                'Kind': MockKind,
                'Image': MockImage,
                'Funiture': MockFuniture,
                'Currency': MockCurrency,
                'Status_room': MockStatus_room,
                'User': MockUser
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
