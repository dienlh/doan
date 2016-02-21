'use strict';

angular.module('hotelApp').controller('KindDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Kind', 'Policy', 'Event', 'User',
        function($scope, $stateParams, $uibModalInstance, entity, Kind, Policy, Event, User) {

        $scope.kind = entity;
        $scope.policys = Policy.query();
        $scope.events = Event.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            Kind.get({id : id}, function(result) {
                $scope.kind = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('hotelApp:kindUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.kind.id != null) {
                Kind.update($scope.kind, onSaveSuccess, onSaveError);
            } else {
                Kind.save($scope.kind, onSaveSuccess, onSaveError);
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
