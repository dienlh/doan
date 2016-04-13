'use strict';

angular.module('hotelApp').controller('Type_roomDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Type_room', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Type_room, User) {

        $scope.type_room = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            Type_room.get({id : id}, function(result) {
                $scope.type_room = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:type_roomUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.type_room.id != null) {
                Type_room.update($scope.type_room, onSaveSuccess, onSaveError);
            } else {
                Type_room.save($scope.type_room, onSaveSuccess, onSaveError);
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
}]);
