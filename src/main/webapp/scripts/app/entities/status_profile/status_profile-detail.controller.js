'use strict';

angular.module('hotelApp')
    .controller('Status_profileDetailController', function ($scope, $rootScope, $stateParams, entity, Status_profile, User) {
        $scope.status_profile = entity;
        $scope.load = function (id) {
            Status_profile.get({id: id}, function(result) {
                $scope.status_profile = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:status_profileUpdate', function(event, result) {
            $scope.status_profile = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
