'use strict';

angular.module('hotelApp').controller('RoomDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Room', 'Type_room', 'Currency', 'Images', 'Status_room', 'User', 'Amenity',
        function($scope, $stateParams, $uibModalInstance, entity, Room, Type_room, Currency, Images, Status_room, User, Amenity) {

        $scope.room = entity;
        $scope.type_rooms = Type_room.query();
        $scope.currencys = Currency.query();
        $scope.imagess = Images.query();
        $scope.status_rooms = Status_room.query();
        $scope.users = User.query();
        $scope.amenitys = Amenity.query();
        $scope.load = function(id) {
            Room.get({id : id}, function(result) {
                $scope.room = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:roomUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.room.id != null) {
                Room.update($scope.room, onSaveSuccess, onSaveError);
            } else {
                Room.save($scope.room, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForCreate_date = {};

        $scope.datePickerForCreate_date.status = {
            opened: false
        };

        $scope.datePickerForCreate_dateOpen = function($event) {
            $scope.datePickerForCreate_date.status.opened = true;
        };
        $scope.datePickerForLast_modified_date = {};

        $scope.datePickerForLast_modified_date.status = {
            opened: false
        };

        $scope.datePickerForLast_modified_dateOpen = function($event) {
            $scope.datePickerForLast_modified_date.status.opened = true;
        };
}]);
