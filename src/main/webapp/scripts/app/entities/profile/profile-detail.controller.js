'use strict';

angular.module('hotelApp')
    .controller('ProfileDetailController', function ($scope, $rootScope, $stateParams, entity, Profile, Position, Department, Currency, Status_profile, User, Employee) {
        $scope.profile = entity;
        $scope.load = function (id) {
            Profile.get({id: id}, function(result) {
                $scope.profile = result;
            });
        };
        var unsubscribe = $rootScope.$on('hotelApp:profileUpdate', function(event, result) {
            $scope.profile = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
