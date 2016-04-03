'use strict';

angular.module('hotelApp').controller('UserManagementDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'User', 
        function($scope, $stateParams, $uibModalInstance, entity, User) {
    	$scope.warning="Mật khẩu mặc định : 123456"
        $scope.user = entity;
        $scope.authorities = ["ROLE_USER", "ROLE_ADMIN"];
        var onSaveSuccess = function (result) {
            $scope.isSaving = false;
            $uibModalInstance.close(result);
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.user.id != null) {
            	console.log($scope.user);
                User.update($scope.user, onSaveSuccess, onSaveError);
            } else {
                $scope.user.langKey = 'en';
                console.log($scope.user);
                User.save($scope.user, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
